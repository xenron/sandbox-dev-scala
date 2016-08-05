// code-examples/Rounding/scoped-for-script.scala

val dogBreeds = List("Doberman", "Yorkshire-Terrier", "Dachshund",
                     "Schottischer Terrier", "Deutsche Dogge", "Portugiesischer Wasserhund")
for {
  breed <- dogBreeds
  upcasedBreed = breed.toUpperCase()
} println(upcasedBreed)