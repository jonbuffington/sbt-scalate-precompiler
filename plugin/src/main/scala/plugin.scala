package pragmagica.scalate.plugin

import sbt._
import FileUtilities._

/**
 *  Based on code from Yasushi Abe's http://github.com/Yasushi/sbt-scalate-compiler
 */ 
trait ScalatePlugin extends DefaultWebProject {

  def templateRoots             = (mainSourcePath / "templates")##
  def generatedDirectory        = outputRootPath / "gen"
  def importsFile               = mainResourcesPath / "scalate_imports.txt"

  override def mainSourceRoots  = super.mainSourceRoots +++ (generatedDirectory##)
  override def watchPaths       = super.watchPaths      +++ (templateRoots***) --- (generatedDirectory***)

  override def compileAction    = super.compileAction dependsOn(precompileScalateAction)

  lazy val precompileScalate = precompileScalateAction
  def precompileScalateAction = {
    val depPath = info.parent match {
      case Some(p) => p.info.pluginsManagedDependencyPath
      case _ => info.pluginsManagedDependencyPath
    }
    task {createDirectory(generatedDirectory, log)} && 
    runTask(
      Some("pragmagica.scalate.Generator"),                               //main class
      depPath ** "*.jar",                                                 //classpath
      Seq(generatedDirectory.absolutePath, importsFile.absolutePath) ++ templateRoots.getPaths      //options
    )
  }

}
 
