package dojo

object RomanNumerals {
  def roman(number: Int): String = {
    number match {
      case 1 => "I"
      case 2 => "II"
      case 3 => "III"
      case _ => throw new RuntimeException
    }
  }
}