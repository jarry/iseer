#!/bin/bash

## 
## file: copy-include.sh
## path: PROJECT_ROOT/shell/
## author: jarryli@gmail.com
## date : 2011-9-12
##

## constant
ROOT_DIR=~/
#PROJECT_NAME=~/iseer

CURRENT_DIR=`pwd`
RELATION_DIR=`dirname $0`

# shell envi path + shell file relation path + ../../ = iseer project path
PROJECT_NAME=${CURRENT_DIR}/${RELATION_DIR}/../..

BUILD_DIR=build
FE_DIR=${PROJECT_NAME}/fe

JS_DIR=${FE_DIR}/js-src
CSS_DIR=${FE_DIR}/css-src
RUN_DIR=${FE_DIR}/webapp

##


# if [ -d ${RUN_DIR} ]; then
#    rm ${RUN_DIR} -rf
#    echo 'delete ' ${RUN_DIR}
# fi

if [ ! -d ${RUN_DIR} ]; then
    mkdir ${RUN_DIR} 
    echo 'mkdir ' ${RUN_DIR}
fi

if [ ! -d ${RUN_DIR}/js ]; then
    mkdir ${RUN_DIR}/js
    echo 'mkdir ' ${RUN_DIR} '/js'
fi

if [ ! -d ${RUN_DIR}/css ]; then
    mkdir ${RUN_DIR}/css
    echo 'mkdir ' ${RUN_DIR} '/css'
fi

echo "copy start..."

#cp ${JS_DIR}/*.js  ${RUN_DIR}/js
#cp  ${JS_DIR}/core.js ${RUN_DIR}/js/
#cp  ${JS_DIR}/iseer.js ${RUN_DIR}/js/
cp  ${JS_DIR}/*.js ${RUN_DIR}/js/
cp  ${JS_DIR}/collect/collect.js ${RUN_DIR}/js/

cp ${CSS_DIR}/*.css        ${RUN_DIR}/css
cp ${CSS_DIR}/collect/collect.css ${RUN_DIR}/css/

ls -l ${RUN_DIR}
ls -l ${RUN_DIR}/js
ls -l ${RUN_DIR}/css

echo "copy end."


exit 0
