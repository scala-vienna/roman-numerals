package dojo

import akka.actor.{ActorRef, Props, ActorSystem, Actor}
import akka.pattern.ask
import java.net.URL
import java.io.{File, InputStream}
import io.Source
import akka.routing.RoundRobinRouter
import collection.mutable
import akka.util.Timeout
import scala.concurrent.duration._
import concurrent.Await

object RomanNumerals {

  private val system = ActorSystem()

  def roman(number: Int): String = {
    val reference = new File(System.getProperty("java.io.tmpdir") + File.separator + "romanNumerals.txt")

    val numerals: Map[Int, String] = if (reference.exists() && reference.isFile) {
      val cached = Source.fromFile(reference).getLines() map {
        line => {
          val Array(arabic: String, roman: String) = line.split("=")
          (arabic.toInt, roman)
        }
      }
      cached.toMap
    } else {
      val downloader = system.actorOf(Props[RomanNumeralDownloader])
      implicit val timeout = new Timeout(5 minutes)
      val promise = (downloader ? Download)
      val result = Await.result(promise, 5 minutes).asInstanceOf[Map[Int, String]]

      reference.createNewFile()

      printToFile(reference) { p =>
        for (pair <- result) {
          p.println(pair._1 + "=" + pair._2)
        }
      }

      result
    }

    numerals.get(number).getOrElse("Unknown")
  }

  private def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }
}

class RomanNumeralDownloader extends Actor {

  private val howMany = 3000

  private val reference = mutable.HashMap[Int, String]()

  private var start: Long = 0

  private var client: ActorRef = null

  def receive = {

    case Download =>
      start = System.currentTimeMillis()
      val fetchers = Props[RomanNumeralFetcher].withRouter(RoundRobinRouter(10))
      val fetchersRef = context.actorOf(fetchers)

      for (i <- 1 to howMany) {
        fetchersRef ! i
      }

      client = sender

    case RomanNumeral(arabic, result) =>
      result map { roman =>
        reference put (arabic, roman)
      } getOrElse {
        println(s"Ooops, can't get the roman version of $arabic")
      }

      if (reference.size % 50 == 0) {
        println(s"Fetched ${reference.size} numerals so far")
      }

      if (reference.size == howMany) {
        println(s"Done downloading all roman numerals from 1 to $howMany in ${System.currentTimeMillis() - start} ms")
        client ! reference.toMap
      }

  }



}

case object Download
case class RomanNumeral(arabic: Int, result: Option[String])

class RomanNumeralFetcher extends Actor {

  def receive = {
    case n if n.isInstanceOf[Int] =>
      val contentStream = new URL(s"http://www.miniwebtool.com/roman-numerals-converter/?number=$n").getContent.asInstanceOf[InputStream]
      val content = Source.fromInputStream(contentStream).getLines().mkString("\n")
      val result = extractResult(content)
      sender ! RomanNumeral(n.asInstanceOf[Int], result)
  }

  private def extractResult(content: String): Option[String] = {
    val resultPattern = """Roman Number</td><td>(.*?)</td>""".r
    val maybeMatch = resultPattern.findFirstMatchIn(content)
    maybeMatch.map(_.group(1))
  }

}