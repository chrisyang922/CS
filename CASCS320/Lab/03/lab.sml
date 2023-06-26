

fun list2string(xs: int list) =
    case xs of
        [] => "[]" 
        | x :: xs => Int.toString(x) ^ " :: " ^ list2string (xs)

fun list2string(toString: 'a -> string, xs: 'a list) =
    case xs of 
        [] => "[]"
        | x :: xs =>  toString(x) ^ " :: " ^ list2string(toString, xs)

fun intlist2string (xs: int list): string = 
    list2string(Int.toString,xs)

(* val zero = list2string([1,2,3,4]) *) 
(* gives error because we need the second argument toString function *)
val one = list2string(Int.toString, [1,2,3,4])

fun list_lte (xs: int list, pivot: int): int list =
    (* less than the pivot *)
    case xs of 
        [] => [] 
        | x :: xs =>
            if x <= pivot then
                x :: list_lte(xs, pivot)
            else list_lte(xs, pivot)

fun list_gt(xs: int list, pivot: int): int list =
    (* greater than the pivot *)
    case xs of 
        [] => []
        | x :: xs =>
            if x > pivot then 
                x :: list_gt(xs,pivot)
            else list_gt(xs,pivot)

val less = list_lte([1,2,3,4,5,6], 4)
val great = list_gt([1,2,3,4,5,6], 4)

fun qsort(xs: int list): int list =
    case xs of 
    [] => []
    | x :: xs =>
        let
            val l = list_lte(xs, x)
            val r = list_gt(xs, x)
        in
            qsort(l) @ [x] @ qsort(r)
        end

(* implement higher order function *)
fun list_filter(cond: 'a -> bool, xs: 'a list): 'a list= 
(* since list_lte and list_gt has similar style in the case that inside x :: xs, we discard the x under a condition and cons x under a condition *)
    case xs of
    [] => []
    | x :: xs =>
        if cond x then
            x :: list_filter(cond, xs)
        else
            list_filter(cond, xs)

(* 
    In python, it would look like [x for x in xs if x<= pivot]
    and
    [x for x in xs if x > pivot]
*)

fun new_qsort(xs: int list): int list =
    case xs of 
    [] => []
    | pivot :: xs =>
        let
            val l = list_filter(fn x => x <= pivot, xs)
            (* anonymous function that keeps x if x is less than or equal to pivot *)
            val r = list_filter(fn x => x > pivot, xs)
        in
            new_qsort(l) @ [pivot] @ new_qsort(r)
        end


fun anyType_qsort(lte: 'a * 'a -> bool, xs: 'a list): 'a list =
    case xs of 
        [] => []
        | pivot :: xs =>
            let
                val l = list_filter(fn x => lte(x, pivot), xs)
                val r = list_filter(fn x => not(lte(x,pivot)),xs)
            in
                anyType_qsort(lte, l) @ [pivot] @ anyType_qsort(lte, r)
            end

val xs = anyType_qsort(fn(xs,ys) => List.length(xs) <= List.length(ys), [[1,2], [2], [1,2,3,4], [1,2,3], [1,2,3,4,5], []])
val ys = anyType_qsort(fn(xs,ys) => List.length(xs) > List.length(ys), [[1,2], [2], [1,2,3,4], [1,2,3], [1,2,3,4,5], []])

fun list_map(fopr: 'a -> 'b, xs: 'a list): 'b list =
    case xs of 
        [] => []
        | x :: xs =>
            fopr x :: list_map(fopr, xs)

val xs0 = [1,2,3,4,5,6]
val xs1 = list_map(fn x => x + 1, xs0)
val xs2 = list_map(fn x => x * x, xs0)

val ys0 = ["abc", "xyz", "321", "hello", "world", "Boston"]
(* val ys1 = list_map(fn x => ?, xs0, ys0) *)
(* takes argument ('a -> 'b) * 'a list) and outputs 'b list *)
(* list_map takes two arguments but in this case it takes three arguments so it is impossible *)

(* possible solution --> zip two lists and submit to the argument of list_map as a single list *)
fun list_zip(xs: 'a list, ys: 'b list) : ('a * 'b) list =
    case (xs, ys) of
        ([], _) => []
        | (_, []) => []
        | (x :: xs, y:: ys) => (x,y) :: list_zip(xs, ys)

val ys1 = list_map(fn(x,y) => Int.toString x ^ y, list_zip(xs0, ys0))
val ys2 = list_map(fn(x,y) =>
    let
        fun loop(i) =
            if i < x then y ^ loop(i+1)
            else ""
    in
        loop(0)
    end,
    list_zip(xs0, ys0)
)

(*
In python, 
acc = 0
for x in xs:
    acc = something with x with acc 
return acc 
*)

fun foldleft(fopr: 'a * 'b -> 'b, xs: 'a list, acc: 'b): 'b = 
    case xs of
        [] => acc
        | x :: xs => foldleft(fopr, xs, fopr(x, acc))

(* 
In python,
xs0 = [1,2,3,4,5,6]
acc = 0
for x in xs0:
    acc = x + acc
return acc
*)

val sum = foldleft(fn (x, acc) => x + acc, xs0, 0)
val product = foldleft(fn(x,acc)=> x * acc, xs0, 1)

(*
In python,
acc = 0
for (x, i) in enum(xs0):
    acc = computed from x, i and acc
return acc
*)

val (ranVal, _) = foldleft(fn (x, (acc, i)) => ((x+i) * acc, i + 1), xs0, (1,0))
