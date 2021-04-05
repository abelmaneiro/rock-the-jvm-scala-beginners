package lectures.part2oop

object S18Generics extends App {

  // Generic class - can use generic type A where a normal type would be
  class MyList[+A] {  // A covariant class - Allow subtypes but not supertypes. A = Animal, then list can include Cats & Dogs
    def add[B >: A] (element: B): MyList[B] = {  // B must be a supertype of A. A = Animal, B = Sentient then resulting list is Sentient List
      println(element)
      new MyList[B]
    }
  }
  class MyMap[K, V]
  trait walkable[A]  // Generic trait

  val listOfIntegers = new MyList[Int]
  val listOfStrings = new MyList[String]

  object MyList {
    def empty[A]: MyList[A] = new MyList[A]  // Generic method
  }
  val emptyListOfIntegers = MyList.empty[Int]

  // variance problem
  class Sentient
  class Animal extends Sentient
  class Cat extends Animal

  // Invariant class, trait - Only allow of that class
  class InvariantList[A]
  //val invAnimalSentient: InvariantList[Animal] = new InvariantList[Sentient]// Type mismatch. Required: InvariantList[Animal], found: InvariantList[Sentient]
  val invAnimalAnimal: InvariantList[Animal] = new InvariantList[Animal]
  //val invAnimalCat: InvariantList[Animal] = new InvariantList[Cat] // Type mismatch. Required: InvariantList[Animal], found: InvariantList[Cat]

  // covariant class, trait -  Allow subtypes but not supertypes
  class CovariantList[+A]
  //val covAnimalSentient: CovariantList[Animal] = new CovariantList[Sentient] // Type mismatch. Required: CovariantList[Animal], found: CovariantList[Sentient]
  val covAnimalAnimal: CovariantList[Animal] = new CovariantList[Animal]
  val covAnimalCat: CovariantList[Animal] = new CovariantList[Cat]

  // contravariant class, trait - Allows supertypes but not subtypes
  class ContravariantList[-A]
  val conAnimalSentient: ContravariantList[Animal] = new ContravariantList[Sentient]
  val conAnimalAnimal: ContravariantList[Animal] = new ContravariantList[Animal]
 // val conAnimalCat: ContravariantList[Animal] = new ContravariantList[Cat] // Type mismatch. Required: ContravariantList[Animal], found: ContravariantList[Cat]

  // Upper & Lower  Type Bounds
  class ExampleList[A] {  // A covariant class - Allow subtypes but not supertypes. A = Animal, then list can include Cats & Dogs
    def addUpper[B >: A] (element: B): MyList[B] = {  // B must be a supertype of A. A = Animal, B = Sentient, then resulting list is Sentient List
      println(element)
      new MyList[B]
    }
    def addLower[B <: A] (element: B): MyList[B] = {  // B must be a subtype of A. A = Animal, B = Cat, then resulting list is Cat list
      println(element)
      new MyList[B]
    }
  }
  val animal = new ExampleList[Animal]
  val upperAnimalString = animal.addUpper("Hi") //val upperAnimalString: MyList[Object] //Common supertype of Animal & String is Object
  val upperAnimalSentient = animal.addUpper(new Sentient)  //val upperAnimalSentient: MyList[Sentient] //Common supertype of Animal and Sentient is Sentiment
  val upperAnimalAnimal = animal.addUpper(new Animal) //val upperAnimalAnimal: MyList[Animal] //Common supertype of Animal and Sentient is Animal
  val upperAnimalCat = animal.addUpper(new Cat)  //val upperAnimalCat: MyList[Animal] // Common supertype of Animal and Cat is Animal
  println()
  //val lowerAnimalString = animal.addLower("Hi") // inferred type arguments [String] do not conform to method addLower's type parameter bounds [B <: lectures.part2oop.S18Generics.Animal]
  //val lowerAnimalSentient = animal.addLower(new Sentient) //inferred type arguments [lectures.part2oop.S18Generics.Sentient] do not conform to method addLower's type parameter bounds [B <: lectures.part2oop.S18Generics.Animal]
  val lowerAnimalAnimal = animal.addLower(new Animal)  // val lowerAnimalAnimal: MyList[Animal]  //Common subtype of Animal and Animal is Animal
  val lowerAnimalCat = animal.addLower(new Cat) // val lowerAnimalCat: MyList[Cat] //Common subtype of Animal and Cat is Cat
}
