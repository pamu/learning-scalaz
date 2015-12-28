package com.pnagarjuna.learning.scalaz

/**
  * Created by pnagarjuna on 28/12/15.
  */
object LearningFunctor {

  trait Functor[F[_]] {
    def map[A, B](xs: F[A])(f: A => B): B
  }

  object FunctorLaws {

    def identity[F[_], A](xs: F[A])(implicit functor: Functor[F]) =
      functor.map(xs)(elem => elem) == xs

    def composition[F[_], A, B, C](xs: F[A])(f: A => B, g: B => C)(implicit functor: Functor[F]) = ???

  }

}
