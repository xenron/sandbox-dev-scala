// code-examples/TypeSystem/variances/mutable-type-variance-script.scala
// NICHT KOMPILIERBAR: Mutable parametrisierte typen künnen keine Varianzannotationen haben

class ContainerPlus[+A](var value: A)      // FEHLER
class ContainerMinus[-A](var value: A)     // FEHLER

println( new ContainerPlus("Hallo Welt!") )
println( new ContainerMinus("Hallo Welt!") )
