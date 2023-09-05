(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
//
HX-2023-06-20: 20 points
Please implement the following function
that enumerates all the pairs (i, j) of natural
numbers satisfying $i <= j$; a pair (i1, j1) must
be enumerated ahead of another pair (i2, j2) if the
following condition holds:
  i1*i1*i1 + j1*j1*j1 < i2*i2*i2 + j2*j2*j2
//
val
theNatPairs_cubesum: (int * int) stream = fn () => ...
//
*)

(* ****** ****** *)

fun computeSum(i0: int, i1: int): int = 
  i0 * i0 * i0 + i1 * i1 * i1

fun lowerPair((i1, j1), (i2, j2)): bool =
  if computeSum(i1, j1) <= computeSum(i2, j2) then true
  else false

fun generateStream(i0: int, j0: int): (int * int) stream = fn() =>
  strcon_cons((i0, j0), stream_tabulate(1, fn x => (i0, j0 + x + 1)))

fun first(fxs: (int * int) stream) = 
  strcon_head(fxs())

fun last(fxs: (int * int) stream) = 
  strcon_tail(fxs())

val theNatPairs_cubesum: (int * int) stream = fn() =>
  let
    fun cubesum_helper(i0: int, insertStream: (int * int) stream): (int * int) stream = fn() =>
      strcon_cons(first(insertStream), stream_merge2(last(insertStream), cubesum_helper(i0+1, generateStream(i0 + 1, i0 + 1)), lowerPair))
  in
    cubesum_helper(1,generateStream(1,1))()
  end









(* end of [CS320-2023-Sum1-assign04-02.sml] *)
