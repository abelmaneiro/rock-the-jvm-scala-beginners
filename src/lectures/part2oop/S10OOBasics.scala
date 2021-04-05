package lectures.part2oop

object S10OOBasics extends App {
  val fred = new Person("Fred", 15)
  println(fred.height + "cm")
  fred.greet("Jim")

  new Person("Lee").greet()
  new Person().greet()

  val author = new Writer("Charles", "Dickens", 1812)
  val imposter = new Writer("Charles", "Dickens", 1812)

  val novel = new Novel("Great Expectations", 1861, author)
  println(novel.authorAge)
  println(novel.isWrittenBy(author))
  println(novel.isWrittenBy(imposter))

  val counter = new Counter
  counter.increment.print()
  counter.increment(10).print()
}

class Person(name: String, val age: Int = 0) {
  var height = 150

  def greet(name: String): Unit = println(s"Hi $name, my name is ${this.name}")  // using this
  def greet(): Unit = println(s"My name is $name")  // overloaded

  def this() = { // Auxiliary constructor
    this("Henry", 0) //  must call constructor as the first thing
    height = 0
  }

  println("\nPerson instantiated")
}

class Writer(firstName: String, lastName: String, val yearOfBirth: Int) {
  def fullName: String = s"$firstName $lastName"
}

class Novel(name: String, yearOfRelease: Int, author: Writer) {
  def authorAge: Int = yearOfRelease- author.yearOfBirth
  def isWrittenBy(author: Writer): Boolean = {author == this.author}
  def copy(newYearOfRelease: Int): Novel = new Novel(name, newYearOfRelease, author)
}

class Counter(val value: Int = 0) {
  def increment: Counter = {
    println("Incrementing")
    new Counter(value + 1)
  }
  def increment(by: Int): Counter = if (by <= 0) this else increment.increment(by - 1)

  def decrement: Counter = {
    println("Decrementing")
    new Counter(value - 1)
  }
  def decrement(by: Int): Counter = if (by <= 0) this else decrement.decrement(by - 1)
  def print(): Unit = println("counter: " + value)

}

