package lectures.part3fp

import scala.collection.immutable.Vector
import scala.util.Random

object S30Sequences extends App {
  /*
   trait Seq[+A] {
      def head: A
      def tail: Seq[A]
    }
   */
  val aSequence = Seq(1,2,3,4) // Seg.apply(1,2,3,4)
  println(aSequence)  // List(1, 2, 3, 4)
  println(aSequence.reverse)  // List(4, 3, 2, 1)
  println(aSequence(2))  // aSequence.apply(2)) // 3
  println(aSequence ++ Seq(5,6,7)) // List(1, 2, 3, 4, 5, 6, 7)
  println(Seq(4,3,1,3).sorted) // List(1, 3, 3, 4)
  println(aSequence.updated(2, 6)) // List(1, 2, 6, 4)

  // Ranges
  val aRange1to10: Seq[Int] = 1 to 10  // 1,2,...10
  println(aRange1to10.mkString(", "))
  val aRange1Until10: Seq[Int] = 1 until 10  // 1,2,...9
  println(aRange1Until10.mkString(", "))
  (1 to 10).foreach(x => println("hi " + x ))

  /*
    sealed abstract class List[+A]
	  case object Nil extends List[Nothing]
    case class ::[A](val hd: A, val tl: List[A]) extends List[A]
   */
  val aList = List(1,2,3)
  println(5 :: 6 :: 7 :: Nil)
  println(42 :: aList)  // aList.::(42)  // List(42, 1, 2, 3)
  println(42 +: aList)  // aList.+:(42)  // List(42, 1, 2, 3) // Above syntax better
  println(aList :+ 89)  // List(1, 2, 3, 89)  // Slow
  println(List.fill(3)(Random.nextInt(100))) //def fill[A](n: Int)(elem: => A): CC[A]
  println(List.tabulate(3)("Apple " + _)) // def tabulate[A](n: Int)(f: Int => A): CC[A]

  /*
    final class Array[T] extends java.io.Serializable with java.lang.Cloneable
   */
  val aArray1 = Array(1,2,3,4)  // 1-2-3-4
  val aArray2 = Array.ofDim[Int](3)  //0-0-0
  val aArray3 = new Array[String](3) // null-null-null
  val aArray4 = Array.fill(3)(Random.nextInt(100))
  val aArray5 = Array.tabulate(3)("Cat" + _)
  println(aArray1.mkString("-"))
  println(aArray2.mkString("-"))
  println(aArray3.mkString("-"))
  println(aArray4.mkString("-"))
  println(aArray5.mkString("-"))
  aArray1(1) = 8  // sugar for aArray1.update(1, 9) // 1-8-3-4
  println(aArray1.mkString("-"))
  //  array amd seq
  val numberSeq: Seq[Int] = aArray1 // Implicit conversion to WrappedArray(1, 8, 3, 4)
  println(numberSeq)

  /*
    final class Vector[+A]
  * */
  final class Vector[+A]
  val aVector1 = Vector(1,2,3,4)
  val aVector2 = 1 +: 2 +: Vector.empty
  val aVector3 = Vector.empty :+ 1 :+ 2
  println(aVector1)
  println(aVector2)
  println(aVector3)

  // vectors vs lists
  val maxRuns = 1000
  val maxCapacity = 1000000
  def getWriteTimeMap(collection: Seq[Int]): Double = {
    val times = (1 to maxRuns).toList.map {_ =>
      val random = Random.nextInt(maxCapacity)
      val currentTime = System.nanoTime()
      collection.updated(random, random)
      System.nanoTime() - currentTime
    }
    (times.sum / maxRuns) / 1e9
  }
  def getWriteTimeFor(collection: Seq[Int]): Double = {
    val times =  for (_ <- 1 to maxRuns) yield {
      val random2 = Random.nextInt(maxCapacity)
      val currentTime = System.nanoTime()
      collection.updated(random2, random2)
      System.nanoTime() - currentTime
    }
    (times.sum / maxRuns) / 1e9
  }
  println(getWriteTimeMap((0 until maxCapacity).toList))
  println(getWriteTimeFor((0 until maxCapacity).toList))
  println(getWriteTimeMap((0 until maxCapacity).toVector))
  println(getWriteTimeFor((0 until maxCapacity).toVector))
  println(1000000000 / 1e9)
}
