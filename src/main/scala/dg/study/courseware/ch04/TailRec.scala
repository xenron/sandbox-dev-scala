package dg.study.courseware.ch04



object TailRec {
  def boom(x: Int): Int = {
    
    if (x == 0) throw new Exception("boom!")
    else boom(x - 1) + 1
  }
  
  def boom2(x: Int): Int = {
    
    if (x == 0) throw new Exception("boom!")
    else boom2(x - 1)
  }
  
  def main(args: Array[String]): Unit = {
    boom2(5)
  }
}