package net.arya

import sbt._, sbt.Keys._
//import wartremover.WartRemover.autoImport._

object ProjectCommon extends sbt.AutoPlugin {
  override def trigger = allRequirements
  override def requires = wartremover.WartRemover
  override val projectSettings = Seq(
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
//    scalacOptions in compile ++= Seq(
//      "-Xfatal-warnings",
//      "-Ywarn-unused-import"     // 2.11 only
//      ),
    libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.2",
    libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.3.2" % "test" cross CrossVersion.full,
    initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""
//    ,wartremoverErrors ++= Warts.unsafe
  )
  object autoImport {
    val macroParadise = addCompilerPlugin("org.scalamacros" %% "paradise" % "2.0.1" cross CrossVersion.full)
    val noPredef = scalacOptions += "-Yno-predef"
    val NOIMPORTS = scalacOptions in compile += "-Yno-imports" // will break REPL if not restricted to compile
    val warnUnusedImport = scalacOptions in compile += "-Ywarn-unused-import"
    val fatalWarnings = scalacOptions in compile += "-Xfatal-warnings"
    val picky = scalacOptions in compile ++= Seq("-Ywarn-unused-import", "-Xfatal-warnings")

    val monocle = Seq(
      macroParadise,
      resolvers += Resolver.sonatypeRepo("releases"),
      libraryDependencies ++= {
        val libraryVersion = "1.1.1"
        Seq(
          "com.github.julien-truffaut" %% "monocle-core" % libraryVersion,
          "com.github.julien-truffaut"  %%  "monocle-generic" % libraryVersion,
          "com.github.julien-truffaut"  %%  "monocle-macro"   % libraryVersion
          // "com.github.julien-truffaut"  %%  "monocle-law"     % libraryVersion % "test"
        )
      }
    )
  }

}
