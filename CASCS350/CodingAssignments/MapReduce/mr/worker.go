package mr

import (
	"encoding/json"
	"fmt"
	"hash/fnv"
	"io/ioutil"
	"log"
	"net/rpc"
	"os"
	"sort"
)

// Map functions return a slice of KeyValue.
type KeyValue struct {
	Key   string
	Value string
}

var readyForReduce int = 0

type ByKey []KeyValue

// for sorting by key.
func (a ByKey) Len() int           { return len(a) }
func (a ByKey) Swap(i, j int)      { a[i], a[j] = a[j], a[i] }
func (a ByKey) Less(i, j int) bool { return a[i].Key < a[j].Key }

// use ihash(key) % NReduce to choose the reduce
// task number for each KeyValue emitted by Map.
func ihash(key string) int {
	h := fnv.New32a()
	h.Write([]byte(key))
	return int(h.Sum32() & 0x7fffffff)
}

type WorkerSt struct {
	mapf    func(string, string) []KeyValue
	reducef func(string, []string) string
}

// main/mrworker.go calls this function.
func Worker(mapf func(string, string) []KeyValue, reducef func(string, []string) string) {

	w := WorkerSt{
		mapf:    mapf,
		reducef: reducef,
	}
	w.CallTask() //Give the assignment of map or reduce to the worker

}

// function that tells what assignment the worker should do
func (w *WorkerSt) CallTask() {
	args := EmptyArs{}
	reply := WhatTask{}

	call("Coordinator.TaskToComplete", &args, &reply)
	if reply.Type == "map" {
		w.RequestMapTask() //map
	} else {
		w.RequestReduceTask() //reduce
	}
}

// Requests map task, tries to do it, and repeats
func (w *WorkerSt) RequestMapTask() {
	for {
		args := EmptyArs{}
		reply := MapTask{}

		call("Coordinator.RequestMapTask", &args, &reply)

		file, err := os.Open(reply.Filename)
		if err != nil {
			log.Fatalf("cannot open %v", reply.Filename)
		}
		content, err := ioutil.ReadAll(file)
		if err != nil {
			log.Fatalf("cannot read %v", reply.Filename)
		}
		file.Close()

		kva := w.mapf(reply.Filename, string(content))

		// store kva in multiple files according to rules described in the README
		// ...

		//divide the keyValue pairs to buckets
		buckets := make([][]KeyValue, reply.NumReduce)
		for _, kv := range kva {
			hash := ihash(kv.Key) % reply.NumReduce
			buckets[hash] = append(buckets[hash], kv)
		}

		//divide the files given
		for x := 0; x < reply.NumReduce; x++ {
			intermediateFile := fmt.Sprintf("mr-%d-%d", reply.CurrentMapTask, x)
			iFile, _ := os.Create(intermediateFile)
			enc := json.NewEncoder(iFile)
			for _, kv := range buckets[x] {
				err := enc.Encode(&kv)
				if err != nil {
					log.Fatalf("error is: ", err)
				}
			}
		}
		/*
					dec := json.NewDecoder(file)
			  		for {
			    		var kv KeyValue
			    		if err := dec.Decode(&kv); err != nil {
			      			break
			    		}
			    		kva = append(kva, kv)
			  		}
		*/

		emptyReply := EmptyReply{}
		call("Coordinator.TaskCompleted", &reply, &emptyReply)

		w.CallTask()
		return
	}

}

// Request reduce task
func (w *WorkerSt) RequestReduceTask() {
	for {
		args := EmptyArs{}
		reply := ReduceTask{}

		call("Coordinator.RequestReduceTask", &args, &reply)

		kva := []KeyValue{}

		for _, x := range reply.MapFiles {
			file, err := os.Open(x)
			if err != nil {
				log.Fatalf("err")
			}
			dec := json.NewDecoder(file)

			for {
				var kv KeyValue
				if err := dec.Decode(&kv); err != nil {
					break
				}
				kva = append(kva, kv)

			}

			file.Close()
		}
		sort.Sort(ByKey(kva))
		reduceFile := fmt.Sprintf("mr-out-%d", reply.ReduceFile)
		openFile, _ := os.Create(reduceFile)

		i := 0
		for i < len(kva) {
			j := i + 1
			for j < len(kva) && kva[j].Key == kva[i].Key {
				j++
			}
			values := []string{}
			for k := i; k < j; k++ {
				values = append(values, kva[k].Value)
			}
			output := w.reducef(kva[i].Key, values)

			// this is the correct format for each line of Reduce output.
			fmt.Fprintf(openFile, "%v %v\n", kva[i].Key, output)

			i = j
		}

		openFile.Close()
		emptyReply := EmptyReply{}
		call("Coordinator.ReduceTaskComplete", &reply, &emptyReply)

		if reply.CompleteReduce {

			return
		}
	}
}

// send an RPC request to the coordinator, wait for the response.
// usually returns true.
// returns false if something goes wrong.
func call(rpcname string, args interface{}, reply interface{}) bool {
	// c, err := rpc.DialHTTP("tcp", "127.0.0.1"+":1234")
	sockname := coordinatorSock()
	c, err := rpc.DialHTTP("unix", sockname)
	if err != nil {
		log.Fatal("dialing:", err)
	}
	defer c.Close()

	err = c.Call(rpcname, args, reply)
	if err == nil {
		return true
	}

	fmt.Println(err)
	return false
}
