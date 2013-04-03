package dojo

object RomanNumerals {

  val c = List(
    Map(0 -> "I", 1 -> "V", 2 -> "X")
  )


  def roman(number: Int): String = {
   roman1(number, 0)
  }
  def roman1(number: Int, pos: Int) = {
    number match {
      case n if n <= 3 => c(pos)(0) * n
      case 4 => c(pos)(0) + c(pos)(1)
      case 9 => c(pos)(0) + c(pos)(2)
      case 10 => c(pos)(2)
      case n  if n > 10  => c(pos)(2) + c(pos)(0)


      case 11 => c(pos)(2) + c(pos)(0)
      case n if n >= 5 => c(pos)(1) + c(pos)(0) * (n-5)
      case _ => throw new RuntimeException
    }


  }
}