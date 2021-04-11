package lectures.part3fp

object s28MapFlatmapFilterFor extends App {

  val list = List(1,2,3)  // List created using the apply() method of List Companion Option
  println(list)
  println(list.head)
  println(list.tail)


  // map
  println(list.map(_ + 1))
  println(list.map(_ + " is a number")) // List(1 is a number, 2 is a number, 3 is a number)

  // filter
  println(list.filter(_ % 2 == 0)) // List(2)

  // flatMap
  val toPair: Int => List[Int] = (x: Int) => List(x, x + 10)
  def twoPair(x: Int): List[Int] =  List(x, x + 20)
  println(list.flatMap(toPair) + " vs " + list.map(toPair)) // List(1, 11, 2, 12, 3, 13) vs List(List(1, 11), List(2, 12), List(3, 13))

  println(list.flatMap(twoPair) + " vs " + list.map(twoPair))
  println(list.flatMap(x => List(x, x + 10)))  // List(1, 11, 2, 12, 3, 13)

  val numbers = List(1,2,3,4)
  val chars = List('a','b','c','d')
  val colors = List("black", "white")

  // How to do Iterations (foreach map, flatMap, for comprehension and filter )
  println(chars.flatMap(s => numbers.flatMap(n => colors.map(c => "" + s + n + c))))
  // for comprehension with yield
  println(for {
    s <- chars    // flatmap
    n <- numbers  // flatmap
    c <- colors   // map
  } yield "" + s + n + c)
  //List(a1black, a1white, a2black, a2white, a3black, a3white, a4black, a4white, b1black, b1white, b2black, b2white, b3black, b3white, b4black, b4white, c1black, c1white, c2black, c2white, c3black, c3white, c4black, c4white, d1black, d1white, d2black, d2white, d3black, d3white, d4black, d4white)

  println(chars.flatMap(s => numbers.filter(_ % 2 == 0).flatMap(n => colors.map(c => "" + s+ n + c))))
  println(for {
    s <- chars
    n <- numbers if n % 2 == 0 // filter (an if guard)
    c <- colors
  } yield "" + s + n + c)
  //List(a2black, a2white, a4black, a4white, b2black, b2white, b4black, b4white, c2black, c2white, c4black, c4white, d2black, d2white, d4black, d4white)

  // foreach
  list.foreach(println)
  // for comprehension without yield
  for {
    n <- numbers
  } println(n)

}
