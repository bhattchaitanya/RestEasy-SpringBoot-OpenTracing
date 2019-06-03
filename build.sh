#!bin/sh
mvn -B -f pom.xml -DskipTests=true -Dcobertura.skip install

