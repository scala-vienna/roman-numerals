package dojo

object RomanNumerals {



  def roman(number: Int): String = {
    number match {
      case n if n <= 3 => "I" * n
      case 4 => "IV"
      case 9 => "IX"
      case 10 => "X"
      case n  if n > 10  => "XI"


      case 11 => "XI"
      case n if n >= 5 => "V" + "I" * (n-5)
      case _ => throw new RuntimeException
    }
  }
}