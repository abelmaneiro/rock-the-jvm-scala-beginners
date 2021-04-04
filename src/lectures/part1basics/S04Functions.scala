package lectures.part1basics

import scala.annotation.tailrec

object S04Functions extends App {

  def aFunction(a: String, b: Int ): String = a + " " + b
  println(aFunction("hello", 3))

  def aParameterlessFunction() = 42  // not need to include the return type
  println(aParameterlessFunction())

  def aRepeatingFunction(aString: String, n: Int): String = {  // recursive functors must have a return type
    if (n <= 0) ""
    else  aString + aRepeatingFunction(aString, n - 1)
  }
  println(aRepeatingFunction("ab", 5))

  def aFunctionWithSideEfforts(aString: String): Unit = println(aString)

  def aBigFunction(n: Int): Int = {
    def aSmallerFunction(a: Int, b: Int): Int = a + b
    aSmallerFunction(n, n)
  }

  def greetings(name: String, age: Int): String = s"Hi, my name is $name and I am $age years old"
  println(greetings("Joe", 16))

  def factorial(n: Int): Int = { // factorial(4) = 1 * 2 * 3 * 4
    if (n <= 1) 1
    else n * factorial(n - 1)
  }
  println(factorial(4))

  def fibonacci(n: Int): Int = {  // fibonacci = = fibonacci(n - 1) + fibonacci(n - 2)
    if (n <= 2) n
    else fibonacci(n - 1) + fibonacci(n - 2)
  }
  println(fibonacci(-1))

  def isPrime(n: Int): Boolean = { //greater than 1, which is only divisible by 1 and itself. 2 3 5 7 11 13 17 19 23
    @tailrec
    def isPrimeUnit(t: Integer): Boolean = {
      if (t <= 1) true
      else n % t != 0 && isPrimeUnit(t - 1)
    }
    isPrimeUnit(n / 2)
  }
  println(isPrime(19))
  println(isPrime(15))
}
