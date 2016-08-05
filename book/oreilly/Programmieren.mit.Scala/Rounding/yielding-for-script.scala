// code-examples/Rounding/yielding-for-script.scala

val dogBreeds = List("Doberman", "Yorkshire-Terrier", "Dachshund",
                     "Schottischer Terrier", "Deutsche Dogge", "Portugiesischer Wasserhund")
val filteredBreeds = for {
  breed <- dogBreeds
  if breed.contains("Terrier")
  if !breed.startsWith("Yorkshire")
} yield breed

println(filteredBreeds)