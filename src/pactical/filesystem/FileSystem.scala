package pactical.filesystem

import pactical.commands.Command
import pactical.files.Directory

// import java.util.Scanner

object FileSystem extends App {

  val root = Directory.ROOT
//  var state = State(root, root)
//  val scanner = new Scanner(System.in)
//  while(true) {
//    state.show()
//    val input = scanner.nextLine()
//    state = Command.from(input)(state)  // Command.from(input).apply(state)
//  }

  /*
    [1,2,3,4].foldLeft(0)((acc, v) => acc + v)
      0 + 1 = 1
      1 + 2 = 3
      3 + 3 = 6
      6 + 4 = 10
   */
  io.Source.stdin.getLines().foldLeft(State(root, root)) ((currentState, newLine) => {
    currentState.show()
    Command.from(newLine).apply(currentState)
  })



}
