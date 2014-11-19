package bool

import org.specs2.mutable._

class BooleanValSpec extends Specification {
    import BooleanVal._

    //#boolean-val-spec-1
    "Boolean.and" should {
        "work properly" in {
            True.and(False) mustEqual False
            False.and(True) mustEqual False
            False.and(False) mustEqual False
            True.and(True) mustEqual True
        }
    }
    "True.not" should {
        "return False" in { True.not mustEqual False }
    }
    "False.not" should {
        "return True" in { False.not mustEqual True }
    }
    //#boolean-val-spec-1
    "True.or" should {      
        "return True when False is passed" in { True.or(True) mustEqual True }
        "return True when True is passed" in { True.or(False) mustEqual True }
    }
    "False.or" should {
        "return False when False is passed" in { False.or(False) mustEqual False }
        "return True when True is passed" in { False.or(True) mustEqual True}
    }
}

class BooleanTypeSpec { // no spec needed, compiling is enough!
    import BooleanType._
    import shapeless.test.illTyped
    def assert[T] = null
    // test equality
    implicitly[True =:= True]
    implicitly[False =:= False]
    illTyped("implicitly[False =:= True]")
    // test or
    implicitly[True#or[True] =:= True]
    implicitly[True#or[False] =:= True]
    implicitly[False#or[False] =:= False]
    implicitly[False#or[True] =:= True]
    //#boolean-type-spec-1
    // test and
    implicitly[True#and[False] =:= False]
    implicitly[False#and[True] =:= False]
    implicitly[False#and[False] =:= False]
    implicitly[True#and[True] =:= True]
    // test not
    implicitly[True#not =:= False]
    implicitly[False#not =:= True]
    //#boolean-type-spec-1
    //#boolean-type-spec-2
    import shapeless.test.illTyped
    illTyped("implicitly[False#not =:= False]")
    //#boolean-type-spec-2
}