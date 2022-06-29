@echo off 
set uname=root
set pass=root

echo\
echo ---------------------- Preparing MySQL ----------------------
echo\
echo Creating DataBase "private_announcements"..
mysql -u%uname% -p%pass% -e "CREATE DATABASE IF NOT EXISTS `private_announcements`"
echo Deploying content to the DataBase..
mysql -u%uname% -p%pass% private_announcements < src\main\resources\MySQL\Schema.sql
mysqlshow -u%uname% -p%pass% private_announcements

echo\
echo ---------------------- Preparing WAR file ----------------------
echo\
mvn install

