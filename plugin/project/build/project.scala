import sbt._

class PrecompilerPluginProject(info: ProjectInfo) extends PluginProject(info) {
  val precompiler = "pragmagica" % "scalate-precompiler_2.8.1" % "0.0.8"
  override def managedStyle = ManagedStyle.Maven
  val publishTo = Resolver.sftp("repobum", "repobum", "/home/public/%s".format(
    if (projectVersion.value.toString.endsWith("-SNAPSHOT")) "snapshots"
    else "releases"
  )) as("repobum_repobum", new java.io.File(Path.userHome + "/.ssh/repobum"))
}


