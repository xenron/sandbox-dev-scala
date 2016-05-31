package dg.study.courseware.ch06



case class Triangle(val side1: Double, val side2: Double, val side3: Double, override val name: String) 
  extends Shape(name) {
  
  override def shapeType = "triangle"
  
  override def area = {
    val s = (side1 + side2 + side3) / 2
    math.sqrt(s * (s - side1) * (s - side2) * (s - side3))
  }
}