(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-03-24: 10 points
The following is a well-known series:
ln 2 = 1 - 1/2 + 1/3 - 1/4 + ...
Please implement a stream consisting of all the partial
sums of this series.
The 1st item in the stream equals 1
The 2nd item in the stream equals 1 - 1/2
The 3rd item in the stream equals 1 - 1/2 + 1/3
The 4th item in the stream equals 1 - 1/2 + 1/3 - 1/4
And so on, and so forth
//
*)

(* ****** ****** *)

(*
val the_ln2_stream: real stream = fn() => ...
*)

val the_ln2_stream: real stream = fn() =>
    let
        fun ln2_helper(numer: real, denom: real, answer: real): real stream = fn () =>
            let
                val partialSum = ~1.0 * numer / (denom + 1.0) + answer
            in
                strcon_cons(partialSum, ln2_helper(~1.0 * numer, denom + 1.0, partialSum))
            end
    in
        strcon_cons (1.0, ln2_helper(1.0, 1.0, 1.0))
    end

(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign03-04.sml] *)
