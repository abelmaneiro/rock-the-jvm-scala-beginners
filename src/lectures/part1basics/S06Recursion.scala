package lectures.part1basics

import scala.annotation.tailrec

object S06Recursion extends App {

  def factorial(n: Int): Int = { // factorial(4) = 1 * 2 * 3 * 4
    if (n <= 1) 1
    else {
      println("Computing factorial of " + n + " - I first need factorial of " + (n - 1))
      val result = n * factorial(n - 1)
      println("Computed factorial of "+ n + " which is " + result)
      result
    }
  }
  println(factorial(1))

  def anotherFactorial(n: Int): BigInt = {
    @tailrec
    def factHelper(x: Int, accumulator: BigInt): BigInt = {
      if (x <= 1) accumulator
      else factHelper(x - 1, accumulator * x) // recursive call mist be the LAST expression
    }
    factHelper(n, 1)
  }
  println(anotherFactorial(5000))

  def concatString(aString: String, count: Int): String = {
    @tailrec
    def concatRec (c: Int, accumulator: String): String = {
      if (c <= 0) accumulator
      else concatRec(c - 1, accumulator + aString)
    }
    concatRec(count, "")
  }
  println(concatString("Ab", 5))

  def isPrime(n: Int): Boolean = {
    @tailrec
    def isPrimeRec(t: Int, isStillPrime: Boolean): Boolean = {
      if (!isStillPrime) false
      else if (t <= 1) true
      else isPrimeRec(t - 1, n % t != 0)
    }
    isPrimeRec(n / 2, isStillPrime = true)
  }
  println(isPrime(19))
  println(isPrime(1))

  def isPrime2(number: Int): Boolean = {
    @tailrec
    def primeInner(divisor: Int): Boolean =
      if (divisor == 1) true
      else if (number % divisor == 0) false
      else primeInner(divisor - 1)

    if (number <= 1) false
    else primeInner(number / 2)
  }
  println(isPrime2(19))
  println(isPrime2(4))


  def fibonacci(number: Int): Int = {
    @tailrec
    def fibAux(n: Int, last: Int, nextToLast: Int): Int = {
      if (n >= number) last
      else fibAux(n + 1, last + nextToLast, last)
    }
    if (number <= 2) 1
    else fibAux(2 ,1, 1)
  }
}
