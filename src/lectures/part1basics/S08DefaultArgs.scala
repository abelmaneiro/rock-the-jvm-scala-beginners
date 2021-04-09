package lectures.part1basics

object S08DefaultArgs extends App {

  def savePicture(name: String, format: String = "jpg", width: Int = 1920, height:Int  = 1080): Unit =
    println(s"Save $name, $format, $width, $height")
  savePicture("MyPic", width = 800)
}
