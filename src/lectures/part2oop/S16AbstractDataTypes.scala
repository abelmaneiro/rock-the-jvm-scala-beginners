package lectures.part2oop

object S16AbstractDataTypes extends App{

  abstract class Animal {  // abstract class contain undefined fields methods
    val createType: String = "wild"
    def eat(): Unit
  }

  class Dog extends Animal { // makes concrete abstract class
    override val createType: String = "dog"
    override def eat(): Unit = println("crunch woof")
  }

  trait Carnivore {  // trait is abstract
    def eat(animal: Animal): Unit
    val preferredMeal: String = "fresh meat"
  }
  trait ColdBlooded

  class Crocodile extends Animal with Carnivore with ColdBlooded {
    override val createType: String = "crocodile"
    override def eat(): Unit = println("crunch splash")
    override def eat(animal: Animal): Unit = println(s"I am $createType and eating ${animal.createType}")
  }

  val dog = new Dog
  val crocodile = new Crocodile
  crocodile.eat()
  crocodile.eat(dog)


}
