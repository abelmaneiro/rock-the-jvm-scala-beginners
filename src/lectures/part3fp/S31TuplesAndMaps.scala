package lectures.part3fp

object S31TuplesAndMaps extends App {
  // tuples 1 to 22
  val tuple2a = new Tuple2[Int, String](1, "One")
  val tuple2b = Tuple2(2, "two")
  val tuple2c = (3, "Three") // Sugar for Tuple2(2, "two") or new Tuple2[Int, String](1, "One")
  println(tuple2a._1 + "-" +  tuple2a._2)  // 1-One
  println(tuple2a.copy(_2 = "Uno"))  // (1,Uno)
  println(tuple2a.swap)  // (One,1)

  // Map collection
  val aMap: Map[String, Int] = Map()  // empty Map
  val phoneBook = Map(("Jim", 555), "Daniel" -> 789).withDefaultValue(-1)  // key -> value is sugar for (key, value)
  println(phoneBook)
  println(phoneBook.contains("Jim")) // true
  println(phoneBook("Jim"))  // 555
  println(phoneBook("Mary"))  // -1
  println(phoneBook.get("Jim")) // Some(555)
  println(phoneBook.getOrElse("Mary", -2))  // -2
  println(phoneBook + ("Sue" -> 333)) // Map(Jim -> 555, Daniel -> 789, Sue -> 333) // Note the () around Tuple2
  println(phoneBook - "Daniel") // Map(Jim -> 555)
  // map, flatMap, filter
  println(phoneBook.map(pair => pair._1 + "-" + pair._2))  // List(Jim-555, Daniel-789)
  println(phoneBook.map{ case (k, v) => k + "-" + v})  // List(Jim-555, Daniel-789)  // Need {} around as case
  println(phoneBook.mapValues(v => v * 10)) // Map(Jim -> 5550, Daniel -> 7890)
  println(phoneBook.filterKeys(k => k.startsWith("J")))  // Map(Jim -> 555)
  // groupBy
  println(List("Bob", "James", "Angela", "Mary", "Daniel", "Jim").groupBy(x => x.charAt(0)))
    // Map(J -> List(James, Jim), A -> List(Angela), M -> List(Mary), B -> List(Bob), D -> List(Daniel))

}
