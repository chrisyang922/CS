(* ****** ****** *)
use
"./../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)
(*
HX-2023-06-05: 10 point
The function [list_subsets]
returns all the subsets of a given
set (where sets are represented as lists)
//
fun
list_subsets
(xs: 'a list): 'a list list =
(
case xs of
  nil => [[]]
| x1 :: xs =>
  let
    val res = list_subsets(xs)
  in
    res @ list_map(res, fn(xs) => x1 :: xs)
  end
)
//
Please give a NON-RECURSIVE implementation of
list_subsets based on list-combinators. Note that
the order of the elements in a list representation
of a set is insignificant.
//
*)
(* ****** ****** *)

(*
val
list_subsets =
fn(xs: 'a list) => ...
*)

fun
list_map
( xs: 'apple list
, fopr: 'apple -> 'banana): 'banana list =
list_foldr(xs, [], fn(x, r) => fopr(x) :: r)

fun list_subsets(xs: 'a list): 'a list list = 
  list_foldl(xs, [[]], fn(r,x) => r @ list_map(r, fn(y) => y @ [x]))

(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign02-06.sml] *)
