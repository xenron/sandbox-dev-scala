object LazyLists extends App {

  def addOne(x: Int) = {
    println(s"addOne(${x}))" )
    x + 1
  }

  def multByTwo(x: Int) = {
    println(s"multByTwo(${x}))" )
    x * 2
  }

  def weedOutDivisibleByFour(x: Int) = {
    println(s"weedOutDivisibleByFour(${x}))" )
    x % 4 != 0
  }

  val list = (1000 to 1000000).toList.view

  list.map(addOne).map(multByTwo).filter(weedOutDivisibleByFour).take(5).foreach { println }
}

