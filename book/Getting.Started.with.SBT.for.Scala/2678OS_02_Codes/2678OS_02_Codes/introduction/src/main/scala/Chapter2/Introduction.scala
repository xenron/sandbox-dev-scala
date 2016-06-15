package Chapter2

object Introduction extends App { 

  def isPalindrome(word: String) = { 
    val modifiedWord = word.toLowerCase.replaceAll("[^a-z0-9]", "") 
    val reversed = modifiedWord.reverse 
    modifiedWord == reversed 
  } 
 
  val givenString = args.mkString 
  println("Is "+givenString+" a palindrome? " + isPalindrome(givenString)) 
}
