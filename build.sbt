name := "demo"
 
version := "1.0" 
      
lazy val `untitled` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

resolvers += "Maven central" at "https://repo1.maven.org/maven2/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice )

libraryDependencies += "com.47deg" %% "github4s" % "0.22.0"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % "test"
)

libraryDependencies += "io.chrisdavenport" %% "vault" % "2.0.0"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )


