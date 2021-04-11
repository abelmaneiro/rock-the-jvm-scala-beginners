package excerises

abstract class Maybe[+T] {
  // Needed for For comprehension
  def map[B](transformer: T => B): Maybe[B]
  def flatMap[B](transformer: T => Maybe[B]): Maybe[B]
  def filter(predicate: T => Boolean): Maybe[T]
  def withFilter(predicate: T => Boolean): Maybe[T]
}


case object MaybeNot extends Maybe[Nothing] {
  override def map[B](transformer: Nothing => B): Maybe[B] = MaybeNot

  override def flatMap[B](transformer: Nothing => Maybe[B]): Maybe[B] = MaybeNot

  override def filter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot

  override def withFilter(predicate: Nothing => Boolean): Maybe[Nothing] = MaybeNot
}

case class Just[+T](value: T) extends Maybe[T] {
  override def map[B](transformer: T => B): Maybe[B] = Just(transformer(value))

  override def flatMap[B](transformer: T => Maybe[B]): Maybe[B] = transformer(value)

  override def filter(predicate: T => Boolean): Maybe[T] = if (predicate(value)) this else MaybeNot

  override def withFilter(predicate: T => Boolean): Maybe[T] = if (predicate(value)) this else MaybeNot
}

object MaybeTest extends App {
  val just3 = Just(3)
  val just4 = Just(4)
  val maybeNot: Maybe[Int] = MaybeNot
  println(just3)
  println(just3.map(_ * 2))
  println(just3.flatMap(x => Just(x % 2 == 0)))
  println(just3.filter(_ % 2 == 0))
  println()
  println(maybeNot)
  println(maybeNot.map(x => x * 2))
  println(maybeNot.flatMap(x => Just(x % 2 == 0)))
  println(maybeNot.filter(_ % 2 == 0))
  println()
  println(for (j <- just3) yield j * 5)
  println(for {
    j3 <- just3
    j4 <- just4
  } yield j3 * j4)
  println(for {
    j3 <- just3 if j3 != 3
    j4 <- just4
  } yield j3 * j4)
  println(for {
    j3 <- just3 if j3 == 3
    j4 <- just4
  } yield j3 * j4)
  println(for {
    j3 <- just3
    j <- maybeNot
    j4 <- just4
  } yield j * j3 * j4)




}