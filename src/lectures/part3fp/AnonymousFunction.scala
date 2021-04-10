package lectures.part3fp

object AnonymousFunction extends App {
  // creates Anonymous class, the OO way
  val doubler = new Function1[Int, Int] {
    override def apply(v1: Int): Int = v1 * 2
  }

  // creates Anonymous functions (LAMBDA)
  val doublerSugar1: Int => Int = (v1: Int) => v1 * 2  // creates a Function1 and overrides apply()
  val doublerSugar2: Int => Int = v1 => v1 * 2
  val doublerSugar3 = (v1: Int) => v1 * 2
  val doublerSugar4: Int => Int = _ * 2
  println(doublerSugar1(2))
  println(doublerSugar2(3))
  println(doublerSugar3(4))

  val adder1: (Int, Int) => Int = (v1: Int, v2: Int) => v1 + v2
  val adder2: (Int, Int) => Int = (v1, v2) => v1 + v2
  val adder3 = (v1: Int, v2: Int) => v1 + v2
  println(adder1(1,0))
  println(adder2(2,0))
  println(adder3(3,0))

  val justDoSomething1: () => String = () => "Just do something 1"
  val justDoSomething2: () => String = () => "Just do something 2"
  val justDoSomething3 = () => "Just do something 3"
  println(justDoSomething1())
  println(justDoSomething2())
  println(justDoSomething3())
  //WARNING when calling Lambdas must include ()
  println(justDoSomething1)  // prints lectures.part3fp.AnonymousFunction$$$Lambda$16/434091818@3d24753a

  val stringToInt1 = (s: String) =>
    s.toInt
  val StringToInt2 = { (s: String) =>  // sometimes people put { } around lambdas
    s.toInt
  }

  // more sugar
  val niceInc1 =  (n: Int) => n + 1
  val niceInc2: Int => Int = _ + 1

  val niceAdder1: (Int, Int) => Int = (x: Int, y: Int) => x + y
  val niceAdder2: (Int, Int) => Int = (x, y) => x + y
  val niceAdder3: (Int, Int) => Int = _ + _
  val niceAdder4 = (x: Int, y: Int) => x + y

  val supperAdd1: Int => Int => Int = (x: Int) => (y: Int) => x + y
  val supperAdd2: Int => Int => Int = x => y => x + y
  val supperAdd3: Int => Int => Int = x => x + _
  val supperAdd4 = (x: Int) => (y: Int) => x + y


  println(supperAdd1(10)(1))
  println(supperAdd2(20)(1))
  println(supperAdd3(30)(1))
  println(supperAdd4(40)(1))



}
