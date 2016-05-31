package dg.study.hw.ch04

import math._

object GetSqrtValue {

  def getSqrtValue(num: Double, diff: Double = 0.0001): Double = {
    var sqrtValue = 1;
    if (abs(sqrtValue * sqrtValue - num) < diff)
      sqrtValue
    else
      getSqrtValue(num, (sqrtValue * sqrtValue + num) / (2 * sqrtValue))
  }

  def main(args: Array[String]) {
    println(getSqrtValue(4));
  }
}
