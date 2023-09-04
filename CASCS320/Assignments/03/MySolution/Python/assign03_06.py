####################################################
#!/usr/bin/env python3
####################################################
import sys
import queue
sys.path.append('./../../../../mypylib')
from mypylib_cls import *
####################################################
"""
//
HX-2023-06-12: 20 points
Solving the N-queen puzzle
Here is an implementation of the famous 8-queen puzzle:
https://ats-lang.sourceforge.net/DOCUMENT/INT2PROGINATS/HTML/x631.html
//
"""

"""
######
A board of size N is a tuple of length N.
######
For instance, a tuple (0, 0, 0, 0) stands
for a board of size 4 (that is, a 4x4 board)
where there are no queen pieces on the board.
######
For instance, a tuple (2, 1, 0, 0) stands
for a board of size 4 (that is, a 4x4 board)
where there are two queen pieces; the queen piece
on the 1st row is on the 2nd column; the queen piece
on the 2nd row is on the 1st column; the last two rows
contain no queen pieces.
######
This function [solve_N_queen_puzzle] should return
a stream of ALL the boards of size N that contain N
queen pieces (one on each row and on each column) such
that no queen piece on the board can catch any other ones
on the same board.
"""
####################################################
def board_safety_all(bd,N):
    return \
        int1_forall\
        (N, \
         lambda i0: board_safety_one(bd, i0))
def board_safety_one(bd, i0):
    def helper(j0):
        pi = bd[i0]
        pj = bd[j0]
        return pi != pj and abs(i0-j0) != abs(pi-pj)
    return int1_forall(i0, helper)

def gtree_bfs(nxs, fchlds):
    def helper(qnxs):
        if qnxs.empty():
            return strcon_nil()
        else:
            nx1 = qnxs.get()
            # print("gtree_bfs: helper: nx1 = ", nx1)
            for nx2 in fchlds(nx1):
                qnxs.put(nx2)
            return strcon_cons(nx1, lambda: helper(qnxs))
        # end-of-(if(qnxs.empty())-then-else)
    qnxs = queue.Queue()
    for nx1 in nxs:
        qnxs.put(nx1)
    return lambda: helper(qnxs)

def solve_N_queen_puzzle(N):
    tuple_size_N = (1,) * N
    def board_get(bd, i):
        return bd[i]
    def board_set(bd, i,j):
        if i == len(bd) - 1:
            return bd[:i] + (j,)
        else:
            return bd[:i] + (j,) + bd[i+1:]
    def safetyTestOne(i0, j0, i, j):
        return j0 != j and abs(i0 -i) != abs(j0-j)
    def safetyTestTwo(i0, j0, bd, i):
        if i >= 0:
            if safetyTestOne(i0, j0, i, board_get(bd, i)):
                return safetyTestTwo(i0, j0, bd, i-1)
            else:
                return False
        return True
    def search(bd, i, j):
        if j < N:
            test = safetyTestTwo(i, j, bd, i - 1)
            if test:
                bd1 = board_set(bd, i, j)
                if i + 1 == N:
                    return search(bd1,i,j)
                else:
                    result = search(bd1, i + 1, 0)
                    if result is not None:
                        return result
            return search(bd, i, j + 1)
        else:
            if i > 0:
                return search(bd, i - 1, board_get(bd, i - 1) + 1)
    
    def fc(nx):
        print(nx[0])
        bd, i, j = nx
        children = []

        if j < N:
            test = safetyTestTwo(i, j, bd, i - 1)
            if test:
                bd1 = board_set(bd, i, j)
                
                
                if i + 1 == N:
                    if board_safety_all(bd1,N):
                        children.append((bd1, i,j+ 1))  # Increment row index to i + 1
                    else:
                        i = i
                        j = j + 1
                        nx = (bd1, i, j)
                        yield bd1, i, j
                    
                else:
                    if board_safety_all(bd1,N):
                        children.append((bd1, i + 1, 1))
                    else:
                        i = i + 1
                        j = 1
                        nx = (bd1, i, j)
                        yield nx
                    
            else:
                    if board_safety_all(bd,N):
                        children.append((bd, i, j + 1))
                    i = i 
                    j = j + 1
                    nx = (bd, i, j)
                    yield nx
        elif i > 1:
            if board_safety_all(bd, N):
                children.append((bd, i - 1, board_get(bd, i - 1) + 1))
            else:
                i = i - 1
                j = board_get(bd,i-1) + 1
                nx = (bd, i, j)
                yield nx

            
        
        return children

                

    queenList = [(tuple_size_N,1,1)]
    solution = gtree_bfs(queenList,fc)
    

    
    return solution

theNQueenSols_10 = solve_N_queen_puzzle(10)
######################################################
theNQueenSols_16 = solve_N_queen_puzzle(16)
######################################################
theNQueenSols_20 = solve_N_queen_puzzle(20)
######################################################
fxs = theNQueenSols_16
cxs = fxs()
fxs = cxs.cons2
print(cxs.cons1)
fxs = theNQueenSols_16
for x in range(10):
    cxs = fxs()
    fxs = cxs.cons2
    print(cxs.cons1)
cxs = fxs()
fxs = cxs.cons2
print(cxs.cons1)
######################################################
fxs = theNQueenSols_20
cxs = fxs()
fxs = cxs.cons2
print(cxs.cons1)
cxs = fxs()
fxs = cxs.cons2
print(cxs.cons1)



####################################################
