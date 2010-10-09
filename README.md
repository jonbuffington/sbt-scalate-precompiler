# SBT Scalate Template Compiler

Generates scala from scalate templates and compiles them to class files.
This is useful to deploy templates to a restrictive environment like Google App Engine.
(But check the warnings section below)

Mostly based on code from Yasushi Abe's [Scalate Cli](http://github.com/Yasushi/scalate-cli) and
[sbt scalate plugin](github.com/Yasushi/sbt-scalate-plugin/) which were not working for me.
I trimmed ( probably with ill effects ) some code and plan to add some features.

## How to use

  The artifacts do not live in any repo yet so you must build them yourself.
Clone the repo and do `sbt update & sbt publish-local` first in the compiler subdirectory and then in the plugin subdirectory.

Then look at the test subdirectory which contains a test project for an example configuration.
The plugin assumes the templates live in `src/main/templates` if you prefer otherwise, in the project definition override `templateRoots` like this `override def templateRoots: PathFinder = mainSourcePath / "templates"`
 

## Warning

* I have not fully tested this yet so use with caution.
* I have modified the original generator in a couple of ways which I still have not tested.
* While it generates code, compiles it and the class files end in WEB-INF/classes, GAE still complains: `java.security.AccessControlException: access denied (java.io.FilePermission /index.ssp read)`. I need to check with the people in Scalate's ML because I thought it would load the class and not the file.

## Pending work 

* Replace separators with correct platform independent separators.
* Make a clean action.
* Write tests.
* FIXED <s>BUG Generates code into mainSources triggering compilation twice when using `~ compile` or similar.  This was my original problem ( original code entered into a loop ).</s>
