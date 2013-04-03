package dojo

object RomanNumerals {



  def roman(number: Int): String = {
    number match {
      case n if n <= 3 => "I" * n
      case 4 => "IV"
      case n if (n%5==0) => "V"
      case n if n > 5 => "V" + "I" * (n-5)
      case _ => throw new RuntimeException
    }
  }
}