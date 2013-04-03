package dojo

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RomanNumeralsSuite extends FunSuite {

  import RomanNumerals._

  val list = List((1, "I"),
    (2, "II"),
    (3,"III")

  )

  for ((k, v) <- list) {
    test(k.toString) {
      assert(roman(k) === v)
    }
  }

  test("4") {
    assert(roman(4) === "IV")
  }

  test("5") {
    assert(roman(5) === "V")
  }
  test("6") {
    assert(roman(6) === "VI")
  }
  test("7") {
    assert(roman(7) === "VII")
  }
  test("8") {
    assert(roman(8) === "VIII")
  }
  test("9") {
    assert(roman(9) === "IX")
  }
  test("10") {
    assert(roman(10) === "X")
  }
  test("11") {
    assert(roman(11) === "XI")
  }


}