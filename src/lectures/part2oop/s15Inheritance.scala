package lectures.part2oop

object s15Inheritance extends App {

  // Visibility
  class Animal {  // Superclass of Cat
    private def forMeOnly = "my secret"  // Private - only visible in Animal class
    protected def forMeAndDescendants = "our secret" // Protected - Visible to subclasses
    def forEveryone = "No secrets here" // Public which is the default
    def eat(): Unit = println("munch munch")
    val creatureType = "wild"
    forMeOnly
  }
  class Cat extends Animal { //SubClass of Animal
   def crunch():Unit = {
     eat()
     println("crunch meow")
   }
  }

  val cat = new Cat
  cat.crunch()

  //Constructor
  class Person(val name: String, val age: Int){
    def this(name: String) = this(name, 0)
  }

  class Adult(name: String, age: Int, val idCard: String) extends Person(name, age) {}
  class Alien(name: String) extends Person(name)

  // Overriding
  class Dog(override val creatureType: String) extends Animal {
    // override val creatureType: String = "domestic"
    override def eat(): Unit = println("crunch woff")
  }
  val dog = new Dog("Domestic")
  dog.eat()
  println(dog.creatureType)

  // Polymorphism
  val unknownAnimal: Animal = new Dog("K9")
  unknownAnimal.eat()
}
