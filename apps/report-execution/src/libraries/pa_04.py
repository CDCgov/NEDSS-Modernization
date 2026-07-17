from src.db_transaction import Transaction
from src.errors import InvalidLibraryParamsError
from src.libraries.support.pa_04.calculations import (
    build_bucket_metrics,
    build_case_metrics,
    build_std_bucket_metrics,
)
from src.libraries.support.pa_04.queries import (
    case_query,
    contact_query,
    index_query,
    std_index_query,
)
from src.models import ReportResult, Table

_SUPPORTED_VARIANTS = frozenset({'HIV', 'STD'})


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """PA04 Program Indicator Report.

    PA04 is really two SAS programs -- PA04_HIV.sas and PA04_Std.sas -- that
    share a lot of structure (case-level metrics, the Initiated/Examined/
    Not-Examined shape for the Partner/Cluster blocks) but diverge in real
    business logic (different disposition vocabularies, and different index
    calculations). This library merges them and dispatches on a required 
    'variant' library param.

    Conversion notes:
    * 'Cases Closed' (Val_A) and 'Cases Interviewed' (Val_B)
      come from the exact same unfiltered query (PA04_HIV.sas:61-66,
      PA04_Std.sas:60-67) and are therefore always equal.
    * 'Partners/Clusters Initiated' never has a percentage --
      the %fills macro computes PER_PM/PER_CM but the assignment that would
      write them into the report is commented out in both variants
      (PA04_HIV.sas:636,656; PA04_STD.sas:605,624). Preserved as always-None
      here, not a display bug.
    * Period Partner Index rounds to 0.1 in HIV but 0.01 in
      STD -- see support/pa_04/calculations.py.

    Conversion notes (HIV):
    * The Notification Index and Testing Index are always
      the same value -- their source datasets (pix/testindex,
      PA04_HIV.sas:191-228) differ only by a filter that's already guaranteed
      true by the base case filter. See support/pa_04/queries.py.
    * The Combined-scope Notification/Testing Index sums
      per-IX_TYPE-group counts rather than taking a fresh distinct count, so
      it can double-count a case present under both Initial/Original and
      Re-Interview -- unlike every other Combined-scope metric. See
      support/pa_04/calculations.py.

    Conversion notes (STD):
    * Unlike HIV, Treatment Index and DI Index are genuinely different
      values (not two displays of the same number): Treatment Index sums the
      per-contact disposition counts already computed for the breakdown rows,
      while DI Index is a separate case-level distinct count requiring a
      matching D_PROVIDER row and valid CTT_PROCESSING_DECISION -- see
      support/pa_04/queries.py's std_index_query and
      support/pa_04/calculations.py's build_std_bucket_metrics.
    * PA04_STD.sas's PP04_OI/CLUSTER queries additionally
      exclude contacts still at the 'no interview yet' sentinel value
      (`CONTACT_INTERVIEW_KEY <> 1`), a filter HIV's equivalent queries don't
      have. See support/pa_04/queries.py's contact_query.
    """
    if not isinstance(library_params, dict) or 'variant' not in library_params:
        raise InvalidLibraryParamsError("'variant' is required (one of: 'HIV', 'STD').")

    variant = library_params['variant']
    if variant not in _SUPPORTED_VARIANTS:
        raise InvalidLibraryParamsError(
            f'Unsupported PA04 variant: {variant!r}. '
            f'Supported variants: {sorted(_SUPPORTED_VARIANTS)}.'
        )

    case_rows = trx.query(case_query(subset_query)).data
    contact_rows = trx.query(contact_query(subset_query, variant)).data

    if variant == 'STD':
        totals, case_metric_rows = build_case_metrics(
            case_rows, period_partner_index_ndigits=2
        )
        treatment_index_rows = trx.query(std_index_query(subset_query)).data
        bucket_rows = build_std_bucket_metrics(
            contact_rows, treatment_index_rows, totals['B']
        )
    else:
        totals, case_metric_rows = build_case_metrics(case_rows)
        index_rows = trx.query(index_query(subset_query)).data
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
