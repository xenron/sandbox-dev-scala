// code-examples/TypeSystem/bounds/abbrev-list.scala
// Stark vereinfachte Version der Scala-Bibliothek.

package bounds.abbrevlist

sealed abstract class AbbrevList[+A] {

  def isEmpty: Boolean
  def head: A
  def tail: AbbrevList[A]

  def ::[B >: A] (x: B): AbbrevList[B] = new bounds.abbrevlist.::(x, this)

  final def foreach(f: A => Unit) = {
    var these = this
    while (!these.isEmpty) {
      f(these.head)
      these = these.tail
    }
  }
}

// Die leere AbbrevList.

case object AbbrevNil extends AbbrevList[Nothing] {
  override def isEmpty = true

  def head: Nothing =
    throw new NoSuchElementException("Leere Liste hat keinen Anfang")

  def tail: AbbrevList[Nothing] =
    throw new NoSuchElementException("Leere Liste hat keinen Schwanz")
}

// Eine nicht-leere AbbrevList, charakterisiert durch einen Kopf und 
// einen Schwanz.

final case class ::[B](private var hd: B,
    private[abbrevlist] var tl: AbbrevList[B]) extends AbbrevList[B] {

  override def isEmpty: Boolean = false
  def head : B = hd
  def tail : AbbrevList[B] = tl
}
