# PA01 Conversion Notes

## Case Management - STD

Applies to both variants:

- Interview Assign Date
- Closed Date

15 + 9 + 34 + 10 = 68 total columns

### Columns

#### Case Assignments and Outcomes (15 Columns)

- Cases Assigned, Int
- Cases Closed, Int With %
- Cases IX'D, Int With %
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %
- Cases Reinterviewed, Int With %
- HIV Previous Positive, Int With %
- HIV Tested, Int With %
- HIV New Positive, Int With %
- HIV Posttest Counsel, Int With %
- Disease Intervention Index, Decimal
- Treatment Index, Decimal
- Cases w/ Source Identified, Int With %

#### Partners and Clusters Initiated (9 Columns)

- Total Period Partners, Int
- Total Partners Initiated, Int
  - From OI, Int
  - From RI, Int
- Contact Index, Decimal
- Cases w/ No Partners, Int With %
- Total Clusters Initiated, Int
  - Cluster Index, Decimal
  - Cases w/ No Clusters, Int With %

#### Dispositions - New Partners and Clusters (34 Columns)

- New partners Examined, Int With %
  - Preventative RX, Int With %
  - Refused Prev. RX, Int With %
  - Infected, RX'D, Int With %
  - Infected, No RX, Int With %
  - Not Infected, Int With %
- New partners No Exam, Int With %
  - Insufficient Info, Int With %
  - Unable To Locate, Int With %
  - Refused Exam, Int With %
  - OOJ, Int With %
  - Other, Int With %
  - Domestic Violence Risk, Int With %
  - Patient Deceased, Int With %
  - Previous Prev RX, Int With %
- Previous RX, Int With %
- Open, Int With %
- New Clusters Examined, Int With %
  - Preventative RX, Int With %
  - Refused Prev. RX, Int With %
  - Infected, RX'D, Int With %
  - Infected, No RX, Int With %
  - Not Infected, Int With %
- New Clusters No Exam, Int With %
  - Insufficient Info, Int With %
  - Unable To Locate, Int With %
  - Refused Exam, Int With %
  - OOJ, Int With %
  - Other, Int With %
  - Domestic Violence Risk, Int With %
  - Patient Deceased, Int With %
  - Previous Prev RX, Int With %
- Previous RX, Int With %
- Open, Int With %

#### Speed of Exam - Partners and Clusters (10 Columns)

- New Partners Examined, Int
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %
- New Clusters Examined, Int
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %

## Case Management - HIV

### Columns

Applies to both variants:

- Interview Assign Date
- Closed Date

14 + 9 + 34 + 10 = 67 total columns

#### Case Assignments and Outcomes (14 Columns)

- Cases Assigned, Int
- Cases Closed, Int With %
- Cases IX'D, Int With %
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %
- Cases Reinterviewed, Int With %
- HIV Previous Positive, Int With %
- HIV Tested, Int With %
- HIV New Positive, Int With %
- HIV Posttest Counsel, Int With %
- Partner Notification Index, Decimal
- Testing Index, Decimal

#### Partners and Clusters Initiated (9 Columns)

- Total Period Partners, Int
- Total Partners Initiated, Int
  - From OI, Int
  - From RI, Int
- Contact Index, Decimal
- Cases w/ No Partners, Int With %
- Total Clusters Initiated, Int
  - Cluster Index, Decimal
  - Cases w/ No Clusters, Int With %

#### Dispositions - New Partners and Clusters (34 Columns)

- New Partners Notified, Int With %
  - Prev. Neg, New Pos, Int With %
  - Prev. Neg, Still Neg, Int With %
  - Prev. Neg, No Test, Int With %
  - No Prev. Test, New Pos, Int With %
  - No Prev. Test, New Neg, Int With %
  - No Prev. Test, No Test, Int With %
- New Partners Not Notified, Int
  - Insufficient Info, Int With %
  - Unable To Locate, Int With %
  - Refused Exam, Int With %
  - OOJ, Int With %
  - Other, Int With %
  - Domestic Violence Risk, Int With %
  - Patient Deceased, Int With %
- Previous Pos, Int With %
- Open, Int With %
- New Clusters Notified, Int With %
  - Prev. Neg, New Pos, Int With %
  - Prev. Neg, Still Neg, Int With %
  - Prev. Neg, No Test, Int With %
  - No Prev. Test, New Pos, Int With %
  - No Prev. Test, New Neg, Int With %
  - No Prev. Test, No Test, Int With %
- New Clusters Not Notified, Int With %
  - Insufficient Info, Int With %
  - Unable To Locate, Int With %
  - Refused Exam, Int With %
  - OOJ, Int With %
  - Other, Int With %
  - Domestic Violence Risk, Int With %
  - Patient Deceased, Int With %
- Previous Pos, Int With %
- Open, Int With %

#### Speed of Notification - Partners and Clusters (10 Columns)

- New Partners Notified, Int
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %
- New Clusters Notified, Int
  - Within 3 Days, Int With %
  - Within 5 Days, Int With %
  - Within 7 Days, Int With %
  - Within 14 Days, Int With %

## SQL Scratch

```
WITH base AS 
(
	SELECT *
	FROM RDB.dbo.STD_HIV_DATAMART
),
filtered_base AS
(
  -- STD_HIV_DATAMART1 in PA01_HIV.sas
  SELECT b.*
  FROM base b
    INNER JOIN RDB.dbo.INVESTIGATION i
            ON i.INVESTIGATION_KEY = b.INVESTIGATION_KEY
           AND i.INV_CASE_STATUS IN ('Probable', 'Confirmed')                   
           AND b.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
),
cases AS
(
    -- pa1_new in PA01_HIV.sas
	SELECT fb.INV_LOCAL_ID,
	       di.IX_TYPE,
	       i.INV_CASE_STATUS,
	       i.RECORD_STATUS_CD,
	       fb.CC_CLOSED_DT,
	       fb.ADI_900_STATUS_CD,
	       fb.HIV_POST_TEST_900_COUNSELING,
	       fb.HIV_900_RESULT,
	       fb.ADI_900_STATUS,
		   fb.HIV_900_TEST_IND,
		   fb.SOURCE_SPREAD,
		   fb.FL_FUP_INIT_ASSGN_DT,
		   i.CURR_PROCESS_STATE,
		   fb.CA_PATIENT_INTV_STATUS,
		   fb.INVESTIGATOR_INTERVIEW_KEY,
		   fb.INVESTIGATOR_INTERVIEW_QC,
		   --/* Should it be CA_INIT_INTVWR_ASSGN_DT or CA_INTERVIEWER_ASSIGN_DT? */
		   DATEDIFF(day, di.IX_DATE, fb.CA_INTERVIEWER_ASSIGN_DT) AS Days,
		   dp.PROVIDER_QUICK_CODE
	FROM filtered_base fb
	  LEFT OUTER JOIN RDB.dbo.F_INTERVIEW_CASE fic ON fic.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
	  LEFT OUTER JOIN RDB.dbo.D_INTERVIEW di
	               ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
	              AND di.RECORD_STATUS_CD <> 'LOG_DEL'
	  LEFT OUTER JOIN RDB.dbo.D_PROVIDER dp ON dp.PROVIDER_KEY = fb.INVESTIGATOR_INTERVIEW_KEY
	  LEFT OUTER JOIN RDB.dbo.INVESTIGATION i ON i.INVESTIGATION_KEY = fb.INVESTIGATION_KEY
)
SELECT *
FROM cases;
```
