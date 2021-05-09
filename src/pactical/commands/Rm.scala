package pactical.commands
import pactical.files.Directory
import pactical.filesystem.State

class Rm (name: String) extends Command {
  override def apply(state: State): State = {
    def doRm(state: State, path: String): State = {
      /*
        /a => [a] => rmHelper(/, ["a"])  // remove a
          is path empty? No
          is tail empty? Yes
            new root without the folder a

        /a/b => [a, b] => rmHelper(/, [a, b])  // remove b
          is path empty? No
          is tail empty? No
          nextDirectory = /a
          is nextDirectory a directory? Yes
          --------
          rmHelper(/a, [b])
            is path empty? No
            is tail empty? No
            nextDirectory = /b
            is nextDirectory a directory? Yes
            rmHelper(/b, [])
            is path empty? No
            is tail empty? Yes
            remove entry /b and return new /a
          --------
          is New Next the same as Next directory, i.e. nothing has changed? No
          replace /a with new /a
       */
      def rmHelper(currentDirectory: Directory, path: List[String]): Directory = {
        if (path.isEmpty) currentDirectory
        else if (path.tail.isEmpty) currentDirectory.removeEntry(path.head)
        else {
          val nextDirectory = currentDirectory.findEntry(path.head)
          if (!nextDirectory.isDirectory) currentDirectory
          else {
            val newNextDirectory = rmHelper(nextDirectory.asDirectory, path.tail)
            if (newNextDirectory == nextDirectory) currentDirectory
            else currentDirectory.replaceEntry(path.head, newNextDirectory)
          }
        }
      }

      val tokens = path.substring(1).split(Directory.SEPARATOR).toList
      val newRoot: Directory = rmHelper(state.root, tokens)
      if (newRoot == state.root) state.setMessage(path + ":no such file or directory")
      else State(newRoot, newRoot.findDescendant(state.wd.path.substring(1)))
    }

    // ger working directory
    val wd = state.wd
    // get absolute path
    val absolutePath = if (name.startsWith(Directory.SEPARATOR)) name
    else  if (wd.isRoot) wd.path + name
    else wd.path + Directory.SEPARATOR + name
    // do some checks
    if (Directory.ROOT_PATH.equals(absolutePath)) state.setMessage("Can not remove root " + Directory.ROOT_PATH)
    else {
      // find the entry to remove & update structure like for mkdir
      doRm(state, absolutePath)
    }
  }
}
