#!bin/sh
ps -ef | grep tomcat | awk '{ print $2 }' | tr '\n' ' ' | xargs kill -9
export CATALINA_HOME=/Users/cbhatt1/tomcat/echo-tomcat/
echo $CATALINA_HOME
rm -rf $CATALINA_HOME/webapps/ius*
rm -rf $CATALINA_HOME/webapps/spring*
rm -rf $CATALINA_HOME/logs/*
cp /Users/cbhatt1/.m2/repository/org/jboss/resteasy/spring-boot-sample-app/3.1.0.Final/spring-boot-sample-app-3.1.0.Final.war $CATALINA_HOME/webapps/ius.war
sh $CATALINA_HOME/bin/startup.sh

export CATALINA_HOME=/Users/cbhatt1/tomcat/risk-tomcat/
rm -rf $CATALINA_HOME/webapps/ius*
rm -rf $CATALINA_HOME/webapps/spring*
rm -rf $CATALINA_HOME/logs/*
cp /Users/cbhatt1/.m2/repository/org/jboss/resteasy/spring-boot-sample-app/3.1.0.Final/spring-boot-sample-app-3.1.0.Final.war $CATALINA_HOME/webapps/ius.war
sh $CATALINA_HOME/bin/startup.sh


export CATALINA_HOME=/Users/cbhatt1/tomcat/timestamp-tomcat/
rm -rf $CATALINA_HOME/webapps/ius*
rm -rf $CATALINA_HOME/webapps/spring*
rm -rf $CATALINA_HOME/logs/*
cp /Users/cbhatt1/.m2/repository/org/jboss/resteasy/spring-boot-sample-app/3.1.0.Final/spring-boot-sample-app-3.1.0.Final.war $CATALINA_HOME/webapps/ius.war
sh $CATALINA_HOME/bin/startup.sh

