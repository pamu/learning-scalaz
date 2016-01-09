package com.pnagarjuna.learning.scalaz

/**
  * Created by pnagarjuna on 09/01/16.
  */
object Demo {

  def main(args: Array[String]): Unit = {
    println("Scalaz")

    implicit val greeting = "hello"

    implicit val name = "Pamu Nagarjuna"

    def greet(implicit greeting: String, name: String): String = s"${greeting} ${name}"

    greet
  }

}
