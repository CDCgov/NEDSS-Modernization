%let approot=s:;

libname sqlv "&approot\data";

filename sock 'c:\out';
filename rpttemp "&approot\test";
filename rpttest "&approot\pgm";
filename rptutil "&approot\util";

options  mautosource sasautos=(rptutil);
%include rptutil(	chk_mv.sas 
			parse_wcls.sas 
			ascii.sas
			);
%global ExportType DataSourceName TimePeriod rptdate update footer skip;
