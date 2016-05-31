package dg.study.courseware.ch06



case class Elipse(val a: Double, val b: Double, override val name: String) 
  extends Shape(name) {
  
  override def area = a * b * math.Pi
  
  override def shapeType = "elipse"
}