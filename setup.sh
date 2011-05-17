#!/bin/sh

echo ================
echo Cleaning modules and work directory
echo ================
rm -rf modules
mkdir modules
play clean

echo
echo
echo ================
echo Resolving dependencies
echo ================
echo
echo
play dependencies --sync

echo
echo
echo ================
echo Installing additional modules
echo ================
echo
echo
git clone https://github.com/manuelbernhardt/tree modules/tree
git clone https://github.com/manuelbernhardt/play-excel modules/play-excel
cd modules/play-excel && git checkout with-lib && cd ../..

echo
echo
echo ================
echo Done! Visit http://oxiras.localhost.com:9000
echo (note that you must have added oxiras.localhost to your /etc/hosts file)
echo ================
echo
echo

