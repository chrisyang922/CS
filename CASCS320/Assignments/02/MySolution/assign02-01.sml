(* ****** ****** *)
use "./../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
//
HX-2023-02-09: 10 points
//
The function find_root(f0)
finds the first root of [f0] in
the following sequence:
0, 1, -1, 2, -2, 3, -3, 4, -4, ...
*)
(*
fun
find_root(f0: int -> int): int = ...
*)
fun find_root(f0: int -> int): int =
    let
        fun root_helper(i0: int): int =
            if f0(i0) = 0 then i0
            else
                if i0 > 0 then root_helper(~1 * i0)
                else root_helper(~1 * i0 + 1)
    in
        root_helper(0)
    end


(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign02-01.sml] *)
