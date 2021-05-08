package pactical.files

import pactical.filesystem.FileSystemException

class File(override val parentPath: String, override val name: String, contents: String)
  extends DirEntry(parentPath, name) {

  override def asDirectory: Directory = throw new FileSystemException("A File can not be converted to a Directory")
  override def asFile: File = this


  override def isDirectory: Boolean = false
  override def isFile: Boolean = true
  override def getType: String = "File"
}

object File {
  def empty(parentPath: String, name: String): File = new File(parentPath, name, "")
}