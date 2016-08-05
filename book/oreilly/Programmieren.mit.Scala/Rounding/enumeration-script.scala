// code-examples/Rounding/enumeration-script.scala

object Breed extends Enumeration {
  val doberman = Value("Dobermann-Pinscher")
  val yorkie = Value("Yorkshire-Terrier")
  val scottie = Value("Schottischer Terrier")
  val dane = Value("Deutsche Dogge")
  val portie = Value("Portugiesischer Wasserhund")
}

// Eine Liste der Rassen mit ihren IDs ausgeben
println("ID\tRasse")
for (breed <- Breed.values) println(breed.id + "\t" + breed)

// print a list of Terrier breeds
println("\nNur Terrier:")
Breed.values.filter(_.toString.endsWith("Terrier")).foreach(println)