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
../play/play clean

echo
echo
echo ======================
echo Resolving dependencies
echo ======================
echo
echo
../play/play dependencies --sync

echo
echo
echo =============================
echo Installing additional modules
echo =============================
echo
echo
git clone https://github.com/manuelbernhardt/tree modules/tree

echo
echo
echo =========================================================================
echo Done! Visit http://test.localhost:9000
echo
echo Note:
echo   you must have added the following line to your /etc/hosts file:
echo   "127.0.0.1 test.localhost demo.localhost"
echo =========================================================================
echo
echo

