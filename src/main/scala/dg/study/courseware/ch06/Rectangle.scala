package dg.study.courseware.ch06



case class Rectangle(val height: Double, val width: Double, override val name: String) 
  extends Shape(name) {
  
  override def shapeType = "rectangle"
  
  override def area = height * width
}