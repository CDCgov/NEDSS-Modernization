options linesize=max;
data template2 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $10 percent2 $10;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
TOTAL PERIOD PARTNERS:		!   !	!	!	!	TOTAL CLUSTERS INITIATED:	!	!	!	
TOTAL PARTNERS INITIATED:	!	!	!	!	!		CLUSTER INDEX:			!	!	!	
	FROM OI:				!   !	!	!	!		CASES W/NO CLUSTERS:	!	!	!	
	FROM RI:				!   !	!	!	!								!	!	!	
CONTACT INDEX:				!   !	!	!	!								!	!	!	
CASES W/NO PARTNERS:		!   !	!	!	!								!	!	!	
RUN;
