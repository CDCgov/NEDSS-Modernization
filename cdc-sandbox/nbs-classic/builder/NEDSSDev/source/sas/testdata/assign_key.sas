%macro assign_key (ds, key);
 data &ds;
  retain &key 1;
  if &key=1 then output;
  set &ds;
	&key+1;
	output;
 run;
%mend assign_key;