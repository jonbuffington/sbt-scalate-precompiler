
import sbt._

class ScalatePrecompilerProject(info: ProjectInfo) extends ParentProject(info)
{

  override def parallelExecution = true

  lazy val  compiler = project( "compiler",  "scalate-precompiler",      new Precompiler(_) )
  lazy val  plugin   = project( "plugin",    "sbt-scalate-precompiler",  new PrecompilerPlugin(_), compiler )

}



protected class Precompiler(info: ProjectInfo) extends DefaultProject(info) {

  val scalate = "org.fusesource.scalate"  % "scalate-core"  % "1.2"
  val slf4j   = "org.slf4j"               % "slf4j-jdk14"   % "1.6.1"

  override def mainClass = Some("pragmagica.tools.web.scalate.ScalatePrecompiler")

}       


protected class PrecompilerPlugin(info: ProjectInfo) extends PluginProject(info) {

}

