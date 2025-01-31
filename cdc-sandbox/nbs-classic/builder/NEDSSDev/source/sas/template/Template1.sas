options linesize=max;
data template1 (drop = blank blank2 );
length descrip $35 blank $1 value1 $10 percent1 $10 blank $1 descrip2 $35 blank2 $1 value2 $10 percent2 $10;
infile datalines truncover dsd dlm='!' ;
input descrip $ blank $ value1 $ percent1 $ blank $ descrip2 $ blank2 $ value2 $ percent2 $;
datalines ;
CASES ASSIGNED:			! 0	!		!	!	!		HIV PREVIOUS POSITIVE:		!0	!	!	
CASES CLOSED:			!  0 !		!	!	!		HIV TESTED:					!0	!	!	
CASES IX'D:				! 0  !		!	!	!		HIV NEW POSITIVE:			!0	!	!	
      WITHIN 3 DAYS:	! 0  !		!	!	!		HIV POSTTEST COUNSEL:		!0	!	!	
      WITHIN 5 DAYS:	! 0  !		!	!	!									!   !	!   
      WITHIN 7 DAYS:	! 0  !		!	!	!									!   !	!   
      WITHIN 14 DAYS:	! 0  !		!	!	!		DISEASE INTERVENTION INDEX:	!0	!	!	
						!   !		!	!	!		TREATMENT INDEX:			!0	!	!	
CASES REINTERVIEWED:	! 0  !		!	!	!		CASES W/SOURCE IDENTIFIED:	!0	!	!	
run;
