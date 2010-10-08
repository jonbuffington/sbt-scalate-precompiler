import sbt._

class PrecompilerPluginProject(info: ProjectInfo) extends PluginProject(info) {
  //pragmagica/scalate-precompiler_2.8.1.RC2/0.0.1/
  val precompiler = "pragmagica" % "scalate-precompiler_2.8.1.RC2" % "0.0.2"
}


