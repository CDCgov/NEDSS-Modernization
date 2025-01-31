*************************************************************************************;
*** UTITLITY PROGRAM WHICH RESETS ALL THE USER DEFINED GLOBAL MACRO VARIABLES     ***;
*** CREATED : 06FEB2002
*************************************************************************************;
data _null_;
   set sashelp.vmacro(where=(scope='GLOBAL'));
   str="%"||"LET "||trim(name) ||"=;";
   call execute(str);
run;
