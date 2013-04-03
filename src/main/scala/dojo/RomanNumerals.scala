package dojo

object RomanNumerals {

  val c = Map(0 -> "I", 1 -> "V", 2 -> "X")

  def roman(number: Int): String = {
    number match {
      case n if n <= 3 => c(0) * n
      case 4 => c(0) + c(1)
      case 9 => c(0) + c(2)
      case 10 => c(2)
      case n  if n > 10  => c(2) + c(0)


      case 11 => c(2) + c(0)
      case n if n >= 5 => c(1) + c(0) * (n-5)
      case _ => throw new RuntimeException
    }
  }
}