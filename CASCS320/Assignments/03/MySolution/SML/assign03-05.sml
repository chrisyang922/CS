(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-06-12: 10 points
Please enumerate all the pairs of natural
numbers. Given pairs (i1, j1) and (i2, j2),
(i1, j1) should be enumerated ahead of (i2, j2)
if i1+j1 < i2+j2.
*)

(* ****** ****** *)

(*
val theNatPairs: (int*int) stream = fn () => ...
*)

(* ****** ****** *)

val theNatPairs: (int * int) stream = fn () => 
    let
        fun pairHelper(i: int, j: int): (int * int) stream = fn () =>
            if i = j andalso i = 0 then strcon_cons((i, j+1), pairHelper(i, j+1))
            else 
                if j = 0 then strcon_cons((0, i+1), pairHelper(0,i+1))
                else strcon_cons((i+1, j-1),pairHelper(i+1,j-1))
    in
        strcon_cons((0,0), pairHelper(0, 0))
    end


(* end of [CS320-2023-Sum1-assign03-05.sml] *)
