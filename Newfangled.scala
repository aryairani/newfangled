package net.arya

import sbt._, sbt.Keys._

object Newfangled extends sbt.AutoPlugin { self =>
  override def trigger = allRequirements
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
    
    // https://github.com/non/kind-projector
    resolvers += "bintray/non" at "http://dl.bintray.com/non/maven",
    addCompilerPlugin("org.spire-math" % "kind-projector" % "0.6.0" cross CrossVersion.binary),

    // https://github.com/mpilquist/local-implicits
    addCompilerPlugin("com.github.mpilquist" %% "local-implicits" % "0.3.0"),

    // https://github.com/typelevel/machinist
    libraryDependencies += "org.typelevel" %% "machinist" % "0.3.0",

    // https://github.com/mpilquist/simulacrum
    libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.3.0",
    macroParadise,

    // // https://github.com/HairyFotr/linter not experienced enough with this to enable by default
    // resolvers += "Linter Repository" at "https://hairyfotr.github.io/linteRepo/releases",
    // addCompilerPlugin("com.foursquare.lint" %% "linter" % "0.1-SNAPSHOT"),

    libraryDependencies += "com.lihaoyi" % "ammonite-repl" % "0.3.2" % "test" cross CrossVersion.full,
    initialCommands in (Test, console) := """ammonite.repl.Repl.run("")"""
  )

  // https://github.com/non/cats
  lazy val cats = Seq(
    libraryDependencies += "org.spire-math" %% "cats-state" % "0.1.0-SNAPSHOT",
    resolvers += Resolver.sonatypeRepo("snapshots")
  )

  lazy val macroParadise = addCompilerPlugin("org.scalamacros" %% "paradise" % "2.0.1" cross CrossVersion.full)
  
  object autoImport {
    // already included for simulacrum
    // val macroParadise = addCompilerPlugin("org.scalamacros" %% "paradise" % "2.0.1" cross CrossVersion.full)

    val scalaReflect = libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

    val noPredef = scalacOptions += "-Yno-predef"
    val noImports = scalacOptions in compile += "-Yno-imports" // will break REPL if not restricted to compile

    val warnUnusedImport = scalacOptions in compile += "-Ywarn-unused-import"
    val fatalWarnings = scalacOptions in compile += "-Xfatal-warnings"
    val picky = Seq(warnUnusedImport, fatalWarnings)

    val catsSnapshot = self.cats
    def cats(module: String, gitReference: String = "#master"): ProjectRef =
      ProjectRef(uri(s"git@github.com:non/cats.git$gitReference"), s"cats-$module")

    def scalaz(module: String): ModuleID = "org.scalaz" %% s"scalaz-$module" % "7.1.3"

    // https://github.com/julien-truffaut/Monocle
    val monocle = Seq(
      macroParadise,
      libraryDependencies ++= {
        val libraryVersion = "1.1.1"
        Seq(
          "com.github.julien-truffaut"  %%  "monocle-core"    % libraryVersion,
          "com.github.julien-truffaut"  %%  "monocle-generic" % libraryVersion,
          "com.github.julien-truffaut"  %%  "monocle-macro"   % libraryVersion
        )
      }
    )
  }
}
