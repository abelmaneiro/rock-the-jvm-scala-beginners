package pactical.commands
import pactical.files.{DirEntry, Directory}
import pactical.filesystem.State

import scala.annotation.tailrec

class Cd(dir: String) extends Command {

  override def apply(state: State): State = {
    /*
    cd /folder1/folder2/.../folderN   // absolute path
    cd folder3/folder3/../folderN     // relative path
    cd ..   // relative token parent directory
    cd .    // relative token this directory
    cd folder/./.././a/
   */

    // find root
    val root = state.root
    val wd = state.wd

    // find absolute path of directory I want to go to
    val absolutePath = if (dir.startsWith(Directory.SEPARATOR)) dir
    else if (wd.isRoot) wd.path + dir
    else wd.path + Directory.SEPARATOR + dir

    // find the directory to cd to, given the path
    val destinationDirectory = doFindEntry(root, absolutePath)

    // change to the state given the new directory
    if (destinationDirectory == null || !destinationDirectory.isDirectory) state.setMessage(dir + " no such directory")
    else State(root, destinationDirectory.asDirectory)
  }
  def doFindEntry(root: Directory, path: String): DirEntry = {
    @tailrec
    def findEntryHelper(currentDirectory: Directory, path: List[String]): DirEntry = {
      if (path.isEmpty || path.head.isEmpty) currentDirectory
      else if (path.tail.isEmpty) currentDirectory.findEntry(path.head)
      else {
        val nextDir = currentDirectory.findEntry(path.head)
        if (nextDir == null || !nextDir.isDirectory) null
        else findEntryHelper(nextDir.asDirectory, path.tail)
      }
    }

    @tailrec
    def collapseRelativeTokens(paths: List[String], result: List[String]): List[String] = {
      /*
        a/. --> ["a","."] --> ["a"]
        a/b/./. --> ["a", "b", "." "."] --> ["a", "b"]
        a/../  --> ["a", ".."] --> ["a", ..] --> []
        a/b/../ --> ["a", "b", ".."] --> ["a"]
      */
      if (paths.isEmpty) result
      else if (paths.head.equals(".")) collapseRelativeTokens(paths.tail, result)
      else if (paths.head.equals("..")) {
        if (result.isEmpty) null
        else collapseRelativeTokens(paths.tail, result.init)
      // else is not . or ..
      } else collapseRelativeTokens(paths.tail, result :+ paths.head)
    }

    // get tokens
    val tokens = path.substring(1).split(Directory.SEPARATOR).toList
    val newTokens = collapseRelativeTokens(tokens, List())
    // navigate to the correct entry
    if (newTokens == null) null
    else findEntryHelper(root, newTokens)
  }

}
