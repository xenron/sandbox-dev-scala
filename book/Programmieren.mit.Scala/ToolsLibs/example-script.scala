// code-examples/ToolsLibs/example-script.scala

case class Message(name: String)

def printMessage(msg: Message) = {
  println(msg)
}

printMessage(Message(
    "Skript kompilieren mit scalac -Xscript <name>!"))
