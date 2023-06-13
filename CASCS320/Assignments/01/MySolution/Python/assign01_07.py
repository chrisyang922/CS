####################################################
import sys
sys.path.append('./../..')
from assign01_lib import *
####################################################
print("[import ./../../assign01_lib.py] is done!")
####################################################
#
# Please implement (20 points)
# mylist_mergesort (see list_mergesort in assign01-lib.sml)
#
####################################################


def mylist_mergesort(xs):
    if mylist_size(xs) <= 0:
        return mylist_nil()
    else:
        xone = xs.get_cons1()
        xlist = xs.get_cons2()
        if mylist_size(xlist) <= 0:
            return mylist_cons(xone,mylist_nil())
        else:
            xtwo = xlist.get_cons1()
            xlistlist = xlist.get_cons2()
            ys, zs = split(xlistlist)
            return merge(mylist_mergesort(mylist_cons(xone, ys)), mylist_mergesort(mylist_cons(xtwo, zs)))


def merge(ys, zs):
    if mylist_size(ys) <= 0:
        return zs
    else:
        yone = ys.get_cons1()
        ylist = ys.get_cons2()
        if mylist_size(zs) <= 0:
            return mylist_cons(yone, ylist)
        else:
            zone = zs.get_cons1()
            zlist = zs.get_cons2()
            if yone <= zone:
                return mylist_cons(yone, merge(ylist, mylist_cons(zone, zlist)))
            else:
                return mylist_cons(zone, merge(mylist_cons(yone, ylist), zlist))
        
def split(xs):
    if mylist_size(xs) <= 0:
        return (mylist_nil(), mylist_nil())
    else:
        xone = xs.get_cons1()
        xlist = xs.get_cons2()
        if mylist_size(xlist) <= 0:
            return (mylist_cons(xone, mylist_nil()), mylist_nil())
        else:
            xtwo = xlist.get_cons1()
            xlistlist = xlist.get_cons2()
            ys, zs = split(xlistlist)
            return (mylist_cons(xone,ys), mylist_cons(xtwo,zs))


xs = mylist_nil()
xs = mylist_cons(2, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(1, xs)
xs = mylist_cons(3, xs)
xs = mylist_cons(4, xs)
xs = mylist_cons(2, xs)
xs = mylist_cons(1, xs)
print("xs = ",end=''); mylist_print(xs); print();
print(type(xs.get_cons2()))
ys = mylist_mergesort(xs)
print("ys = ",end=''); mylist_print(ys); print();

