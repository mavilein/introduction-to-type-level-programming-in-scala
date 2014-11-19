package bool

object BooleanVal {
    
    //#boolean-trait-val
    sealed trait Boolean {
        def not : Boolean
        def or(that : Boolean) : Boolean
        def and(that : Boolean) : Boolean = (this.not or that.not).not
    }
    //#boolean-trait-val
    
    //#true-val
    case object True extends Boolean {
        def not : Boolean = False
        def or(that : Boolean) : Boolean = True
    }
    //#true-val

    //#false-val
    case object False extends Boolean {
        def not : Boolean = True
        def or(that : Boolean) : Boolean = that
    }
    //#false-val
}

object BooleanType {
    //#boolean-trait-type
    sealed trait Boolean {
        type not <: Boolean
        type or[that <: Boolean] <: Boolean
        type and[that <: Boolean] = this.type#not#or[that#not]#not
    }
    //#boolean-trait-type
    //#true-type
    sealed trait True extends Boolean {
        type not = False
        type or[that <: Boolean] = True
    }
    //#true-type
    //#false-type
    sealed trait False extends Boolean {
        type not = True
        type or[that <: Boolean] = that
    }
    //#false-type
}