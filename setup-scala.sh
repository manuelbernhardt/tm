#!/bin/sh

echo =======================
echo Cloning scala module...
echo =======================
echo
echo

cd ..
git clone git://github.com/playframework/play-scala.git

echo
echo
echo =======================
echo Linking scala module...
echo =======================
echo
echo

cd tm
echo '../play-scala' > modules/scala

echo
echo
echo =====
echo Done!
echo =====

