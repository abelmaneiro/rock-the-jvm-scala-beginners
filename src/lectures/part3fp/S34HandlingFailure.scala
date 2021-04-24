package lectures.part3fp

import scala.util.{Failure, Random, Success, Try}

object S34HandlingFailure extends App {
/*
  // try-catch-final block. Multiple nested try's make it hard to follow code and can't chain them together
  try {
    // some code which may throw an exception
  } catch {
    case _: Exception => // handle Exception
    case _: RuntimeException => // Something really bad went wrong
  } finally {
    // do any cleanup
  }

  // Instead use the Try wrapper class
  sealed abstract class Try[+T]
  case class Failure[+T](t: Throwable) extends Try[T]
  case class Success[+T](value: T) extends Try[T]
  */

  //create success and failure manually
  val aSuccess: Try[Int]  = Success(3)  // Success(3)
  val aFailure: Try[Int] = Failure(new RuntimeException("*Failure*")) // Failure(java.lang.RuntimeException: *Failure*)
  val aTrySuccess: Try[Int] = Try(4) // Success(4)  // Better to use Try(result)
  val aTryFailure: Try[Int] = Try( throw new RuntimeException("*Fail*")) //Failure(java.lang.RuntimeException: *Fail*)
  println(aSuccess)
  println(aFailure)
  println(aTrySuccess)
  println(aTryFailure)

  def unsafeMethod(): String = throw new RuntimeException("*No String*")
  def backupMethod(): String = "It worked"

  // create try instance via apply method
  val potentialFailure1 = Try.apply(unsafeMethod())  //Failure(java.lang.RuntimeException: *No String*)
  println(potentialFailure1)
  val potentialFailure2 = Try { //sugar way using {}
     // code which might throw exception
    unsafeMethod()
   }

  // methods
  println(potentialFailure1.isSuccess)
  println(potentialFailure1.isFailure)
  val fallbackTry = Try(unsafeMethod()).orElse(Try(backupMethod()))
  fallbackTry.foreach(println)  //It worked

  // Better way to handle exception with Try
  def betterUnsafeMethod(): Try[String] = Try(throw new RuntimeException("*Not again*"))
  def betterBackupMethod(): Try[String] = Try("Wow, it worked")
  val betterFallbackTry = betterUnsafeMethod() orElse betterBackupMethod()
  betterFallbackTry.foreach(println)  //Wow, it worked

  // map, flatMap, filter
  println(aSuccess.map(_ * 2))  // Success(6)
  println(aFailure.map(_ * 2))  // Failure(java.lang.RuntimeException: *Failure*)
  println(aSuccess.flatMap(x => Try(x * 10)))  // Success(30)
  println(aSuccess.filter(_ > 10))  // Failure(java.util.NoSuchElementException: Predicate does not hold for 3)

  println()
  // Exercise
  val host = "localhost"
  val port = "8080"

  def renderHTML(page: String): Unit = println(page)

  class Connection {
    val random = new Random(System.nanoTime())
    def get(url: String): String = {
      if (random.nextBoolean()) s"<HTML>.$url.</HTML>"
      else throw new RuntimeException("Connection Interrupted")
    }
    def getSafe(url: String): Try[String] = Try(get(url))
  }

  object HttpService {
    val random = new Random(System.nanoTime())
    def getConnection(host: String, port: String): Connection = {
      if (random.nextBoolean()) new Connection
      else throw new RuntimeException(s"Someone took IP $host port $port")
    }
    def getConnectionSafe(host: String, port: String): Try[Connection] = Try(getConnection(host, port))
  }

  val possibleHTML1 = Try(HttpService.getConnection(host, port))
      .flatMap(server => Try(server.get("/home")))
  println("FlatMap " + possibleHTML1)
  possibleHTML1.foreach(renderHTML)

  val possibleHTML2 = for {
    connection <- Try(HttpService.getConnection(host, port))
    getHTML <- Try(connection.get("/parent"))
  } yield getHTML
  println("For " + possibleHTML2)
  possibleHTML2.foreach(renderHTML)

  println("Daniels long version")
  val possibleConnection = HttpService.getConnectionSafe(host, port)
  val possibleHTML = possibleConnection.flatMap(_.getSafe("Daniel-Long"))
  possibleHTML.foreach(renderHTML)

  println("Daniels flatmap version")
  HttpService.getConnectionSafe(host, port)
    .flatMap(connection => connection.getSafe("Daniel-flatmap"))
    .foreach(renderHTML)

  println("Daniels for version")
  for {
    connection <- HttpService.getConnectionSafe(host, port)
    html <- connection.getSafe("Daniel-for")
  } renderHTML(html)   // foreach

  /*
  try {
    connection = HttpService.getConnection(host, port)
    try {
      page = connection.get("/home")
      renderHTML(page)
    } catch (some other exception) {
    }
  } catch (exception) {
  }
 */


}
