package lectures.part3fp

import scala.annotation.tailrec

object S26HOFsCurries extends App {
  //  val superFunction: (Int, (String, Int => Boolean) => Int) => Int => Int = ???

  // function that applies a function n tiles over a value x
  // nTimes(f, n, x)
  // nTimes(f, 3, x) = nTimes(f, 2, f(x) = nTimes(f, 1, f(f(x)) = nTimes(f, 0, f(f(f(x)))  == f(f(f(x)))
  val nTimesT = (f: Int => Int, n: Int, x: Int) => {
    if (x <= 1) f(x)
    else nTimes(f, n - 1, x)
  }

  @tailrec
  def nTimes(f: Int => Int, n: Int, x: Int): Int = {
    if (n <= 0) x
    else nTimes(f, n - 1, f(x))
  }

  val plusOne = (x: Int) => x + 1
  println(nTimes(plusOne, 10, 1))
  println(nTimes(_ * 3, 2, 10))

  // ntb(plusOne, n) => x => f(f(f(...(x)))
  // plus3 = ntb(plusOne, 3) => x => plusOne(plusOne....(x))
  def nTimesBetter1(f: Int => Int, n: Int): Int => Int = {
    if (n <= 0) (x: Int) => x
    else (x: Int) => nTimesBetter1(f, n - 1)(f(x))
  }

  def nTimesBetter2(f: Int => Int, n: Int): Int => Int = { x: Int =>
    if (n <= 0) x
    else nTimesBetter2(f, n - 1)(f(x))
  }

  val plusTen1 = nTimesBetter1(plusOne, 10)
  println(plusTen1(1))
  val plusTen2 = nTimesBetter2(plusOne, 10)
  println(plusTen2(1))
  println(nTimesBetter1(_ * 3, 2)(10))
  println(nTimesBetter2(_ * 3, 2)(10))

  // curried function
  val supperAdder: Int => Int => Int = (x: Int) => (y: Int) => x + y
  println(supperAdder(3)(4))
  val add3 = supperAdder(3) // (y: Int) => 3 + y
  println(add3(10))

  // functions with multiple parameter lists  - like curried functions
  def curriedFormatter(format: String)(value: Double): String = format.format(value)

  val standardFormat: Double => String = curriedFormatter("%4.2f") // need to include function type
  // val problemFormat = curriedFormatter("%4.4f")  // Omitting the function type results in compilation error
  val preciseFormat = curriedFormatter("%10.8f") _ // or include the CurriedFormatter _
  println(standardFormat(12345.6789123456789))
  println(preciseFormat(12345.6789123456789))
  // Note following
}
