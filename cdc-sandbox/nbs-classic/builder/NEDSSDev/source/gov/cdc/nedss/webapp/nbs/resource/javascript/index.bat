@echo off
rem Creates an index of functions in a JavaScript file.
set index.filename=
if "%1" == "" (
echo.
echo Syntax:  index filename.js
echo Where filename.js is the name of the JavaScript file to be indexed.
echo This will create a file called filename.js.index.txt.
echo The index will provide a sorted list all functions in the input file.
echo If you do not specify a filename on the command line,
echo you will be prompted to enter one.
echo.
set /p index.filename=What file do you want to index? 
) else (
set index.filename=%1
)
if exist %index.filename% (
find "function" %index.filename% | find /v "*" | find /v "----------" | sort > %index.filename%.index.txt
echo.
echo Done.
) else (
echo.
echo Sorry.  That file does not exist.
)
set index.filename=
