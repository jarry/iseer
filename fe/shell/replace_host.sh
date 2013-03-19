#!/bin/bash

## 
## file: copy-include.sh
## path: PROJECT_ROOT/shell/
## author: jarryli@gmail.com
## date : 2011-9-12
##

## constant
#PROJECT_ROOT=/home/work/webapp/iseer/
ROOT_DIR=~/
PROJECT_NAME=~/iseer
BUILD_DIR=build
FE_DIR=${PROJECT_NAME}/fe

JS_DIR=${FE_DIR}/js-src
CSS_DIR=${FE_DIR}/css-src
RUN_DIR=${FE_DIR}/webapp

files="${RUN_DIR}/js/core.js ${RUN_DIR}/js/iseer.js ${RUN_DIR}/css/iseer.css"

for file in $files
do
    cat $file | sed -e "s/$1/$2/" > $file.1
    mv $file.1 $file    
done

exit 0
