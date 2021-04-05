package excerises

abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](element: B): MyList[B]
  protected[excerises] def printElements: String  // polymorphic call
  override def toString: String = s"[$printElements]"
  def map[B](transformer: MyTransformer[A, B]): MyList[B]
  def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B]
  def filter(predicate: MyPredicate[A]): MyList[A]

  def ++[B >: A](list: MyList[B]): MyList[B]

}

object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException("head of empty list")
  override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")
  override def isEmpty: Boolean = true
//  override def add[A](element: A): MyList[A] = new Cons(element, Empty)
   override def add[B >: Nothing](element: B): MyList[B] = new Cons(element, Empty)  // But everything is a supertype of Nothing???
  override def printElements: String = ""

  override def map[B](transformer: MyTransformer[Nothing, B]): MyList[B] = Empty
  override def flatMap[B](transformer: MyTransformer[Nothing, MyList[B]]): MyList[B]  = Empty
  override def filter(predicate: MyPredicate[Nothing]): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
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

  /*
    [1,2,3,Empty].filter(n % 2 == 0)
    = [2,3,Empty].filter(n % 2 == 0)
    = new Cons(2, [3,Empty].filter(n % 2 == 0)
    = new Cons(2, [Empty].filter(n % 2 == 0)
    = new Cons(2, Empty]
   */

  override def filter(predicate: MyPredicate[A]): MyList[A] = {
    if (predicate.test(h)) new Cons[A](h, t.filter(predicate))
    else t.filter(predicate)
  }
  /*
    [1,2,3,Empty].map(n * 2)
    = new Cons(1*2, [2,3,Empty].map(n * 2))
    = new Cons(2, new Cons(2*2, [3,Empty].map(n * 2)))
    = new Cons(2, new Cons(4, new Cons(3*2, [Empty].map(n * 2))))
    = new Cons (2, new Cons(4, new Cons(6, Empty)))
    = [2,3,6,Empty]
   */
  override def map[B](transformer: MyTransformer[A, B]): MyList[B] = {
    new Cons(transformer.transform(h), t.map(transformer))
  }
  /*
    [1,2,Empty] ++ [3,4,5,Empty]
    = new Cons(1, [2,Empty] ++ [3,4,5,Empty]
    = new Cons(1, new Cons(2), Empty ++ [3,4,5,Empty]))
    = new Cons(1, new Cons(2), [3,4,5,Empty]))  - Note Empty ++ [3,4,5,Empty] returns [3,4,5,Empty]
    = [1,2,3,4,5,Empty]
  */
  override def ++[B >: A](list: MyList[B]): MyList[B] = new Cons(h, t ++ list)

  /*
    [1,2,Empty].flatmap(n => [n, n+1])
    = [1,2] ++ [2,Empty].flatmap(n => [n. n+1])
    = [1,2] ++ [2,3] ++ [Empty].flatmap(n => [n, n+1])
    = [1,2] ++ [2.3] ++ Empty
    == [1,2,2,3,Empty]
   */
  override def flatMap[B](transformer: MyTransformer[A, MyList[B]]): MyList[B] = {
    transformer.transform(h) ++ t.flatMap(transformer)
  }
}

trait MyPredicate[-T] {
  def test(elem: T): Boolean
}

trait MyTransformer[-A, B] {
  def transform(elem: A): B
}

object ListTest extends App {
  val listOfIntEmpty: MyList[Int] = Empty
  println(listOfIntEmpty.toString)
  println(Empty.toString)
  println()
  val listOfInt = new Cons(1, new Cons(2, new Cons(3, Empty)))
  val anotherListOfInt = new Cons(4, new Cons(5, Empty))
  println(listOfInt.head)
  println(listOfInt.tail.head)
  println(listOfInt.add(4).head)
  println(listOfInt.toString)
  println()
  val listOfString = new Cons("hello", new Cons("Scala", Empty))
  println(listOfString.toString)

  //noinspection ConvertExpressionToSAM
  println(listOfInt.map(new MyTransformer[Int, Int] {
    override def transform(elem: Int): Int = elem * 2
  }))
  println(listOfInt.map((elem: Int) => elem * 2))
  println(listOfInt.map(elem => elem * 2))
  println(listOfInt.map(_ * 2))

  //noinspection ConvertExpressionToSAM
  println(listOfInt.filter(new MyPredicate[Int] {
    override def test(elem: Int): Boolean = elem % 2 == 0
  }))
  println(listOfInt.filter((elem: Int) => elem % 2 == 0))
  println(listOfInt.filter(elem => elem % 2 == 0))
  println(listOfInt.filter(_ % 2 == 0))

  println(listOfInt ++ anotherListOfInt)

  //noinspection ConvertExpressionToSAM
  println(listOfInt.flatMap(new MyTransformer[Int, MyList[Int]] {
    override def transform(elem: Int): MyList[Int] = new Cons(elem, new Cons(elem + 1, Empty))
  }))
}