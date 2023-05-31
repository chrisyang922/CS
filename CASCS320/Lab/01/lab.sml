val x = 1
val y = x + 1
(* val z = true + true *)

fun fact(n: int) : int = 
    if n <=0 then 1
    else n * fact(n-1)

val _ = print "hello world" (* string to unit *) (* unit is like void, none *)
(*val _ = print(fact 10)*) (* print goes from string to unit but the function fact goes from int to int *) 
(*print expects string but it gets int type which leads to error *)

val _ = print(Int.toString 10 ^ "\n") (* change from int to string *)
val _ = print(Int.toString (fact 10) ^ "\n")

(*val _ = print(Int.toString fact 10 ^ "\n") *) (* this means that we are applying function to the Int.toString which fails *)

fun print_int (n: int): unit = let
  val s0 = Int.toString n 
  val s1 = "\n"
  val s2 = s0 ^ s1
in
  print s2
end

fun print_int (n: int): unit = 
    print (Int.toString n ^ "\n")

fun fibo (n: int): int =
    case n of 
        0 => 0
        | 1 => 1
        | _ => fibo (n-1) + fibo (n-2)

val _ = print_int(fibo 10)

(*faster implementation of fibo *)
fun fiboX (n: int) : int = let
    fun loop (n: int, a: int, b: int): int =
        if n <= 0 then a 
        else loop(n-1, b, a + b)
    in
        loop(n, 0, 1)
    end

val _ = print_int (fiboX 50)



