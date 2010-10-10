import sbt._

class PrecompilerPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val precompiler = "pragmagica" % "scalate-precompiler_2.8.1.RC2" % "0.0.5"
}


