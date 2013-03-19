#!/bin/bash

## 
## file: release.sh
## path: PROJECT_ROOT/shell/
## desc: update new version from svn  
##       and execute ant script of compile
##       and restart tomcat
## author: jarryli@gmail.com
## date : 2011-9-12
##

## constant
#PROJECT_ROOT=/home/work/webapp/iseer/
ROOT_DIR=./
PROJECT_NAME=iseer
BUILD_DIR=build
JAVASCRIPT_DIR=js-src
CSS_DIR=css-src
TOMCAT_HOME=$TOMCAT_HOME
#TOMCAT_HOME=/usr/local/tomcat/

## svn update 
#cd ${PROJECT_ROOT}/
svn update --force
echo "svn updating end"

## compile java
ant -f ${ROOT_DIR}/build/compile-classes.xml
echo "compiling classes end"

## merge and compress javascript
ant -f ${ROOT_DIR}/${JAVASCRIPT_DIR}/build/merge-js.xml
ant -f ${ROOT_DIR}/${JAVASCRIPT_DIR}/build/copy-template.xml
ant -f ${ROOT_DIR}/${JAVASCRIPT_DIR}/build/compress-js.xml

## merge and compress css
ant -f ${ROOT_DIR}/${CSS_DIR}/build/merge-css.xml
ant -f ${ROOT_DIR}/${CSS_DIR}/build/compress-css.xml

## build war package
ant -f ${ROOT_DIR}/build/build-war.xml
echo "building war package end"

## restart tomcat
if [  -d ${TOMCAT_HOME} ]; then

#    killall -9 java
#    sleep 2
    killall -9 java
    sleep 2
    sh ${TOMCAT_HOME}/bin/shutdown.sh
    sleep 10
    sh ${TOMCAT_HOME}/bin/startup.sh
    # print startup information
    tail -f ${TOMCAT_HOME}/logs/catalina.out
    #tail -f ${TOMCAT_HOME}/logs/iseer/log4j.log

fi

exit 0
