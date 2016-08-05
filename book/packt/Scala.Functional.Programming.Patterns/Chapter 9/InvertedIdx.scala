object InvertedIdx extends App {
  val m = List("Carr" -> "And So To Murder",
               "Carr" -> "The Arabian Nights Murder",
                "Carr" -> "The Mad Hatter Mystery",
    "Christie" -> "The Murder Of Roger Ackroyd",
    "Christie" -> "The Sittaford Mystery",
    "Carr" -> "The Plague Court Murders")
  val ignoreWordsSet = Set("To", "The", "And", "So", "Of")
  val invertedIdx = (for {
    (k, v) <- m
    w <- v.split("\\W")
    if !ignoreWordsSet.contains(w)
  }
    yield(w -> k)).groupBy(_._1).map { case (k,v) => (k,v.map(_._2))}
  println(invertedIdx)
}
