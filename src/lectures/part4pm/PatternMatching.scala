package lectures.part4pm

import scala.util.Random

object PatternMatching extends App {
  val x = new Random().nextInt(10)
  val description = x match {
    case 1 => "one"
    case 2 => "two"
    case 3 => "three"
    case _ => "some number"  // Except if sealed call always include a default, else if nothing matching it will crash
  }
  println(s"$x is $description")

  // 1. Decompose value and if guard
  case class Person(name: String, age: Int)
  val bob = Person("Bob", x)
  val greeting = bob match {
    case Person(n, a) if a < 5 => s"$n is $a years young"  // if guard
    case Person(n, a) => s"$n is $a years old"
    case _ => "Your are not a person"  // Default, will throw exception otherwise if no match is found
  }
  println(greeting)

  /*
    1. Cases are matched in order
    2. What if no cases match and not default? Get a MatchError
    3. What is the type returned by Pattern Match? Unified type of all the types in all the cases
    4. Pattern Matching works really well with case cases and sealed classes/traits
   */

  // Pattern Matching with sealed classes
  sealed class Animal
  case class Dog(breed: String) extends Animal
  case class Parrot(greeting: String) extends Animal
 // val animal = if (x < 5) Dog("Terra Nova") else Parrot("Who's a pretty boy")
  val animal = Dog("Terra Nova")
  animal match {
    case Dog(b) => println(s"Dog is a $b")  // Compiler warning: match may not be exhaustive. It would fail on the following input: Parrot(_)
  }

  // Pattern matching is powerful, but don't be silly
  val isEvenPattern = x match {
    case n if n % 2 == 0 => true
    case _ => false
  }
  // or stupid
  val isEvenCondition = if (x % 2 == 0) true else false
  // be practical
  val isEven = x % 2 == 0

  /*
    Exercise - Take an Expr => human readable string
    Sum(Number(2), Number(3)) => 2 + 3
    Sum(Number(2), Number(3), Number(4) => 2 + 3 + 4
    Prod(Sum(Number(2), Number(1)), Number(3)) = (2 + 1) * 2
    Sum(Prod(Number(2), Number(2)), Number(3) = 2 * 1 + 3
  */
  class Expr
  case class Number(n: Int) extends Expr
  case class Sum(e1: Expr, e2: Expr) extends Expr
  case class Prod(e1: Expr, e2: Expr) extends Expr

  def show(expr: Expr): String = expr match {
    case Number(x) => x.toString
    case Sum(e1, e2) => s"${show(e1)} + ${show(e2)}"
    case Prod(e1, e2) =>
      (e1, e2) match {
        case (Sum(_,_), Sum(_, _))  => s"(${show(e1)}) * (${show(e2)})"
        case (Sum(_,_), _)          => s"(${show(e1)}) * ${show(e2)}"
        case (_, Sum(_, _))         => s"${show(e1)} * (${show(e2)})"
        case _                      => s"${show(e1)} * ${show(e2)}"
      }
  }

  def showD(expr: Expr): String = expr match {
    case Number(x) => x.toString
    case Sum(e1, e2) => s"${showD(e1)} + ${showD(e2)}"
    case Prod(e1, e2) =>
      def maybeShowParentheses(e: Expr): String = e match {
        case Sum(_, _) => s"(${showD(e)})"
        case _ => showD(e)
      }
      s"${maybeShowParentheses(e1)} * ${maybeShowParentheses(e2)}"
  }


  println(show(Number(3)))  // 3
  println(show(Sum(Number(2), Number(3))))  // 2 + 3
  println(show(Sum(Sum(Number(2), Number(3)), Number(4))))    // 2 + 3 + 4
  println(show(Sum(Number(4), Sum(Number(2), Number(3)))))    // 4 + 2 + 3
  println(show(Sum(Prod(Number(2), Number(1)), Number(3))))   // 2 * 1 + 3
  println(show(Prod(Sum(Number(2), Number(1)), Number(3))))   // (2 + 1) * 3
  println(show(Prod(Number(3), Sum(Number(2), Number(1)))))   // 3 * (2 + 1)
  println(show(Prod(Sum(Number(1), Number(2)), Sum(Number(3), Number(4)))))  // (2 + 1) * (3 + 4)

  println(showD(Number(3)))  // 3
  println(showD(Sum(Number(2), Number(3))))  // 2 + 3
  println(showD(Sum(Sum(Number(2), Number(3)), Number(4))))    // 2 + 3 + 4
  println(showD(Sum(Number(4), Sum(Number(2), Number(3)))))    // 4 + 2 + 3
  println(showD(Sum(Prod(Number(2), Number(1)), Number(3))))   // 2 * 1 + 3
  println(showD(Prod(Sum(Number(2), Number(1)), Number(3))))   // (2 + 1) * 3
  println(showD(Prod(Number(3), Sum(Number(2), Number(1)))))   // 3 * (2 + 1)
  println(showD(Prod(Sum(Number(1), Number(2)), Sum(Number(3), Number(4)))))  // (2 + 1) * (3 + 4)






}
