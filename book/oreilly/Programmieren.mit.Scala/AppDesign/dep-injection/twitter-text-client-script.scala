// code-examples/AppDesign/dep-injection/twitter-text-client-script.scala

import twitterclient._

val client = new TextClient(new TwitterUserProfile("FritzTester"))
client.ui.sendTweet("Mein erster Tweet. Wie funktionert das hier?")
client.ui.sendTweet("Ist das Ding in Betrieb?")
client.ui.sendTweet("Jetzt muss ich mal wohin...")
println("Chat-Historie:")
client.localCache.history.foreach {t => println(t)}