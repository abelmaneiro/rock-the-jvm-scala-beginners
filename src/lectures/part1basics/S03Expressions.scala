package lectures.part1basics

object S03Expressions extends App {
  var x = 1 + 2 // Expressions evaluate to a value

  println(2 + 3 * 4)
  // maths operations  + - * / & | ^ << >> >>> (right shift with zero extensions)

  println(1 == x)
  // relational operations == != > >= < <=

  println(!(1 == x))
  // boolean logical operations ! && ||

  x += 10
  //change value operations += -= *= /= .... side effects

  // Instructions (DO) vs Expression (VALUE)
  println(if (x == 3) "is 3" else "not 3") // If is an expression not a Instructions (statement)

  // Everything in Scala is an EXPRESSION!
  var count = 0
  val aWhile: Unit = while (count < 10) {  // returns () which is of type Unit === void
    println(count)
    count += 1
  }
  val assignment: Unit = count += 3  // even assignments are expressions
  // side effects: println(), while, reassigning. Look out for this returning () of type Unit

  // code blocks are expression and evaluate to a value
  val aCo11deBlock = {
    val y = 2
    val z = y + 1
    if (z > 2) "hello" else "Bye"
  }
}
