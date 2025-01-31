/* Check whether a macro variable exists.               */
/* The macro returns yes if the macro variable exists   */
/* Otherwise it returns no.                             */
/* Example Usage:                                       */
/* %let isThere = %checkmv(myMcVar);                    */

%macro checkmv(mvar);
%local i tmp;
%let dsid=%sysfunc(open(sashelp.vmacro));
%let num=%sysfunc(varnum(&dsid,name));
%do %until(&ob = -1);
 %let i=%eval(&i+1);
 %let ob=%sysfunc(fetchobs(&dsid,&i));
 %let val=%sysfunc(getvarc(&dsid,&num));

 %if &val = %upcase(&mvar) %then %do;
  %let ob = -1;
  %let tmp=yes;
 %end;

 %else %do;
  %let tmp=no;
 %end;

 %if &ob=-1 %then %do;
  &tmp
 %end;

%end;
%let rc=%sysfunc(close(&dsid));
%mend checkmv;

