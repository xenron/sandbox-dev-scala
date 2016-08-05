import Chapter1.Introduction
import org.specs2.mutable._


class WordSpec extends Specification{
  "The phrase 'Never odd or even'" should{
    "be a palindrome" in {
      Introduction.isPalindrome("Never odd or even") must beTrue
    }
  }

  "The phrase 'Mr. Owl ate my metal worm'" should{
    "be a palindrome" in {
      Introduction.isPalindrome("Mr. Owl ate my metal worm") must beTrue
    }
  }
}
