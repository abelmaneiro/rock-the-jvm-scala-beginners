package excerises

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  protected[excerises] def printElements: String  // polymorphic call
  override def toString: String = s"[$printElements]"
}

object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException("head of empty list")
  override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")
  override def isEmpty: Boolean = true
//  override def add[A](element: A): MyList[A] = new Cons(element, Empty)
   override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)  // But everything is a supertype of Nothing???

  override def printElements: String = ""

}

class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList [B]= new Cons(element, this)
  override def printElements: String = {
    if (t.isEmpty) h.toString
    else h + " " + t.printElements
  }
}

object ListTest extends App {
  val listOfIntEmpty: MyList[Int] = Empty
  println(listOfIntEmpty.toString)
  println(Empty.toString)
  println()
  val listOfInt = new Cons(1, new Cons( 2, new Cons(3, Empty)))
  println(listOfInt.head)
  println(listOfInt.tail.head)
  println(listOfInt.add(4).head)
  println(listOfInt.toString)
  println()
  val listOfString = new Cons("hello", new Cons("Scala", Empty))
  println(listOfString.toString)
}