package bools

object BooleanValExamples{
    object Ex1{
        //#bool-trait
        sealed trait Boolean {
            def not : Boolean
            def or(that : Boolean) : Boolean
            def and(that : Boolean) : Boolean
        }
        //#bool-trait
        //#bool-impl
        case object True extends Boolean {
            def not : Boolean = False
            def or(that : Boolean) : Boolean = True
            def and(that : Boolean) : Boolean = that
        }
        case object False extends Boolean {
            def not : Boolean = True
            def or(that : Boolean) : Boolean = that
            def and(that : Boolean) : Boolean = False
        }       
        //#bool-impl
        //#bool-ex1
        val b1 = True
        val b2 = False
        b1.not // == False
        b2.not // == True
        b1.or(b2) // == True
        //#bool-ex1
    }
    object Ex2{
        //#bool-impl-2
        sealed trait Boolean {
            def not : Boolean
            def or(that : Boolean) : Boolean
            // b1 && b2 == !(!b1 || !b2)
            def and(that : Boolean) : Boolean = (this.not or that.not).not
        }
        case object True extends Boolean {
            def not : Boolean = False
            def or(that : Boolean) : Boolean = True
        }
        case object False extends Boolean {
            def not : Boolean = True
            def or(that : Boolean) : Boolean = that
        }
        //#bool-impl-2
    }   
    object Ex3a {
        //#bool-val-trait-3
        sealed trait Boolean {
            def not : Boolean
            def or(that : Boolean) : Boolean
        }
        //#bool-val-trait-3
    }
    object Ex3b {
        //#bool-type-trait-3
        sealed trait Boolean {
            type not <: Boolean
            type or[that <: Boolean] <: Boolean
        }
        //#bool-type-trait-3        
    }
    object Ex4a{
        // same as original code but line breaks are different because of formattting for final code slide
        //#bool-val-trait-4
        sealed trait Boolean {
            def not : Boolean
            def or(that : Boolean) : Boolean
            def and(that : Boolean) : Boolean = 
                (this.not or that.not).not
        }
        //#bool-val-trait-4
    }
    object Ex4b{
        //#bool-type-trait-4
        sealed trait Boolean {
            type not <: Boolean
            type or[that <: Boolean] <: Boolean
            type and[that <: Boolean] = 
                this.type#not#or[that#not]#not
        }
        //#bool-type-trait-4        
    }
}