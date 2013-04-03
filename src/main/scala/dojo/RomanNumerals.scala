package dojo

object RomanNumerals {



  def roman(number: Int): String = {
    number match {
      case 1 => "I"
      case 2 => "II"
      case 3 => "III"
      case 4 => "IV"
      case n if (n%5==0) => "V"
      case _ => throw new RuntimeException
    }
  }
}