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
STATIC_DIR=${PROJECT_NAME}/static

JS_DIR=${FE_DIR}/js-src
CSS_DIR=${FE_DIR}/css-src
RUN_DIR=${FE_DIR}/webapp

##

echo "copy start..."

if [ ! -d ${STATIC_DIR} ]; then
    mkdir ${STATIC_DIR}
    echo 'mkdir ' ${STATIC_DIR}
fi

if [ ! -d ${STATIC_DIR}/css ]; then
    mkdir ${STATIC_DIR}/css
    echo 'mkdir ' ${STATIC_DIR}/css
fi

if [ ! -d ${STATIC_DIR}/js ]; then
    mkdir ${STATIC_DIR}/js
    echo 'mkdir ' ${STATIC_DIR}/js
fi

cp ${RUN_DIR}/css/*        ${STATIC_DIR}/css
cp ${RUN_DIR}/js/*        ${STATIC_DIR}/js


ls -l ${STATIC_DIR}/
ls -l ${STATIC_DIR}/js
ls -l ${STATIC_DIR}/css

echo "copy end."


exit 0
