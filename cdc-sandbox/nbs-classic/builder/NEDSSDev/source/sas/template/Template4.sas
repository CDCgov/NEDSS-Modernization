
data template4 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS EXAMINED:	!   !0	!	!	!	NEW CLUSTERS EXAMINED:			!	!0	!	
	WITHIN 3 DAYS:		!   !0	!	!	!		WITHIN 3 DAYS:				!	!0	!	
	WITHIN 5 DAYS:		!   !0	!	!	!		WITHIN 5 DAYS:				!	!0	!	
	WITHIN 7 DAYS:		!   !0	!	!	!		WITHIN 7 DAYS:				!	!0	!	
	WITHIN 14 DAYS:		!   !0	!	!	!		WITHIN 14 DAYS:				!	!0	!	
;
RUN;
