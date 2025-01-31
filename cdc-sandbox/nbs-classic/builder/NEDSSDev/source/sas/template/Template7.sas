
options linesize=max;
data templatePA03_1 (drop = blank blank2 percent1 percent2);
length descrip $35 blank $1 value1 $10 percent1 $10 blank $1 descrip2 $35 blank2 $1 value2 $10 percent2 $10;
infile datalines truncover dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
Total Number of Cases:					! 0	 !		!	!	!		No. Cases w/Internet Follow-up:		!0	!	!	
	Total No. Partners Init'd:			!  0 !		!	!	!		Total No. Partners:					!0	!	!	
	Total No. Social Contacts Init'd:	! 0  !		!	!	!		Total No. Social Contacts:			!0	!	!	
    Total No. Associates Init'd:		! 0  !		!	!	!		Total No. Associates:				!0	!	!	
      									!    !		!	!	!											!   !	!   
    Contact Index:						! 0  !		!	!	!		IPS Contact Index:					!0	!	!	
    Cluster Index:						! 0  !		!	!	!		IPS Cluster Index:					!0	!	!	
										!    !		!	!	!											!	!	!	
run;


data templatePA03_2 (drop = blank  percent1);
length descrip $35 blank $1 value1 5 percent1 $10;
infile datalines truncover dsd dlm='!' ;
input descrip $ blank $ value1 BEST9. percent1 $ ;
datalines ;
Total No. IPS Partners:					! 0	 !0		!	!
Total No. IPS Social Contacts:			! 0  !0		!	!
Total No. IPS Associates:				! 0  !0		!	!
run;


data templatePA03_3 ;
length descrip $35 I1 5  I2 5 I3 5  I4 5 I5 5 I6 5 I7 5;
infile datalines truncover dsd dlm='!' ;
input descrip $ I1 best9.  I2  best9. I3  best9.  I4  best9. I5  best9. I6  best9. I7  best9. ;
datalines ;
Sexual Contact:	!  0 !	0	!	0!	0!	0! 	0 !	0	!
run;

data templatePA03_4 ;
length descrip $35 I1 5  I2 5 I3 5  I4 5 I5 5 I6 5 I7 5;
infile datalines truncover dsd dlm='!' ;
input descrip $ I1 best9.  I2  best9. I3  best9.  I4  best9. I5  best9. I6  best9. I7  best9. ;
datalines ;
Social Contact:	! 0  !	0	!	0!	0!	0! 	0 !	0	!
run;

data templatePA03_5 ;
length descrip $35 I1 5  I2 5 I3 5  I4 5 I5 5 I6 5 I7 5;
infile datalines truncover dsd dlm='!' ;
input descrip $ I1 best9.  I2  best9. I3  best9.  I4  best9. I5  best9. I6  best9. I7  best9. ;
datalines ;
Associate:		! 0  !	0	!	0!	0!	0! 	0 !	0	!
run;
