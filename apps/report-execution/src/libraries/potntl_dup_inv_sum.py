from src.db_transaction import Transaction
from src.errors import MissingColumnError
from src.models import ReportResult

REQUIRED_COLS = [
    {'name': 'PATIENT_LOCAL_ID', 'sort': 'asc'},
    {'name': 'DISEASE_CD', 'sort': 'asc'},
    {'name': 'EVENT_DATE', 'sort': 'desc'},
]

REQUIRED_COL_NAMES = [col['name'] for col in REQUIRED_COLS]


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    days_value: None | int,
    column_map: list[list[str]],
    sort_by: str | None,
    **kwargs,
):
    """Potential Duplicate Investigations.

    Identifies potential duplicate investigations for the same patient,
    with the same disease, within a user-specified number of days.

    Conversion notes:
    * The way SAS sorts data depends on the session's encoding. This library
    will always sort alphabetically by Patient Local ID, Disease Code, and
    Event Date to ensure consistent results regardless of encoding.
    * Dates defer to default formatting
    """
    if not column_map:
        raise MissingColumnError(REQUIRED_COL_NAMES)
    # for easier lookups when order doesn't matter
    col_dict = {m[0]: m[1] for m in column_map}

    missing_columns = []
    for col in REQUIRED_COL_NAMES:
        if col not in col_dict:
            missing_columns.append(col)
    if len(missing_columns) > 0:
        raise MissingColumnError(missing_columns)

    # Only use default if days_value is None (not provided)
    # If days_value is 0, treat it as 0 (not default)
    # days_value = kwargs.get('days_value')
    if days_value is None:
        days_value = 3650

    select_clause = ', '.join([f'd.[{item[1]}]' for item in column_map])
    # can't sort by the same column twice - remove sort_by from the base sort
    base_order_by = ', '.join(
        [
            f'd.[{col_dict[col["name"]]}] {col["sort"]}'
            for col in REQUIRED_COLS
            if sort_by is None or f'[{col_dict[col["name"]]}]' not in sort_by
        ]
    )

    full_query = f"""
    WITH subset AS ({subset_query})
    , clean_data AS (
        SELECT *
        FROM subset
        WHERE [{col_dict['EVENT_DATE']}] IS NOT NULL
            AND [{col_dict['PATIENT_LOCAL_ID']}] IS NOT NULL
            AND [{col_dict['DISEASE_CD']}] IS NOT NULL
    )
    -- Calculate days since previous and until next event
    , datediff_calc AS (
        SELECT 
            *,
            DATEDIFF(day, 
                LAG([{col_dict['EVENT_DATE']}]) OVER (
                    PARTITION BY
                    [{col_dict['PATIENT_LOCAL_ID']}],
                    [{col_dict['DISEASE_CD']}] 
                    ORDER BY [{col_dict['EVENT_DATE']}]
                ),
                [{col_dict['EVENT_DATE']}]
            ) AS days_since_prev,
            DATEDIFF(day, 
                [{col_dict['EVENT_DATE']}],
                LEAD([{col_dict['EVENT_DATE']}]) OVER (
                    PARTITION BY [{col_dict['PATIENT_LOCAL_ID']}],
                    [{col_dict['DISEASE_CD']}]
                    ORDER BY [{col_dict['EVENT_DATE']}]
                )
            ) AS days_until_next
        FROM clean_data
    )
    -- Count events for each patient and disease to identify potential duplicates
    , event_counts AS (
        SELECT 
            [{col_dict['PATIENT_LOCAL_ID']}],
            [{col_dict['DISEASE_CD']}],
            COUNT(*) AS event_count
        FROM clean_data
        GROUP BY [{col_dict['PATIENT_LOCAL_ID']}], [{col_dict['DISEASE_CD']}]
    )
    -- Final selection of potential duplicates based on days thresholds
    SELECT {select_clause}
    FROM datediff_calc d
    JOIN event_counts c 
        ON d.[{col_dict['PATIENT_LOCAL_ID']}] = c.[{col_dict['PATIENT_LOCAL_ID']}]
        AND d.[{col_dict['DISEASE_CD']}] = c.[{col_dict['DISEASE_CD']}]
    WHERE c.event_count > 1
    AND (
        (d.days_since_prev IS NOT NULL AND d.days_since_prev <= {days_value})
        OR (d.days_until_next IS NOT NULL AND d.days_until_next <= {days_value})
    )
    ORDER BY
        {sort_by.replace('[', 'd.[') + ',' if sort_by else ''}
        {base_order_by}
    """

    content = trx.query(full_query)

    subheader = f'Duplicate Investigations Time Frame: {days_value} Days'

    return ReportResult(
        content_type='table', content=content, subheader=subheader
    )
