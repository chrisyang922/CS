package mr

import (
	"fmt"
	"log"
	"net"
	"net/http"
	"net/rpc"
	"os"
	"sync"
	"time"
)

type Coordinator struct {
	NumReduce        int             // Number of reduce tasks
	Files            []string        // Files for map tasks, len(Files) is number of Map tasks
	MapTasks         chan MapTask    // Channel for uncompleted map tasks
	CompletedTasks   map[string]bool // Map to check if task is completed
	Lock             sync.Mutex      // Lock for contolling shared variables
	MapTaskNumber    int             // The current Map Task number
	MapTaskCompleted bool            // Check if the Map Task is all completed

	ReduceTaskCompleted bool   //Check if the Reduce Task is all completed
	Value               int    //The reduce task number
	TaskType            string //Is it map task or reduce task

	MapTaskArray []string        //string array that keeps the files to reduce
	ReduceTasks  chan ReduceTask //Channel for reduce task
}

// Starting coordinator logic
func (c *Coordinator) Start() {
	fmt.Println("Starting Coordinator, adding Map Tasks to channel")

	// Prepare initial MapTasks and add them to the queue

	for _, file := range c.Files {
		mapTask := MapTask{
			Filename:       file,
			NumReduce:      c.NumReduce,
			CurrentMapTask: c.MapTaskNumber,
			CompleteMap:    c.MapTaskCompleted,
		}

		fmt.Println("MapTask", mapTask, "added to channel")

		c.MapTasks <- mapTask
		c.CompletedTasks["map_"+mapTask.Filename] = false
	}

	// Initial reduce task
	for j := 0; j < len(c.Files); j++ {
		intermediateFile := fmt.Sprintf("mr-%d-%d", j, 0)
		c.MapTaskArray = append(c.MapTaskArray, intermediateFile)
	}

	for i := 0; i < c.NumReduce; i++ {
		reduceTask := ReduceTask{
			CompleteReduce: c.ReduceTaskCompleted,
			ReduceFile:     c.Value,
		}
		c.ReduceTasks <- reduceTask
	}

	c.server()
}

// RPC that worker calls when idle (worker requests a map task)
func (c *Coordinator) RequestMapTask(args *EmptyArs, reply *MapTask) error {
	fmt.Println("Map task requested")

	task, _ := <-c.MapTasks // check if there are uncompleted map tasks. Keep in mind, if MapTasks is empty, this will halt
	fmt.Println("Map task found,", task.Filename)

	*reply = task
	reply.CurrentMapTask = c.MapTaskNumber
	c.MapTaskNumber++
	reply.CompleteMap = c.MapTaskCompleted
	go c.WaitForWorker(task)

	return nil
}

// RPC that worker calls when worker requests a reduce task
func (c *Coordinator) RequestReduceTask(args *EmptyArs, reply *ReduceTask) error {
	fmt.Println("Reduce task requested", c.Value)

	task, _ := <-c.ReduceTasks

	fmt.Println("Reduce task found", c.Value)

	*reply = task
	reply.MapFiles = c.MapTaskArray

	//Empty the current array
	c.MapTaskArray = c.MapTaskArray[:0]

	//plug in corresponding name of files to reduce
	for j := 0; j < len(c.Files); j++ {
		intermediateFile := fmt.Sprintf("mr-%d-%d", j, c.Value)
		c.MapTaskArray = append(c.MapTaskArray, intermediateFile)
	}
	reply.ReduceFile = c.Value
	c.Value++

	reply.CompleteReduce = c.ReduceTaskCompleted
	return nil
}

// Goroutine will wait 10 seconds and check if map task is completed or not
func (c *Coordinator) WaitForWorker(task MapTask) {
	time.Sleep(time.Second * 10)
	c.Lock.Lock()
	if c.CompletedTasks["map_"+task.Filename] == false {
		fmt.Println("Timer expired, task", task.Filename, "is not finished. Putting back in queue.")
		c.MapTasks <- task
	}
	c.Lock.Unlock()
}

// RPC for reporting a completion of a task
func (c *Coordinator) TaskCompleted(args *MapTask, reply *EmptyReply) error {
	c.Lock.Lock()
	defer c.Lock.Unlock()

	c.CompletedTasks["map_"+args.Filename] = true

	fmt.Println("Task", args, "completed")

	// If all of map tasks are completed, go to reduce phase
	// ...
	count := 0
	for _, e := range c.CompletedTasks {
		if e != true {
			count += 1
		}
	}
	if count == 0 {
		c.MapTaskCompleted = true
		c.TaskType = "reduce"
	}

	return nil
}

// Check which task to do
func (c *Coordinator) TaskToComplete(args *EmptyArs, reply *WhatTask) error {
	c.Lock.Lock()
	defer c.Lock.Unlock()
	if c.MapTaskCompleted == true {
		reply.Type = "reduce"
	} else {
		reply.Type = "map"
	}
	return nil
}

// reporting completion of a reduce
func (c *Coordinator) ReduceTaskComplete(args *ReduceTask, reply *EmptyReply) error {
	c.Lock.Lock()
	defer c.Lock.Unlock()

	if c.Value == (c.NumReduce - 1) {
		c.ReduceTaskCompleted = true
		c.TaskType = "done"

	}
	return nil
}

// start a thread that listens for RPCs from worker.go
func (c *Coordinator) server() {
	rpc.Register(c)
	rpc.HandleHTTP()
	//l, e := net.Listen("tcp", ":1234")
	sockname := coordinatorSock()
	os.Remove(sockname)
	l, e := net.Listen("unix", sockname)
	if e != nil {
		log.Fatal("listen error:", e)
	}
	go http.Serve(l, nil)
}

// main/mrcoordinator.go calls Done() periodically to find out
// if the entire job has finished.
func (c *Coordinator) Done() bool {
	ret := false

	if c.MapTaskCompleted == true {
		if c.ReduceTaskCompleted == true {
			return true
		}
	}

	return ret
}

// create a Coordinator.
// main/mrcoordinator.go calls this function.
// nReduce is the number of reduce tasks to use.
func MakeCoordinator(files []string, nReduce int) *Coordinator {
	c := Coordinator{
		NumReduce:        nReduce,
		Files:            files,
		MapTasks:         make(chan MapTask, 100),
		CompletedTasks:   make(map[string]bool),
		MapTaskNumber:    0,
		MapTaskCompleted: false,

		ReduceTaskCompleted: false,
		ReduceTasks:         make(chan ReduceTask, 100),
		Value:               0,
		TaskType:            "map",
	}

	fmt.Println("Starting coordinator")

	c.Start()

	return &c
}
