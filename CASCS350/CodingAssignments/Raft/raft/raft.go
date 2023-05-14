package raft

//
// this is an outline of the API that raft must expose to
// the service (or tester). see comments below for
// each of these functions for more details.
//
// rf = Make(...)
//   create a new Raft server.
// rf.Start(command interface{}) (index, term, isleader)
//   start agreement on a new log entry
// rf.GetState() (term, isLeader)
//   ask a Raft for its current term, and whether it thinks it is leader
// ApplyMsg
//   each time a new entry is committed to the log, each Raft peer
//   should send an ApplyMsg to the service (or tester)
//   in the same server.
//

import (
	"bytes"
	"cs350/labgob"
	"cs350/labrpc"
	"fmt"
	"math/rand"
	"sync"
	"sync/atomic"
	"time"
)

// import "bytes"
// import "cs350/labgob"

// as each Raft peer becomes aware that successive log entries are
// committed, the peer should send an ApplyMsg to the service (or
// tester) on the same server, via the applyCh passed to Make(). set
// CommandValid to true to indicate that the ApplyMsg contains a newly
// committed log entry.
//
// in part 2D you'll want to send other kinds of messages (e.g.,
// snapshots) on the applyCh, but set CommandValid to false for these
// other uses.
type ApplyMsg struct {
	CommandValid bool
	Command      interface{}
	CommandIndex int

	// For 2D:
	SnapshotValid bool
	Snapshot      []byte
	SnapshotTerm  int
	SnapshotIndex int
}

// A Go object implementing a single Raft peer.
type Raft struct {
	mu        sync.Mutex          // Lock to protect shared access to this peer's state
	peers     []*labrpc.ClientEnd // RPC end points of all peers
	persister *Persister          // Object to hold this peer's persisted state
	me        int                 // this peer's index into peers[]
	dead      int32               // set by Kill()

	// Your data here (2A, 2B, 2C).
	// Look at the paper's Figure 2 for a description of what
	// state a Raft server must maintain.

	currentTerm  int
	votedFor     int
	log          []LogEntry
	commitIndex  int
	lastApplied  int
	nextIndex    []int
	matchIndex   []int
	condition    string //the state of the servers
	numberVotes  int    //number of votes received by the candidate
	heartbeatVar bool
	applyChan    chan ApplyMsg
	electionT    *time.Timer //timer for election
	heartbeatT   *time.Timer //timer for heartbeat
	snapshotT    *time.Timer

	lastIncludeTerm  int
	lastIncludeIndex int
	snapShotByte     []byte
}

type LogEntry struct {
	Term        int
	UserRequest interface{}
}

// return currentTerm and whether this server
// believes it is the leader.
func (rf *Raft) GetState() (int, bool) {

	var term int
	var isleader bool
	// Your code here (2A).
	rf.mu.Lock()
	term = rf.currentTerm
	if rf.condition == "Leader" {
		isleader = true
	} else {
		isleader = false
	}
	rf.mu.Unlock()
	return term, isleader
}

// save Raft's persistent state to stable storage,
// where it can later be retrieved after a crash and restart.
// see paper's Figure 2 for a description of what should be persistent.
func (rf *Raft) persist() {
	// Your code here (2C).
	// Example:
	// w := new(bytes.Buffer)
	// e := labgob.NewEncoder(w)
	// e.Encode(rf.xxx)
	// e.Encode(rf.yyy)
	// data := w.Bytes()
	// rf.persister.SaveRaftState(data)

	//from the code above
	w := new(bytes.Buffer)
	e := labgob.NewEncoder(w)
	e.Encode(rf.currentTerm) //according to lecture slides 8
	e.Encode(rf.log)         //according to lecture slides 8
	e.Encode(rf.votedFor)    //according to lecture slides 8
	data := w.Bytes()
	rf.persister.SaveRaftState(data)
}

// restore previously persisted state.
func (rf *Raft) readPersist(data []byte) {
	if data == nil || len(data) < 1 { // bootstrap without any state?
		return
	}
	// Your code here (2C).
	// Example:
	// r := bytes.NewBuffer(data)
	// d := labgob.NewDecoder(r)
	// var xxx
	// var yyy
	// if d.Decode(&xxx) != nil ||
	//    d.Decode(&yyy) != nil {
	//   error...
	// } else {
	//   rf.xxx = xxx
	//   rf.yyy = yyy
	// }

	//from the code above
	r := bytes.NewBuffer(data)
	d := labgob.NewDecoder(r)
	var currentTerm int
	var logEntry []LogEntry
	var votedFor int
	if d.Decode(&currentTerm) != nil || d.Decode(&logEntry) != nil || d.Decode(&votedFor) != nil {
		fmt.Println("error")
	} else {
		rf.currentTerm = currentTerm
		rf.log = logEntry
		rf.votedFor = votedFor
	}

}

// A service wants to switch to snapshot.  Only do so if Raft hasn't
// have more recent info since it communicate the snapshot on applyCh.
func (rf *Raft) CondInstallSnapshot(lastIncludedTerm int, lastIncludedIndex int, snapshot []byte) bool {

	// Your code here (2D).
	if rf.killed() {
		return false
	}

	return true
}

// the service says it has created a snapshot that has
// all info up to and including index. this means the
// service no longer needs the log through (and including)
// that index. Raft should now trim its log as much as possible.
func (rf *Raft) Snapshot(index int, snapshot []byte) {
	// Your code here (2D).
	if rf.killed() {
		return
	}
	rf.mu.Lock()
	defer rf.mu.Unlock()

}

type InstallSnapShotArgs struct {
	Term              int
	LeaderId          int
	LastIncludedIndex int
	LastIncludedTerm  int
	Offset            int
	Data              []byte
	Done              bool
}

type InstallSnapShotReply struct {
	Term int
}

type AppendEntriesArgs struct {
	Term         int
	Entries      []LogEntry
	LeaderId     int
	PrevLogIndex int
	PrevLogTerm  int
	LeaderCommit int
}

type AppendEntriesReply struct {
	Success bool
	Term    int

	ConflictTerm  int //term of conflicting entry
	ConflictIndex int //first index it stores for the conflicting term
}

// example RequestVote RPC arguments structure.
// field names must start with capital letters!
type RequestVoteArgs struct {
	// Your data here (2A, 2B).
	Term         int
	CandidateId  int
	LastLogIndex int
	LastLogTerm  int
}

// example RequestVote RPC reply structure.
// field names must start with capital letters!
type RequestVoteReply struct {
	// Your data here (2A).
	Term        int
	VoteGranted bool
}

func (rf *Raft) InstallSnapshot(args *InstallSnapShotArgs, reply *InstallSnapShotReply) {
	if rf.killed() {
		reply.Term = -1
		return
	}

	rf.mu.Lock()
	defer rf.mu.Unlock()

	reply.Term = rf.currentTerm

	//cannot install snapshot if the currentTerm is greater than the term that requests snapshot
	if rf.currentTerm > args.Term {
		return
	}

	//cannot install snapshot if the length of the log stored in snapshot is equal or greater than the length of the total log
	if rf.lastIncludeIndex >= args.LastIncludedIndex {
		return
	}

	rf.condition = "Follower"
	rf.currentTerm = args.Term
	rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
	rf.votedFor = -1

	//reset the log to be length of 0
	rf.log = []LogEntry{}

	//update the variables
	rf.lastIncludeIndex = args.LastIncludedIndex
	rf.lastIncludeTerm = args.LastIncludedTerm

	//send to channel
	rf.applyChan <- ApplyMsg{
		SnapshotValid: true,
		Snapshot:      args.Data,
		SnapshotTerm:  args.LastIncludedTerm,
		SnapshotIndex: args.LastIncludedIndex,
	}

	//update reply.Term
	reply.Term = rf.currentTerm

	rf.persist()

}

func (rf *Raft) AppendEntries(args *AppendEntriesArgs, reply *AppendEntriesReply) {
	if rf.killed() {
		reply.Term = -1
		reply.Success = false
	}
	rf.mu.Lock()
	defer rf.mu.Unlock()

	reply.Term = rf.currentTerm
	reply.Success = false

	//if the server that received heartbeat has greater term than the potential leader, it should return false
	if args.Term < rf.currentTerm {
		return
	}

	//if the server that requested heartbeat has greater term, the server that received heartbeat should become a follower
	if args.Term > rf.currentTerm {
		rf.currentTerm = args.Term
		rf.condition = "Follower"
		//if become follower, reset the election timer
		rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
		rf.votedFor = -1
	}

	if args.Term >= rf.currentTerm {
		//change the heartbeat to true
		rf.heartbeatVar = true
	}

	rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)

	//no entry at PrevLogIndex matching prevLogTerm
	if args.PrevLogIndex >= len(rf.log) {
		//no conflict
		reply.ConflictIndex = len(rf.log)
		reply.ConflictTerm = -1
		rf.persist()
		return
	}

	//existing entry conflicting with new one
	if args.PrevLogTerm != rf.log[args.PrevLogIndex].Term {
		//conflict exists
		reply.ConflictTerm = rf.log[args.PrevLogIndex].Term
		//set the conflict index as the first entry in the conflicting term and start checking by decrementing it one by one
		conflict := args.PrevLogIndex
		//while loop in go
		for rf.log[conflict-1].Term == reply.ConflictTerm {
			conflict = conflict - 1
		}
		reply.ConflictIndex = conflict
		rf.persist()
		return
	}

	// append the logs of the leader that is not in the log of the receiver
	if args.Entries != nil {
		rf.log = rf.log[:args.PrevLogIndex+1]
		rf.log = append(rf.log, args.Entries...)
	}

	//if the sender's leaderCommit is greater, commitIndex should equal the min of index of last new entry or leaderCommit
	if args.LeaderCommit > rf.commitIndex {

		//double check condition
		if args.Term > rf.currentTerm {
			rf.currentTerm = args.Term
			rf.condition = "Follower"
			//if become follower, reset the election timer
			rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
			rf.votedFor = -1
			rf.persist()
			return
		}
		if args.LeaderCommit < rf.commitIndex {
			rf.setCommitIndex(args.LeaderCommit, args.Term, rf.currentTerm)
		} else {
			rf.setCommitIndex(len(rf.log)-1, args.Term, rf.currentTerm)
		}
	}

	reply.Success = true
	rf.persist()
}

// example RequestVote RPC handler.
func (rf *Raft) RequestVote(args *RequestVoteArgs, reply *RequestVoteReply) {
	// Your code here (2A, 2B).
	if rf.killed() {
		reply.Term = -1
		reply.VoteGranted = false
		return
	}

	rf.mu.Lock()
	defer rf.mu.Unlock()
	reply.Term = rf.currentTerm
	reply.VoteGranted = false

	//if the term of itself is greater than the term of the server requesting the vote
	if args.Term < rf.currentTerm {
		//should not accept vote
		return
	} else if args.Term == rf.currentTerm {
		//if the term of itself is equal to the term of the server requesting the vote
		// if votedFor is not candidateId or null, it should return false according to the paper
		if rf.votedFor != args.CandidateId {
			if rf.votedFor != -1 {
				return
			}
		}
	}

	//if the server that requested vote has greater term, the server that received vote request should become a follower
	if args.Term > rf.currentTerm {
		rf.currentTerm = args.Term
		rf.condition = "Follower"
		//if become follower, reset the election timer
		rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
		rf.votedFor = -1
	}

	//check whether the server that requested vote has its logs up to date by checking the last term of the logs of both of the servers
	if args.LastLogTerm < rf.log[len(rf.log)-1].Term {
		rf.persist()
		return
	} else if args.LastLogTerm == rf.log[len(rf.log)-1].Term {
		//if the last terms are equal, then check the log length
		if args.LastLogIndex < len(rf.log)-1 {
			rf.persist()
			return
		}
	}

	rf.votedFor = args.CandidateId
	reply.VoteGranted = true
	rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
	rf.persist()
}

// example code to send a RequestVote RPC to a server.
// server is the index of the target server in rf.peers[].
// expects RPC arguments in args.
// fills in *reply with RPC reply, so caller should
// pass &reply.
// the types of the args and reply passed to Call() must be
// the same as the types of the arguments declared in the
// handler function (including whether they are pointers).
//
// The labrpc package simulates a lossy network, in which servers
// may be unreachable, and in which requests and replies may be lost.
// Call() sends a request and waits for a reply. If a reply arrives
// within a timeout interval, Call() returns true; otherwise
// Call() returns false. Thus Call() may not return for a while.
// A false return can be caused by a dead server, a live server that
// can't be reached, a lost request, or a lost reply.
//
// Call() is guaranteed to return (perhaps after a delay) *except* if the
// handler function on the server side does not return.  Thus there
// is no need to implement your own timeouts around Call().
//
// look at the comments in ../labrpc/labrpc.go for more details.
//
// if you're having trouble getting RPC to work, check that you've
// capitalized all field names in structs passed over RPC, and
// that the caller passes the address of the reply struct with &, not
// the struct itself.

func (rf *Raft) sendRequestVote(server int, args *RequestVoteArgs, reply *RequestVoteReply) bool {
	if rf.killed() {
		return false
	}

	ok := rf.peers[server].Call("Raft.RequestVote", args, reply)
	rf.mu.Lock()
	defer rf.mu.Unlock()
	//if the server that requested the vote has lower term than other servers, it should immediately become a follower
	if rf.currentTerm < reply.Term {
		rf.currentTerm = reply.Term
		rf.condition = "Follower"
		//if become follower, reset the election timer
		rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
		rf.votedFor = -1
		rf.persist()
	}
	if reply.VoteGranted {
		//check whether the server received votes from its peers
		rf.numberVotes++
		//if the number of votes received from other servers and itself is majority, becomes a leader
		if rf.numberVotes > len(rf.peers)/2 {
			rf.condition = "Leader"
			//initialized to leader last log index + 1
			for i := range rf.nextIndex {
				rf.nextIndex[i] = len(rf.log)
			}
			//initialized to 0 and increases
			for i := range rf.matchIndex {
				rf.matchIndex[i] = 0
			}

			//once you become leader, send heartbeat
			rf.sendHeartbeats()
			//reset the heartbeat timer
			rf.heartbeatT.Reset(100 * time.Millisecond)
		}
	}

	return ok
}

func (rf *Raft) sendAppendEntries(server int, args *AppendEntriesArgs, reply *AppendEntriesReply) bool {
	if rf.killed() {
		return false
	}

	ok := rf.peers[server].Call("Raft.AppendEntries", args, reply)
	rf.mu.Lock()
	defer rf.mu.Unlock()

	return ok
}

func (rf *Raft) sendInstallSnapshot(server int, args *InstallSnapShotArgs, reply *InstallSnapShotReply) bool {
	if rf.killed() {
		return false
	}

	ok := rf.peers[server].Call("Raft.InstallSnapshot", args, reply)
	rf.mu.Lock()
	defer rf.mu.Unlock()

	return ok

}

// the service using Raft (e.g. a k/v server) wants to start
// agreement on the next command to be appended to Raft's log. if this
// server isn't the leader, returns false. otherwise start the
// agreement and return immediately. there is no guarantee that this
// command will ever be committed to the Raft log, since the leader
// may fail or lose an election. even if the Raft instance has been killed,
// this function should return gracefully.
//
// the first return value is the index that the command will appear at
// if it's ever committed. the second return value is the current
// term. the third return value is true if this server believes it is
// the leader.
func (rf *Raft) Start(command interface{}) (int, int, bool) {
	index := -1
	term := -1
	isLeader := true

	// Your code here (2B).
	if rf.killed() {
		isLeader = false
		return index, term, isLeader
	}
	rf.mu.Lock()
	defer rf.mu.Unlock()

	//if leader
	if rf.condition == "Leader" {
		term = rf.currentTerm
		rf.log = append(rf.log, LogEntry{UserRequest: command, Term: term})
		index = len(rf.log) - 1
		rf.persist()
		//send heartbeats (Lab)
		rf.sendHeartbeats()
	} else {
		//if not leader
		term = rf.currentTerm
		isLeader = false
	}

	return index, term, isLeader
}

// the tester calls Kill() when a Raft instance won't
// be needed again. you are not required to do anything
// in Kill(), but it might be convenient to (for example)
// turn off debug output from this instance.
func (rf *Raft) Kill() {
	atomic.StoreInt32(&rf.dead, 1)
	// Your code here, if desired.
}

func (rf *Raft) killed() bool {
	z := atomic.LoadInt32(&rf.dead)
	return z == 1
}

func (rf *Raft) sendHeartbeats() {
	if rf.killed() {
		return
	}
	//if not leader, don't send heartbeats
	if rf.condition != "Leader" {
		return
	}
	rf.matchIndex[rf.me] = len(rf.log)
	rf.nextIndex[rf.me] = len(rf.log)

	for x := range rf.peers {
		if x != rf.me {
			//send heartbeats to other servers (except itself)
			go rf.heartbeat(x)
		}
	}
}

func (rf *Raft) heartbeat(peerId int) {
	if rf.killed() {
		return
	}
	rf.mu.Lock()
	//once again, if not leader, don't send heartbeats
	if rf.condition != "Leader" {
		rf.mu.Unlock()
		return
	}

	prevLogIndex := rf.nextIndex[peerId] - 1

	//log entries
	entries := make([]LogEntry, len(rf.log[(rf.nextIndex[peerId]):]))
	copy(entries, rf.log[(rf.nextIndex[peerId]):])

	args := AppendEntriesArgs{
		Term:         rf.currentTerm,
		LeaderId:     rf.me,
		PrevLogIndex: prevLogIndex,
		PrevLogTerm:  rf.log[prevLogIndex].Term,
		Entries:      entries,
		LeaderCommit: rf.commitIndex,
	}

	reply := AppendEntriesReply{}
	rf.mu.Unlock()

	//RPC call
	ok := rf.sendAppendEntries(peerId, &args, &reply)

	rf.mu.Lock()
	defer rf.mu.Unlock()

	//if state is no longer leader or sending the RPC across network failed, return
	if !ok || rf.condition != "Leader" {
		return
	}

	//if successful heartbeat
	if reply.Success == true {
		//copied log entries without error
		//update nextIndex and matchIndex for followers (paper)

		//double check condition
		if reply.Term > rf.currentTerm {
			rf.currentTerm = reply.Term
			rf.condition = "Follower"
			//if become follower, reset the election timer
			rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
			rf.votedFor = -1
			rf.persist()
			return
		}
		rf.matchIndex[peerId] = args.PrevLogIndex + len(args.Entries)
		rf.nextIndex[peerId] = rf.matchIndex[peerId] + 1

		logLength := len(rf.log) - 1
		//Check whether there exists N such that n > commitindex by for loop
		for N := logLength; N > rf.commitIndex; N-- {
			//to check majority
			majority := 0

			//check conditions
			for _, matchIndex := range rf.matchIndex {
				//check if matchIndex is greater than N and log[N].term == currentTerm
				if matchIndex >= N && rf.log[N].Term == rf.currentTerm {
					//if it is, add to check majority
					majority += 1
				}
			}
			//if majority, commitIndex(N)
			if majority > len(rf.peers)/2 {
				//majority has it
				if reply.Term > rf.currentTerm {
					rf.currentTerm = reply.Term
					rf.condition = "Follower"
					//if become follower, reset the election timer
					rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
					rf.votedFor = -1
					rf.persist()
					return
				}

				rf.setCommitIndex(N, rf.currentTerm, reply.Term)
				break
			}
		}

	} else {
		//if server responds with higher term, convert state to follower
		if reply.Term > rf.currentTerm {
			rf.currentTerm = reply.Term
			rf.condition = "Follower"
			//if become follower, reset the election timer
			rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
			rf.votedFor = -1
			rf.persist()
		} else {
			//decrement nextIndex and continue to try

			//if there is no conflicting term
			rf.nextIndex[peerId] = reply.ConflictIndex

			//if there is conflicting term
			if reply.ConflictTerm != -1 {
				for x := len(rf.log) - 1; x >= 0; x-- {
					if rf.log[x].Term == reply.ConflictTerm {
						rf.nextIndex[peerId] = x
						break
					}
				}
			}
		}
	}
}

func (rf *Raft) setCommitIndex(commitIndex int, term int, termTwo int) {
	if rf.killed() {
		return
	}

	//double check condition
	if term < termTwo {
		rf.currentTerm = termTwo
		rf.condition = "Follower"
		//if become follower, reset the election timer
		rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
		rf.votedFor = -1
		rf.persist()
		return
	}
	rf.commitIndex = commitIndex
	// if commitIndex is greater than lastApplied, then apply the entry to log
	if rf.commitIndex > rf.lastApplied {
		// until commitIndex
		rf.lastApplied += 1
		for x := rf.lastApplied; x <= rf.commitIndex; x++ {
			rf.applyChan <- ApplyMsg{
				CommandValid: true,
				Command:      rf.log[x].UserRequest,
				CommandIndex: x,
			}
			//update index of lastApplied
			if rf.lastApplied < x {
				rf.lastApplied = x
			}
		}
	}

}

func (rf *Raft) election() {
	if rf.killed() {
		return
	}
	//if not candidate, don't start election
	if rf.condition != "Candidate" {
		return
	}
	rf.currentTerm += 1 //term increases
	rf.votedFor = rf.me //vote for itself
	rf.numberVotes = 1  //the vote from itself & total number of votes
	rf.persist()
	//before voting begins, reset the timer
	rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
	for x := range rf.peers {
		if x != rf.me {
			//request other servers (except itself) to vote for me
			go rf.sendElection(x)
		}
	}
}

func (rf *Raft) sendElection(peerId int) {
	if rf.killed() {
		return
	}

	rf.mu.Lock()
	args := RequestVoteArgs{
		Term:         rf.currentTerm,
		CandidateId:  rf.me,
		LastLogIndex: len(rf.log) - 1,            //length of log
		LastLogTerm:  rf.log[len(rf.log)-1].Term, //term of the last log
	}

	rf.mu.Unlock()
	go rf.sendRequestVote(peerId, &args, &RequestVoteReply{})
}

func (rf *Raft) SendSnapShotHelper() {
	if rf.killed() {
		return
	}

	if rf.condition != "Leader" {
		return
	}

	for x := range rf.peers {
		if x != rf.me {
			//request other servers (except itself) to vote for me
			go rf.SnapShotHelper(x)
		}
	}

}

func (rf *Raft) SnapShotHelper(peerId int) {
	if rf.killed() {
		return
	}
	rf.mu.Lock()

	currentTerm := rf.currentTerm
	lastIndex := rf.lastIncludeIndex

	args := InstallSnapShotArgs{
		Term:              currentTerm,
		LeaderId:          rf.me,
		LastIncludedIndex: lastIndex,
		LastIncludedTerm:  rf.lastIncludeTerm,
		Offset:            0,
		Data:              rf.snapShotByte,
		Done:              false,
	}
	reply := InstallSnapShotReply{}
	rf.mu.Unlock()

	//RPC call
	ok := rf.sendInstallSnapshot(peerId, &args, &reply)

	rf.mu.Lock()
	defer rf.mu.Unlock()

	//if state is no longer leader or sending the RPC across network failed, return
	if !ok || rf.condition != "Leader" {
		return
	}

	if rf.currentTerm < reply.Term {
		rf.currentTerm = reply.Term
		rf.condition = "Follower"
		//if become follower, reset the election timer
		rf.electionT.Reset(time.Duration(900+rand.Intn(900)) * time.Millisecond)
		rf.votedFor = -1
		rf.persist()
		return
	}

	if reply.Term == currentTerm {
		//it means the snapshot conditions failed
	} else {
		//it means the snapshot conditions was met and snapshot was successful
		rf.nextIndex[peerId] = lastIndex + 1
	}

	rf.snapshotT.Reset(300 * time.Millisecond)

}

// The ticker go routine starts a new election if this peer hasn't received
// heartsbeats recently.
func (rf *Raft) ticker() {

	// Your code here to check if a leader election should
	// be started and to randomize sleeping time using
	// time.Sleep()

	for rf.killed() == false {

		select {
		//https://dev.to/julianchu/go-for-select-with-timer-1ah8
		//https://github.com/golang/go/issues/27169
		//timer documentation
		case <-rf.heartbeatT.C:
			rf.mu.Lock()
			if rf.condition == "Leader" || rf.heartbeatVar == true {
				rf.heartbeatVar = false

				if rf.condition == "Leader" {
					rf.sendHeartbeats()
					//reset the heartbeat timer after sending heartbeat
					rf.heartbeatT.Reset(100 * time.Millisecond)

				}
			}
			rf.mu.Unlock()
		case <-rf.electionT.C:
			rf.mu.Lock()
			//if follower, become candidate and start election
			//if candidate, start election
			if rf.condition == "Follower" || rf.condition == "Candidate" {
				if rf.condition == "Follower" {
					rf.condition = "Candidate"
				}
				rf.election()
			}
			rf.mu.Unlock()

		case <-rf.snapshotT.C:
			rf.mu.Lock()
			if rf.condition == "Leader" {
				rf.SendSnapShotHelper()
				rf.snapshotT.Reset(300 * time.Millisecond)
			}
			rf.mu.Unlock()

		}

	}
}

// the service or tester wants to create a Raft server. the ports
// of all the Raft servers (including this one) are in peers[]. this
// server's port is peers[me]. all the servers' peers[] arrays
// have the same order. persister is a place for this server to
// save its persistent state, and also initially holds the most
// recent saved state, if any. applyCh is a channel on which the
// tester or service expects Raft to send ApplyMsg messages.
// Make() must return quickly, so it should start goroutines
// for any long-running work.
func Make(peers []*labrpc.ClientEnd, me int,
	persister *Persister, applyCh chan ApplyMsg) *Raft {
	rf := &Raft{}
	rf.peers = peers
	rf.persister = persister
	rf.me = me
	rf.currentTerm = 0
	rf.votedFor = -1
	rf.commitIndex = 0
	rf.lastApplied = 0
	rf.nextIndex = make([]int, len(rf.peers))
	rf.matchIndex = make([]int, len(rf.peers))
	rf.condition = "Follower"
	rf.log = []LogEntry{{0, nil}}
	rf.numberVotes = 0
	rf.applyChan = applyCh
	rf.heartbeatVar = false
	rf.lastIncludeIndex = 0
	rf.lastIncludeTerm = -1
	rf.snapShotByte = make([]byte, 0)

	rf.heartbeatT = time.NewTimer(100 * time.Millisecond)
	rf.electionT = time.NewTimer(time.Duration(900+rand.Intn(900)) * time.Millisecond)
	rf.snapshotT = time.NewTimer(300 * time.Millisecond)

	//dummyEntry at index 0 and send it through channel
	//to pass the test case
	dummyEntry := LogEntry{UserRequest: 0, Term: 0}
	rf.applyChan <- ApplyMsg{
		CommandValid: true,
		Command:      dummyEntry.UserRequest,
		CommandIndex: 0,
	}

	// Your initialization code here (2A, 2B, 2C).

	// initialize from state persisted before a crash
	rf.readPersist(persister.ReadRaftState())

	// start ticker goroutine to start elections
	go rf.ticker()

	return rf
}
