(* ****** ****** *)
use "./../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
//
HX-2023-02-10: 10 points
The function list_tabulate takes an integer
[n] and a function [f] and returns a list that
equals [f(0), f(1), ..., f(n-1)]
//
list_tabulate(n: int, f: int -> 'a): 'a list
//
*)

fun list_tabulate(n: int, f: int -> 'a): 'a list =
    let
        fun tabulate_helper(index: int, n: int, f: int -> 'a, lst: 'a list): 'a list =
            if index < n then
                tabulate_helper(index + 1, n, f, lst @ [f(index)])
            else lst

    in
        tabulate_helper(0, n, f, [])
    end


(* ****** ****** *)

(* end of [CS320-2023-Spring-assign03-03.sml] *)
