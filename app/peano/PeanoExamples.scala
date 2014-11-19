package bool

object PeanoValExamples {
    import peano.PeanoVal._
    object Ex1 {
    //#peano-ex1
        val _0 = Zero
        val _1 = PeanoN(_0)
        val _3 = PeanoN(PeanoN(PeanoN(_0)))     
        val _3b = PeanoN(PeanoN(_1))
    //#peano-ex1
    }
    object Ex2 {
    //#peano-ex2
        // val _0 = Zero
        // val _1 = _0.succ
        // val _2 = _1.succ
        // val _3 = _2.succ        
    //#peano-ex2
    }

}