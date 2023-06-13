(* ****** ****** *)

use "./../../assign01-lib.sml";

(* ****** ****** *)

(*
//
Assign01-01: 10 points
//
Please implement a function
that computes the size of an xlist DIRECTLY:
//
fun xlist_size(xs: 'a xlist): int
//
That is, you should NOT convert xlist into list
and then compute the size of the converted list
//
*)

(* ****** ****** *)

fun
xlist_size(xs: 'a xlist): int = raise NotImplemented320

fun xlist_size(xs: 'a xlist): int =
  case xs of
    xlist_nil => 0
    | xlist_cons(_, xs) => 1 + xlist_size(xs)
    | xlist_snoc(xs, _) => xlist_size(xs) + 1
    | xlist_reverse(xs) => xlist_size(xs)
    | xlist_append(xs, ys) => xlist_size(xs) + xlist_size(ys)



(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign01-01.sml] *)
