package dojo

case class Score(val value: Int)

abstract class Frame(val first: Int, val second: Int) {

  def score: Int
}
object Frame {
  def charToInt(c: Char): Int = c match {
    case '-' => 0
    case c => Integer.valueOf(c.toString)
  }
}

case class Strike extends Frame(10, 0) {
  override def score: Int = 10
}
object Spare {
  import Frame._

  def fromChar(c: Char) = {
    Spare(charToInt(c))
  }
}

case class Spare(override val first: Int) extends Frame(first, 10 - first) {
  override def score: Int = 10
}

object Incomplete {
  import Frame._

  def fromChars(d1: Char, d2: Char) = {
    Incomplete(charToInt(d1), charToInt(d2))
  }
}

case class Incomplete(override val first: Int, override val second: Int) extends Frame(first, second) {
  override def score: Int = first + second
}

object BowlingGame {
  def score(hits: String): Score = {
    val rolls = hits.toCharArray()

    val score = headFrame(hits)._1.score

    Score(score)
  }

  def listOfFrames(s: String): List[Frame] = {
    s match {
      case "" => List()
      case str => {
        val result = headFrame(str)
        List(result._1) ++ listOfFrames(result._2)
      }
    }

  }

  def headFrame(s: String): (Frame, String) = {
    s.toList match {
      case 'X' :: rest => (Strike(), rest.mkString)
      case c :: '/' :: rest => (Spare.fromChar(c), rest.mkString)
      case d1 :: d2 :: rest => (Incomplete.fromChars(d1, d2), rest.mkString)
      // case _ => ???
    }

  }
}