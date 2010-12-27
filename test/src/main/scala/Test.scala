package test

import org.fusesource.scalate._
import org.fusesource.scalate.support._
import scala.io._

import java.io._

object ScalateCompilationTest { 

  

  def main(args:Array[String]) {

    println("BEGIN RENDERING ")

    val engine    = new TemplateEngine
    engine.workingDirectory = new File("target/scala_2.8.1/classes")

    val templates = List("/test.ssp", "/subfolder/test.scaml", "/subfolder/test.ssp") 
    templates foreach { t =>

      val template  = engine.load( t )
      val buffer    = new StringWriter()
      val context   = new DefaultRenderContext(engine, new PrintWriter(buffer))

      template.render(context)
      println(buffer.toString)

    }

        println("END RENDERING ")

  }

}

