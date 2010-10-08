import sbt._

import pragmagica.scalate.plugin._

class TestProject(info:ProjectInfo) extends DefaultWebProject(info) 
 with ScalatePlugin  {

  val scalate = "org.fusesource.scalate" % "scalate-core" % "1.2"
  val servlet = "javax.servlet" % "servlet-api" % "2.5" % "provided"
  val slf4j   = "org.slf4j"     % "slf4j-jdk14" % "1.6.1"
        
}
