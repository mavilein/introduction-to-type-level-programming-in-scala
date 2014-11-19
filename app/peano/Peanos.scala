package peano

object PeanoVal {
    //#peano-trait-val
    sealed trait Peano {
        def add(that : Peano) : Peano
    }
    //#peano-trait-val
    //#zero-val
    case object Zero extends Peano {
        def add(that : Peano) = that
    }
    //#zero-val

    //#peanon-val
    case class PeanoN(prev : Peano) extends Peano {
        def add(that : Peano) = PeanoN(prev.add(that))
    }
    //#peanon-val
}

object PeanoType {
    //#peano-trait-type
    sealed trait Peano {
        type add[that <: Peano] <: Peano
    }
    //#peano-trait-type
    //#zero-type
    sealed trait Zero extends Peano {
        type add[that <: Peano] = that
    }
    //#zero-type
    //#peanon-type
    sealed trait PeanoN[prev <: Peano] extends Peano {
        type add[that <: Peano] = PeanoN[prev#add[that]]
    }
    //#peanon-type
}