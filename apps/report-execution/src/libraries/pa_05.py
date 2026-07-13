from src.db_transaction import Transaction
from src.libraries.support.pa_05.calculations import build_metric_counts, build_rows
from src.libraries.support.pa_05.queries import activity_query, ixs_query
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """PA05 Worker Interview Activity Report.

    Conversion notes:
    * Mirrors the PA03 approach: narrow SQL plus SAS metric math in Python.
    * Returns a long-form summary table (`Worker`, `Category 1/2/3`, `Count`,
      `Percentage`, `Index`) instead of reproducing the SAS PDF layout.
    * `Worker` is the case's PROVIDER_QUICK_CODE ('ALL' for the overall summary
      row). SAS groups its worker-level output by PROVIDER_QUICK_CODE for display
      but computes per-row detail by (INVESTIGATOR_INTERVIEW_KEY,
      PROVIDER_QUICK_CODE) without collapsing across investigator keys that share
      a quick code -- this port keeps that same grouping, so a quick code shared by
      multiple investigator keys will appear as multiple worker-row blocks rather
      than a single summed block.
    * `ixs_query` (src/libraries/support/pa_05/queries.py) deliberately does not
      join PA05_IXS_INIT-equivalent rows back to the subset by investigation key.
      PA05.sas (lines 141-168) builds this dataset the same way: from the full
      STD_HIV_DATAMART, filtered only by worker key membership and by the
      min/max assign/closed date bounds of the subset. Per a comment in the SAS
      source (line 162-163), this is intentional -- it picks up interviews a
      worker performed even on cases not originally assigned to them.
    * Within that, the closed-date bound has one extra clause
      (`std.CC_CLOSED_DT IS NULL AND ob.NUM_OPEN > 0`) to reproduce a SAS-specific
      quirk: PA05.sas's fallback subquery (lines 166-167) selects CC_CLOSED_DT from
      the subset without excluding NULLs, and SAS PROC SQL's IN operator treats
      missing = missing as a match. So an open candidate case matches whenever the
      subset contains any open case, regardless of whether the subset also has
      closed cases. T-SQL's IN never matches NULL, so without this clause the
      Python port would silently exclude open-case interview activity any time the
      subset has at least one closed case.
    * KNOWN DEVIATION: PA05.sas computes MIN/MAX(CA_INTERVIEWER_ASSIGN_DT) into
      macro variables, and SAS's default numeric-to-text conversion for those
      macro variables truncates to ~5 significant digits (e.g. a MAX of
      `28JAN2024:00:00:00`, datetime value 2,022,019,200, gets interpolated into
      the query as the literal `2.022E9` = 2,022,000,000 -- about 5.3 hours
      *earlier*, which crosses the midnight boundary down to 27JAN2024). SAS's
      `datepart()` upper bound on assign date is therefore effectively one day
      short of what the report intends, silently excluding any interview whose
      assign date lands exactly on the true max day. This is a SAS-side
      floating-point artifact, not a deliberate business rule, and its size
      depends on the current date.
    """
    activity_rows = trx.query(activity_query(subset_query)).data
    ixs_rows = trx.query(ixs_query(subset_query)).data

    metric_counts = build_metric_counts(activity_rows, ixs_rows)
    rows = build_rows(metric_counts)

    content = Table(
        columns=[
            'Worker',
            'Category 1',
            'Category 2',
            'Category 3',
            'Count',
            'Percentage',
            'Index',
        ],
        data=rows,
    )

    return ReportResult(content_type='table', content=content)
