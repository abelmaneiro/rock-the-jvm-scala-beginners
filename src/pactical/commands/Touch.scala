package pactical.commands
import pactical.files.{DirEntry, File}
import pactical.filesystem.State

class Touch(name: String) extends CreateEntry(name) {
  override def createSpecificEntry(state: State): DirEntry = File.empty(state.wd.path, name)
}
