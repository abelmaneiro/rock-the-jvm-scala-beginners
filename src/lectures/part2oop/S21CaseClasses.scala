package lectures.part2oop

//noinspection RedundantNewCaseClass
object S21CaseClasses extends App {
  case class Person(name: String, age: Int)  // case class:
  // 1. class parameters are val fields
  val jim = new Person("Jim", 34)
  // 2. sensible toString & println(instance) is actually println(instance.toString)
  println(jim)  // console-> Person(Jim,34)
  // 3. equals and hashcode implemented
  println(jim == new Person("Jim", 34))  // console-> true
  // 4. have copy method
  println(jim.copy(age=16))  // console -> Person(Jim,16)
  // 5. have a Companion Object & have a default apply factory constructor
  val thePersonCompanionObject = Person
  val mary = Person.apply("Mary", 21)
  val Jane = Person("Jane", 34)
  // 6. are serializable
  // 7. have extractor patterns so can be used in PATTEN MATCHING

  case object UnitedKingdom  // case class:
  // share all the benefits expect the Companion Object as they are already objects

}
