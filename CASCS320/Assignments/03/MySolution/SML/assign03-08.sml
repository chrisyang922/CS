(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)

(*
HX-2023-06-12: 10 points
Please implement the following function
that turns a list of streams into stream of
lists.
//
fun
stream_ziplst('a stream list): 'a list stream
//
If we use a list of streams to represent a
list of rows of a matrix, then the returned
stream consist of lists that are columns of the
matrix.
*)


exception endOfStream
fun stream_ziplst(sList: 'a stream list): 'a list stream = fn() =>
    let
        val streamOutputs = list_map(sList, fn fxs => fxs())
    in 
        strcon_cons(list_map(streamOutputs, fn fxs => case fxs of strcon_nil => strcon_nil | strcon_cons(cx1, fxs) => cx1), 
        stream_ziplst(list_map(streamOutputs, fn fxs => case fxs of strcon_nil => strcon_nil | strcon_cons(cx1, fxs) => fxs)))
    end
    handle endOfStream => strcon_nil



(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign03-08.sml] *)
