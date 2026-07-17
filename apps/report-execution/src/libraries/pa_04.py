from src.db_transaction import Transaction
from src.errors import InvalidLibraryParamsError
from src.libraries.support.pa_04.calculations import (
    build_bucket_metrics,
    build_case_metrics,
)
from src.libraries.support.pa_04.queries import case_query, contact_query, index_query
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """PA04 Program Indicator Report.

    PA04 is really two SAS programs -- PA04_HIV.sas and PA04_Std.sas -- that
    share a lot of structure but diverge in real business logic (different
    disposition vocabularies, and different index calculations). This library
    merges them the way nbs_sr_19 merged SR19/SR20: one module, dispatching on
    a required 'variant' library param. Only 'hiv' is implemented so far;
    'std' is a deliberate follow-up.

    Conversion notes (HIV variant):
    * Unlike PA03/PA05, this returns a wide/pivoted table (one row per
      metric, with From OI / From RI / Total column-triplets for the
      Partner/Cluster metrics) rather than a long-form table -- matching the
      shape of the original SAS report's side-by-side OI/RI/Combined layout
      (PA04_HIV.sas's col1..col6 in templatePA04_HIV) more directly than the
      long-form convention used elsewhere in this app.
    * KNOWN SAS QUIRK: 'Cases Closed' (Val_A) and 'Cases Interviewed' (Val_B)
      come from the exact same unfiltered query in PA04_HIV.sas (lines
      61-66) and are therefore always equal.
    * KNOWN SAS QUIRK: the Notification Index and Testing Index are always
      the same value -- their source datasets (pix/testindex,
      PA04_HIV.sas:191-228) differ only by a filter that's already guaranteed
      true by the base case filter. See support/pa_04/queries.py.
    * KNOWN SAS QUIRK: the Combined-scope Notification/Testing Index sums
      per-IX_TYPE-group counts rather than taking a fresh distinct count, so
      it can double-count a case present under both Initial/Original and
      Re-Interview -- unlike every other Combined-scope metric. See
      support/pa_04/calculations.py.
    * KNOWN SAS QUIRK: 'Partners/Clusters Initiated' never has a percentage --
      PA04_HIV.sas's %fills macro computes PER_PM/PER_CM but the assignment
      that would write them into the report is commented out (lines 636,
      656). Preserved as always-None here, not a display bug.
    """
    if not isinstance(library_params, dict) or 'variant' not in library_params:
        raise InvalidLibraryParamsError(
            "'variant' is required (currently only 'hiv' is supported)."
        )

    variant = library_params['variant']
    if variant != 'hiv':
        raise InvalidLibraryParamsError(
            f"Unsupported PA04 variant: {variant!r}. Only 'hiv' is currently supported."
        )

    case_rows = trx.query(case_query(subset_query)).data
    contact_rows = trx.query(contact_query(subset_query)).data
    index_rows = trx.query(index_query(subset_query)).data

    totals, case_metric_rows = build_case_metrics(case_rows)
    bucket_rows = build_bucket_metrics(contact_rows, index_rows, totals['B'])

    content = Table(
        columns=[
            'Category 1',
            'Category 2',
            'Category 3',
            'Count',
            'Percentage',
            'Index',
            'From OI Count',
            'From OI Percentage',
            'From OI Index',
            'From RI Count',
            'From RI Percentage',
            'From RI Index',
            'Total Count',
            'Total Percentage',
            'Total Index',
        ],
        data=case_metric_rows + bucket_rows,
    )

    return ReportResult(content_type='table', content=content)
