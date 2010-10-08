package pragmagica.scalate

import java.io.File
import scala.collection.Set

import org.fusesource.scalate.{TemplateSource, Binding, TemplateEngine}
import org.fusesource.scalate.servlet.ServletRenderContext
import org.fusesource.scalate.support.FileResourceLoader
import org.fusesource.scalate.util.IOUtil



/**  Precompiles Scalate templates
 *   Based on code from Yasushi Abe's http://github.com/Yasushi/scalate-cli
 */
object Precompiler {

  def main(args:Array[String]) {

    assert(args.size >= 2, "invalid argument")
    val sources = args.drop(1).toList

    precompile(sources,args.head)

  }

  def precompile(sources:List[String], out:String ) {

    val output = new File(out)
    output.mkdirs()

    var engine = new TemplateEngine
    engine.resourceLoader = new FileResourceLoader(None)

    for( source <- sources ) {

      val paths = Finder.scan(source, engine.codeGenerators.keySet)

      for( file <- paths ) {
        println( "Generating source for "+file )
        val code        = engine.generateScala(file.getCanonicalPath)
        val sourceFile  = new File(output, sourceName(source, file))
        sourceFile.getParentFile.mkdirs
        IOUtil.writeBinaryFile(sourceFile, code.source.getBytes("UTF-8"))
              
      }
    }

  }

  private def sourceName( source:String, file:File ) = {
    val rsrc = 
        if(source.endsWith("/")) source.take(source.size-1)
        else source
    file.getParent.replace(rsrc,"")+"/"+file.getName+".scala"
  }


}


object Finder {

  def scan(root:String, exts:Set[String] = Set.empty):List[File] = {
    val rootDir = new File(root)
    require( rootDir.exists && rootDir.isDirectory, 
              "root must be a directory name. Was:  " + root )
    find(rootDir).filter(filterExt(_,exts))
  }

  protected def find(basedir:File):List[File] = {
    assert( basedir.canRead, "Can't read "+basedir )
    val (dirs,files) = basedir.listFiles.toList.partition(_.isDirectory)
    println(basedir)
    (files ++ dirs.flatMap(find(_))) 
  }

  private def filterExt(f:File, exts:Set[String]) = 
    if(exts.isEmpty) true
    else exts.find(_ == (f.getName.split("""\.""").last)).isDefined

}
