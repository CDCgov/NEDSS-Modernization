/*  Macro Code to Check if a SAS Dataset Exists        */
/*  Example Usage:                                     */
/*                                                     */
/*  %let isDataSetExist = %checkds(sasuser.houses);    */

%macro checkds(dsn);
   %if %sysfunc(exist(&dsn)) % then
       %let tmp = yes;
   %else
       %let tmp = no;
   &tmp
%mend checkds;


