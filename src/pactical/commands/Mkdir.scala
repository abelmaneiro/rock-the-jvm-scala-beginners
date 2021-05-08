package pactical.commands
import pactical.files.{DirEntry, Directory}
import pactical.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = Directory.empty(state.wd.path, name)
}
