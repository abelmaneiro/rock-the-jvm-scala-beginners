package pactical.commands
import pactical.files.{Directory, File}
import pactical.filesystem.State

import scala.annotation.tailrec

class Echo(args: Array[String]) extends Command {
  override def apply(state: State): State = {
    /*
      if no args, do nothing return state
      else if just one arg, print to console
      else {
        operator = next to last argument
        if operator is > echo to file
        else if operator is >> append to file
        else echo everything to console
     */
    if (args.isEmpty) state
    else if (args.length == 1) state.setMessage(args(0))
    else {
      val operator = args(args.length - 2)
      val filename = args(args.length - 1)
      val contents = createContent(args, args. length - 2)
      if (">>".equals(operator)) doEcho(state, contents, filename, append = true)
      else if (">".equals(operator)) doEcho(state, contents, filename, append = false)
      else state.setMessage(createContent(args, args. length))
    }
  }

  def getRootAfterEcho(currentDirectory: Directory, path: List[String], contents: String, append: Boolean): Directory = {
    /*
      if path is empty then fail by returning currentDirectory
      else if no more things to explore i.e path.tail.isEmpty then
        find the file to create/add content to
        if file not found, create file
        else if the entry is actually a directory, the fail
        else
          replace or append content to the file
          replace the entry with the filename with the NEW file
      else
        find th enet directory to navigate
        call gTAE recursively on that

        if recursive call failed, fail
        else relocate entry with the NEW directory after the recursive call
     */
    if (path.isEmpty) currentDirectory
    else if (path.tail.isEmpty) {
      val dirEntry = currentDirectory.findEntry(path.head)
      if (dirEntry == null) currentDirectory.addEntry(new File(currentDirectory.path, path.head, contents))
      else if (dirEntry.isDirectory) currentDirectory
      else {
        if (append) currentDirectory.replaceEntry(path.head, dirEntry.asFile.appendContents(contents))
        else currentDirectory.replaceEntry(path.head, dirEntry.asFile.setContents(contents))
      }
    } else {
      val nextDirectory = currentDirectory.findEntry(path.head).asDirectory
      val newNextDirectory = getRootAfterEcho(nextDirectory, path.tail, contents, append)
      if (newNextDirectory == nextDirectory) currentDirectory
      else currentDirectory.replaceEntry(path.head, newNextDirectory)
    }
  }

  def doEcho(state: State, contents: String, filename: String, append: Boolean): State = {
    if (filename.contains(Directory.SEPARATOR)) state.setMessage("Echo: Filename must not contain separators")
    else {
      val newRoot = getRootAfterEcho(state.root, state.wd.getAllFoldersInPath :+ filename, contents, append)
      if (newRoot == state.root) state.setMessage(filename + " no such file")
      else State(newRoot, newRoot.findDescendant(state.wd.getAllFoldersInPath))
    }
  }

  def createContent(args: Array[String], topIndex: Int): String = {  // non-inclusive
    @tailrec
    def createContentHelper(currentIndex: Int, accumulator: String): String = {
      if (currentIndex >= topIndex) accumulator
      else {
        createContentHelper(currentIndex + 1, accumulator + " " + args(currentIndex))
      }
    }
    createContentHelper(0, "")
  }

}
