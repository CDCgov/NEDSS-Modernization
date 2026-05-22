from src.db_transaction import Transaction
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    days_value: None | int,
    column_map: dict[str, str],
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
        SELECT *
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
                [EVENT_DATE],
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
    SELECT {', '.join(key + ' AS ' + '[' + item + ']' for key, item in column_map.items())}
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
