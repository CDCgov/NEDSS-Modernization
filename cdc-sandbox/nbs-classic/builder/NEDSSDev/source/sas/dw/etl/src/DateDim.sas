/*----------------------------------------------------

	DateDim.sas

	This program generates the Date dimension table
	in RDB.  Run the program as part of the setup
	or after all the fact table records have been deleted.

------------------------------------------------------*/
Data rdbdata.Datetable (DROP=SASDATE);
 Format DATE_KEY 8.
		DATE_MM_DD_YYYY datetime20.
        DAY_OF_WEEK $9.
		DAY_NBR_IN_CLNDR_MON 8.
		DAY_NBR_IN_CLNDR_YR 8.
		WK_NBR_IN_CLNDR_MON 8.
		WK_NBR_IN_CLNDR_YR 8.
        CLNDR_MON_NAME $12.
        CLNDR_MON_IN_YR 8.
        /* SEASON $6. */
		CLNDR_QRTR 8. 
        CLNDR_YR 8.
;
  DATE_KEY = 1;
  output;        /* first record is null */

  do CLNDR_YR = 1990 to 2030;   /* loop through years */
    do CLNDR_MON_IN_YR = 1 to 12;   /* loop through months for each year */

      SASDATE = MDY(CLNDR_MON_IN_YR,1,CLNDR_YR);   /* internal SAS date for the 1st of the month (number of days since 1/1/60) */

      do while (Month(SASDATE)=CLNDR_MON_IN_YR);   /* loop through days until month changes */

	    DATE_KEY = DATE_KEY + 1;
	    DATE_MM_DD_YYYY = SASDATE*24*60*60;       /* number of seconds since 1/1/60; stored as datetime format */
	    CLNDR_QRTR = Qtr(SASDATE);
	    CLNDR_MON_NAME = Left(Put(SASDATE, monname.));
		DAY_OF_WEEK = Left(Put(SASDATE, downame.));      /* returns day of week.  Use weekday format for number */
        DAY_NBR_IN_CLNDR_MON = Day(SASDATE);

	    /* determine day (week) number as difference between 1st of year (month) and current date */
        DAY_NBR_IN_CLNDR_YR = intck('day',MDY(1,1,CLNDR_YR),SASDATE)+1;   /* # days since Jan 1 */
        WK_NBR_IN_CLNDR_YR = intck('week',MDY(1,1,CLNDR_YR),SASDATE+1)+1;  /* # weeks since Jan 1 (Saturay start) */
        WK_NBR_IN_CLNDR_MON = intck('week',MDY(CLNDR_MON_IN_YR,1,CLNDR_YR),SASDATE+1)+1;  /* # weeks since 1st of month (Saturday start) */
*        WK_NBR_IN_CLNDR_YR = intck('week',MDY(1,1,CLNDR_YR),SASDATE)+1;  /* # weeks since Jan 1 (Sunday start) */
*        WK_NBR_IN_CLNDR_MON = intck('week',MDY(CLNDR_MON_IN_YR,1,CLNDR_YR),SASDATE)+1;  /* # weeks since 1st of month (Sunday start) */
*        WK_NBR_IN_CLNDR_YR = int(intck('day',MDY(1,1,CLNDR_YR),SASDATE)/7)+1;  /* # days since Jan 1 / 7 */
*        WK_NBR_IN_CLNDR_MON = int(intck('day',MDY(CLNDR_MON_IN_YR,1,CLNDR_YR),SASDATE)/7)+1;  /* # days since 1st of month / 7 */

	    output;   /* write record to data set */

        SASDATE=intnx('day',SASDATE,1);  /* increment date by one day */

  	  end;  /* day loop */ 
    end;  /* month loop */
  end;  /* year loop */
Run;
%dbload (RDB_DATE, rdbdata.Datetable);
*%dbload (RDB_DATE, rdbdata.Datetable);
