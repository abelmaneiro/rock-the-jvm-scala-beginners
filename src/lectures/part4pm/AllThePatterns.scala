package lectures.part4pm

import excerises.{Cons, Empty, MyList}

object AllThePatterns extends App {

  // 1 - Match constants
  val x: Any = "Scala"
  val constantsMatching = x match {
    case 1 => "A number"
    case "Scala" => "A string"
    case true => "A boolean"
    case AllThePatterns => "An object"
  }

  // 2 - Match anything
  // 2.1 - using Wildcard
  val wildcardMatching = x match {
    case _ => "I don't care"
  }
  // 2.2 - using wildcard variable
  val wildcardVariableMatching = x match {
    case variable => s"Found something of $variable"
  }

  // 3 - Match Tuples
  val aTuple = (1,(2, 3))
  val tupleMatching = aTuple match {
    case (_, (2,v)) => s"Inter tuple of 2 value $v"
  }

  // 4 - Match Case Classes - constructor pattern
  val myList: MyList[Int] = Cons(1, Cons(2, Empty))
  val caseClassMatching = myList match {
    case Empty => "List is Empty"
    case Cons(hd, Cons(subHd, subTl)) => s"$hd - $subHd - $subTl" // 1 - 2 - []
  }

  // 5 - Match List patters
  val aList = List(1,2,3,42)
  val listMatching = aList match {
    case List(1, _, _, _) => "Match a list with 4 items and 1st being 1" // extractor method
    case List(1, _*) => "Match any length with 1st item being 1"
    case 1 :: List(_*) =>  "Match any length with 1st item being 1"  // infix pattern
    case List(_,_,_) :+ 42 => "match anything where 4th items is 42"   // infix pattern
    case hd :: tl => s"head is $hd, tail is $tl"  // head is 1, tail is List(2, 3, 42)
  }

  // 6 - Type Specifiers
  val unknown: Any = List(2,3)
  val typeMatching = unknown match {
    case _: List[Int] => "This is an list of Int"
    case _: Int => "This is an Int"
  }

  // 7 - Name binding
  val nameBindingMatching = myList match {
    case nonEmptyList @ Cons(_, _) => s"Contains $nonEmptyList" // Contains [1 2]
    case Cons(1, getTail @ Cons(2, _)) => s" 1 and tail of $getTail"  //  1 and tail of [2]
  }

  // 8 - Multi-pattern |
  val multiPatternMatching  = myList match {
    case  Cons(1,_) | Cons(2,_) => "1st element must be 1 or 2"
  }

  // 9 - if guards
  val ifGuardMatching = myList match {
    case Cons(_, Cons(num, _)) if num % 2 == 0 => s"Second element is $num which is even"
  }

  // Trick Question
  val numbers = List(1,2,3)
  val numbersMatch = numbers match {
    case listOfStrings: List[String] => s"A list of Strings: $listOfStrings"  // This is returned
    case listOfInts: List[Int] => s"A list of Ints: $listOfInts"
  }
  // Because of backward compatibly the JVM 5+ compiler will erase all generic types after type
  // checking which makes the JVM oblivious to generic types.
  // So basically after type checking our pattern match expressions looks like this
  //    case listOfStrings: List => s"A list of Strings: $listOfStrings"
  //    case listOfInts: List => s"A list of Ints: $listOfInts"
  // because the type parameters are erased so it matched on the first case statement
  println(numbersMatch)

}
