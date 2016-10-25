package list

object ListValExamples {
    object Ex1{
        //#list-ex1
        
        //#list-ex1
    }

     object ExFinal1{
        import peano.PeanoType._
        //#list-trait-type
        sealed trait IntList[Size <: Peano]{
            def ::(head: Int): IntList[PeanoN[Size]] = 
                                            ConsIntList(head, this) 
            
            def +(other: IntList[Size]): IntList[Size]
        }
        //#list-trait-type
        //#nil-type
        case object IntNil extends IntList[Zero] {
            def +(other: IntList[Zero]) = IntNil
        }
        //#nil-type
        //#cons-type
        case class ConsIntList[SizeTail <: Peano]
                               (head: Int, tail: IntList[SizeTail]) 
                                extends IntList[PeanoN[SizeTail]] { 

            def +(other: IntList[PeanoN[SizeTail]]) =
              other match {
                case ConsIntList(h, t) => (head + h) :: (tail + t)
              }
        }
        //#cons-type
    }
    object ExFinal2{
        //#list-trait-val 
        sealed trait IntList {
            def ::(head: Int): IntList = 
                                    ConsIntList(head, this)
            
            def +(other: IntList): IntList
            def size:Int 
        }
        //#list-trait-val
        //#nil-val
        case object IntNil extends IntList {
            def +(other: IntList) = {
              require(other == IntNil, "other list must be Nil List!")
              IntNil
            }
            override val size = 0
        }
        //#nil-val
        //#cons-val 
        case class ConsIntList(head: Int, tail: IntList) 
                                                extends IntList {
            
            def +(other: IntList) = {
              require(other.size == size, "other is !")
              other match {
                case ConsIntList(h, t) => (head + h) :: (tail + t)
              }
            }
            override def size = 1 + tail.size  
        }
        //#cons-val
    }
}