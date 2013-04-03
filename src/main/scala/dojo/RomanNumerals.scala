package dojo

object RomanNumerals {

  val c = List(
    Map(0 -> "I", 1 -> "V", 2 -> "X"),
    Map(0 -> "X", 1 -> "L", 2 -> "C"),
    Map(0 -> "C", 1 -> "D", 2 -> "M"),
    Map(0 -> "M", 1 -> "?", 2 -> "??")
  )


  def roman(number: Int): String = {
    val thousands = number/1000
    val hundreds = (number - thousands*1000)/100
    val tens = (number - hundreds*100 - thousands*1000)/10
    val units = number % 10
    roman1(thousands, 3) + roman1(hundreds, 2) + roman1(tens, 1) + roman1(units, 0)
  }
  def roman1(number: Int, pos: Int) = {
    number match {
      case n if n <= 3 => c(pos)(0) * n
      case 4 => c(pos)(0) + c(pos)(1)
      case 9 => c(pos)(0) + c(pos)(2)
      case 10 => c(pos)(2)
      case n  if n > 10  => c(pos)(2) + c(pos)(0)


      case n if n >= 5 => c(pos)(1) + c(pos)(0) * (n-5)
      case _ => throw new RuntimeException
    }


  }
}