package list
import org.specs2.mutable._

class ListValSpec extends Specification {
	import ListVal._

	"adding to a Nil List" should {
		"work if the other List is Nil too" in {
			(IntNil + IntNil) mustEqual IntNil
		}
		"fail if the other List is not empty" in {
			IntNil + (1 :: IntNil) must throwA[IllegalArgumentException]
		}
	}
	"adding to a non empty List" should {
		"work if the other List has the same size" in {
			val list1 = 1 :: 2 :: IntNil
			val list2 = 6 :: 7 :: IntNil
			(list1 + list2) mustEqual (7 :: 9 :: IntNil)
		}
		"fail if the other List does not has the same size" in {
			val list1 = 1 :: 2 :: IntNil
			val lessEles = 1 :: IntNil
			val moreEles = 1 :: 2 :: 3 :: IntNil
			list1 + lessEles must throwA[IllegalArgumentException]
			list1 + moreEles must throwA[IllegalArgumentException]
		}
	}
}

class ListTypeSpec extends Specification {
	import ListType._
	import shapeless.test.illTyped

	"adding to a Nil List" should {
		"work if the other List is Nil too" in {
			(IntNil + IntNil) mustEqual IntNil
		}
		"fail if the other List is not empty" in {
			illTyped("IntNil + (1 :: IntNil)") 
			true mustEqual true
		}	
	}
	"adding to a non empty List" should {
		"work if the other List has the same size" in {
			val list1 = 1 :: 2 :: IntNil
			val list2 = 6 :: 7 :: IntNil
			(list1 + list2) mustEqual (7 :: 9 :: IntNil)
		}
		"fail if the other List does not has the same size" in {
			val list1 = 1 :: 2 :: IntNil
			val lessEles = 1 :: IntNil
			val moreEles = 1 :: 2 :: 3 :: IntNil
			illTyped("list1 + lessEles")
			illTyped("list1 + moreEles")
			true mustEqual true
		}
	}
}