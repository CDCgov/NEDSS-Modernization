options linesize=max;
data template1 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
CASES ASSIGNED:	! 	!0	!		!	!HIV PREVIOUS POSITIVE:	!	!0	!	
CASES CLOSED:	!   !0	!		!	!	HIV TESTED:			!	!0	!	
CASES IX'D:		!   !0	!0.0%	!	!	HIV NEW POSITIVE:	!	!0	!	
WITHIN 3 DAYS:	!   !0	!0.0%	!	!HIV POSTTEST COUNSEL:	!	!0	!	
WITHIN 5 DAYS:	!   !0	!0.0%	!	!								!	!	!	
WITHIN 7 DAYS:	!   !0	!0.0%	!	!PARTNER NOTIFICATION INDEX:!	!0	!	
WITHIN 14 DAYS:	!   !0	!0.0%	!	!TESTING INDEX:				!	!0	!	
						!   !	!	!	!								!	!	!	
CASES REINTERVIEWED:!   !0	!	!	!								!	!	!	
run;

data template2 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
TOTAL PERIOD PARTNERS:	 !   !0	!	!	!	TOTAL CLUSTERS INITIATED:		!	!0	!	
TOTAL PARTNERS INITIATED:!   !0	!	!	!		CLUSTER INDEX:				!	!0	!	
FROM OI:				 !   !0	!	!	!		CASES /W NO CLUSTERS:		!	!0	!	
FROM RI:				 !   !0	!	!	!									!	!	!	
CONTACT INDEX:			 !   !0	!	!	!									!	!	!	
CASES W/NO PARTNERS:	 !   !0	!	!	!									!	!	!	
;
RUN;


data template3 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS NOTIFIED:		!   !0	!	!	!NEW CLUSTERS NOTIFIED:		!	!0	!	
PREV. NEG, NEW POS:			!   !0	!	!	!PREV. NEG, NEW POS:		!	!0	!	
PREV. NEG, STILL NEG:		!   !0	!	!	!PREV. NEG, STILL NEG:		!	!0	!	
PREV. NEG, NO TEST:			!   !0	!	!	!PREV. NEG, NO TEST:		!	!0	!	
NO PREV. TEST, NEW POS:		!   !0	!	!	!NO PREV. TEST, NEW POS:	!	!0	!	
NO PREV. TEST, NEW NEG:		!   !0	!	!	!NO PREV. TEST, NEW NEG:	!	!0	!	
NO PREV TEST, NO TEST:		!   !0	!	!	!	NO PREV TEST, NO TEST:	!	!0	!	
NEW PARTNERS NOT NOTIFIED:	!   !0	!	!	!NEW CLUSTERS NOT NOTIFIED:	!	!0	!	
INSUFFICIENT INFO:			!  	!0	!	!	!INSUFFICIENT INFO:			!	!0	!	
UNABLE TO LOCATE:			!   !0	!	!	!UNABLE TO LOCATE:			!	!0	!	
REFUSED EXAM:				!   !0	!	!	!REFUSED EXAM:				!	!0	!	
OOJ:						!   !0	!	!	!OOJ:						!	!0	!	
OTHER:						!   !0	!	!	!OTHER:						!	!0	!	
	DOMESTIC VIOLENCE RISK:	!   !0	!	!	!		DOMESTIC VIOLENCE RISK:		!	!0	!0.0%	
	PATIENT DECEASED:		!   !0	!	!	!		PATIENT DECEASED:			!	!0	!0.0%	
PREVIOUS POS:				!   !0	!	!	!PREVIOUS POS:					!	!0	!	
OPEN:						!   !0	!	!	!OPEN:							!	!0	!	
;
RUN;


data template4 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS NOTIFIED:!   !	!	!	!NEW CLUSTERS NOTIFIED:	!	!0	!	
WITHIN 3 DAYS:!   !0	!0.0%	!	!WITHIN 3 DAYS:			!	!0	!0.0%	
WITHIN 5 DAYS:!   !0	!0.0%	!	!WITHIN 5 DAYS:			!	!0	!0.0%	
WITHIN 7 DAYS:!   !0	!0.0%	!	!WITHIN 7 DAYS:			!	!0	!0.0%	
WITHIN 14 DAYS:!   !0	!0.0%	!	!WITHIN 14 DAYS:		!	!0	!0.0%	
;
RUN;
