trait Prompter2 {
  // the self type annotation
  this: GreetingProvider =>

  val prompt = "> "

  def printGreeting() {
    println(prompt + greeting)
    // Next line would work too. 'this' can refer to things within this trait or
    // the declared self type trait
    //println(this.prompt + this.greeting)
  }
}

trait GreetingProvider {

  val greeting = "Hello world"
}

val prompter2 = new Object with Prompter2 with GreetingProvider
prompter2.printGreeting