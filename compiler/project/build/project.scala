import sbt._

class PrecompilerProject(info: ProjectInfo) extends DefaultProject(info) {

  val scalate_core = "org.fusesource.scalate"  % "scalate-core"  % "1.3.2"
  val scalate_util = "org.fusesource.scalate"  % "scalate-util"  % "1.3.2"
  val slf4j        = "org.slf4j"               % "slf4j-jdk14"   % "1.6.1"

  override def mainClass = Some("pragmagica.scalate.Precompiler")

}
