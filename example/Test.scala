package net.arya

import monocle.macros.Lenses
import monocle.syntax._

import scala.language.postfixOps

import scalaz.effect.{IO, SafeApp}
import scalaz.syntax.bind._

@Lenses case class Foo(a: Int, b: String)

import simulacrum._
import Semigroup.ops._

@typeclass trait Semigroup[@specialized A] {
  @op("|+|", alias=true)
  def append(x: A, y: A): A
}

object Semigroup {
  val intAdd = new Semigroup[Int] { def append(x: Int, y: Int) = x + y }
  val intMult = new Semigroup[Int] { def append(x: Int, y: Int) = x * y }
  implicit val string = new Semigroup[String] { def append(x: String, y: String) = x + y }
}

object Test extends SafeApp {

  val foo1 = Foo(3, "Hello")
  val foo2 = Foo(4, "")

  implicit def fooSemigroup(implicit i: Semigroup[Int], s: Semigroup[String]): Semigroup[Foo] =
    new Semigroup[Foo] { def append(x: Foo, y: Foo) = Foo(x.a |+| y.a, x.b |+| y.b) }

  override def runc =
    imply(Semigroup.intMult) {
      IO.putStrLn(foo1 |+| foo2 toString)
    } >> imply(Semigroup.intAdd) {
      IO.putStrLn(foo1 |+| (foo2 applyLens Foo.b modify (_ + "!!!")) toString)
    }
}
