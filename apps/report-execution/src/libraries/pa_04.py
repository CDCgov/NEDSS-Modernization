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
    * Mirrors the PA03/PA05 approach: narrow SQL plus SAS metric math in
      Python. Returns a long-form table instead of reproducing the SAS PDF
      layout.
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
        columns=['Scope', 'Category 1', 'Category 2', 'Count', 'Percentage', 'Index'],
        data=case_metric_rows + bucket_rows,
    )

    return ReportResult(content_type='table', content=content)
