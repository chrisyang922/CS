(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-06-19: 20 points
Given a list xs, stream_permute_list(xs) returns
a stream of ALL the permutations of xs.
For instance, if xs = [1,2,3], then the returned
stream should enumerate the following 6 lists:
[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2] and [3,2,1]
//
fun
stream_permute_list(xs: 'a list): 'a list stream = ...
//
*)


(* ****** ****** *)


fun streamize(xss: 'a list list): 'a list stream = fn() =>
    case xss of
    [] => strcon_nil
    | xss1 :: xss2 => strcon_cons(xss1, streamize(xss2))

fun
list_append2
(xs: 'a list list, ys: 'a list list): 'a list list=
(
case xs of
  nil => ys
| x1 :: xs => x1 :: list_append2(xs, ys)
)

fun combineLists(lst: 'a list list): 'a list list =
    let
        fun combineTwoLists(lst1: 'a list, lst2: 'a list): 'a list =
            lst1 @ lst2

        fun helper(ls: 'a list list, ans: 'a list list): 'a list list =
            case ls of
            [] => ans
            | lst1 :: lst2 :: xs =>
                let
                    val combined = combineTwoLists(lst1, lst2)
                in
                    helper(xs, ans @ [combined])
                end
            | _ => ans

    in
        helper(lst, [])
    end

fun
list_extend2
(xs: 'a list list, x0: 'a list): 'a list list =
(
case xs of
  nil => [x0]
| x1 :: xs => x1 :: list_extend2(xs, x0)
)

fun
list_length2
(xs: 'a list list): int =
let
  fun
  loop
  (xs: 'a list list, res: int): int =
  case xs of
    nil => res
  | _ :: xs => loop(xs, res+1)
in
  loop(xs, 0)
end 

fun
list_length2
(xs: 'a list): int =
let
  fun
  loop
  (xs: 'a list, res: int): int =
  case xs of
    nil => res
  | _ :: xs => loop(xs, res+1)
in
  loop(xs, 0)
end

fun filtering(xss: 'a list list, count: int): 'a list list =
    let
        fun filtering_helper(xyy: 'a list list, c0: int, final_answer: 'a list list): 'a list list =
            case xyy of
            [] => final_answer
            | x1 :: xs => filtering_helper(xs, c0, if list_length(x1) = c0 then list_append2(final_answer, [x1]) else final_answer)
    in
        filtering_helper(xss, count, [])
    end


fun plugIntoList(returnList: int list, thingToInsert: int): int list list =
    let
        fun plug_helper(listToIterate: int list, plugVal: int, answerList: int list list, index: int) = 
            let
                fun plugItIn(lst: int list, plugVal: int, i0: int, thatValue: int, front: int list): int list list = 
                    case lst of
                    [] => if list_length(front) = 0 then [] else [front @ [plugVal]]
                    | x1 :: xs => 
                        if thatValue = i0 then [front @ [plugVal] @ [x1] @ xs]
                        else plugItIn(xs, plugVal, i0, thatValue + 1, list_append(front, [x1]))
            in
                if index = list_length(listToIterate)+1 then answerList
                else
                    plug_helper(listToIterate, plugVal, list_append2(answerList, plugItIn(listToIterate, plugVal, index,0, [])), index+1)
            end

    in
       plug_helper(returnList, thingToInsert, [], 0)
    end



fun stream_permute_list2(original: int list): int list stream = 
    let
        fun helper(xs: int list, answerList: int list list): int list list =
            let
                fun helper2(thingToInsert: int, iterateList: int list list, returnAnswerList: int list list, firstTime: int): int list list =
                    case iterateList of
                    [] => if firstTime = 0 then [[thingToInsert]] else returnAnswerList
                    | x1 :: xs => (print(Int.toString(thingToInsert) ^ "want to insert \n");helper2(thingToInsert, xs, plugIntoList(x1, thingToInsert), firstTime +1))
            in
                case xs of
                [] => answerList
                | xs1 :: xs2 => helper(xs2, helper2(xs1, answerList, answerList, 0))
            end

    in
        case original of 
        [] => streamize([])
        | x1 :: xs => streamize(helper(original, []))
    end


fun stream_permute_list(xs: 'a list): 'a list stream =
let
    
      fun permute_helper(xss: 'a list, res: 'a list list, c0: int): 'a list stream =
        case xss of
        [] => 
            let
            in
                streamize(filtering(res,c0))
            end 
        | xss1 :: xss2 => 
            let
                fun helper2(answer: 'a list list, final_answer: 'a list list): 'a list list =
                    let
                        fun helper3(value: 'a list, final_val: 'a list list, front_val: 'a list): 'a list list =
                            case value of 
                            [] => 
                                if list_length(front_val) = 0 then final_val
                                else list_extend2(final_val, front_val @ [xss1])
                            | x1 :: xs => 
                                let
                                    val yz = list_append(front_val,xss1::x1::xs)
                                in
                                    if list_length(front_val) = 0 then
                                    helper3(xs, list_append2(final_val, [xss1::x1::xs]), [x1])
                                    else helper3(xs, list_extend2(final_val, yz), list_extend(front_val, x1))
                                end
                    in
                        case answer of
                        [] => final_answer
                        | ans1 :: ans2 => helper2(ans2, helper3(ans1,res, []))
                    end
            in
                let 
                    val received = helper2(res,[[]])
                    val f = list_append2(res, received)
                in
                    permute_helper(xss2,received, c0+1)
                end
                
            end
    in
      case xs of 
      [] => streamize([[]])
      | x1 :: xs => permute_helper(xs, [[x1]],1)
    end










(* ****** ****** *)













