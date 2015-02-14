#!/bin/sh


# get comment
comment="$1"

if [ "$comment" == "" ]; then
comment="push form CI"
echo "no comment specified to deploy, using default : $comment"
fi

ghPagesPath="/Users/chandrasekharkode/Desktop/Kode/Programming/scalaprojects/chandu0101.github.io"

projectPath=$ghPagesPath/sjru

cp index.html $projectPath

cp  js/demo-opt.js $projectPath/js/

cp  js/demo-jsdeps.js $projectPath/js/

cd $ghPagesPath

git add sjru

git commit -m "$comment"

git push