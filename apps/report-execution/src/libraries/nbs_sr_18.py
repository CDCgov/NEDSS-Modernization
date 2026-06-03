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
        ),

        -- ensure we have all case verification values even if there's no matching data
        static_cv AS (
            SELECT '0 - Not a Verified Case' AS 'CASE_VERIFICATION'
            UNION
            SELECT '1 - Positive Culture' AS 'CASE_VERIFICATION'
            UNION
            SELECT '1A - Positive NAA' AS 'CASE_VERIFICATION'
            UNION
            SELECT '2 - Positive Smear/Tissue' AS 'CASE_VERIFICATION'
            UNION
            SELECT '3 - Clinical Case Definition' AS 'CASE_VERIFICATION'
            UNION
            SELECT '4 - Verified by Provider Diagnosis' AS 'CASE_VERIFICATION'
            UNION
            SELECT '5 - Suspect' AS 'CASE_VERIFICATION'
        ),

        -- count all of the calc disease sites for each case verification
        counts AS (
            SELECT CASE_VERIFICATION,
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
        )

        -- display the final counts, substituting 0 where no data was found
        SELECT sc.CASE_VERIFICATION AS [Case Verification],
            CASE
                WHEN c.[Pulmonary Count] IS NULL
                    THEN 0
                ELSE c.[Pulmonary Count]
                END AS [Pulmonary Count],
            CASE
                WHEN c.[Extrapulmonary Count] IS NULL
                    THEN 0
                ELSE c.[Extrapulmonary Count]
                END AS [Extrapulmonary Count],
            CASE
                WHEN c.[Both Count] IS NULL
                    THEN 0
                ELSE c.[Both Count]
                END AS [Both Count],
            CASE
                WHEN c.[Unknown Count] IS NULL
                    THEN 0
                ELSE c.[Unknown Count]
                END AS [Unknown Count],
            CASE
                WHEN c.[All Cases Count] IS NULL
                    THEN 0
                ELSE c.[All Cases Count]
                END AS [All Cases Count]
        FROM counts c
        RIGHT OUTER JOIN static_cv sc ON sc.CASE_VERIFICATION = c.CASE_VERIFICATION
        ORDER BY sc.CASE_VERIFICATION;
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
