(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-06-12: 20 points
Recall that a reference is precisely an array
of size 1. And it can be treated as a sequence.
For instance, we can define ref_foreach as follows
*)

(* ****** ****** *)

fun
ref_foreach
(r0: 'a ref, work: 'a -> unit): unit = work(!r0)

(* ****** ****** *)

(*
Please implement directly the following combinators
for for references. That is, your implementation should
not make use of any third-order functions defined in the
library for this class.
*)

(* ****** ****** *)

(*
fun
ref_get_at
(ref: 'a ref, i: int): 'a
fun
ref_forall
(ref: 'a ref, test: 'a -> bool): bool
fun
ref_map_list
(ref: 'a ref, fopr: ('a) -> 'b): 'b list
fun
ref_foldleft
(ref: 'a ref, res: 'r, fopr: ('r * 'a) -> 'r): 'r
fun
ref_ifoldleft
(ref: 'a ref, res: 'r, fopr: ('r * int * 'a) -> 'r): 'r
*)



fun ref_get_at(refx: 'a ref, i: int): 'a =
    !refx

fun ref_forall(refx: 'a ref, test: 'a -> bool): bool =
    test(!refx)

fun ref_map_list(refx: 'a ref, fopr: ('a) -> 'b): 'b list =
    fopr(!refx) :: []

fun ref_foldleft(refx: 'a ref, res: 'r, fopr: ('r * 'a) -> 'r): 'r = 
    fopr(res, !refx)

fun ref_ifoldleft(refx: 'a ref, res: 'r, fopr: ('r * int * 'a) -> 'r): 'r = 
    fopr(res, 0, !refx)

    


(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign03-01.sml] *)
