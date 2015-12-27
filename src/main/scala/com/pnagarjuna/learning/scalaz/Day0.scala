package com.pnagarjuna.learning.scalaz

/**
  * Created by pnagarjuna on 27/12/15.
  */
object Day0 {

  def main(args: Array[String]): Unit = {

    "Learning Scalaz Day0" pln

    //Parametric Polymorphism

    head(1 :: 2 :: Nil) pln

    case class Car(name: String)

    head(Car("BMW") :: Car("Porsche") :: Car("HondaCity") :: Nil) pln

    //Subtype polymorphism

    case class Age(age: Int) extends Plus[Age] {

      override def plus(a2: Age): Age = Age(this.age + a2.age)

    }

    plus(Age(21), Age(18)) pln

    case class Cat(name: String)

    implicit object CatPlus extends CoolPlus[Cat] {
      override def coolPlus(a1: Cat, a2: Cat): Cat = Cat(s"${a1.name} ${a2.name}")
    }

    coolPlus(Cat("pamu"), Cat("nagarjuna")) pln

    implicit val intMonoid = new Monoid[Int] {
      override def mappend(a1: Int, a2: Int): Int = a1 + a2

      override def mzero: Int = 0
    }

    sum2(List(1, 2, 3)) pln

    implicit val FoldLeftList: FoldLeft[List] = new FoldLeft[List] {
      override def foldLeft[A, B](xs: List[A], b: B, f: (B, A) => B): B = xs.foldLeft(b)(f)
    }

    sum3(List(1, 2, 3, 4, 5)) pln

  }

  /**
    * Classic example of parametric polymorphism
    *
    * @param xs
    * @tparam A
    * @return
    */
  def head[A](xs: List[A]): A = xs(0)

  trait Plus[A] {
    def plus(a2: A): A
  }

  /**
    * Classic example of subtype polymorphism
    *
    * @param a1
    * @param a2
    * @tparam A
    * @return
    */
  def plus[A <: Plus[A]](a1: A, a2: A): A = a1.plus(a2) // or a2.plus(a1)

  /**
    * Adhoc polymorphism
    *
    */

  trait CoolPlus[A] {
    def coolPlus(a1: A, a2: A): A
  }

  /**
    * Example of Adhoc ploymorphism
    *
    * @param a1
    * @param a2
    * @tparam A
    * @return
    */
  def coolPlus[A: CoolPlus](a1: A, a2: A): A = implicitly[CoolPlus[A]].coolPlus(a1, a2)

  //good old foldLeft does the summing work of list of ints
  def sum(xs: List[Int]): Int = xs.foldLeft(0) { _ + _ }

  object IntMonoid {

    def mappend(a1: Int, a2: Int): Int = a1 + a2

    def mzero = 0

  }

  def sum1(xs: List[Int]): Int = xs.foldLeft(IntMonoid.mzero) { IntMonoid.mappend( _, _ ) }

  trait Monoid[A] {

    def mappend(a1: A, a2: A): A

    def mzero: A

  }

  def sum2[A: Monoid](xs: List[A]): A = {
    val m = implicitly[Monoid[A]]
    xs.foldLeft(m.mzero)(m.mappend)
  }

  //Generalising on FoldLeft operation

  trait FoldLeft[F[_]] {
    def foldLeft[A, B](xs: F[A], b: B, f: (B, A) => B): B
  }

  def sum3[M[_]: FoldLeft, A: Monoid](xs: M[A]): A = {

    val m = implicitly[Monoid[A]]

    val fl = implicitly[FoldLeft[M]]

    fl.foldLeft(xs, m.mzero, m.mappend)

  }

}
