

build: target/zb-profiler-1.0-SNAPSHOT-jar-with-dependencies.jar
	mvn clean compile assembly:single

test: build
	java -javaagent:target/zb-profiler-1.0-SNAPSHOT-jar-with-dependencies.jar de.zell.zb.profiler.MainClass

all: build test
