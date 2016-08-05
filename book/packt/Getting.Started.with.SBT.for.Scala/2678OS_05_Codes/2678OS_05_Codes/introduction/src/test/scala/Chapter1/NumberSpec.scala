import Chapter1.Introduction
import org.specs2.mutable._


class NumberSpec extends Specification{
  
  "The date '20:02 02/20 2002'" should{
    "be a palindrome" in{
      Introduction.isPalindrome("20:02 02/20 2002") must beTrue
    }
  }

  "The date '20:02 12120 2002'" should{
    "not be a palindrome" in{
      Introduction.isPalindrome("20:02 12120 2002") must beFalse
    }
  }

}
