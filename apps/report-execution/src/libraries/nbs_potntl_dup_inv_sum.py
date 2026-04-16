from src.db_transaction import Transaction
from src.models import ReportResult, TimeRange


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    time_range: TimeRange | None = None,
    days_value: int | None = None,
    **kwargs,
):
    """Potential Duplicate Investigations

    Conversion notes:
    * SAS defaults to 3650 Days in the subheader if no days value is provided.
    Here, we calculate what the actual date range is in the data and display
    that in the subheader.
    
    """
    
    # Build the WHERE clause for date filtering if days_value is provided
    date_filter = ""
    if days_value:
        date_filter = f"""
          AND (
            (d.days_since_prev IS NOT NULL AND d.days_since_prev <= {days_value})
            OR (d.days_until_next IS NOT NULL AND d.days_until_next <= {days_value})
          )
        """
    
    full_query = f"""
    WITH subset AS ({subset_query})
    -- Filter to only needed columns and clean data
    , clean_data AS (
        SELECT 
            PATIENT_LOCAL_ID,
            PATIENT_FIRST_NAME,
            PATIENT_LAST_NAME,
            PATIENT_DOB,
            INVESTIGATION_LOCAL_ID,
            DISEASE,
            CASE_STATUS,
            EVENT_DATE,
            EVENT_DATE_TYPE,
            MMWR_YEAR,
            NOTIFICATION_STATUS,
            DISEASE_CD
        FROM subset
        WHERE EVENT_DATE IS NOT NULL
            AND PATIENT_LOCAL_ID IS NOT NULL
            AND DISEASE_CD IS NOT NULL
    )
    -- Calculate days between consecutive events
    , datediff_calc AS (
        SELECT 
            *,
            DATEDIFF(day, 
                LAG(EVENT_DATE) OVER (PARTITION BY PATIENT_LOCAL_ID, DISEASE_CD ORDER BY EVENT_DATE),
                EVENT_DATE
            ) AS days_since_prev,
            DATEDIFF(day, 
                EVENT_DATE,
                LEAD(EVENT_DATE) OVER (PARTITION BY PATIENT_LOCAL_ID, DISEASE_CD ORDER BY EVENT_DATE)
            ) AS days_until_next
        FROM clean_data
    )
    -- Count events per patient/disease
    , event_counts AS (
        SELECT 
            PATIENT_LOCAL_ID,
            DISEASE_CD,
            COUNT(*) AS event_count
        FROM clean_data
        GROUP BY PATIENT_LOCAL_ID, DISEASE_CD
    )
    -- Final selection - 
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
    FROM datediff_calc d
    JOIN event_counts c 
        ON d.PATIENT_LOCAL_ID = c.PATIENT_LOCAL_ID 
        AND d.DISEASE_CD = c.DISEASE_CD
    WHERE c.event_count > 1
    {date_filter}
    ORDER BY 
    d.PATIENT_LOCAL_ID,
    d.DISEASE_CD,
    d.EVENT_DATE
    """
    
    content = trx.query(full_query)
    
    header = 'Potential Duplicate Investigations'
    subheader = None
    if days_value is not None:
        subheader = f'Duplicate Investigations Time Frame: {days_value} Days'
    else:
        # Calculate date range from the data
        date_range_query = f"""
        WITH subset AS ({subset_query})
        SELECT 
            MIN(EVENT_DATE) AS min_date,
            MAX(EVENT_DATE) AS max_date
        FROM subset
        WHERE EVENT_DATE IS NOT NULL
        """
        
        date_range_result = trx.query(date_range_query)
        
        if date_range_result.data and date_range_result.data[0][0] is not None:
            min_date = date_range_result.data[0][0]
            max_date = date_range_result.data[0][1]
            if min_date and max_date:
                days_diff = (max_date - min_date).days
                subheader = f'Duplicate Investigations Time Frame: {days_diff} Days'

    return ReportResult(
        content_type='table', content=content, header=header, subheader=subheader
    )