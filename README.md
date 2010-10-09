# SBT Scalate Template Compiler

Generates scala from scalate templates and compiles them to class files.
This is useful to deploy templates to a restrictive environment like Google App Engine.

Because sbt 0.7.4 is scala 2.7.7 based it can't directly use scalate which is based in scala 2.8.0; so the project is based in two parts: a 2.8.0 based generator and the plugin proper which invokes the generator as an external process.

## How to 

### Build

  The artifacts do not live in any repo yet so you must build them yourself.
Clone the repo and do `sbt update & sbt publish-local` first in the compiler subdirectory and then in the plugin subdirectory.

Then look at the test subdirectory which contains a test project for an example configuration.
The plugin assumes the templates live in `src/main/templates` if you prefer otherwise, in the project definition override `templateRoots` like this `override def templateRoots = mainSourcePath / "resources"` 

### Use

The plugin redefines the compile action to depend on two more actions: `precompileScalate` which generates scala code from the templates and `compileScalate` which compiles that code to class files with the same compiler sbt compiles the rest of the sources.

If you run `~ compile` or `~ prepare-webapp` sbt recompiles the templates when you save them.
 
### Google App Engine

If you deploy to google app engine you must set this properties in your app.yaml

  system_properties:
    scalate.allowReload: false
    scalate.workdir: WEB-INF

If you are an xml lover and use appengine-web.xml :

  <system-properties>
      <property name="java.util.logging.config.file" value="WEB-INF/classes/logging.properties" />
      <property name="scalate.allowReload" value="false"/>
      <property name="scalate.workdir" value="WEB-INF"/>
  </system-properties>

I think this can be set programatically, too.

For a sample servlet look at [hello-scalate-appengine's template server](http://github.com/Yasushi/hello-scalate-appengine/blob/master/src/main/scala/ya/TemplateEngineServlet.scala). I will upload a sample project soon. 

## Warning

* I have not fully tested this yet so use with caution: it may eat your soul and burn your house.
* <s>While it generates code, compiles it and the class files end in WEB-INF/classes, GAE still complains: `java.security.AccessControlException: access denied (java.io.FilePermission /index.ssp read)`. I need to check with the people in Scalate's ML because I thought it would load the class and not the file.</s>

## Pending work 

* Make a GAE/J sample app.
* Replace separators with correct platform independent separators.
* Stop using MainCompilerConfig. (Why?)
* Make a clean action.
* Write tests.
* Automate the full building thing ( sbt is 2.7.4 and scalate 2.8.0. sbt does not like to build projects with mixed scala versions ).
* <s>BUG Generates code into mainSources triggering compilation twice when using `~ compile` or similar.  This was my original problem ( original code entered into a loop ).</s>


## Acknowledgements and credits

Mostly based on code from Yasushi Abe's [Scalate Cli](http://github.com/Yasushi/scalate-cli) and
[sbt scalate plugin](github.com/Yasushi/sbt-scalate-plugin/) which were not working for me.
I trimmed ( probably with ill effects ) some code and plan to add some features.

