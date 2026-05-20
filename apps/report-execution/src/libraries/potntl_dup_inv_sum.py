from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    days_value: None | int,
    **kwargs,
):
    """Potential Duplicate Investigations.

    Identifies potential duplicate investigations for the same patient,
    with the same disease, within a user-specified number of days.
    """
    # Only use default if days_value is None (not provided)
    # If days_value is 0, treat it as 0 (not default)
    # days_value = kwargs.get('days_value')
    if days_value is None:
        days_value = 3650

    full_query = f"""
    WITH subset AS ({subset_query})
    -- Capture SQL Server's physical row order
    , source_order AS (
        SELECT 
            *,
            ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS sas_row_num
        FROM subset
    )
    , clean_data AS (
        SELECT 
            *,
            sas_row_num
        FROM source_order
        WHERE EVENT_DATE IS NOT NULL
            AND PATIENT_LOCAL_ID IS NOT NULL
            AND DISEASE_CD IS NOT NULL
    )
    -- Calculate days since previous and until next event
    , datediff_calc AS (
        SELECT 
            *,
            DATEDIFF(day, 
                LAG(EVENT_DATE) OVER (
                    PARTITION BY
                    PATIENT_LOCAL_ID,
                    DISEASE_CD 
                    ORDER BY EVENT_DATE, sas_row_num
                ),
                EVENT_DATE
            ) AS days_since_prev,
            DATEDIFF(day, 
                EVENT_DATE,
                LEAD(EVENT_DATE) OVER (
                    PARTITION BY PATIENT_LOCAL_ID,
                    DISEASE_CD 
                    ORDER BY EVENT_DATE, sas_row_num
                )
            ) AS days_until_next
        FROM clean_data
    )
    -- Count events for each patient and disease to identify potential duplicates
    , event_counts AS (
        SELECT 
            PATIENT_LOCAL_ID,
            DISEASE_CD,
            COUNT(*) AS event_count
        FROM clean_data
        GROUP BY PATIENT_LOCAL_ID, DISEASE_CD
    )
    -- Final selection of potential duplicates based on days thresholds
    SELECT 
        d.PATIENT_LOCAL_ID AS [Patient Local ID],
        d.PATIENT_FIRST_NAME AS [Patient First Name],
        d.PATIENT_LAST_NAME AS [Patient Last Name],
        d.PATIENT_DOB AS DOB,
        d.INVESTIGATION_LOCAL_ID AS [Investigation Local ID],
        d.DISEASE AS Disease,
        d.CASE_STATUS AS [Case Status],
        d.EVENT_DATE AS [Event Date],
        d.EVENT_DATE_TYPE AS [Event Date Type],
        d.MMWR_YEAR AS [MMWR Year],
        d.NOTIFICATION_STATUS AS [Notification Record Status],
        d.DISEASE_CD AS [Disease Code]
        d.AGE_REPORTED as [Age Reported],
        d.AGE_REPORTED_UNIT as [Age Reported Unit],
        d.CONFIRMATION_DATE as [Confirmation Date],
        -- figure out Date of Report field
        d.DIAGNOSIS_DATE as [Diagnosis Date],
        d.EARLIEST_RPT_T_CNTY_DATE as [Earliest Date Reported to County],
        d.EARLIEST_RPT_STATE_DATE as [Earliest Date Reported to State],
        d.EARLIEST_SPECIMEN_COLLECTION_DATE as [Earliest Specimen Collection Date],
        d.PATIENT_ETHNICITY as [Ethnicity],
        d.FIRST_POSITIVE_CULTURE_DT as [First Positive Culture Date],
        d.HSPTL_ADMISSION_DATE as [Hospital Admission Date],
        d.ILLNESS_ONSET_DATE as [Illness Onset Date],
        d.INVESTIGATION_CREATE_DATE as [Investigation Create Date],
        d.INVESTIGATION_CREATED_BY as [Investigation Created By User],
        d.CURR_PROCESS_STATE as [Investigation Current Process Stage],
        d.INVESTIGATION_KEY as [Investigation Key],
        d.INVESTIGATION_LAST_UPDTD_BY as [Investigation Last Updated By User],
        d.INVESTIGATION_LAST_UPDTD_DATE as [Investigation Last Updated Date],
        d.INV_START_DT as [Investigation Start Date],
        -- figure out Investigation Status field,
        -- figure out Jurisdiction Code field,
        d.LABORATORY_INFORMATION as [Laboratory Information],
        d.MMWR_WEEK as [MMWR Week],
        d.NOTIFICATION_CREATE_DATE as [Notification Create Date],
        d.NOTIFICATION_LAST_UPDATED_USER as [Notification Last Updated By User],
        d.NOTIFICATION_LAST_UPDATED_DATE as [Notification Last Updated Date],
        d.NOTIFICATION_LOCAL_ID as [Notification Local ID],
        d.NOTIFICATION_SUBMITTER as [Notification Submitter],
        d.PATIENT_STREET_ADDRESS_1 as [Patient Address 1],
        d.PATIENT_STREET_ADDRESS_2 as [Patient Address 2],
        d.PATIENT_CITY as [Patient City],
        d.PATIENT_STATE as [Patient State],
        d.PATIENT_ZIP as [Patient Zip],
        d.PHYSICIAN_FIRST_NAME as [Physician First Name],
        d.PHYSICIAN_LAST_NAME as [Physician Last Name],
        d.PROGRAM_AREA as [Program Area],
        d.RACE_CALCULATED as [Race - Calculated],
        d.RACE_CALC_DETAILS as [Race - Calculated Details],
        d.PATIENT_CURRENT_SEX as [Sex]
    FROM datediff_calc d
    JOIN event_counts c 
        ON d.PATIENT_LOCAL_ID = c.PATIENT_LOCAL_ID 
        AND d.DISEASE_CD = c.DISEASE_CD
    WHERE c.event_count > 1
    AND (
        (d.days_since_prev IS NOT NULL AND d.days_since_prev <= {days_value})
        OR (d.days_until_next IS NOT NULL AND d.days_until_next <= {days_value})
    )
    ORDER BY 
        d.PATIENT_LOCAL_ID COLLATE Latin1_General_BIN,
        d.DISEASE_CD COLLATE Latin1_General_BIN,
        d.EVENT_DATE,
        d.sas_row_num
    """

    content = trx.query(full_query)

    header = 'Potential Duplicate Investigations'
    subheader = f'Duplicate Investigations Time Frame: {days_value} Days'

    return ReportResult(
        content_type='table', content=content, header=header, subheader=subheader
    )
