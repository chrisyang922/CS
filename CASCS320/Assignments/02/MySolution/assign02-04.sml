(* ****** ****** *)
use "./../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-02-10: 20 points
Given a list of integers xs,
please implement a function that find
the longest ascending subsequences of [xs].
If there are more than one such sequences,
the left most one should be returned.

fun list_longest_ascend(xs: int list): int list
*)

fun
list_extend
(xs: 'a list, x0: 'a): 'a list =
(
case xs of
  nil => [x0]
| x1 :: xs => x1 :: list_extend(xs, x0)
)

fun
list_length
(xs: 'a list): int =
list_foldl(xs, 0, fn(r, x) => r + 1)

fun list_longest_ascend(xs: int list): int list =
    let
        fun longest_ascend_helper(xs: int list, value: int list list): int list list = 
            case xs of
            [] => value
            | x1 :: xs => 
                let 
                    fun insert_value(valueList: int list list, i0: int): int list list =
                        case valueList of
                        [] => [[i0]] @ value 
                        | v1 :: valueListTwo => 
                                if list_last(v1) <= i0 then [list_extend(v1,i0)] @ insert_value(valueListTwo, x1)
                                else insert_value(valueListTwo, x1)
                            
                in
                    longest_ascend_helper(xs, insert_value(value, x1))
                end

        fun longest_list(lst: int list list): int list =
            let
                fun longest_helper(ls: int list list, longestNum: int list): int list =
                    case ls of 
                    [] => longestNum
                    | l1 :: ls => 
                        if list_length(longestNum) <= list_length(l1) then longest_helper(ls, l1)
                        else longest_helper(ls, longestNum)
            in
                longest_helper(lst, [])
            end

    in
        longest_list(longest_ascend_helper(xs, []))
    end


(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign03-04.sml] *)
