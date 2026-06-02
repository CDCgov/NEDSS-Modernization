import pandas as pd

from src.db_transaction import Transaction
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """SR18: TB Case Verification Report. Computes statistics about TB cases."""
    query = f"""
        WITH subset AS (
            {subset_query}
        )
        SELECT CASE_VERIFICATION AS [Case Verification],
            SUM(CASE 
                    WHEN CALC_DISEASE_SITE = 'Pulmonary'
                        THEN 1
                    ELSE 0
                    END) AS [Pulmonary Count],
            SUM(CASE 
                    WHEN CALC_DISEASE_SITE = 'Extra Pulmonary'
                        THEN 1
                    ELSE 0
                    END) AS [Extrapulmonary Count],
            SUM(CASE 
                    WHEN CALC_DISEASE_SITE = 'Both'
                        THEN 1
                    ELSE 0
                    END) AS [Both Count],
            SUM(CASE 
                    WHEN CALC_DISEASE_SITE = 'Unknown'
                        THEN 1
                    ELSE 0
                    END) AS [Unknown Count],
            COUNT(*) AS [All Cases Count]
        FROM subset
        WHERE CASE_VERIFICATION IS NOT NULL
            AND CALC_DISEASE_SITE IS NOT NULL
        GROUP BY CASE_VERIFICATION
        ORDER BY CASE_VERIFICATION;
    """

    content = trx.query(query)
    table = _perform_calculations(content)

    return ReportResult(content_type='table', content=table)


def _perform_calculations(table: Table) -> Table:
    """Take the raw query result data and calculate the data needed
    to recreate the SAS report.
    """
    df = pd.DataFrame.from_records(table.data, columns=table.columns)

    # order the columns to match the SAS report
    cols = ['Pulmonary', 'Extrapulmonary', 'Both', 'Unknown', 'All Cases']
    ordered_cols = ['Case Verification']
    for col in cols:
        ordered_cols.extend([f'{col} Count', f'{col} %'])

    # short-circuit for no data
    if not table.data:
        return Table(columns=ordered_cols, data=table.data)

    # calculate percentage of total for each value in each "Count" column
    for col in cols:
        df[f'{col} %'] = (df[f'{col} Count'] / df[f'{col} Count'].sum() * 100).round(1)

    # re-write the case verification strings to match SAS report
    df['Case Verification'] = [
        '0 - Not a Verified Case',
        '1 - Positive Culture',
        '1A - Positive NAA',
        '2 - Positive Smear',
        '3 - Clinical Case Definition',
        '4 - Provider Diagnosis',
        '5 - Suspect Case',
    ]

    # ensure column ordering matches the SAS report
    df = df[ordered_cols]

    return Table(data=df.values.tolist(), columns=df.columns.to_list())
