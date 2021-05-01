package lectures.part4pm

import excerises.{Cons, Empty}

object PatternsEverywhere extends App {
  // big idea 1  - catch is actually MATCHES
  println(try {
    throw new NullPointerException
  } catch {
    case _: NoSuchElementException => "No such Element"
    case _: NullPointerException => "Null pointer"
  })  //  Null pointer
  // same as
  println(try {
    throw new NullPointerException
  } catch {
    case e: Throwable => e match {
      case _: NoSuchElementException => "No such Element\""
      case _: NullPointerException => "Null pointer"
    }
  })  // Null pointer

  // big idea 2 - Generators are also based on PATTERN MATCHING
  println(for {
    x <- List(1,2,3,4) if x % 2 == 0  // Patten matching
  } yield x * 10) // List(20, 40)
  println(for {
    (first, second) <- List((1,2), (3,4))  // // Patten matching
  } yield first * second)  // List(2, 12)

  // big idea 3 - Multiple value definition based on PATTERN MATCHING
  val (a, b, c) = Tuple3(1, 2, 3)
  println(s" $a, $b, $c")  //  1, 2, 3
  val hd :: tl = List(1,2,3,4)
  println(s"Head: $hd Tail: $tl")  // Head: 1 Tail: List(2, 3, 4)
  val Cons(x, y) = Cons(1, Cons(2, Empty))
  println(s"Head: $x Tail: $y")   // Head: 1 Tail: [2]

  // big idea 4 - Partial function based on PATTERN MATCHING
  val mappedList1 = List(1,2,3,4).map {
    case v if v % 2 == 0 => v + " is even"
    case 1 => "The one"
    case _ => "Something else"
  }  //List(The one, 2 is even, Something else, 4 is even)
  // same as
  val mappedList2 = List(1,2,3,4).map {_ match {
    case v if v %2 == 0 => v + " is even"
    case 1 => "The one"
    case _ => "Something else"
  }}  //List(The one, 2 is even, Something else, 4 is even)
  println(mappedList1)
  println(mappedList2)
}
