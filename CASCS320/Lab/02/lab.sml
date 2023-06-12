datatype 'a mylist = mynil | mycons of 'a * 'a mylist

val xs: int mylist = mynil
val xs: int mylist = mycons(1, mynil)
val xs: int mylist = mycons(1, xs)

val ys: string mylist = mynil
(* this does not work because it is string type while xs is int type *)
(* val ys: string mylist = mycons("",xs) *)

val ys: string mylist = mycons("2", ys)
val ys: string mylist = mycons("2", mycons("1", mycons ("0", mynil)))

(* predefined by SML *)
val zs: string list = []
val zs: string list = "2" :: zs (* this acts same as the mycons we created *)
val zs: string list = "2" :: "1" :: []
(* is equivalent to *)
val zs: string list = ["2", "1"]

fun append(xs: 'a mylist, ys: 'a mylist): 'a mylist =
    case xs of
      mynil => ys
    | mycons(x1, xs) => mycons(x1,append(xs, ys))

fun mylist2string (xs: int mylist): string =
    case xs of
        mynil => "mynil"
        | mycons(x1, xs) => "mycons (" ^ Int.toString x1 ^ ", " ^ mylist2string xs ^ ")"

val ex = append(xs, xs)
val stringVer = mylist2string(ex)

(*

1 2 3 4 5 6
(1,2,3) (4,5,6)

OR

(1,3,5) (2,4,6)

*)

fun split (xs: int list): int list * int list =
    case xs of
        [] => ([], [])
        | xh :: xt => 
            case xt of
                [] => ([xh], [])
                | y :: xt => 
                    let 
                        val (xs, ys) = split xt
                    in 
                        (xh:: xs, y :: ys)
                    end

fun split(xs: int list): int list * int list =
    case xs of
        [] => ([],[])
        | x :: [] => ([x],[])
        | x :: y :: tl =>
            let 
                val (xs, ys) = split tl
            in
                (x::xs, y::ys)
            end

fun merge(xs: int list, ys: int list): int list =
    case xs of  
        [] => ys
        | x1 :: xs0 => 
            case ys of
            [] => xs
            | y1 :: ys0 =>
                if x1 <= y1 then x1 :: merge(xs0, ys)
                else y1 :: merge(xs, ys0)

val spl = split[1,2,3,4,5,6]
val mer = merge([1,3,5],[2,4,6])

fun merge(xs: int list, ys: int list): int list =
    case (xs, ys) of    
        ([], _) => ys
        | (_,[]) => xs
        | (x1 :: xs0, y1 :: ys0) =>
            if x1 <= y1 then x1 :: merge(xs0, ys)
            else y1 :: merge(xs, ys0)

val spl = split[1,2,3,4,5,6]
val mer = merge([1,3,5],[2,4,6])

fun msort(xs: int list): int list = 
    case xs of
    [] => []
    | [ x ] => [ x ]
    | _ =>
        let
            val (xs,ys) = split xs
        in
            merge(msort xs, msort ys)
        end


datatype 'a tree =
    Leaf | Node of 'a tree * 'a * 'a tree

val x1: int tree = Leaf
val x1: int tree = Node(x1, 1, x1)
val x1: int tree = Node(x1, 2, x1)
val x1: int tree = Node(x1, 3, x1)
val x1: int tree = Node(x1, 4, x1)
val x1: int tree = Node(x1, 4, Leaf)
val x1: int tree = Node(Leaf, 4, x1)

fun tree2string (t: int tree): string =
    case t of
        Leaf => "Leaf"
        | Node (l,x,r) => 
            "Node (" ^ tree2string(l) ^ ", " ^ Int.toString(x) ^ ", " ^ tree2string(r) ^ ")"

fun insert (t: int tree, entry: int): int tree =
    case t of
        Leaf => Node (Leaf, entry, Leaf)
        | Node (l,v,r) =>
            if entry <= v then Node (insert(l, entry), v, r)
            else Node (l, v, insert(r, entry))

val ex = Leaf
val ex = insert(ex, 5)
val ex = insert(ex, 3)
val ex = insert(ex, 4)
val ex = insert(ex, 7)

fun treeSize(t: 'a tree): int =
    case t of
        Leaf => 0
        | Node(l,_,r) => treeSize(l) + 1 + treeSize(r)

val y1: string tree = Leaf
val y1: string tree = Node(y1, "1", y1)
val y1: string tree = Node(y1, "3", y1)



