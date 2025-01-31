%macro odsmarkup (lib, dsname, exfile, label, tagset);
ods listing close;
ods markup tagset=&tagset body=sock;
proc print data=&lib..&dsname noobs &label;
run;
ods markup close;
ods listing;
%mend odsmarkup;