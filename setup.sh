#!/bin/sh

echo ========================
echo Copying application.conf
echo ========================
cp conf/application.conf.template conf/application.conf

echo ===================================
echo Cleaning modules and work directory
echo ===================================
rm -rf modules
mkdir modules
play clean

echo
echo
echo ======================
echo Resolving dependencies
echo ======================
echo
echo
play dependencies --sync

echo
echo
echo =============================
echo Installing additional modules
echo =============================
echo
echo
git clone https://github.com/manuelbernhardt/tree modules/tree

# git clone https://github.com/manuelbernhardt/play-excel modules/play-excel
# cd modules/play-excel && git checkout with-lib && cd ../..

echo
echo
echo =========================================================================
echo Done! Visit http://test.localhost:9000
echo
echo Note:
echo - you must have added the following line to your /etc/hosts file:
echo   127.0.0.1 test.localhost demo.localhost
echo - we are currently developing against the 1.2.x branch of play so if you
echo   are using a development version you need to switch to that branch via
echo   "git checkout 1.2.x"
echo   and re-compile the framework via
echo   "cd play/framework && ant"
echo =========================================================================
echo
echo

