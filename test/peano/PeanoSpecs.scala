package peano

import org.specs2.mutable._

class PeanoValSpec extends Specification {
    import PeanoVal._   

    //#peano-val-spec-1
    val _0 = Zero
    val _1 = PeanoN(_0)
    val _2 = PeanoN(_1)
    "add" should {
        "0 + 0 = 0" in { _0 add _0 mustEqual _0 }
        "0 + 1 = 1" in { _0 add _1 mustEqual _1 }
        "1 + 0 = 1" in { _1 add _0 mustEqual _1 }
        "1 + 1 = 2" in { _1 add _1 mustEqual _2 }
    }
    //#peano-val-spec-1
}

class PeanoTypeSpec {
    import PeanoType._
    import shapeless.test.illTyped
    def assert[T] = null

    //#peano-type-spec-1
    type _0 = Zero
    type _1 = PeanoN[_0]
    type _2 = PeanoN[_1]
    implicitly[ _0#add[_0] =:= _0 ]
    implicitly[ _0#add[_1] =:= _1 ]
    implicitly[ _1#add[_0] =:= _1 ]
    implicitly[ _1#add[_1] =:= _2 ]
    illTyped("implicitly[ _1#add[_1] =:= _1 ]")
    //#peano-type-spec-1
    // succ
    //type _3 = _2#succ
    //implicitly[ _0 =:= _0 ]
    // implicitly[ _0#succ =:= _1 ]
    // implicitly[ _1#succ =:= _2 ]
    // implicitly[ _2#succ =:= _3 ]
    // illTyped("implicitly[ _2#succ =:= _2 ]")

}