// code-examples/BasicOOP/ui/widget.scala

package ui

abstract class Widget {
  class Properties {
    import scala.collection.immutable.HashMap

    private var values: Map[String, Any] = new HashMap

    def size = values.size

    def get(key: String) = values.get(key)

    def update(key: String, value: Any) = {
      // Führe irgendwelche Verarbeitungen durch,
      // z.B. Filtern.
      values = values.updated(key, value)
      // Führe Nachverarbeitungen durch.
    }
  }

  val properties = new Properties
}