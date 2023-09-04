(* ****** ****** *)
use
"./../../../../mysmlib/mysmlib-cls.sml";
(* ****** ****** *)
(*
HX-2023-06-12: 20 points
*)
(* ****** ****** *)
(*
Note that you are not allowed to define
recursive functions for solving this one.
*)
(* ****** ****** *)
(*
Here are some functions that you can use in
your solution to this one.
*)
val
strsub = String.sub
val
string_length = String.size
val
string_implode = String.implode
val
string_filter = foreach_to_filter_list(string_foreach)
val
string_tabulate =
fn(len, fopr) => string_implode(list_tabulate(len, fopr))

(* ****** ****** *)

(*
A word here is defined to be a (possibly empty)
sequence of lowcase letters in the English alphabet.
Two words w1 and w2 are neighbors if they are of the
same length and differ by exactly one letter.
//
For instance, [water] and [later] are neighbors.
For instance, [abcde] and [abxde] are neighbors.
For instance, [abcde] and [abcde] are not neighbors.
//
Given a word, the function [word_neighbors] returns
a list consisting of *all* the neighbors of the word.
//
fun word_neighbors(word: string): string list = ...
//
Please give an implementation of word_neighbors.
Note that you are NOT allowed to define recursive functions
in your implementation.
//
*)

(* ****** ****** *)

val AB =
"abcdefghijklmnopqrstuvwxyz"

val characterList = [#"a", #"b", #"c", #"d", #"e", #"f", #"g", #"h", #"i",#"j", #"k", #"l", #"m", #"n", #"o", #"p", 
                     #"q", #"r", #"s", #"t", #"u", #"v", #"w", #"x", #"y", #"z"]

(* ****** ****** *)


val
string_iforeach =
foreach_to_iforeach(string_foreach)
val
string_imap_list =
fn(cs, ifopr) =>
foreach_to_map_list(string_iforeach)(cs, ifopr)

(* ****** ****** *)

(*
val
word_neighbors = fn(word: string) => ...
*)

val word_neighbors = fn(word: string) => 
    let
        fun neighbors_helper(i0: int, c0: char): string =
            string_tabulate(string_length(word), fn x =>
                if x = i0 then c0
                else strsub(word, x))
    in
        list_filter(
            list_concat(
                string_imap_list(word, fn (r0, x0) =>
                    list_map(characterList, fn x => neighbors_helper(r0, x)))), (fn x => x <> word))
    end

    

    
    
(* ****** ****** *)

(* end of [CS320-2023-Sum1-assign03-02.sml] *)
