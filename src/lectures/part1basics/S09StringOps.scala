package lectures.part1basics

object S09StringOps extends App {
  val name = "Jim"; val age = 8; val speed = 4.52546
  println(s"$name is ${age  + 1} next year and can run $speed miles per hour") // S-Interpolation
  // Jim is 9 next year and can run 4.52546 miles per hour
  println(f"$name%s is ${age  + 1}%02d next year and can run $speed%2.2f miles per hour") // F-Interpolation
  // Jim is 09 next year and can run 4.53 miles per hour
  println(raw"This is a \n newline symbol")  // Raw-Interpolation
  // This is a \n newline symbol
  val escaped = "This is a \n newline symbol"
  println(raw"$escaped")   // BUT raw ignores escape characters in Sting variables
  // This is a
  // newline symbol
}
