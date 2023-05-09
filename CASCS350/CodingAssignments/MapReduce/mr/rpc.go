package mr

//
// RPC definitions.
//
// remember to capitalize all names.
//

import (
	"os"
	"strconv"
)

type EmptyArs struct {
}

type EmptyReply struct {
}

// Universal Task structure
type MapTask struct {
	Filename       string // Filename = key
	NumReduce      int    // Number of reduce tasks, used to figure out number of buckets
	CurrentMapTask int    //The current maptask number
	CompleteMap    bool   //Check whether map task is done
	AssignAllTask  bool
}

type ReduceTask struct {
	MapFiles        []string //The current set of files for reduce
	CompleteReduce  bool     //Check whether request task is done
	ReduceFile      int      //the reduce task number
	AssignAllReduce bool
}

type WhatTask struct {
	Type string //Is it map or reduce task?
}
type ReduceCompleted struct {
	IsReduceDone bool
}

type MapCompleted struct {
	IsMapDone bool
}

// Cook up a unique-ish UNIX-domain socket name
// in /var/tmp, for the coordinator.
// Can't use the current directory since
// Athena AFS doesn't support UNIX-domain sockets.
func coordinatorSock() string {
	s := "/var/tmp/824-mr-"
	s += strconv.Itoa(os.Getuid())
	return s
}
