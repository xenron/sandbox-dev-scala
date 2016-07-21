
object Tree extends App {

  case class TreeNode(v: Int, left: Option[TreeNode] = None,
                      right: Option[TreeNode] = None) {
    def add(av: Int) = {
      insert(av).get
    }

    def insert(av: Int): Option[TreeNode] = {
      def insertLeft(av: Int) =
        left.flatMap(_.insert(av)) orElse
          Option(TreeNode(av)) // 1

      def insertRight(av: Int) =
        right.flatMap(_.insert(av)) orElse
          Option(TreeNode(av)) // 2

      av.compare(v) match {
        case 0 => Option(this) // 3
        case -1 => Option(TreeNode(v, insertLeft(av),
          right)) // 4
        case 1 => Option(TreeNode(v, left, insertRight
          (av))) // 5
      }
    }


    def traverseItInOrder(): Option[List[TreeNode]] = {
      val leftList = left.flatMap(x => x
        .traverseItInOrder()).getOrElse(Nil)
      val rightList = right.flatMap(x => x
        .traverseItInOrder()). getOrElse(Nil)
      val result = (leftList :+ this) ++ rightList
      Option(result)
    }

    def traverseInOrder() = {
      val result: Option[List[TreeNode]] = traverseItInOrder()
      result.getOrElse(Nil)
    }
  }

  val p = TreeNode(4).add(3).add(0).add(1).add(99).
    add(1).add(4)

  for {
    q <- p.traverseInOrder() // 5
  } println(q.v)

}

