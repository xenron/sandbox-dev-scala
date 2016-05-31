object FlatMap {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  def toInt(s: String): Option[Int] = {
  
    try {
      Some(Integer.parseInt(s.trim))
    } catch {
      case e: Exception => None
    }
  }                                               //> toInt: (s: String)Option[Int]
  
  val strings = Seq("1", "2", "foo", "3", "bar")  //> strings  : Seq[String] = List(1, 2, foo, 3, bar)
  strings.map(toInt)                              //> res0: Seq[Option[Int]] = List(Some(1), Some(2), None, Some(3), None)
  strings.flatMap(toInt)                          //> res1: Seq[Int] = List(1, 2, 3)
  strings.flatMap(toInt).sum                      //> res2: Int = 6
  
  val map = strings.map(toInt)                    //> map  : Seq[Option[Int]] = List(Some(1), Some(2), None, Some(3), None)
  val res = map.flatten                           //> res  : Seq[Int] = List(1, 2, 3)
  res.sum                                         //> res3: Int = 6
}