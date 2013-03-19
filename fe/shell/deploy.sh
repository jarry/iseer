#!/bin/bash

## 
## file: deploy.sh
## path: PROJECT_ROOT/fe/js-src/shell/
## author: jarryli@gmail.com
## date : 2012-6-10
##

## constant
ROOT_DIR=~/
#PROJECT_NAME=~/iseer

CURRENT_DIR=`pwd`
RELATION_DIR=`dirname $0`

# shell envi path + shell file relation path + ../../ = iseer project path
PROJECT_NAME=${CURRENT_DIR}/${RELATION_DIR}/../..

echo 'iseer project path : '${PROJECT_NAME}
cd ${PROJECT_NAME}

BUILD_DIR=build
FE_DIR=${PROJECT_NAME}/fe
STATIC_DIR=${PROJECT_NAME}/static

JS_DIR=${FE_DIR}/js-src
CSS_DIR=${FE_DIR}/css-src
RUN_DIR=${FE_DIR}/webapp

##
# 1. copy js,css including files to `webapp`
# 2. merge and compress js,css files to `webapp`
# 3. copy the merged and compressed files from `webaap` to `/iseer/static/`,
#    override the files of same name.
##

echo "deploy start..."

## copy javascript and css
echo "copy including files..."
ant -f ${JS_DIR}/build/copy-include.xml
ant -f ${CSS_DIR}/build/copy-include.xml

## merge and compress javascript
echo "merge and compress js files..."
ant -f ${JS_DIR}/build/merge-js.xml
ant -f ${JS_DIR}/build/compress-js.xml

## merge and compress css
echo "merge and compress css files..."
ant -f ${CSS_DIR}/build/merge-css.xml
ant -f ${CSS_DIR}/build/compress-css.xml

## copy compressed files to static
echo "copy compressed file to static..."
cd ${FE_DIR}
#sh ${FE_DIR}/shell/copy-static.sh 
sh shell/copy-static.sh 
cd ${PROJECT_NAME}

## build war package
# ant -f ${ROOT_DIR}/build/build-war.xml


echo "deploy end."

exit 0
