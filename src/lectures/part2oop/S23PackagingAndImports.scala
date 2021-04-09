package lectures.part2oop  // in file lectures/part2oop/S23PackagingAndImports

import playground.{Cinderella, PrinceCharming => Prince} // Package Alias change PrinceCharming to Prince

object S23PackagingAndImports {
  val writer = new Writer("Jim", "Cross", 2049)

  val princess = new Cinderella
  val princess2 = new playground.Cinderella  // fully qualified name

  // Package Object field
  sayHello()
  println(SPEED_OF_LIGHT)

  val prince = new Prince  // Package Alias changed name to Prince instead of PrinceCharming


}
