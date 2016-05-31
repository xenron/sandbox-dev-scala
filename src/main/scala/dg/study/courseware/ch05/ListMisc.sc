object ListMisc {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  val a = List(1, 2, 3, 4, 5, 6, 7, 8)            //> a  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8)
  
  val a1 = a filter (_ % 2 != 0)                  //> a1  : List[Int] = List(1, 3, 5, 7)
  val a2 = a filter (_ % 2 == 0)                  //> a2  : List[Int] = List(2, 4, 6, 8)
  val a3 = a1 zip a2                              //> a3  : List[(Int, Int)] = List((1,2), (3,4), (5,6), (7,8))
  a3.unzip                                        //> res0: (List[Int], List[Int]) = (List(1, 3, 5, 7),List(2, 4, 6, 8))
  
  val b = List(1, 2, 3, 4, 5, 6, 7, 8, 9)         //> b  : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
  val b1 = b filter (_ % 2 != 0)                  //> b1  : List[Int] = List(1, 3, 5, 7, 9)
  val b2 = b filter (_ % 2 == 0)                  //> b2  : List[Int] = List(2, 4, 6, 8)
  val b3 = b1 zip b2                              //> b3  : List[(Int, Int)] = List((1,2), (3,4), (5,6), (7,8))
  
  val a4 = a takeWhile ( _ % 2 == 0 )             //> a4  : List[Int] = List()
  val a5 = a dropWhile ( _ < 5 )                  //> a5  : List[Int] = List(5, 6, 7, 8)
  a span ( _ < 5 )                                //> res1: (List[Int], List[Int]) = (List(1, 2, 3, 4),List(5, 6, 7, 8))
}