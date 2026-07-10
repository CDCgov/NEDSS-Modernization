from src.db_transaction import Transaction
from src.libraries.support.pa_01.calculations import build_output_for_worker
from src.libraries.support.pa_01.models import (
    CSV_COLUMNS,
    Pa01Row,
    Pa01Worker,
)
from src.libraries.support.pa_01.queries import (
    case_interview_rows_query,
    cases_with_no_clusters_query,
    cases_with_no_partners_query,
    cluster_previous_pos_query,
    clusters_initiated_query,
    disease_intervention_index_query,
    examined_clusters_query,
    examined_partners_query,
    not_examined_partners_query,
    not_notified_clusters_query,
    not_notified_partners_query,
    notified_clusters_query,
    notified_partners_by_speed_query,
    notified_partners_query,
    partner_case_dispositions_query,
    partner_notification_query,
    period_partners_query,
    previously_treated_partners_query,
    testing_index_query,
    timed_interviews_query,
    treatment_index_query,
)
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """PA01 HIV and STD: Case Management Report.

    Conversion notes:
    * Report is too large for a single file, additional files found in
      directory `./support/pa_01/`
    * This report is the combination of both `PA01_HIV.sas` and `PA01_STD.sas`
    * The variant (STD or HIV) is determined by `library_params['report_variant']`
      which is defined in the Report_Library db table.
    * There are some rounding peculiarities between SAS and Python, so for instance
      a value of 0.075 rounded to 2 decimal places in Python will yield 0.07, but in
      SAS it will be 0.08.  In such cases the Python value will be used as is.
    * There is a quirk in PA01_HIV.sas in which the data point "HIV Tested" output does
      not include percentages for individual workers, only "ALL WORKERS".  This report
      includes percentages for individual workers.
    * There is a bug in PA01_HIV.sas in which the "CASES /W NO CLUSTERS" shows up as
      0 for "ALL WORKERS" even if there is data present.  This is because the SAS
      template for this calculation for "ALL WORKERS" uses a slightly different string:
      "CASES W/NO CLUSTERS" (note the difference in "/W" vs. "W/").  The Python library
      will give the proper answer, so in that way it differs from the SAS source.
    * There is a bug in PA01_HIV.sas in which subsections of "NEW PARTNERS NOT
      NOTIFIED" will show a percentage of 0.0% when they have more than 0 items.  The
      Python version of this will show the actual percentages.
    * There is a quirk in PA01_HIV.sas in which the "NEW CLUSTERS NOTIFIED" count
      for "ALL WORKERS" doesn't use the distinct case id count like most other
      calculations.  I have replicated this behavior in Python but it is likely
      incorrect.
    * For the HIV variant in the "DISPOSITIONS - PARTNERS & CLUSTERS" section, there
      are 2 occurances of "PREVIOUS POS" and "OPEN" that come at the bottom of each
      column of the report section.  Since these names are identical, and they contain
      no other grouping I have labled them as:

      - New Partners Previous Pos
      - New Partners Open
      - New Clusters Previous Pos
      - New Clusters Open

      in order to distinguish them in the CSV.  Otherwise if the CSV data were ever
      sorted it would lose its positional meaning.
    * The above changes also apply in a similar fashion to the STD variant, converting 
      "PREVIOUS RX" and "OPEN" to:

      - New Partners Previous RX
      - New Partners Open
      - New Clusters Previous RX
      - New Clusters Open
    * In the HIV variant section "SPEED OF NOTIFICATION - PARTNERS & CLUSTERS", under
      "New Clusters Notified" for "ALL WORKERS", the percentages appear as 0.0% even
      if there are counts.  This is due to an issue in the SAS and the Python library
      includes the correct percentages.
    * There is a bug in PA01_STD.sas for the rightmost "PREVIOUS RX" calculation in the
      "DISPOSITIONS - NEW PARTNERS & CLUSTERS" section.  It erroneously re-uses the
      "PREVIOUS PREV RX" value for each individual workers (ALL WORKERS has the correct
      value).  The Python library will not re-create this bug and instead will give
      the actual values for each worker.
    """
    if not isinstance(library_params, dict):
        raise ValueError(
            f"Parameter 'library_params' is not a dict (is {type(library_params)})"
        )

    report_variant = library_params.get('report_variant')

    if report_variant is None:
        raise ValueError(
            f"Parameter 'library_params' missing key 'report_variant': {library_params}"
        )
    elif report_variant not in ['HIV', 'STD']:
        raise ValueError(
            f'Report variant can only be "STD" or "HIV" and was: {report_variant}'
        )

    # short circuit if there's no data from subset_query
    data_check = trx.query(subset_query)
    if len(data_check.data) == 0:
        content = Table(
            columns=CSV_COLUMNS,
            data=[],
        )

        return ReportResult(content_type='table', content=content)

    # run queries
    tables: dict[str, Table] = {}
    tables['case_interview_rows'] = trx.query(
        case_interview_rows_query(subset_query, report_variant)
    )
    tables['timed_interviews'] = trx.query(
        timed_interviews_query(subset_query, report_variant)
    )
    tables['partner_notification'] = trx.query(partner_notification_query(subset_query))
    tables['testing_index'] = trx.query(testing_index_query(subset_query))
    tables['period_partners'] = trx.query(period_partners_query(subset_query))
    tables['cases_with_no_partners'] = trx.query(
        cases_with_no_partners_query(subset_query)
    )
    tables['clusters_initiated'] = trx.query(clusters_initiated_query(subset_query))
    tables['cases_with_no_clusters'] = trx.query(
        cases_with_no_clusters_query(subset_query)
    )
    tables['notified_partners'] = trx.query(notified_partners_query(subset_query))
    tables['not_notified_partners'] = trx.query(
        not_notified_partners_query(subset_query)
    )
    tables['notified_clusters'] = trx.query(notified_clusters_query(subset_query))
    tables['not_notified_clusters'] = trx.query(
        not_notified_clusters_query(subset_query)
    )
    tables['partner_case_dispositions'] = trx.query(
        partner_case_dispositions_query(subset_query)
    )
    tables['clusters_previous_pos'] = trx.query(
        cluster_previous_pos_query(subset_query)
    )
    tables['notified_partners_by_speed'] = trx.query(
        notified_partners_by_speed_query(subset_query)
    )
    tables['disease_intervention_index'] = trx.query(
        disease_intervention_index_query(subset_query)
    )
    tables['treatment_index'] = trx.query(treatment_index_query(subset_query))
    tables['examined_partners'] = trx.query(examined_partners_query(subset_query))
    tables['not_examined_partners'] = trx.query(
        not_examined_partners_query(subset_query)
    )
    tables['previously_treated_partners'] = trx.query(
        previously_treated_partners_query(subset_query)
    )
    tables['examined_clusters'] = trx.query(examined_clusters_query(subset_query))

    # get list of workers (nb. None treated as "ALL WORKERS")
    workers: list[Pa01Worker | None] = [None]
    workers.extend(_get_workers(tables['case_interview_rows']))

    # build output CSV data for each worker
    output_rows: list[Pa01Row] = []
    for worker in workers:
        output_rows.extend(build_output_for_worker(tables, report_variant, worker))

    content = Table(
        columns=CSV_COLUMNS,
        data=output_rows,
    )

    return ReportResult(content_type='table', content=content)


def _get_workers(case_interview_rows: Table) -> list[Pa01Worker]:
    """Get all unique workers within the report's data.  A worker is defined as the
    combination of 'investigator_interview_key' and 'provider_quick_code'.
    """
    workers = {
        Pa01Worker(
            row['INVESTIGATOR_INTERVIEW_KEY'],
            row['PROVIDER_QUICK_CODE'],
        )
        for row in case_interview_rows.data_as_dicts()
        if row['INVESTIGATOR_INTERVIEW_KEY'] is not None
        and row['PROVIDER_QUICK_CODE'] is not None
    }

    # nb. sorting should mimic the ordering of workers found in the PDF output
    return sorted(
        workers, key=lambda w: (w.provider_quick_code, w.investigator_interview_key)
    )
