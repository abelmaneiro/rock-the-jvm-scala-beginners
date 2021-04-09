package lectures.part2oop

object S14Objects extends App {

  // Unlike Java which has static final, Scala doesn't have Class-Level functionality. Instead it as object
  object Person {  // Companion object has static/class level functionality
    println ("Person Singleton Object created")
//    val N_EYES = 2
//    def canFly = false
    def apply(name: String ): Person = new Person(name) // Factory methods
    def apply(): Person = new Person("Doe")
  }
  class Person(val name: String) { } // Companion class has instance level functionality

  // works on object Person
  println("Before person1")   // Console -> Before person1
  val person1 = Person        // Console -> Person Singleton Object created
  println ("Before person2")  // Console -> Before person2
  val person2 = Person        // Console -> <!!! nothing outputted !!!>
  println("After")            // Console -> After
  println(person1 == person2) // Console -> true

  // Using object Person factory methods to create instances of Person class
  val sam = Person("Sam")  // same as Person.apply("Sam")
  val doe = Person()       // same as Person.apply(). Without the () it would assign the object Person instead


}
