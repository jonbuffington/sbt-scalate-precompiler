package test

import org.fusesource.scalate._
import org.fusesource.scalate.support._
import scala.io._

import java.io._

object ScalateCompilationTest { 

  

  def main(args:Array[String]) {

    println("BEGIN RENDERING ")

    val engine    = new TemplateEngine
    engine.workingDirectory = new File("target/scala_2.8.1.RC2/classes")
    val template  = engine.load( "/test.ssp" )
    val buffer    = new StringWriter()
    val context   = new DefaultRenderContext(engine, new PrintWriter(buffer))

    template.render(context)
    println(buffer.toString)
    
    println("END RENDERING ")

  }

}

