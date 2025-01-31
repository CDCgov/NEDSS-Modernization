%macro export(lib, dsname, exfile, exporttype);
%goto &exporttype;
%EXPORT_ASCII_CSV:
	%ascii(&lib, &dsname, &exfile,label,COMMA);
	%*odsmarkup (&lib, &dsname, &exfile, label, CSV);
	%goto exportend;
%EXPORT_ASCII_TAB:
	%ascii(&lib, &dsname, &exfile,label,TAB);
	%*odsmarkup (&lib, &dsname, &exfile, label, tagsets.tab);
	%goto exportend;
%EXPORT_XML:
	%odsmarkup (&lib, &dsname, &exfile, , sasxmog);
	%goto exportend;
%EXPORT_HTML:
	%odsmarkup (&lib, &dsname, &exfile, label, CHTML);
	%goto exportend;
%EXPORT_WML:
	%odsmarkup (&lib, &dsname, &exfile, label, WML);
	%goto exportend;
%EXPORT_EXCEL:
%EXPORT_SAS:

%exportend:
%mend export;