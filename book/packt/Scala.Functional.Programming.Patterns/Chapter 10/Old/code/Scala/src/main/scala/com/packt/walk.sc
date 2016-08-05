import java.io.File

def walkTree(file: File): Iterable[File] = {
  val children = new Iterable[File] {
    def iterator = if (file.isDirectory) file.listFiles.iterator else Iterator.empty
  }
  Seq(file) ++: children.flatMap(walkTree(_))
}

val dir = new File("/Users/Atulkhot/scalabook/chapter10/code/data")
for(f <- walkTree(dir) if f.getName.endsWith(".txt")) println(f)