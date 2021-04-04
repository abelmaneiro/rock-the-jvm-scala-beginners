package excerises

abstract class MyList {
  def head: Int
  def tail: MyList
  def isEmpty: Boolean
  def add(element: Int): MyList
  protected[excerises] def printElements: String  // polymorphic call
  override def toString: String = s"[$printElements]"
}

object Empty extends MyList {
  override def head: Int = throw new NoSuchElementException("head of empty list")
  override def tail: MyList = throw new UnsupportedOperationException("tail of empty list")
  override def isEmpty: Boolean = true
  override def add(element: Int): MyList = new Cons(element, this)
  override def printElements: String = ""

}

class Cons(h: Int, t: MyList) extends MyList {
  override def head: Int = h
  override def tail: MyList = t
  override def isEmpty: Boolean = false
  override def add(element: Int): MyList = new Cons(element, this)
  override def printElements: String = {
    if (t.isEmpty) h.toString
    else h + " " + t.printElements
  }
}

object ListTest extends App {
  val list = new Cons(1, new Cons( 2, new Cons(3, Empty)))
  println(list.head)
  println(list.tail.head)
  println(list.add(4).head)
  println(Empty.toString)
  println(list.toString)
}