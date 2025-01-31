/*Create template data for overall summary report*/
options linesize=max;
data templatePA05 (drop = blank blank2 );
length descrip $50 blank $15 value1 $8 percent1 $15 blank $15 descrip2 $50 blank2 $15 value2 $15 percent2 $15;
infile datalines truncover pad dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
NUM. CASES ASSIGNED:		! !0	!		!	!		NUM. OF OI'S:				!	!0	!		
	NUM. CASES OPEN:		! !0	!0.0%	!	!			OI'S THAT WERE NCI:		!	!0	!0.0%	
	NUM. CASES CLOSED:		! !0	!0.0%	!	!			PERIOD PARTNERS:		!	!0	!0		
	NUM. CASES PENDING:		! !0	!0.0%	!	!			PARTNERS INITIATED:		!	!0	!0		
							! !	 	!		!	!									!	!	!		
	NUM. CASES IX'D:		! !0	!		!	!		NUM. OF RI'S:				!	!0	!		
		CLINIC IX'S:		! !0	!0.0%	!	!			RI'S THAT WERE NCI:		!	!0	!0.0%	
		FIELD IX'S:			! !0	!0.0%	!	!			PARTNERS. INITIATED:	!	!0	!0		
							! !		!		!	!									!	!	!		
		IX'D W/IN 3 DAYS:	! !0	!0.0%	!	!		CLUSTERS INIT (OI & RI):	!	!0	!0		
		IX'D W/IN 5 DAYS:	! !0	!0.0%	!	!									!	!	!		
		IX'D W/IN 7 DAYS:	! !0	!0.0%	!	!									!	!	!		
		IX'D W/IN 14 DAYS:	! !0	!0.0%	!	!									!	!	!		
							! !		!		!	!									!	!	!		
	NUM. CASES NOT IX'D:	! !0	!		!	!									!	!	!		
		REFUSED:			! !0	!0.0%	!	!									!	!	!		
		NO LOCATE:			! !0	!0.0%	!	!									!	!	!		
		OTHER:				! !0	!0.0%	!	!									!	!	!		
run;
