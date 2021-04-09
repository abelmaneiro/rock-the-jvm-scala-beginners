package lectures.part2oop

object s22Exceptions extends App {

  // 1. Raise Exceptions
  val x: String = null
  // println(x.length) // will throw a null pointer exception

  //val aWeirdValue = throw new NullPointerException  // throws an exception of return a type of Nothing
  // NullPointerException is a class so need to be created with new

  // All exceptions are descendant from Throwable class.  Two major Throwable subtypes are
  //    1. Exception - Something wrong with program
  //    2. Error - Something wrong with the JVM

  // 2. Handle exceptions
  def getInt(withException: Boolean): Int = {
    if (withException) throw new RuntimeException("No Int for you")
    else 42
  }
  val potentialFail = try {
    getInt(true)  // dodge code
  } catch {
    case _: RuntimeException =>  // catch and do / return something
      println("Caught Runtime exception")
      43
  } finally { // Optionally always called and doesn't influence the return type. Use only for side-effect
    println("finally")
  }

  // 3. Create your own exceptions
  class MyException extends Exception
  val exception = new MyException
  // throw exception

  // val outOfMemory = new Array[Int](Int.MaxValue)  // java.lang.OutOfMemoryError
  // def stackOverflow: Int = 1 + stackOverflow // java.lang.StackOverflowError
  // stackOverflow

  class OverflowException extends RuntimeException
  class UnderflowException extends RuntimeException
  class MatchCalculationException extends RuntimeException("Divide by 0")
  //noinspection DuplicatedCode
  object  PocketCalculator {
    def add(x: Int, y: Int): Int = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      if (x < 0 && y < 0 && result > 0) throw new UnderflowException
      result
    }
    def subtract(x: Int, y: Int): Int = {
      val result = x - y
      if (x > 0 && y < 0 && result < 0) throw new OverflowException
      if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      result
    }
    def multiply(x: Int, y: Int): Int = {
      val result = x + y
      if (x > 0 && y > 0 && result < 0) throw new OverflowException
      if (x < 0 && y < 0 && result < 0) throw new OverflowException
      if (x > 0 && y < 0 && result > 0) throw new UnderflowException
      if (x < 0 && y > 0 && result > 0) throw new UnderflowException
      result
    }
    def divide(x: Int, y: Int): Int = {
      if (y == 0) throw  new MatchCalculationException
      x / y
    }
  }
  //println(PocketCalculator.add(Int.MaxValue, 1))
  //println(PocketCalculator.divide(2, 0))

}
