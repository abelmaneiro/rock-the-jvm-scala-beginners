package lectures.part3fp

import scala.util.Random

object S33Options extends App {

  val myOption1: Option[Int] = Some(4) //Some(4)
  val myOption2: Option[Int] = Option(4) //Some(4)
  val noOption1: Option[Int] = None  //None
  val noOption2: Option[String] = Option(null) //None  // Better to use Option(result) in case result is null
  val neverDoOp: Option[String] = Some(null)  // Some(null)  // Never use Some(result)
  println(s"$myOption1  $myOption2 ${myOption1 == myOption2}")  // Some(4)  Some(4) true
  println(s"$noOption1  $noOption2 ${noOption1 == noOption2}")  // None  None true  //  Note: noOption1 is of type Int.

  // Working and changing unsafe APIs using .orElse
  def unSafeMethod1(): String = null
  def unSafeMethod2(): String = null
  def backupMethod(): String = "Valid result"
  val neverDo = Some(unSafeMethod1())  // Some(null)
  val correctWay = Option(unSafeMethod1())  // None
  println(s"$neverDo $correctWay ${neverDo == correctWay}")  //Some(null) None false
  val chainedResult = Option(unSafeMethod1()).orElse(Option(unSafeMethod2())).orElse(Option(backupMethod()))
  println(chainedResult)

  // Safe ways to define APIs
  def betterUnsafeMethod1(): Option[String] = Option(null)
  def betterUnsafeMethod2(): Option[String] = None
  def betterBackupMethod(): Option[String] = Option("Better valid result")
  val betteChainedResult = betterUnsafeMethod1() orElse betterUnsafeMethod2() orElse betterBackupMethod()
  println(betteChainedResult)

  // function on Options
  println(myOption1.isEmpty)  // false
  println(myOption1.get)  // 4   // unsafe as will blow up for None/null
  println(noOption1.getOrElse(-1))  // -1
  myOption1.foreach(println)  // 4

  // map, flatMap, filter
  println(myOption1.map(_ * 10))  // Some(40)
  println(noOption1.map(_ * 10))  // None
  println(myOption1.filter(_ % 2 == 0))  // Some(40)
  println(myOption1.filter(_ % 2 != 0))  // None
  println(myOption1.flatMap(x => Option(x * 100)))  // Some(400)

  // Exercise
  val config = Map("host" -> "176.45.36.1", "port" -> "80")  // fetched form elsewhere
  class Connect {
    def connect = "Connected"  // connect to some server
  }
  object Connect {
    val random = new Random(System.nanoTime())
    def apply(host: String, port: String): Option[Connect] = if(random.nextBoolean()) Some(new Connect) else None
  }

  println("XXX")
  val host = config.get("host")
  val port = config.get("port")
  val con1 = host.flatMap(h => port.flatMap(p => Connect(h, p)))
  val conStatus1 = con1.map(c => c.connect)
  println("Map 1 " + conStatus1)
  conStatus1.foreach(println)

  val conStatus1a = config.get("host")
    .flatMap (host => config.get("port")
      .flatMap(port => Connect(host, port)
        .map(con => con.connect)
      )
    )
  println("Map 2 " + conStatus1a)
  conStatus1a.foreach(println)

  val  conStatus2 = for {
    host <- config.get("host")
    port <- config.get("port")
    con <- Connect(host, port)
  } yield con.connect
  println("For " + conStatus2)
  conStatus2.foreach(println)

}
