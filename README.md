# project-common
An sbt plugin providing some nice build defaults and settings (according to me).

## Example Usage:
`project/plugins.sbt`:
```scala
addSbtPlugin("net.arya" % "project-common" % "0.0.3")
```

`build.sbt`
```scala
organization := "net.arya"
name := "example"

// project-common settings
libraryDependencies += scalaz("effect")
monocle
```

`Test.scala`
```scala
package net.arya

import scalaz._, Scalaz._, effect.IO

@Lenses case class Foo(a: Int, b: String)

object Test extends App {
  IO.putStrLn(Foo(3,"b"))
}

```

## Imported sbt plugins
* https://github.com/puffnfresh/wartremover 0.13 (imported but not enabled)
* https://github.com/non/kind-projector 0.5.4 (enabled automatically)

## Default project settings
```scala
scalaVersion := "2.11.6",
scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",        // N.B. doesn't work well with the ??? hole
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
),
libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.3.2" % "test" cross CrossVersion.full,
initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""
```

## Auto-Imports
The following settings are made available to your build definition:
```scala
val macroParadise = addCompilerPlugin("org.scalamacros" %% "paradise" % "2.0.1" cross CrossVersion.full)
val scalaReflect = libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
val catsSnapshot = libraryDependencies += "org.spire-math" %% "cats-state" % "0.1.0-SNAPSHOT"
def scalaz(module: String) = "org.scalaz" %% s"scalaz-$module" % "7.1.2"
def cats(module: String, gitReference: String = "#master"): ProjectRef // depend on particular cats git ref
val monocle: Seq[Setting[...]] // monocle core, generic, macros v1.1.1

val noPredef = scalacOptions += "-Yno-predef"
val noImports = scalacOptions in compile += "-Yno-imports" // will break REPL if not restricted to compile

val warnUnusedImport = scalacOptions in compile += "-Ywarn-unused-import"
val fatalWarnings = scalacOptions in compile += "-Xfatal-warnings"
val picky = Seq(warnUnusedImport, fatalWarnings)
```

Thanks to pfn and OlegYch__ in #sbt, tpolecat for the `scalacOptions`, and all the library and plugin developers.
