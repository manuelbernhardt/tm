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
echo ========================
echo Patching scala module...
echo ========================
echo
echo
cd play-scala
git apply ../tm/fix_scala_model.patch

echo =======================
echo Compiling scala module...
echo =======================
echo
echo
ant -Dplay.path=../play
cd ..

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

