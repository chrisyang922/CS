(* ****** ****** *)

use "./../../assign01-lib.sml";

(* ****** ****** *)

(*
//
Assign01-04: 10 points
//
Please recall the following question in Assign00:
Assign00-04: 10 points
Please implement a function that converts a given
string to an integer:
fun str2int(cs: string): int
In particular, it is expected that str2int(int2str(x)) = x
//
This time you are asked to implement the following
function that only turns a legal representation of an integer
into an integer. By a legal representation of an integer, we
mean a string consisting of a non-empty sequence of digits (where
the digit '0' can be a leading digit).
//
fun str2int_opt(cs: string): int option
//
*)

(* ****** ****** *)

fun
str2int_opt(cs: string): int option = raise NotImplemented320

fun str2int_opt(cs: string): int option =
  let
    fun helper(i0: int): int =
    if i0 <= 0 then 0 
    else
        if Char.ord(String.sub(cs,i0-1)) < Char.ord(#"0") then ~1 
        else if Char.ord(String.sub(cs,i0-1)) > Char.ord(#"9") then ~1 
        else 
          if helper(i0 - 1) = ~1 then ~1 
          else
            if helper(i0 - 1) = 0 andalso Char.ord(String.sub(cs, i0-1)) - Char.ord(#"0") = 0 then 0
            else 10 * helper(i0 - 1) + Char.ord(String.sub(cs, i0-1)) - Char.ord(#"0")
    val x = helper(String.size(cs))


  in
    if String.size(cs) = 0 then NONE
    else 
      if x = ~1 then NONE else SOME(x)
  end





  
(*
fun
str2int_opt
(cs: string): int option =
let
  fun help(i0: int): int option=
    if i0 <= 0 then NONE else
    SOME (10 * SOME(help(i0-1)) + Char.ord(String.sub(cs, i0-1)) - Char.ord(#"0"))
in
  help(String.size(cs))
end
*)
(*
fun str2int_opt(cs: string) int option = 
let
  fun helper(i0: int, b0: bool): int option=
    if i0 <= 0 then NONE else
    if Char.ord(String.sub(cs, i0-1)) - Char.ord(#"0") = 0 
            andalso b0 = false then NONE
        else SOME(10 * helper(i0 - 1, true) + Char.ord(String.sub(cs, i0-1)) - Char.ord(#"0"))
in
  helper(String.size(cs), false)
end
*)

						
(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign01-04.sml] *)
