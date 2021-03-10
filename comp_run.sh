#!/bin/bash
rm *.class
javac Main.java
java -cp .:./postgresql-42.2.18.jre6.jar Main
