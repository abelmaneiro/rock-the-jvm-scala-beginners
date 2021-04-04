package lectures.part1basics

object S07CBNvsCBV extends App {

  def callByValue(x: Long): Unit = {
    println("By value " + x)
    println("By value " + x)
  }

  def callByName(x: => Long): Unit = {
    println("By name " + x)
    println("By name " + x)
  }

  def callByFunc(x: () => Long): Unit = {
    println("By func " + x())
    println("By func " + x())
  }
  callByValue(System.nanoTime())
  callByName(System.nanoTime())
  callByFunc(() => System.nanoTime())
}
