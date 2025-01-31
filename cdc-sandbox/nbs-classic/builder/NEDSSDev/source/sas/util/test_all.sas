%*let ExportType=export_ascii_csv;
%*let ExportType=report;

%include rpttemp(init_sr2.sas);
%*include rpttest(nbssr00002.sas);

%include rpttemp(init_sr5.sas);
%*include rpttest(nbssr00005.sas);

%include rpttemp(init_sr7.sas);
%*include rpttest(nbssr00007.sas);

%include rpttemp(init_sr8.sas);
%*include rpttest(nbssr00008.sas);

%include rpttemp(init_sr9.sas);
%*include rpttest(nbssr00009.sas);

%include rpttemp(init_sr10.sas);
%*include rpttest(nbssr00010.sas);

%include rpttemp(init_sr11.sas);
%*include rpttest(nbssr00011.sas);

%include rpttemp(init_sr12.sas);
%*include rpttest(nbssr00012.sas);

%include rpttest(nbscustom.sas);
