package pragmagica.scalate.plugin

import sbt._
import FileUtilities._

/**
 *  Based on code from Yasushi Abe's http://github.com/Yasushi/sbt-scalate-compiler
 */ 
trait ScalatePlugin extends DefaultWebProject {

  def templateRoots: PathFinder = mainSourcePath / "templates"
  def generatedDirectory        = outputRootPath / "gen"

  override def mainSourceRoots = super.mainSourceRoots +++ (generatedDirectory##)
  
  override def watchPaths = super.watchPaths +++ templateRoots --- (generatedDirectory***)

  override def compileAction = super.compileAction dependsOn(precompileScalateAction)

  lazy val precompileScalate = precompileScalateAction
  def precompileScalateAction = task {createDirectory(generatedDirectory, log)} && 
    runTask(
      Some("pragmagica.scalate.Generator"),                               //main class
      info.pluginsManagedDependencyPath ** "*.jar",                       //classpath
      Seq(generatedDirectory.absolutePath) ++ templateRoots.getPaths      //options
    )

}
 
