package lectures.part2oop

object S19AnonymousClasses extends App {

  abstract class Animal {
    def eat: String
  }

  val funnyAnimal = new Animal { // anonymous class
    override def eat: String = "ah!!!!!!"
  }

  println(funnyAnimal.eat)
  println(funnyAnimal.getClass) // Compiler builds anonymous class $anon$1

  class Person(val name: String) {
    def sayHi = s"Hi, my name is $name"
  }
  val jim = new Person("Jim")
  println(jim.sayHi)
  println(jim.getClass)  // Name of class $Person

  val JimImposter = new Person("Jim") { // anonymous class
    override def sayHi: String = s" Hi, I'm pretending to be $name)"
  }
  println(JimImposter.sayHi)
  println(JimImposter.getClass) // Compiler builds anonymous class $anon$2
}
