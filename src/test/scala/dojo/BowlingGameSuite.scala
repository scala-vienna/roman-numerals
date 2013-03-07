package dojo

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class BowlingGameSuite extends FunSuite {
  import BowlingGame._
  
  test("one strike should have score of 10") {
    assert(score("X------------------") === Score(10))
  }

 //test("two strikes should have score of 30") {
  //  assert(score("1111"+ "-" * 16) === Score(4))
  //}

  test("not strike and spare") {
    assert(score("1" + "-" * 19) === Score(1))
  }
  
  test("not strike of something and spare") {
    assert(score("1" + "-" * 19) === Score(1))
  }
  
  test("parse head frame") {
    assert(headFrame("X") === (Strike(), ""))
  }
  
  test("parse incomplete head frame") {
    assert(headFrame("12") === (Incomplete(1,2), ""))
    assert(headFrame("25") === (Incomplete(2,5), ""))
  }  

  test("parse spare head frame with 2 and /") {
    assert(headFrame("2/") === (Spare(2), ""))
  }
  
  test("parse incomplete of 1 and miss") {
    assert(headFrame("1-") === (Incomplete(1,0),""))
  }
  
  test("parse incomplete of two misses") {
    assert(headFrame("--") === (Incomplete(0,0),""))
  }

  test("parse incomplete of miss and spare") {
    assert(headFrame("-/") === (Spare(0),""))
  }
  
  test("test missand 2") {
    assert(headFrame("-2") === (Incomplete(0,2),""))
  }
  
  test("test two frames XX") {
	  assert(headFrame("XX") === (Strike(),"X"))
  }

  test("list of frames and rest") {
	  assert(listOfFrames("1111") === List(Incomplete(1,1), Incomplete(1,1)))
  }
  
}