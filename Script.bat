@echo off 
set uname=root
set pass=root

echo\
echo ---------------------- Preparing MySQL ----------------------
echo\
echo Creating DataBase "BDG"..
mysql -u%uname% -p%pass% -e "CREATE DATABASE IF NOT EXISTS `bdg`"
echo Deploying content to the DataBase..
mysql -u%uname% -p%pass% bdg < ./Schema.sql
mysqlshow -u%uname% -p%pass% bdg

echo\


