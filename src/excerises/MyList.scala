package excerises

//               Animal
//       Pet ---+       +--- Wild
// Cat -+   +-Dog    Tiger -+    +-Wolf

// A covariant class - Allow subtypes but not supertypes (eg. if A is Pet allow Dog, but not Animal or Tiger)
abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  // / Lower Type Bound - B must be a supertype of A. (eg. if B is Pet allow Animal, but not Dog, Wild)
  def add[B >: A](element: B): MyList[B]
  protected[excerises] def printElements: String  // polymorphic call
  override def toString: String = s"[$printElements]"
  def map[B](transformer: A => B): MyList[B]
  def flatMap[B](transformer: A => MyList[B]): MyList[B]
  def filter(predicate: A => Boolean): MyList[A]

  def ++[B >: A](list: MyList[B]): MyList[B]

}

case object Empty extends MyList[Nothing] {
  override def head: Nothing = throw new NoSuchElementException("head of empty list")
  override def tail: MyList[Nothing] = throw new UnsupportedOperationException("tail of empty list")
  override def isEmpty: Boolean = true
//  override def add[A](element: A): MyList[A] = new Cons(element, Empty)
   override def add[B >: Nothing](element: B): MyList[B] = Cons(element, Empty)  // But everything is a supertype of Nothing???
  override def printElements: String = ""

  // higher-order functions. Either received function as parameters or return other functions as the result
  override def map[B](transformer: Nothing => B): MyList[B] = Empty
  override def flatMap[B](transformer: Nothing =>MyList[B]): MyList[B]  = Empty
  override def filter(predicate: Nothing => Boolean): MyList[Nothing] = Empty

  override def ++[B >: Nothing](list: MyList[B]): MyList[B] = list
}

case class Cons[+A](h: A, t: MyList[A]) extends MyList[A] {
  override def head: A = h
  override def tail: MyList[A] = t
  override def isEmpty: Boolean = false
  override def add[B >: A](element: B): MyList [B]= Cons(element, this)
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

  override def filter(predicate: A => Boolean): MyList[A] = {
    if (predicate(h)) new Cons[A](h, t.filter(predicate))  // or predicate.apply(h)) ....
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
  override def map[B](transformer: A => B): MyList[B] = {
    Cons(transformer(h), t.map(transformer))  // or transformer.apply(h) ....
  }
  /*
    [1,2,Empty] ++ [3,4,5,Empty]
    = new Cons(1, [2,Empty] ++ [3,4,5,Empty]
    = new Cons(1, new Cons(2), Empty ++ [3,4,5,Empty]))
    = new Cons(1, new Cons(2), [3,4,5,Empty]))  - Note Empty ++ [3,4,5,Empty] returns [3,4,5,Empty]
    = [1,2,3,4,5,Empty]
  */
  override def ++[B >: A](list: MyList[B]): MyList[B] = Cons(h, t ++ list)

  /*
    [1,2,Empty].flatmap(n => [n, n+1])
    = [1,2] ++ [2,Empty].flatmap(n => [n. n+1])
    = [1,2] ++ [2,3] ++ [Empty].flatmap(n => [n, n+1])
    = [1,2] ++ [2.3] ++ Empty
    == [1,2,2,3,Empty]
   */
  override def flatMap[B](transformer: A => MyList[B]): MyList[B] = {
    transformer(h) ++ t.flatMap(transformer)  // or transformer.apply(h)
  }
}

// As there are out-of-the box function types don't need them
//trait MyPredicate[-T] {  // Function type T => Boolean
//  def test(elem: T): Boolean
//}
//
//trait MyTransformer[-A, B] {  // Function type A => B
//  def transform(elem: A): B
//}

object ListTest extends App {
  val listOfIntEmpty: MyList[Int] = Empty
  println(listOfIntEmpty.toString)
  println(Empty.toString)
  println()
  val listOfInt = Cons(1, Cons(2, Cons(3, Empty)))
  val cloneListOfInt = Cons(1, Cons(2, Cons(3, Empty)))
  val anotherListOfInt = Cons(4, Cons(5, Empty))

  println(listOfInt.head)
  println(listOfInt.tail.head)
  println(listOfInt.add(4).head)
  println(listOfInt.toString)
  println()
  val listOfString = Cons("hello", Cons("Scala", Empty))
  println(listOfString.toString)

  //noinspection ConvertExpressionToSAM
  println(listOfInt.map(new Function1[Int, Int] {
    override def apply(elem: Int): Int = elem * 2
  }))
  println(listOfInt.map((elem: Int) => elem * 2))
  println(listOfInt.map(elem => elem * 2))
  println(listOfInt.map(_ * 2))

  //noinspection ConvertExpressionToSAM
  println(listOfInt.filter(new Function1[Int, Boolean] {
    override def apply(elem: Int): Boolean = elem % 2 == 0
  }))
  println(listOfInt.filter((elem: Int) => elem % 2 == 0))
  println(listOfInt.filter(elem => elem % 2 == 0))
  println(listOfInt.filter(_ % 2 == 0))

  println(listOfInt ++ anotherListOfInt)

  //noinspection ConvertExpressionToSAM
  println(listOfInt.flatMap(new Function1[Int, MyList[Int]] {
    override def apply(elem: Int): MyList[Int] = Cons(elem, Cons(elem + 1, Empty))
  }))

  println(listOfInt == cloneListOfInt)
}