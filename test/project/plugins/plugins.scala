
import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val scalatePlugin = "pragmagica" % "sbt-scalate-plugin" % "0.0.3"
}


