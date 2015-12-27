package com.pnagarjuna.learning

/**
  * Created by pnagarjuna on 27/12/15.
  */
package object scalaz {
  implicit class PrintUtils(val any: Any) extends AnyVal {
    def pln = Predef.println(s"${any.toString}")
  }
}
