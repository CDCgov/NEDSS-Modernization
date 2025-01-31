
data template3 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NEW PARTNERS EXAMINED:		!   !0	!	!	!	NEW CLUSTERS EXAMINED:			!	!0	!	
	PREVENTATIVE RX:		!   !0	!	!	!		PREVENTATIVE RX:			!	!0	!	
	REFUSED PREV. RX:		!   !0	!	!	!		REFUSED PREV. RX:			!	!0	!	
	INFECTED, RX'D:			!   !0	!	!	!		INFECTED, RX'D:				!	!0	!	
	INFECTED, NO RX:		!   !0	!	!	!		INFECTED, NO RX:			!	!0	!	
	NOT INFECTED:			!   !0	!	!	!		NOT INFECTED:				!	!0	!	
NEW PARTNERS NO EXAM:		!   !0	!	!	!	NEW CLUSTERS NO EXAM:			!	!0	!	
	INSUFFICIENT INFO:		!   !0	!	!	!		INSUFFICIENT INFO:			!	!0	!	
	UNABLE TO LOCATE:		!   !0	!	!	!		UNABLE TO LOCATE:			!	!0	!	
	REFUSED EXAM:			!   !0	!	!	!		REFUSED EXAM:				!	!0	!	
	OOJ:					!   !0	!	!	!		OOJ:						!	!0	!	
	OTHER:					!   !0	!	!	!		OTHER:						!	!0	!	
	DOMESTIC VIOLENCE RISK:	!   !0	!	!	!		DOMESTIC VIOLENCE RISK:		!	!	!	
	PATIENT DECEASED:		!   !0	!	!	!		PATIENT DECEASED:			!	!	!	
	PREVIOUS PREV RX:		!   !0	!	!	!		PREVIOUS PREV RX:			!	!0	!	
PREVIOUS RX:				!   !0	!	!	!	PREVIOUS RX:					!	!0	!	
OPEN:						!   !0	!	!	!	OPEN:							!	!0	!	
RUN;
