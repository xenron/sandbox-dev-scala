// code-examples/TypeLessDoMore/relative-imports.scala

import scala.collection.mutable._
import collection.immutable._             // Da "scala" bereits importiert ist
import _root_.scala.collection.generic._  // Vollständiger Pfad von der "echten" Wurzel an

package scala.actors {
  import remote._                         // Wir sind im Scope von "scala.actors"
}
