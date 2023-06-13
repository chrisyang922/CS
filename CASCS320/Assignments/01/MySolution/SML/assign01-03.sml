(* ****** ****** *)

use "./../../assign01-lib.sml";

(* ****** ****** *)

(*
//
Assign01-03: 10 points
//
Please implement a function that converts a given
xlist 'xs' into another xlist 'ys' by removing the
constructor 'xlist_reverse':
//
fun xlist_remove_reverse(xs: 'a xlist): 'a xlist
//
In particular, your implementation should guarantee:
1. 'xs' and 'ys' represent the same list
2. 'ys' does NOT make any use of 'xlist_reverse'
3. 'xs' and 'ys' use the same number of 'xlist_append'
//
*)
  
(* ****** ****** *)

fun
xlist_remove_reverse
(xs: 'a xlist): 'a xlist = raise NotImplemented320

fun
nappend_afterRev(xs: 'a xlist): int =
(
case xs of
xlist_nil => 0
|
xlist_cons(_, xs) => nappend_afterRev(xs)
|
xlist_snoc(xs, _) => nappend_afterRev(xs)
|
xlist_append(xs, ys) => 1 + nappend_afterRev(xs) + nappend_afterRev(ys)
|
xlist_reverse(xs) => nappend_afterRev(xs)
)

fun add_append(xs: 'a xlist, i0: int): 'a xlist =
let
    fun help(xs: 'a xlist, i0: int): 'a xlist =
        if i0 <= 0 then xs
        else help(xlist_append(xs, xlist_nil), i0 - 1)
in
    help(xs, i0)
end

fun xlist_remove_reverse
(xs: 'a xlist): 'a xlist = 
    case xs of 
        xlist_nil => xlist_nil
        | xlist_cons(x1, xs) => xlist_cons(x1, xlist_remove_reverse(xs))
        | xlist_snoc(xs, x1) => xlist_snoc(xlist_remove_reverse(xs), x1)
        | xlist_reverse(xs) => 
            let
                val num = nappend_afterRev(xs)
                fun reverse_helper (xs: 'a list): 'a xlist =
                case xs of
                [] => xlist_nil
                | x :: xs => xlist_snoc(reverse_helper(xs),x)
            in
                add_append(reverse_helper(list_of_xlist(xs)),num)
            end
            
        | xlist_append(xs, ys) => xlist_append(xlist_remove_reverse(xs), xlist_remove_reverse(ys))




  
  


(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign01-03.sml] *)
