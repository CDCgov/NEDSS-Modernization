@echo off
cls
setlocal

rem set drive and directory
set my.drv=%~d0
set my.dir=%~dp0
set my.dir=%my.dir:~0,-1%

rem set component names
rem set my.jdk.name=jrockit-j2sdk1.4.2_08
rem set my.ant.name=apache-ant-1.6.5
set my.ydoc.name=ydoc-2.2_05-jdk1.4

rem set home directories
rem set JAVA_HOME=C:\jboss-4.0.3SP1\%my.jdk.name%
rem set ANT_HOME=C:\%my.ant.name%

rem set the path
set PATH=.;%JAVA_HOME%\bin;%ANT_HOME%\bin

rem set command-line arguments
set my.cp=.
set my.cp=%my.cp%;%ANT_HOME%\lib\ant-launcher.jar
set my.mem.max=64
set my.mem.min=32
set my.main=org.apache.tools.ant.launch.Launcher

rem parse command line
set arg=%1
if "%1" equ "/?" set arg=-projecthelp

rem build
java -cp "%my.cp%" -Xms%my.mem.min%m -Xmx%my.mem.max%m %my.main% -f doc.xml %arg%
goto :eof
