package pragmagica.scalate.plugin

import sbt._
import FileUtilities._

trait ScalatePlugin extends DefaultWebProject {

  def templateRoots: PathFinder = mainResourcesPath
  def generatedDirectory = outputRootPath / "gen"

  // TODO FIXME This causes continuous compilation to repeat the compilation
  //            because the sources are modified.
  //            maybe we should find a way to add an unobserved sourceRoot
  override def mainSourceRoots = super.mainSourceRoots +++ (generatedDirectory##)

  override def compileAction = super.compileAction dependsOn(precompile)

  lazy val precompile = precompileAction
  def precompileAction = task {createDirectory(generatedDirectory, log)} && 
    runTask(
      Some("pragmagica.scalate.Precompiler"),                             //main
      info.pluginsManagedDependencyPath ** "*.jar",                       //classpath
      Seq(generatedDirectory.absolutePath) ++ templateRoots.getPaths      //options
    )

} 
