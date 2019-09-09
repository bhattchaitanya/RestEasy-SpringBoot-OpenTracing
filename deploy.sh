#!bin/sh
export CATALINA_HOME=/Users/cbhatt1/tomcat/apache-tomcat-8.0.24/
rm -rf $CATALINA_HOME/webapps/ius*
rm -rf $CATALINA_HOME/webapps/spring*
rm -rf $CATALINA_HOME/logs/*
cp /Users/cbhatt1/.m2/repository/org/jboss/resteasy/spring-boot-sample-app/3.1.0.Final/spring-boot-sample-app-3.1.0.Final.war $CATALINA_HOME/webapps/ius.war
ps -ef | grep tomcat | awk '{ print $2 }' | tr '\n' ' ' | xargs kill -9
sh $CATALINA_HOME/bin/startup.sh
tail -f $CATALINA_HOME/logs/catalina.out
