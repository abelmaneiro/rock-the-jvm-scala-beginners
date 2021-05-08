package pactical.commands

import pactical.files.{DirEntry, Directory}
import pactical.filesystem.State

abstract class CreateEntry(name: String) extends Command {
  override def apply(state: State): State = {
    val wd = state.wd
    if (wd.hasEntry(name)) state.setMessage("Entry " + name + "already exists")
    else if (name.contains("/")) state.setMessage(name + "must not contain separators")
    else if (checkIllegal(name)) state.setMessage(name + " illegal name!")
    else doCreateEntry(state, name)
  }

  def checkIllegal(str: String): Boolean = str.contains(".")

  def doCreateEntry(state: State, name: String): State = {
    def updateStructure(currentDirectory: Directory, path: List[String], newEntry: DirEntry): Directory = {
      /*
        someDir            new someDir    |   /a            new /a
          /a         ==>   /a             |     /b    ===>    mew /b
          /b               /b             |      /c                 /c
          (new) / d        /d             |      /d                 /d
                                          |      (new) /e           /e
       */
      if (path.isEmpty) currentDirectory.addEntry(newEntry)
      else {
        /*
          /a
            /b
              /c
              /d
              (new entry) /e
              currentDirectory = /a
              path = ["b'}
         */
        val oldEntry = currentDirectory.findEntry(path.head).asDirectory
        currentDirectory.replaceEntry(oldEntry.name, updateStructure(oldEntry, path.tail, newEntry))
      }
      /*
        /a
          /b
            (contents)
            (new entry) /e
    newRoot = updateStructure(root, ["a', "b"], /e)                   |   root.replaceEntry("a", updateStructure(/a, ["b"], /e)   |    /a.replaceEntry("b", updateStructure(/b, [], /e)      |   /b.add(/e)
          => path.isEmpty? No                                     |
          => oldEntry = /a                                        |
          root.replaceEntry("a", updateStructure(/a, ["b"], /e)   |    /a.replaceEntry("b", updateStructure(/b, [], /e)      |   /b.add(/e)
            => path.isEmpty? No                                   |
            => oldEntry = /b                                      |
            /a.replaceEntry("b", updateStructure(/b, [], /e)      |   /b.add(/e)
              => path.isEmpty? Yes  => /b.add(/e)                 |

      */
    }

    val wd = state.wd

    // Get all the directories in the full path
    val allDirsInPath = wd.getAllFoldersInPath
    // Create new directory in the Working directory
    //  val newDir = Directory.empty(wd.path, name)
    val newEntry = createSpecificEntry(state)
    // Update the while directory structure starting from the root
    val newRoot = updateStructure(state.root, allDirsInPath, newEntry)
    // Find new working directory INSTANCE given Working Directory's full path, in the NEW directory structure
    val newWd = newRoot.findDescendant(allDirsInPath)
    // New state
    State(newRoot, newWd)
  }

  def createSpecificEntry(state: State): DirEntry

}