package dg.study.courseware.ch06



object ShapeTest extends App {
  
  val s1: Shape = new Rectangle(1, 2, "rect1")
  val s2: Shape = Elipse.apply(3, 4, "elipse1")
  val s3: Shape = Triangle(3, 4, 5, "triangle1")
  
  val shapes = List(s1, s2, s3)
  shapes.foreach { x => println(x.shapeType); println(x.area) }
  
  val shapes2 = List(Rectangle(2, 3, "r1"), 
      Triangle(3, 4, 5, "t1"), Elipse(3, 6, "e1"))
  
  shapes2.foreach { s: Shape => s match {
    case Rectangle(_, _, s)    => println("rectangle"); println(s)
    case Elipse(_, _, s)       => println("circle"); println(s)
    case Triangle(_, _, _, s)  => println("triangle"); println(s)
    }
  }
}