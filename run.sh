#!/bin/bash
java -javaagent:target/zb-profiler-1.0-SNAPSHOT-jar-with-dependencies.jar=$1 de.zell.zb.test.MainClass
