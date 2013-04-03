package dojo

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RomanNumeralsSuite extends FunSuite {
  import RomanNumerals._
  
  test("1") {
    assert(roman(1) === "I")
  }
}