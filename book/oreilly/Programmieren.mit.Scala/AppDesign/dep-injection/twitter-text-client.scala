// code-examples/AppDesign/dep-injection/twitter-text-client.scala

package twitterclient

class TextClient(userProfile: TwitterUserProfile) 
    extends TwitterClientComponent 
    with TwitterClientUIComponent
    with TwitterLocalCacheComponent
    with TwitterServiceComponent {
    
  // Aus TwitterClientComponent:
  
  val client = new TwitterClient(userProfile)

  // Aus TwitterClientUIComponent:
  
  val ui = new TwitterClientUI(client) {
    def showTweet(tweet: Tweet) = println(tweet)
  }
  
  // Aus TwitterLocalCacheComponent:
  
  val localCache = new TwitterLocalCache {
    private var tweets: List[Tweet] = Nil

    def saveTweet(tweet: Tweet) = tweets ::= tweet

    def history = tweets
  }
  
  // Aus TwitterServiceComponent:
  
  val service = new TwitterService() {
    def sendTweet(tweet: Tweet) = {
      println("Sending tweet to Twitter HQ")
      true
    }
    def history = List[Tweet]()
  }
}