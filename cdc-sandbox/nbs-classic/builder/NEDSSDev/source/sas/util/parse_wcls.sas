%macro parse_wcls;
%local ft i;
%let i=1;

Footnote;
Footnote1 h=1 j=L Font=swiss "This report was built using the following criteria:"
		  j=R "Report run on: &rptdate, Data refreshed on: &update"; 
%do %until( %length(%scan("&footer",&i,"|^~")) =0 or &i >9);
	%let ft=%scan("&footer",&i,"|~^");
	%let i = %eval(&i+1);
	Footnote&i h=1 j=l Font=swiss "&ft";
%end;
%mend parse_wcls;

