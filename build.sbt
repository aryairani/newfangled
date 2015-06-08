sbtPlugin := true

organization := "net.arya"
name := "project-common"

addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.13")

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.8.4")

enablePlugins(GitVersioning)

enablePlugins(GitBranchPrompt)

git.useGitDescribe := true

resolvers += "bintray/non" at "http://dl.bintray.com/non/maven"
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.5.2")

sonatypeProfileName := "net.arya"

pomExtra := {
  <url>http://github.com/refried/project-common</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/refried/project-common</connection>
      <developerConnection>scm:git:git@github.com:refried/project-common</developerConnection>
      <url>github.com/refried/project-common</url>
    </scm>
    <developers>
      <developer>
        <id>arya</id>
        <name>Arya Irani</name>
        <url>https://github.com/refried</url>
      </developer>
    </developers>
}

