package dg.study.hw.ch04

object FunSets {

  type Set = Int => Boolean

  def contains(s: Set, elem: Int): Boolean = s(elem)

  def singletonSet(elem: Int): Set = { elem2: Int => elem == elem2 }

  def union(s: Set, t: Set): Set = { elem: Int => contains(s, elem) || contains(t, elem) }

  def intersect(s: Set, t: Set): Set = { elem: Int => contains(s, elem) && contains(t, elem) }

  def diff(s: Set, t: Set): Set = { elem: Int => contains(s, elem) && !contains(t, elem) }

  def filter(s: Set, p: Int => Boolean): Set = { elem: Int => contains(s, elem) && contains(p, elem) }

  val bound = 1000

  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a == -bound) true
      else if ((contains(s, a)) && (!contains(filter(s, p), a))) false
      else iter(a - 1)
    }
    iter(bound)
  }

  def exists(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a == -bound) false
      else if ((contains(s, a)) && (contains(filter(s, p), a))) true
      else iter(a - 1)
    }
    iter(bound)
  }

  def map(s: Set, f: Int => Int): Set = { elem: Int => exists(s, { elem2: Int => f(elem2) == elem }) }

  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  def printSet(s: Set) {
    println(toString(s))
  }

  def main(args: Array[String]) {
    var set1 = singletonSet(1);
    var set2 = singletonSet(2);
    var set3 = singletonSet(3);
    var set4 = singletonSet(4);

    println("------ contains ------");
    println(contains(set1, 1))

    var unionSet1 = union(set1, set2);
    println("------ union ------");
    printSet(unionSet1);
    var unionSet2 = union(set1, set3);
    println("------ intersect ------");
    printSet(intersect(unionSet1, unionSet2))
    println("------ diff ------");
    printSet(diff(unionSet1, unionSet2))

    println("------ filter ------");
    printSet(filter(unionSet1, set1))

    println("------ forall ------");
    println(forall(unionSet1, unionSet1))
    println(forall(unionSet1, unionSet2))

  }
}
