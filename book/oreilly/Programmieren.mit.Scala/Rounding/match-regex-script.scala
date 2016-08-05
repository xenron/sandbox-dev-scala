// code-examples/Rounding/match-regex-script.scala

val BookExtractorRE = """Buch: Titel=([^,]+),\s+Autoren=(.+)""".r
val MagazineExtractorRE = """Zeitschrift: Titel=([^,]+),\s+Ausgabe=(.+)""".r

val catalog = List(
  "Buch: Titel=Programmieren mit Scala, Autoren=Dean Wampler, Alex Payne",
  "Zeitschrift: Titel=The New Yorker, Ausgabe=Januar 2009",
  "Buch: Titel=Krieg und Frieden, Autoren=Leo Tolstoi",
  "Zeitschrift: Titel=The Atlantic, Ausgabe=Februar 2009",
  "Falsche Daten: Text=Wer hat dies hier eingetragen??"
)

for (item <- catalog) {
  item match {
    case BookExtractorRE(title, authors) =>
      println("Buch \"" + title + "\", von " + authors)
    case MagazineExtractorRE(title, issue) =>
      println("Zeitschrift \"" + title + "\", Ausgabe " + issue)
    case entry => println("Eintrag nicht erkannt: " + entry)
  }
}
