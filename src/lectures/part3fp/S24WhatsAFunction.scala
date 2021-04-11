package lectures.part3fp

object S24WhatsAFunction extends App {
  // DREAM: use functions as first class elements

  //Java & JVM designed around OOP (where instances of classes are king), so best which can be done, is to simulate FP
  trait MyActon[A, B] {
    def execute(element: A): B
  }

  // So Scala had to resort to cleaver tricks to make it look like a truly FP
  trait MyFunction[A, B] {  // las Type Param (i.e B) is the return type
    def apply(element: A): B // allows it to called as MyFunction(a)
  }
  // Instantiating MyFunction to simulate a function
  val double: MyFunction[Int, Int] = new MyFunction[Int, Int] {
    override def apply(element: Int):Int  = element * 2
  }
  println(double(2))  // Wow, it looks like a function call

  // Scala has out-of-the box Function types from 1 to 22. E.g. Function1[A, B]
  val stringToIntConverter: Function1[String, Int] = new Function1[String, Int] {
    override def apply(v1: String): Int = v1.toInt
  }
  println(stringToIntConverter("3") + 4)
  val adder: Function2[Int, Int, Int] = new Function2[Int, Int, Int] {
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  println(adder(2, 3))

  // there is a Syntactic Sugar way in Scala to do FunctionX. e.g. Function2[A, B, R] === (A, B) => R
  // SO ALL SCALA FUNCTIONS ARE INSTANCES OF FUNCTION1 .. 22
  // Sugar version of above
  val  stringToIntConverterSugar: String => Int = new (String => Int) {  // new Function1[String, Int]
    override def apply(v1: String): Int = v1.toInt
  }
  println(stringToIntConverterSugar("13") + 4)

  val adderSugar: (Int, Int) => Int = new ((Int, Int) => Int) {  // new Function[Int, Int, Int]
    override def apply(v1: Int, v2: Int): Int = v1 + v2
  }
  println(adderSugar(12, 3))

  // Even MORE Sugar, Single abstract method  e.g. def apply(v1: A, v2: B): R  === (v1: A, v2: B) => v1.toSting + v2.tSting
  val stringToIntConverterSugarPlus: String => Int = (v1: String) => v1.toInt  // def apply(v1: String): Int = v1.toInt
  println(stringToIntConverterSugarPlus("91"))

  val adderSugarPlus: (Int, Int) => Int = (v1: Int, v2: Int) => v1 + v2
  println(adderSugarPlus(20, 3))

  // Exercise
  val concatenateStrings: Function2[String, String, String] = new Function2[String, String, String]  {
    override def apply(v1: String, v2: String): String = v1 + v2
  }
  val concatenateStringsPlus: (String, String) => String = (v1: String, v2: String) => v1 + v2
  println(concatenateStrings("hello ", "World"))
  println(concatenateStringsPlus("hello ", "Scala"))


  val funcFuncAdd: Function1[Int, Function1[Int, Int]] = new Function1[Int, Function1[Int, Int]] {
    override def apply(a: Int): Function1[Int, Int] = new Function1[Int, Int] {
      override def apply(b: Int): Int = a + b
    }
  }
  val funcFuncAddPlus: Int => Int => Int = (a: Int) => (b: Int) => a + b
  println(funcFuncAdd(93)(2))  // curried function
  println(funcFuncAddPlus(83)(2))  // curried function
  val add3 = funcFuncAdd(3)
  println(add3(5))

}




