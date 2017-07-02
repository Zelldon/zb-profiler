# ZB-Profiler

Contains an simple profiler for Java applications. It will meassure and print the execution time of each method.

## Usage

To build the profiler use:

$ make build 

After that simply copy the created jar, which can be found in the target directory, to your java application which should be profiled.

To run the profiler together with your application use the `-javaagent` parameter.

$ java -javaagent:target/zb-profiler-1.0-SNAPSHOT-jar-with-dependencies.jar de.zell.zb.profiler.test.MainClass

You can specify a filter so that only specific methods are profiled/logged. 
For that you have to specify the package/class als parameter of the javaagent.

$ java -javaagent:target/zb-profiler-1.0-SNAPSHOT-jar-with-dependencies.jar=/org/package/subpackage de.zell.zb.profiler.test.MainClass

Please note that the package separator is not a dot (you have to use '/').

