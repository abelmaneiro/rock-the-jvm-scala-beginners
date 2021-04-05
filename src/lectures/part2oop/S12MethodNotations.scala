package lectures.part2oop

import scala.language.postfixOps

//noinspection EmptyParenMethodAccessedAsParameterless
object S12MethodNotations extends App {
  class Person(val name: String, favouriteMovie: String, val age: Int = 0) {
    def likes(movie: String): Boolean = movie == favouriteMovie
    def +(person: Person): String = s"$name is hanging out with ${person.name}"
    def +(nickname: String): Person = new Person(s"$name ($nickname)", favouriteMovie)
    def unary_! : String = s"$name, that the heck!"
    def unary_+ : Person = new Person(name, favouriteMovie, age + 1)
    def isAlive: Boolean = true
    def apply(): String = s"Hi, my name is $name and i like $favouriteMovie"
    def apply(number: Int): String = s"$name watched $favouriteMovie $number times"
    def learns(item: String): String = s"$name learns $item"
    def learnsScala: String = learns("Scala")
  }

  val mary = new Person("Mary", "Inception")
  println(mary.likes("Inception"))
  println(mary likes "Inception")  // INFIX - Operator (likes) is between Operands (mary inception)

  val tom = new Person("Tom", "Flight Club")
  println(mary + tom)  // All operators in scala are actually methods!
  println(mary.+(tom)) // e.g.
  println(2.+(2))
  println(100 / 10 * 5)  // left to right associated except ...
  println((100 / 10) * 5)
  println(100./(10).*(5))
  println(1 :: 2 :: 3 :: Nil) // ... if it starts with : the it is right to left
  println(1 :: (2 :: (3 :: Nil)))
  println(Nil.::(3).::(2).::(1))

  println(-1)  // PREFIX - Operator (-) is before Operand (1)
  println(1.unary_-)  // can only be unary_-, unary_+, unary_~, unary_!
  println(!mary)
  println(mary.unary_!)

  println(mary isAlive)  // POSTFIX - Operator (isAlive) is after operand (mary)
  println(mary.isAlive)  // Can only be methods with no parameters

  println(mary.apply())  // Hi, my name is Mary and i like Inception
  println(mary.apply)  // Hi, my name is Mary and i like Inception
  println(mary())  // Hi, my name is Mary and i like Inception
  println(mary)  // lectures.part2oop.S12MethodNotations$Person@511baa65
  println("****")
  println(mary.+("the rockstar").apply())
  println((mary + "the rockstar")())
  println(mary.unary_+.age)
  println((+mary).age)
  println(+mary age)
  println(mary.learnsScala)
  println(mary learnsScala)
  println(mary.apply(2)) // Mary watched Inception 2 times
  println(mary(2)) // Mary watched Inception 2 times



}
