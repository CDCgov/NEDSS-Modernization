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
    
    Identifies potential duplicate investigations for the same patient with the
    same disease within a user-specified number of days. Users can multi-select
    diseases to filter the results.
    
    Args:
        days_value: Number of days to consider for duplicate detection (e.g., 30, 365, 3650)
                   If None, returns all data without date-based filtering.
    """
    # Build the WHERE clause for date filtering if days_value is provided
    date_filter = ""
    if days_value is not None:
        date_filter = f'''
          AND (
            (d.days_since_prev IS NOT NULL AND d.days_since_prev <= {days_value})
            OR (d.days_until_next IS NOT NULL AND d.days_until_next <= {days_value})
          )
        '''

    content = trx.query(
        f'WITH subset as ({subset_query})\n'
        # Filter to only needed columns and clean data
        ', clean_data as (\n'
        'SELECT \n'
        '  PATIENT_LOCAL_ID,\n'
        '  DISEASE_CD,\n'
        '  EVENT_DATE,\n'
        '  INVESTIGATION_LOCAL_ID,\n'
        '  CASE_STATUS\n'
        'FROM subset\n'
        'WHERE EVENT_DATE IS NOT NULL\n'
        '  AND PATIENT_LOCAL_ID IS NOT NULL\n'
        '  AND DISEASE_CD IS NOT NULL\n'
        ')\n'
        # Calculate days between consecutive events
        ', datediff_calc as (\n'
        'SELECT \n'
        '  *,\n'
        '  DATEDIFF(day, \n'
        '    LAG(EVENT_DATE) OVER (PARTITION BY PATIENT_LOCAL_ID, DISEASE_CD ORDER BY EVENT_DATE),\n'
        '    EVENT_DATE\n'
        '  ) as days_since_prev,\n'
        '  DATEDIFF(day, \n'
        '    EVENT_DATE,\n'
        '    LEAD(EVENT_DATE) OVER (PARTITION BY PATIENT_LOCAL_ID, DISEASE_CD ORDER BY EVENT_DATE)\n'
        '  ) as days_until_next\n'
        'FROM clean_data\n'
        ')\n'
        # Count events per patient/disease
        ', event_counts as (\n'
        'SELECT \n'
        '  PATIENT_LOCAL_ID,\n'
        '  DISEASE_CD,\n'
        '  COUNT(*) as event_count\n'
        'FROM clean_data\n'
        'GROUP BY PATIENT_LOCAL_ID, DISEASE_CD\n'
        ')\n'
        # Final selection - only potential duplicates
        'SELECT \n'
        '  d.PATIENT_LOCAL_ID,\n'
        '  d.DISEASE_CD,\n'
        '  d.EVENT_DATE,\n'
        '  d.INVESTIGATION_LOCAL_ID,\n'
        '  d.CASE_STATUS,\n'
        '  d.days_since_prev,\n'
        '  d.days_until_next,\n'
        '  c.event_count\n'
        'FROM datediff_calc d\n'
        'JOIN event_counts c \n'
        '  ON d.PATIENT_LOCAL_ID = c.PATIENT_LOCAL_ID \n'
        '  AND d.DISEASE_CD = c.DISEASE_CD\n'
        'WHERE c.event_count > 1\n'  # Only patients with multiple events
        f'{date_filter}'
        'ORDER BY \n'
        '  d.PATIENT_LOCAL_ID,\n'
        '  d.DISEASE_CD,\n'
        '  d.EVENT_DATE'
    )

    header = 'Potential Duplicate Investigations'
    subheader = None
    if days_value is not None:
        subheader = f'Duplicate Investigations Time Frame: {days_value} Days'

    return ReportResult(
        content_type='table', content=content, header=header, subheader=subheader
    )