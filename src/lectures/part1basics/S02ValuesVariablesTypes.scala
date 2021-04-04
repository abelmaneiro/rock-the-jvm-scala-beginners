package lectures.part1basics

object S02ValuesVariablesTypes extends App {

  val aString: String = "Hello" // Sequence of Chars
  val aBoolean: Boolean = true  // true or false
  val aChar: Char = 'A' // 16 bit unsigned Unicode character U+0000 to U+FFFF
  val aInt: Int = 32 // -2147483648 to 2147483647
  val aByte: Byte = 127  // -128 to 127
  val aShort: Short = 37 // -32768 to 32767
  val aLong: Long = 1L // -9223372036854775808 to 9223372036854775807
  val aDouble: Double = 3.12 // 64 bit IEEE 754 double-precision float
  val aFloat: Float = 2.3F // 32 bit IEEE 754 single-precision float

  val x = 42 // val immutable and type is optional as inferred
  var y = 43 // var mutable
  y = 10
  println(s"$x $y")
}
