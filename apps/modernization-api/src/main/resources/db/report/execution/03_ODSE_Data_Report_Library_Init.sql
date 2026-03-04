/*
Populate the new Report_Library table with data from the Report table in combination with descriptions gathered
from existing library documentation.
*/

USE [NBS_ODSE]

IF NOT EXISTS (SELECT * FROM [dbo].[Report_Library])
BEGIN

    IF OBJECT_ID('tempdb..#TempSasPrograms') IS NOT NULL DROP TABLE #TempSasPrograms;

    CREATE TABLE #TempSasPrograms(
        library_name varchar(50) NOT NULL,
        desc_txt varchar(300) NOT NULL,
    );

    INSERT INTO #TempSasPrograms
    VALUES
        ('CA01_DIAGNOSIS.SAS', 'CA01: Chalk Talk Report: Case. This report includes information on the patients in the same Lot (Epi-linked group) for a specific disease (e.g., named, named by, interview date, exposure, treatments, signs/symptoms).'),
        ('CA02_LOT.SAS', 'CA02: Chalk Talk Report: Lot. This report includes information on the patients in the same Lot (Epi-linked group) for a specific disease (e.g., Patient summary, Case information, interview date, who was named, exposure with contact, contact’s dispositions, and patient’s signs/symptoms).'),
        ('CA03.SAS', 'CA03: Chalk Talk Report: Marginals. This report includes information on the patients in the same Lot (Epi-linked group) for a specific disease (e.g., Patient summary and case information and any marginals named).'),
        ('CA04.SAS', 'CA04: Disease by Hangout Report: Detailed. Gives a listing of social venues where infected patients claimed to meet sex partners or engage in sexual activities.'),
        ('CA05.SAS', 'CA05: Disease by Hangout Report: Summary. Gives a listing of social venues where infected patients claimed to meet sex partners or engage in sexual activities.  The listing also displays the number of cases by diagnosis'  ),
        ('Case_Verification_Report.SAS', 'TB Case Verification Report - 2020'),
        ('RVCTNBSCUSTOM.SAS', 'Default library recommended for tabular. Formats the work query results and checks for result sizes and returns html if run, csv if exportNBSSR00001.SAS 2 Year Disease Count(year to date) with Percentage Change, by Geographic Area'),
        ('NBSSR00002.SAS', 'SR2: Counts of Reportable Diseases by County for Selected Time frame. Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by County irrespective of Case Status.'),
        ('NBSSR00003.SAS', 'Counts of Cases of Selected Diseases by Region and State by Month and Quarter'),
        ('NBSSR00004.SAS', 'Disease by month and YTD cumulative Totals'),
        ('NBSSR00005.SAS', 'SR5: Cases of Reportable Diseases by State. Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by state irrespective of Case Status over various time periods'),
        ('NBSSR00006.SAS', 'Cases of Selected Diseases by County'),
        ('NBSSR00007.SAS', 'SR7: Bar Graph of Cases of Selected Diseases vs. 5-Year Median for Selected Time Period. Report demonstrates, using a horizontal bar graph, Investigation(s) [both Individual and Summary] by year-to-date, and 5-year median irrespective of Case Status'),
        ('NBSSR00008.SAS', 'SR8: State Map Report of Disease Cases Over Selected Time Period. Report demonstrates, using a choropleth map, the total number of Investigation(s) [both Individual and Summary] by County irrespective of Case Status.'),
        ('NBSSR00009.SAS', 'SR9: Bar Graph of Selected Disease by Month.  Report demonstrates, using a vertical bar graph, the total number of monthly Investigation(s) [both Individual and Summary] for a given disease, by State, irrespective of Case Status.'),
        ('NBSSR00010.SAS', 'SR10: Multi-Year Line Graph of Disease Cases. Report demonstrates, using a multi-year line graph, the total number of yearly Investigation(s) [both Individual and Summary] for a given disease, by State, irrespective of Case Status.'),
        ('NBSSR00011.SAS', 'SR11: Cases of Selected Diseases By Year Over Time. Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by calculated MMWR Year irrespective of Case Status.'),
        ('NBSSR00012.SAS', 'SR12: Cases of Selected Disease By County By Year. Report demonstrates, in table form, the total number of Investigation(s) [both Individual and Summary] by Year (calculated based on Event Date year) and by County.'),
        ('NBSSR00015.SAS', 'Bar chart of 5-Year Case Count, By Month, for Selected Diseases'),
        ('NBSSR00016.SAS', 'Number of Cases and Deaths by Disease, Year Count'),
        ('NBSSR00017.SAS', 'Number of Deaths by disease during years. Bar chart of deaths by disease by month and year.'),
        ('NBSSR00018.SAS', 'TB Case Verification Report. Computes statistics about TB cases.'),
        ('NBSSR00019.SAS', 'TB Record Count - Summary Report by Report Date. Count of cases by "countability" and month.'),
        ('NBSSR00020.SAS', 'TB Record Count - Summary Report by Count Date. Count of cases by "countability" and month.'),
        ('NBSSR00021.SAS', 'QA01 Interview Record Listing (Assigned Date)'),
        ('NBSSR00022.SAS', 'QA01 Interview Record Listing (OI Date)'),
        ('NBSSR00023.SAS', 'QA01 Interview Record Listing (Closed Date)'),
        ('NBSSR00024.SAS', 'INTERVIEW RECORD LISTING - PREGNANT/RECENT BIRTH'),
        ('NBSSR00025.SAS', 'INTERVIEW RECORD LISTING - PREGNANT/RECENT BIRTH'),
        ('NBSSR00026.SAS', 'Cases Missing Lab and/or Treatment (STD)'),
        ('NBSSR00027.SAS', 'Patients with Duplicate Cases'),
        ('NBSSR00028.SAS', 'Worker Interview Activity Report'),
        ('NBSSR00029.SAS', 'Worker Interview Activity Report'),
        ('NBSSR00031.SAS', 'Filed Investigation Outcomes - STD'),
        ('NBSSR00033.SAS', 'Program Indicator Report'),
        ('NBSSR00034.SAS', 'Number of Records Entered by User ID'),
        ('NBSSR00035.SAS', 'Case Management Report'),
        ('NBSSR00036.SAS', 'LS02 Syphilis Reactor Statistics'),
        ('NBSSR00037.SAS', 'QA06 Patients with Multiple Cases'),
        ('NBSSR00038.SAS', 'SYPHILIS REACTOR GRID EVALUATION'),
        ('PA01_HIV.SAS', 'PA01: Case Management Report - HIV'),
        ('PA01_STD.SAS', 'PA01: Case Management Report - STD'),
        ('PA02_HIV.SAS', 'PA02: Field Investigation Outcomes - HIV'),
        ('PA02_STD.SAS', 'PA02: Field Investigation Outcomes - STD'),
        ('PA03.SAS', 'PA03: Internet Partner Services Report. This report shows the number of cases, sexual contacts, social contacts, and associates with and without internet partner services follow-up initiated for a given time period and disease.'),
        ('PA04_HIV.SAS', 'PA04: Program Indicator Report. The Program Indicator Report is intended to illustrate the success of an STD Program as a whole rather than by individual worker by outlining program indicator elements.'),
        ('PA04_STD.SAS', 'PA04: Program Indicator Report. The Program Indicator Report is intended to illustrate the success of an STD Program as a whole rather than by individual worker by outlining program indicator elements.'),
        ('PA05.SAS', 'PA 5: Worker Interview Activity Report. The Worker Interview Activity Report details volume of interviews, as well as success and speed of interviewing activity.'),
        ('POTNTL_DUP_INV_SUM.SAS', 'Potential Duplicate Investigations. '),
        ('QA01.SAS', 'QA01: Interview Record Listing Report. This report generates a list, by name, of individuals interviewed during the specified time period.'),
        ('QA03.SAS', 'QA03: Case Listing. This report generates a list, by name, of individuals with cases within a designated time period.'),
        ('QA04.SAS', 'QA04: Cases Missing Lab or Treatment. This report generates a list, by name, of individuals with cases that are not linked to a positive lab test record (for this reported case) or to a treatment record.'),
        ('QA05.SAS', 'QA05: Number of Records Entered by User ID. This report produces a table showing data entry user quick code by the number and type of records the user entered.'),
        ('QA06.SAS', 'QA06: Patients with Multiple Cases. This report generates a list, by name, of individuals who have multiple occasions of cases within a time period.'),
        ('QA07_30.SAS', 'QA07: Duplicate Cases - 30 Days'),
        ('QA07_60.SAS', 'QA07: Duplicate Cases - 60 Days'),
        ('QA07_90.SAS', 'QA07: Duplicate Cases - 90 Days'),
        ('QA07.SAS', 'QA07: Duplicate Cases'),
        ('QA10.SAS', 'QA10: Interviews - Pregnant/Recent Birth. This report generates a list, by name, of females interviewed that have a current or past year pregnancy status of Yes during the specified time period.'),
        ('TB_SUMMARY_COUNT_DATE.SAS', 'TB Record Count - Summary Report by Count Date - 2020 RVCT'),
        ('TB_SUMMARY_COUNT.SAS', 'TB Record Count - Summary Report by Report Date - 2020 RVCT')
    ;

    -- Create a row for all known libraries from the temp table and all libraries
    -- actually used in the Report table today
    INSERT INTO [dbo].[Report_Library] (
        library_name,
        desc_txt,
        runner,
        is_builtin_ind,
        add_time,
        add_user_id,
        last_chg_time,
        last_chg_user_id
    ) SELECT
        COALESCE(l.location, tmp.library_name),
        COALESCE(tmp.desc_txt, l.location),
        'sas',
        IIF(tmp.desc_txt IS NOT NULL, 'Y', 'N'),
        CURRENT_TIMESTAMP,
        99999999,
        CURRENT_TIMESTAMP,
        99999999
    FROM (SELECT distinct r.location from [dbo].[Report] r) l
    FULL JOIN #TempSasPrograms tmp on UPPER(l.location) = UPPER(tmp.library_name);

    -- Insert foreign keys from Report to Report_library based on inserted data
    UPDATE [dbo].[Report]
    SET 
        library_uid = rl.library_uid
    FROM [dbo].[Report_Library] rl
    WHERE UPPER(rl.library_name) = UPPER(location);

    IF OBJECT_ID('tempdb..#TempSasPrograms') IS NOT NULL DROP TABLE #TempSasPrograms;

END
