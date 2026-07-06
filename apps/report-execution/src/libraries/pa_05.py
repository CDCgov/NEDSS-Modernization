from collections import defaultdict
from datetime import date, datetime
from decimal import Decimal

from src.db_transaction import Transaction
from src.models import ReportResult, Table

WorkerKey = tuple[str | None, int | None]
Pa05Row = tuple[
    str, str, str | None, str | None, int, str | None, float | None
]

INITIAL_INTERVIEW = 'Initial/Original'
REINTERVIEW = 'Re-Interview'
INTERVIEWED_STATUS = 'I - Interviewed'
PENDING_STATUS = 'A - Awaiting'
REFUSED_STATUS = 'R - Refused Interview'
UNABLE_TO_LOCATE_STATUS = 'U - Unable to Locate'
OTHER_STATUS = 'O - Other'
PARTNER_BASES = {
    'P1 - Partner, Sex',
    'P2 - Partner, Needle-Sharing',
    'P3 - Partner, Both',
}
CLUSTER_BASES = {
    'A1 - Associate 1',
    'A2 - Associate 2',
    'A3 - Associate 3',
    'S1 - Social Contact 1',
    'S2 - Social Contact 2',
    'S3 - Social Contact 3',
}
VALID_PROCESSING_DECISIONS = {
    'Field Follow-up',
    'Record Search Closure',
    'Secondary Referral',
}

METRICS: tuple[
    tuple[str, str | None, str | None, str, str | None, str | None], ...
] = (
    ('Num. Cases Assigned', None, None, 'A', None, None),  # Var_A, PA05.sas:304-309
    (
        'Num. Cases Assigned',
        'Num. Cases Open',
        None,
        'B',
        'A',
        'percent',
    ),  # Var_B, PA05.sas:312-318
    (
        'Num. Cases Assigned',
        'Num. Cases Closed',
        None,
        'C',
        'A',
        'percent',
    ),  # Var_C, PA05.sas:320-326
    (
        'Num. Cases Assigned',
        'Num. Cases Pending',
        None,
        'D',
        'A',
        'percent',
    ),  # Var_D, PA05.sas:329-335
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        None,
        'E',
        'A',
        'percent',
    ),  # Var_E, PA05.sas:338-345
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "Clinic IX'S",
        'F',
        'E',
        'percent',
    ),  # Var_F, PA05.sas:348-357
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "Field IX'S",
        'G',
        'E',
        'percent',
    ),  # Var_G, PA05.sas:361-370
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 3 Days",
        'H',
        'E',
        'percent',
    ),  # Var_H, PA05.sas:375-381
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 5 Days",
        'I',
        'E',
        'percent',
    ),  # Var_I, PA05.sas:383-389
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 7 Days",
        'J',
        'E',
        'percent',
    ),  # Var_J, PA05.sas:391-397
    (
        'Num. Cases Assigned',
        "Num. Cases IX'D",
        "IX'D w/in 14 Days",
        'K',
        'E',
        'percent',
    ),  # Var_K, PA05.sas:399-406
    (
        "Num. Cases Not IX'D",
        None,
        None,
        'L',
        'A',
        'percent',
    ),  # Var_L, PA05.sas:408-415
    (
        "Num. Cases Not IX'D",
        'Refused',
        None,
        'M',
        'L',
        'percent',
    ),  # Var_M, PA05.sas:418-425
    (
        "Num. Cases Not IX'D",
        'No Locate',
        None,
        'N',
        'L',
        'percent',
    ),  # Var_N, PA05.sas:428-435
    (
        "Num. Cases Not IX'D",
        'Other',
        None,
        'O',
        'L',
        'percent',
    ),  # Var_O, PA05.sas:438-445
    ("Num. of OI'S", None, None, 'P', None, None),  # Var_P, PA05.sas:447-453
    (
        "Num. of OI'S",
        "OI'S that were NCI",
        None,
        'Q',
        'P',
        'percent',
    ),  # Var_Q, PA05.sas:455-461
    (
        "Num. of OI'S",
        'Period Partners',
        None,
        'R',
        'P',
        'ratio',
    ),  # Var_R, PA05.sas:269-289 (pp) + 464-474 (ppnodup/r)
    (
        "Num. of OI'S",
        'Partners Initiated',
        None,
        'S',
        'P',
        'ratio',
    ),  # Var_S, PA05.sas:476-482
    ("Num. of RI'S", None, None, 'T', None, None),  # Var_T, PA05.sas:485-491
    (
        "Num. of RI'S",
        "RI's that were NCI",
        None,
        'U',
        'T',
        'percent',
    ),  # Var_U, PA05.sas:493-499
    (
        "Num. of RI'S",
        'Partners Initiated',
        None,
        'V',
        'T',
        'ratio',
    ),  # Var_V, PA05.sas:501-507
    (
        'Clusters Init. (OI & RI)',
        None,
        None,
        'W',
        'P',
        'ratio',
    ),  # Var_W, PA05.sas:509-516
)


def _to_date(value):
    if value is None:
        return None
    if isinstance(value, datetime):
        return value.date()
    if isinstance(value, date):
        return value
    return None


def _safe_int(value) -> int:
    if value is None:
        return 0
    if isinstance(value, bool):
        return int(value)
    if isinstance(value, (int, Decimal)):
        return int(value)
    if isinstance(value, float):
        return int(value)
    if isinstance(value, str):
        text = value.strip()
        if text == '':
            return 0
        try:
            return int(text)
        except ValueError:
            return 0
    return 0


def _percentage(numerator: int, denominator: int) -> str | None:
    """Format a fraction as a SAS `percent8.1`-style string, e.g. '78.1%'."""
    if denominator == 0:
        return None
    return f'{numerator / denominator * 100:.1f}%'


def _index(numerator: int, denominator: int) -> float | None:
    """Round a fraction to one decimal place, matching SAS's `round(per_x, 0.1)`."""
    if denominator == 0:
        return None
    return round(numerator / denominator, 1)


def _percentage_and_index(
    numerator: int, denominator: int, rate_type: str | None
) -> tuple[str | None, float | None]:
    if rate_type == 'percent':
        return _percentage(numerator, denominator), None
    if rate_type == 'ratio':
        return None, _index(numerator, denominator)
    return None, None


def _sorted_worker_keys(keys: set[WorkerKey]) -> list[WorkerKey]:
    return sorted(keys, key=lambda key: ((key[0] or ''), key[1] or 0))


def _distinct_count_map(source: dict[WorkerKey, set]) -> dict[WorkerKey, int]:
    return {key: len(values) for key, values in source.items()}


def _activity_query(subset_query: str) -> str:
    # Equivalent to the PA05 dataset build (PA05.sas lines 122-132). Drops
    # INVESTIGATION_KEY and INVESTIGATOR_INTERVIEW_QC from the SAS SELECT list:
    # both are per-case attributes already 1:1 with INV_LOCAL_ID (so they can't
    # affect SELECT DISTINCT row counts), and neither is referenced anywhere
    # else in PA05.sas either -- they're dead columns in the original report.
    return f"""
    WITH shd AS (
        {subset_query}
    ),
    std_cases AS (
        SELECT shd.*
        FROM shd
        INNER JOIN RDB.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
          AND shd.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
    )
    SELECT DISTINCT
        std_cases.INV_LOCAL_ID,
        di.LOCAL_ID AS INTERVIEW_LOCAL_ID,
        di.IX_TYPE,
        di.IX_LOCATION,
        std_cases.INVESTIGATOR_INTERVIEW_KEY,
        di.IX_DATE,
        std_cases.CA_INTERVIEWER_ASSIGN_DT,
        dp.PROVIDER_QUICK_CODE,
        std_cases.CC_CLOSED_DT,
        std_cases.CA_PATIENT_INTV_STATUS,
        TRY_CAST(
            std_cases.STD_PRTNRS_PRD_TRNSGNDR_TTL AS INT
        ) AS STD_PRTNRS_PRD_TRNSGNDR_TTL,
        TRY_CAST(std_cases.SOC_PRTNRS_PRD_FML_TTL AS INT) AS SOC_PRTNRS_PRD_FML_TTL,
        TRY_CAST(std_cases.SOC_PRTNRS_PRD_MALE_TTL AS INT) AS SOC_PRTNRS_PRD_MALE_TTL
    FROM std_cases
    LEFT JOIN RDB.DBO.F_INTERVIEW_CASE fic
        ON fic.INVESTIGATION_KEY = std_cases.INVESTIGATION_KEY
    LEFT JOIN RDB.DBO.D_INTERVIEW di
        ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    LEFT JOIN RDB.DBO.D_PROVIDER dp
        ON dp.PROVIDER_KEY = std_cases.INVESTIGATOR_INTERVIEW_KEY;
    """


def _ixs_query(subset_query: str) -> str:
    # Equivalent to the PA05_IXS_INIT dataset build (PA05.sas lines 141-168),
    # including the worker-key/date-bound MIN/MAX derivation (lines 135-139).
    return f"""
    WITH shd AS (
        {subset_query}
    ),
    std_cases AS (
        SELECT shd.*
        FROM shd
        INNER JOIN RDB.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
          AND shd.CA_INTERVIEWER_ASSIGN_DT IS NOT NULL
    ),
    worker_keys AS (
        SELECT DISTINCT INVESTIGATOR_INTERVIEW_KEY
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    ),
    assign_bounds AS (
        SELECT
            MIN(CA_INTERVIEWER_ASSIGN_DT) AS MIN_ASSIGN_DT,
            MAX(CA_INTERVIEWER_ASSIGN_DT) AS MAX_ASSIGN_DT
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    ),
    closed_cases AS (
        SELECT CC_CLOSED_DT
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
          AND CC_CLOSED_DT IS NOT NULL
    ),
    closed_bounds AS (
        SELECT
            COUNT(*) AS NUM_CLOSED,
            MIN(CC_CLOSED_DT) AS MIN_CLOSED_DT,
            MAX(CC_CLOSED_DT) AS MAX_CLOSED_DT
        FROM closed_cases
    ),
    open_bounds AS (
        SELECT
            SUM(CASE WHEN CC_CLOSED_DT IS NULL THEN 1 ELSE 0 END) AS NUM_OPEN
        FROM std_cases
        WHERE INVESTIGATOR_INTERVIEW_KEY <> 1
    )
    SELECT DISTINCT
        di.IX_TYPE,
        dp.PROVIDER_QUICK_CODE,
        fic.IX_INTERVIEWER_KEY AS INVESTIGATOR_INTERVIEW_KEY,
        di.D_INTERVIEW_KEY,
        fcrc.D_CONTACT_RECORD_KEY,
        fcrc.CONTACT_INVESTIGATION_KEY,
        dcr.CTT_REFERRAL_BASIS,
        dcr.CTT_PROCESSING_DECISION
    FROM RDB.DBO.D_INTERVIEW di
    INNER JOIN RDB.DBO.F_INTERVIEW_CASE fic
        ON di.D_INTERVIEW_KEY = fic.D_INTERVIEW_KEY
       AND di.RECORD_STATUS_CD <> 'LOG_DEL'
    INNER JOIN RDB.DBO.STD_HIV_DATAMART std
        ON fic.INVESTIGATION_KEY = std.INVESTIGATION_KEY
    INNER JOIN RDB.DBO.D_PROVIDER dp
        ON fic.IX_INTERVIEWER_KEY = dp.PROVIDER_KEY
    LEFT JOIN RDB.DBO.F_CONTACT_RECORD_CASE fcrc
        ON fcrc.CONTACT_INTERVIEW_KEY = di.D_INTERVIEW_KEY
    LEFT JOIN RDB.DBO.D_CONTACT_RECORD dcr
        ON fcrc.D_CONTACT_RECORD_KEY = dcr.D_CONTACT_RECORD_KEY
       AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
    CROSS JOIN assign_bounds ab
    CROSS JOIN closed_bounds cb
    CROSS JOIN open_bounds ob
    WHERE std.INVESTIGATOR_INTERVIEW_KEY IN (
        SELECT INVESTIGATOR_INTERVIEW_KEY FROM worker_keys
    )
      AND CAST(std.CA_INTERVIEWER_ASSIGN_DT AS DATE) >= CAST(ab.MIN_ASSIGN_DT AS DATE)
      AND CAST(std.CA_INTERVIEWER_ASSIGN_DT AS DATE) <= CAST(ab.MAX_ASSIGN_DT AS DATE)
      AND (
            cb.NUM_CLOSED = 0
            OR (
                std.CC_CLOSED_DT IS NOT NULL
                AND CAST(std.CC_CLOSED_DT AS DATE) >= CAST(cb.MIN_CLOSED_DT AS DATE)
                AND CAST(std.CC_CLOSED_DT AS DATE) <= CAST(cb.MAX_CLOSED_DT AS DATE)
            )
            OR std.CC_CLOSED_DT IN (SELECT CC_CLOSED_DT FROM closed_cases)
            -- SAS's fallback subquery (PA05.sas lines 166-167) selects CC_CLOSED_DT
            -- from the subset without excluding NULLs, and SAS PROC SQL's IN
            -- operator treats missing = missing as a match. That means an open
            -- candidate case (CC_CLOSED_DT IS NULL) matches whenever the subset
            -- itself contains any open case, regardless of whether the subset also
            -- has closed cases. T-SQL's IN never matches NULL, so this clause
            -- reproduces that behavior explicitly to keep parity with SAS.
            OR (std.CC_CLOSED_DT IS NULL AND ob.NUM_OPEN > 0)
      );
    """


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
    * `_ixs_query` deliberately does not join PA05_IXS_INIT-equivalent rows back to
      the subset by investigation key. PA05.sas (lines 141-168) builds this dataset
      the same way: from the full STD_HIV_DATAMART, filtered only by worker key
      membership and by the min/max assign/closed date bounds of the subset. Per a
      comment in the SAS source (line 162-163), this is intentional -- it picks up
      interviews a worker performed even on cases not originally assigned to them.
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
    activity_rows = trx.query(_activity_query(subset_query)).data
    ixs_rows = trx.query(_ixs_query(subset_query)).data

    case_sets = {
        metric: defaultdict(set)
        for metric in ('A', 'B', 'C', 'D', 'E', 'F', 'G', 'L', 'M', 'N', 'O')
    }
    day_counts = {metric: defaultdict(int) for metric in ('H', 'I', 'J', 'K')}
    interview_sets = {metric: defaultdict(set) for metric in ('P', 'Q', 'T', 'U')}
    contact_sets = {metric: defaultdict(set) for metric in ('S', 'V', 'W')}
    period_partner_totals: dict[WorkerKey, int] = defaultdict(int)

    # Single pass over activity_rows: cases/day-buckets (Var_A-L) and period
    # partners (Var_R) are independent per-row calculations, so both can be
    # done from one destructure of the row tuple instead of two.
    pp_seen_local_ids: set[str] = set()
    for row in activity_rows:
        (
            inv_local_id,
            interview_local_id,
            ix_type,
            ix_location,
            investigator_interview_key,
            ix_date,
            assign_dt,
            provider_quick_code,
            cc_closed_dt,
            patient_interview_status,
            std_prtnrs_prd_trnsgndr_ttl,
            soc_prtnrs_prd_fml_ttl,
            soc_prtnrs_prd_male_ttl,
        ) = row
        worker_key = (provider_quick_code, investigator_interview_key)

        if inv_local_id is not None:
            case_sets['A'][worker_key].add(inv_local_id)  # Var_A, PA05.sas:304-309
            if cc_closed_dt is None:
                case_sets['B'][worker_key].add(inv_local_id)  # Var_B, PA05.sas:312-318
            else:
                case_sets['C'][worker_key].add(inv_local_id)  # Var_C, PA05.sas:320-326

            if patient_interview_status == PENDING_STATUS:
                case_sets['D'][worker_key].add(inv_local_id)  # Var_D, PA05.sas:329-335
            if patient_interview_status == INTERVIEWED_STATUS:
                case_sets['E'][worker_key].add(inv_local_id)  # Var_E, PA05.sas:338-345
            if patient_interview_status in {
                OTHER_STATUS,
                REFUSED_STATUS,
                UNABLE_TO_LOCATE_STATUS,
            }:
                case_sets['L'][worker_key].add(inv_local_id)  # Var_L, PA05.sas:408-415
            if patient_interview_status == REFUSED_STATUS:
                case_sets['M'][worker_key].add(inv_local_id)  # Var_M, PA05.sas:418-425
            if patient_interview_status == UNABLE_TO_LOCATE_STATUS:
                case_sets['N'][worker_key].add(inv_local_id)  # Var_N, PA05.sas:428-435
            if patient_interview_status == OTHER_STATUS:
                case_sets['O'][worker_key].add(inv_local_id)  # Var_O, PA05.sas:438-445

            if ix_type == INITIAL_INTERVIEW and ix_location == 'Clinic':
                case_sets['F'][worker_key].add(inv_local_id)  # Var_F, PA05.sas:348-357
            if ix_type == INITIAL_INTERVIEW and ix_location == 'Field':
                case_sets['G'][worker_key].add(inv_local_id)  # Var_G, PA05.sas:361-370

        # Var_H/I/J/K, PA05.sas:250-264 (PA05_DTE) + 295-301 (PROC FREQ) +
        # 375-406 (days_A_03/05/07/14) -- cumulative day buckets, Initial/
        # Original interviews only, non-negative days-to-interview.
        assign_date = _to_date(assign_dt)
        interview_date = _to_date(ix_date)
        if (
            ix_type == INITIAL_INTERVIEW
            and assign_date is not None
            and interview_date is not None
        ):
            days = (interview_date - assign_date).days
            if days >= 0:
                if days <= 3:
                    day_counts['H'][worker_key] += 1
                if days <= 5:
                    day_counts['I'][worker_key] += 1
                if days <= 7:
                    day_counts['J'][worker_key] += 1
                if days <= 14:
                    day_counts['K'][worker_key] += 1

        # Var_R, PA05.sas:269-289 (pp) + 464-474 (ppnodup/r) -- distinct by
        # D_INTERVIEW local_id (interview_local_id here), summed across the
        # three partner-total fields, for Initial/Original interviews with
        # status 'I - Interviewed'.
        if (
            interview_local_id is not None
            and interview_local_id not in pp_seen_local_ids
            and ix_type == INITIAL_INTERVIEW
            and patient_interview_status == INTERVIEWED_STATUS
        ):
            pp_seen_local_ids.add(interview_local_id)
            period_partner_totals[worker_key] += (
                _safe_int(std_prtnrs_prd_trnsgndr_ttl)
                + _safe_int(soc_prtnrs_prd_fml_ttl)
                + _safe_int(soc_prtnrs_prd_male_ttl)
            )

    for row in ixs_rows:
        (
            ix_type,
            provider_quick_code,
            investigator_interview_key,
            d_interview_key,
            d_contact_record_key,
            contact_investigation_key,
            ctt_referral_basis,
            ctt_processing_decision,
        ) = row
        worker_key = (provider_quick_code, investigator_interview_key)

        if d_interview_key is not None:
            if ix_type == INITIAL_INTERVIEW:
                # Var_P, PA05.sas:447-453 (PA05_WRKR filtered to Initial/Original)
                interview_sets['P'][worker_key].add(d_interview_key)
                if d_contact_record_key in (None, 1):
                    # Var_Q, PA05.sas:455-461 (PA05_NCI filtered to Initial/Original)
                    interview_sets['Q'][worker_key].add(d_interview_key)
            elif ix_type == REINTERVIEW:
                # Var_T, PA05.sas:485-491 (PA05_WRKR filtered to Re-Interview)
                interview_sets['T'][worker_key].add(d_interview_key)
                if d_contact_record_key in (None, 1):
                    # Var_U, PA05.sas:493-499 (PA05_NCI filtered to Re-Interview)
                    interview_sets['U'][worker_key].add(d_interview_key)

        if (
            contact_investigation_key is None
            or ctt_referral_basis is None
            or ctt_processing_decision not in VALID_PROCESSING_DECISIONS
        ):
            continue

        contact_key = f'{contact_investigation_key}|{ctt_referral_basis}'
        if ctt_referral_basis in PARTNER_BASES:
            if ix_type == INITIAL_INTERVIEW:
                # Var_S, PA05.sas:476-482 (PA05_PI filtered to Initial/Original)
                contact_sets['S'][worker_key].add(contact_key)
            elif ix_type == REINTERVIEW:
                # Var_V, PA05.sas:501-507 (PA05_PI filtered to Re-Interview)
                contact_sets['V'][worker_key].add(contact_key)
        if (
            ctt_referral_basis in CLUSTER_BASES
            and ix_type in {INITIAL_INTERVIEW, REINTERVIEW}
        ):
            # Var_W, PA05.sas:509-516 (PA05_CI, both ix_types summed together)
            contact_sets['W'][worker_key].add(contact_key)

    metric_counts: dict[str, dict[WorkerKey, int]] = {
        metric: _distinct_count_map(case_sets[metric]) for metric in case_sets
    }
    metric_counts.update(
        {metric: dict(counts) for metric, counts in day_counts.items()}
    )
    metric_counts.update(
        {
            metric: _distinct_count_map(interview_sets[metric])
            for metric in interview_sets
        }
    )
    metric_counts.update(
        {
            metric: _distinct_count_map(contact_sets[metric])
            for metric in contact_sets
        }
    )
    metric_counts['R'] = dict(period_partner_totals)

    all_worker_keys: set[WorkerKey] = set()
    for counts in metric_counts.values():
        all_worker_keys.update(counts.keys())
    worker_keys = _sorted_worker_keys(all_worker_keys)

    overall_counts = {
        metric_key: sum(metric_counts.get(metric_key, {}).values())
        for _, _, _, metric_key, _, _ in METRICS
    }

    rows: list[Pa05Row] = []
    for (
        category_1,
        category_2,
        category_3,
        metric_key,
        denominator_key,
        rate_type,
    ) in METRICS:
        count = overall_counts.get(metric_key, 0)
        denominator = overall_counts.get(denominator_key, 0) if denominator_key else 0
        percentage, index = _percentage_and_index(count, denominator, rate_type)
        rows.append(
            (
                'ALL',
                category_1,
                category_2,
                category_3,
                count,
                percentage,
                index,
            )
        )

    for provider_quick_code, investigator_interview_key in worker_keys:
        worker_key = (provider_quick_code, investigator_interview_key)
        worker_label = provider_quick_code or f'Worker {investigator_interview_key}'
        for (
            category_1,
            category_2,
            category_3,
            metric_key,
            denominator_key,
            rate_type,
        ) in METRICS:
            count = metric_counts.get(metric_key, {}).get(worker_key, 0)
            denominator = (
                metric_counts.get(denominator_key, {}).get(worker_key, 0)
                if denominator_key
                else 0
            )
            percentage, index = _percentage_and_index(count, denominator, rate_type)
            rows.append(
                (
                    worker_label,
                    category_1,
                    category_2,
                    category_3,
                    count,
                    percentage,
                    index,
                )
            )

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
