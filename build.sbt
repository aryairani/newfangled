sbtPlugin := true

organization := "net.arya"

name := "newfangled"

enablePlugins(GitVersioning)

enablePlugins(GitBranchPrompt)

git.useGitDescribe := true

sonatypeProfileName := "net.arya"

pomExtra := {
  <url>http://github.com/refried/newfangled</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:github.com/refried/newfangled</connection>
      <developerConnection>scm:git:git@github.com:refried/newfangled</developerConnection>
      <url>github.com/refried/newfangled</url>
    </scm>
    <developers>
      <developer>
        <id>arya</id>
        <name>Arya Irani</name>
        <url>https://github.com/refried</url>
      </developer>
    </developers>
}

