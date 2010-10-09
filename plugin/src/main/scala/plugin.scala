package pragmagica.scalate.plugin

import sbt._
import FileUtilities._

/**
 *  Based on code from Yasushi Abe's http://github.com/Yasushi/sbt-scalate-compiler
 */ 
trait ScalatePlugin extends DefaultWebProject {

  def templateRoots: PathFinder = mainSourcePath / "templates"
  def generatedDirectory        = outputRootPath / "gen"

  override def watchPaths = super.watchPaths +++ templateRoots

  override def compileAction = super.compileAction dependsOn(compileScalate)

  lazy val compileScalate = compileScalateAction
  protected def compileScalateAction = task { 

    val compilerConfig = new MainCompileConfig { 
      override def sourceRoots = generatedDirectory##
      override def sources = generatedDirectory ** "*.scala"
    } 
    new CompileConditional(compilerConfig, buildCompiler).run 

  } dependsOn(precompileScalate)


  lazy val precompileScalate = precompileScalateAction
  def precompileScalateAction = task {createDirectory(generatedDirectory, log)} && 
    runTask(
      Some("pragmagica.scalate.Generator"),                               //main class
      info.pluginsManagedDependencyPath ** "*.jar",                       //classpath
      Seq(generatedDirectory.absolutePath) ++ templateRoots.getPaths      //options
    )

}
 
