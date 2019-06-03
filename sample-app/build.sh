#!bin/sh
mvn -B -f pom.xml -s ./resteasy-spring-boot-starter/settings.xml -DskipTests=true -Dcobertura.skip install

