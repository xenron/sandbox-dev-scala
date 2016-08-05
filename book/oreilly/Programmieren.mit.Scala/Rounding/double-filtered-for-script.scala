// code-examples/Rounding/double-filtered-for-script.scala

val dogBreeds = List("Doberman", "Yorkshire-Terrier", "Dachshund",
                     "Schottischer Terrier", "Deutsche Dogge", "Portugiesischer Wasserhund")
for (breed <- dogBreeds
  if breed.contains("Terrier");
  if !breed.startsWith("Yorkshire")
) println(breed)