# A gentle introduction to Type Level Programming in Scala

These are the slides and the example code for my talk at Stange Group Berlin.

### Where can i find the example code?
You can find the different code examples from the talk under app/bool, app/peano and app/list.

### How can i view the slides?
You need to have scala and sbt installed. Just execute `sbt run` in the project folder and open `localhost:9000` in your browser.


### Where can i learn more about Type Level Programming?

* https://github.com/milessabin/shapeless - The Type Level Programming Library i mentioned.
* http://www.chuusai.com/blog/ - The blog of Miles Sabin, the author of Shapeless.
* http://proseand.co.nz/2014/02/17/type-programming-shifting-from-values-to-types/ - A Blog Series by Joe Barnes, where i learned quite a bit.



# Some prose if you have not been there (requested by a famous systems euthanizer)

## Motivation - Why does Type Level Programming matter?
Currently we are not getting the most out of our compilers. We rely mostly on simple type checking. For example we are not allowed to pass an Int to a method, which expects a String. As type level programmers we want to have more properties checked at the type level. 

On slide 1-1 you can see the problem that is solved during the talk. We see how 2 lists are created (a simple custom implementation), where each list contains 2 ints. Then the + method is called on those lists, which adds the elements of the both lists together. Unfortunately this can only work if the lists have the same size. Therefore it throws a RuntimeException when we add list with not the same size. The end of the talk we will have enhanced this implementation so that it does not compile.

Because we can clearly see that we trying to add a 2 element list and a 1 element list. That can never work. The compiler should spot that error for us!

The message is: We want to write our programs in a way, that makes us very confident it will work, once it compiles - In Types we trust!

## How & What we will learn
We will always start with a simple Implementation at the Value Level (== normal programming). Then we will translate that simple implementation to one at the Type Level. In order to do this, we will use a simple set of rules. There we will see that programming at the Type Level is actually not that different from programming at the Value Level.
We will explore 3 simple examples:
- A type level Boolean Type: The Hello World of Type Level Programming
- A type level Numeric Type: Recursion is also possible at the Type Level
- Doing something useful: We will solve our initial problem by enhancing our List with some Type Level goodness

## Our own Bool Type
On slide 3-1 we can see how we can implement our own custom Boolean at the Value Level. On slide 3-2 we can see a slightly more complicated version of the Boolean. We replaced the implementation of and with a more complicated one. This is not needed, but i wanted to have a more complicated method, that gets translated.
On slide 3-3 we see our first translation side by side. Instead of methods with the keyword def, we now use the keyword type. Our computations happen at compile instead of runtime. As type level programmers we produce types, not values. Instead of a normal type annotation we now use type bounds (<:). And instead of paramaters lists, we now use square brackets. If you want to formulate precisely, we defined some abstract types. Each type was defined with a type bound (<:). The or type is a so called type constructor, because we have to pass a type to it before we can construct an actual type. 

This may seem all very complicated, but in the end you can just follow those simple translation rules.

On slide 3-4 we can see how the False gets translated. The only thing that is really new in this case, is that we need to omit the return type (type bound), when we implement the abstract types. Otherwise it won't compile.

On slide 3-5 we can see how the more complicated and method gets translated. Instead of this at the value level, we need something similar on the type level. Scala provides the this.type for this purpose. While we call methods with the x.foo notation on the value level, we instead use the x#foo notation now. If you want to be more precise again: The # is a so called type level projection. We use it to access types nested with in other types. It is also possible to access nested types with the dot notation but then we would speak about path dependent types, which we don't want here (they are only relevant at the value level).

On slide 3-6 we can see the final code, and we can clearly see that the visual structures look pretty much the same.

On slide 3-7 you can see a picture of me taken after having written my first type level boolean just a few fays ago. After having achieved that, i wanted to try it out in the REPL. So i fired it up and according to the rules typed in:
False#not
This failed with a weird error and i tried lots of things, until i realized that the REPL does not help us here. A REPL wants to evaluate expressions, which results in values. But we are at the type level! So we need tests for our types to make sure that they actually work.

On slide 3-8 we can see the tests on value and type level side by side. We use the standard scala method implicitly, which takes only one type as a parameter to abuse it as an assert on the type level. Inside the square brackets we see what should be asserted. We are using the =:= type bound. This makes that the left hand side of it, is a subtype of the right hand side. And it also makes sure that the right side is also a subtype of the left side. So it basically says, that these types should be equal. And on the lower right we can see how we can implement negative tests cases (things that should not compile). As we want our whole codebase to compile, we are using a clever macro from the shapeless library. We are passing a String to the macro, that it tries to compile. The macro is only happy, if the String does not compile. If the compiling of the String would succeed, we would get a compile error ourselves. Very irritating at first, but it is definitely useful.

## Numbers at the Type Level
On slide 4-1 we can see the implementation of Peano Numbers at the value level. Peano Numbers are a way to define natural numbers in a recursive fashion. We have a singleton object for the base case Zero. Additionally we have a class for a successor. The number a Successor represents is determined by its number of predecessors. This may seem totally cumbersome, but at the type level we need a unique type for each number. Hopefully the implementation of the add is understandable by itself :)

Nothing really changes on the next slides.

On slide 4-4 we can see that a construtor on the value level translates to something called type constructor on the Type Level. Again we just substitute round brackets with square brackets. We can also see that recursion translates very nicely to the Type Level. We only to needed to add one more rule for translating Constructor calls.

Then we look again at the final code and see that they again are very similar again. Also the Tests translate relatively easy with the help of our Testing Tools, we learned when testing our Booleans.

## Solving our List Problem
At first we remember our initial problem. We have a custom implementation of Lists, which may only take ints. We have a + operation defined on it, which can only work if both lists have the same size. The normal value level implementation throws a runtime exception. We will promote it to a compile error.

Slide 5-2 just gives a super fast and crappy introduction to Lists in functional languages.

Slide 5-3 introduces the normal value level implementation. We see need 3 methods on an IntList. Preprend (::), add (+) and size. It follows the classic functional List implementation, where we have a Nil / empty List and a Cons cell. The implementation for the empty list is quite easy. If we look at the Cons cell, we can see that the 2 lists need to have the same size. If they do, the heads of both list are summed and are prepended to a new list and sum of the tails is added.

Slide 5-4 shows how can translate it. We can see that size method has been removed, because we don't need it anymore. Instead we have promoted size to be a "property" at the type level. We are annotating our List, with its size, e.g. List[_2] or List[_3]. We can see how the arguments and return types have been modified. We can see that the return type of prepend returns a List, which contains one more element, because the return type is paramterized with the Successor of the current Size. We can see that + now takes a List with same size and returns a List with the same size.

Slide 5-5 shows that the implementation is greatly simplified. We removed quite a lot of code and the only important thing, was to add the Zero Type to the types where needed.

On slide 5-6 we can see the modified Cons implementation. It is a little bit tricky at first sight. ConsIntList declares a type called SizeTail. The tail of this cons cell has to have this type. The actual size of this cons cell can be seen on the extends declaration, where we say that the actual size is the successor of SizeTail. This a little weird at first, but this is because we can count only upwards with Peano Numbers. As an example: What we express here could mean -> We have List with SizeTail of 3, the tail IntList then has to have 3, while this ConsCell has Size 3+1. 

Hopefully this was somehow clear. This is difficult to explain in written text :)
And if we look finally at the + implementation nothing really has changed here. We just have to declare that the other list has to have the same size this Cons cell.
The slide shows a hard to read screenshot of a compiler warning as an interesting detail. In the value level implementation, we get a compiler warning for a non exhaustive match, because the compiler can't know it's not possible to reach the pattern matching with a IntNil (because of the require before). But with the Type Level implementation this warning is not that there anymore. The Compiler had enough information now to infer this fact! We moved this property from the Value Level to the Type Level.

In the i just showed the compiler error in the REPL emitted the following when trying to use + on a list of size 2 and another list of size 1. We can see how the compiler says, that he found a list with Size "Sucessor of Zero" (1), but wanted to have size "Sucessor of Successor of Zero" (2).

scala> val list1 = 1 :: 2 :: IntNil
list1: list.ListType.IntList[peano.PeanoType.PeanoN[peano.PeanoType.PeanoN[peano.PeanoType.Zero]]] = ConsIntList(1,ConsIntList(2,IntNil))

scala> val list2 = 10 :: IntNil
list2: list.ListType.IntList[peano.PeanoType.PeanoN[peano.PeanoType.Zero]] = ConsIntList(10,IntNil)

scala> list1 + list2
<console>:13: error: type mismatch;
 found   : list.ListType.IntList[peano.PeanoType.PeanoN[peano.PeanoType.Zero]]
 required: list.ListType.IntList[peano.PeanoType.PeanoN[peano.PeanoType.PeanoN[peano.PeanoType.Zero]]]
              list1 + list2


## Finish :)
I hope this transcript is somehow useful.
