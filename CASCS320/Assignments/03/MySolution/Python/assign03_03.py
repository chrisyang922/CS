####################################################
#!/usr/bin/env python3
####################################################
import sys
sys.path.append('./../../../../mypylib')
from mypylib_cls import *
####################################################
#
# HX-2023-06-12: 10 points
# Please *translate* into Python the SML solution
# to word_neighbors (which is the one for Assign03-02)
#

AB = "abcdefghijklmnopqrstuvwxyz"
characterList = ['a','b','c','d','e','f','g','h','i','j','k','l',
                 'm','n','o','p','q','r','s','t','u','v','w','x','y','z']

def word_neighbors(word):
    """
    Note that this function should returns a fnlist
    (not a pylist)
    Your implementation should be combinator-based very
    much like the posted solution.
    """
    lst = string_imap_fnlist(word, lambda r0, x0: (list(map(lambda x: neighbors_helper(word,r0,x), characterList))))
    lst = pylist_filter_pylist(pylist_concat(lst), lambda x: x!= word)
    lst = fnlist_make_pylist(lst)
    return lst

def neighbors_helper(word, i0, c0):
    emptyString = ""
    def string_tabulate(x):
        if x == i0:
            return c0
        else:
           return word[x]
    return emptyString.join(string_tabulate(x) for x in range(len(word)))



#
####################################################
