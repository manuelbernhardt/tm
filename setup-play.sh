#!/bin/sh

echo ===============
echo Cloning play...
echo ===============
echo
echo

cd ..
git clone git://github.com/playframework/play.git --depth 1
cd play
git checkout 1.2.x
cd ..

echo
echo
echo =================
echo Compiling play...
echo =================
echo
echo
cd play/framework
ant
cd ../../tm

echo ============================================================
echo Done! Run "setup-scala.sh" next to install the scala module.
echo ============================================================
