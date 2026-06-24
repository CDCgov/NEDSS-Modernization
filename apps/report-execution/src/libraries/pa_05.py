from collections import defaultdict
from datetime import date, datetime
from decimal import Decimal

from src.db_transaction import Transaction
from src.models import ReportResult, Table

WorkerKey = tuple[str | None, int | None]
Pa05Row = tuple[str, str | None, int | None, str, str, int, float | None, str | None]

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

METRICS: tuple[tuple[str, str, str, str | None, str | None], ...] = (
    ('Case Activity', 'NUM. CASES ASSIGNED', 'A', None, None),
    ('Case Activity', 'NUM. CASES OPEN', 'B', 'A', 'percent'),
    ('Case Activity', 'NUM. CASES CLOSED', 'C', 'A', 'percent'),
    ('Case Activity', 'NUM. CASES PENDING', 'D', 'A', 'percent'),
    ('Case Activity', "NUM. CASES IX'D", 'E', 'A', 'percent'),
    ('Case Activity', "CLINIC IX'S", 'F', 'E', 'percent'),
    ('Case Activity', "FIELD IX'S", 'G', 'E', 'percent'),
    ('Case Activity', "IX'D W/IN 3 DAYS", 'H', 'E', 'percent'),
    ('Case Activity', "IX'D W/IN 5 DAYS", 'I', 'E', 'percent'),
    ('Case Activity', "IX'D W/IN 7 DAYS", 'J', 'E', 'percent'),
    ('Case Activity', "IX'D W/IN 14 DAYS", 'K', 'E', 'percent'),
    ('Case Activity', "NUM. CASES NOT IX'D", 'L', 'A', 'percent'),
    ('Case Activity', 'REFUSED', 'M', 'L', 'percent'),
    ('Case Activity', 'NO LOCATE', 'N', 'L', 'percent'),
    ('Case Activity', 'OTHER', 'O', 'L', 'percent'),
    ('Interview Activity', "NUM. OF OI'S", 'P', None, None),
    ('Interview Activity', "OI'S THAT WERE NCI", 'Q', 'P', 'percent'),
    ('Interview Activity', 'PERIOD PARTNERS', 'R', 'P', 'ratio'),
    ('Interview Activity', 'PARTNERS INITIATED (OI)', 'S', 'P', 'ratio'),
    ('Interview Activity', "NUM. OF RI'S", 'T', None, None),
    ('Interview Activity', "RI'S THAT WERE NCI", 'U', 'T', 'percent'),
    ('Interview Activity', 'PARTNERS INITIATED (RI)', 'V', 'T', 'ratio'),
    ('Interview Activity', 'CLUSTERS INIT (OI & RI)', 'W', 'P', 'ratio'),
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


def _rate(numerator: int, denominator: int, rate_type: str | None) -> float | None:
    if rate_type is None or denominator == 0:
        return None
    value = numerator / denominator
    return round(value, 3 if rate_type == 'percent' else 1)


def _sorted_worker_keys(keys: set[WorkerKey]) -> list[WorkerKey]:
    return sorted(keys, key=lambda key: ((key[0] or ''), key[1] or 0))


def _distinct_count_map(source: dict[WorkerKey, set]) -> dict[WorkerKey, int]:
    return {key: len(values) for key, values in source.items()}


def _activity_query(subset_query: str) -> str:
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
        std_cases.INVESTIGATION_KEY,
        std_cases.INVESTIGATOR_INTERVIEW_KEY,
        std_cases.INVESTIGATOR_INTERVIEW_QC,
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
    * Returns a long-form summary table instead of reproducing the SAS PDF/template.
    * The `Rate` column contains both percent-style ratios and average-style ratios,
      and `Rate Type` indicates how to interpret it.
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

    for row in activity_rows:
        (
            inv_local_id,
            interview_local_id,
            ix_type,
            ix_location,
            _investigation_key,
            investigator_interview_key,
            _investigator_interview_qc,
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
            case_sets['A'][worker_key].add(inv_local_id)
            if cc_closed_dt is None:
                case_sets['B'][worker_key].add(inv_local_id)
            else:
                case_sets['C'][worker_key].add(inv_local_id)

            if patient_interview_status == PENDING_STATUS:
                case_sets['D'][worker_key].add(inv_local_id)
            if patient_interview_status == INTERVIEWED_STATUS:
                case_sets['E'][worker_key].add(inv_local_id)
            if patient_interview_status in {
                OTHER_STATUS,
                REFUSED_STATUS,
                UNABLE_TO_LOCATE_STATUS,
            }:
                case_sets['L'][worker_key].add(inv_local_id)
            if patient_interview_status == REFUSED_STATUS:
                case_sets['M'][worker_key].add(inv_local_id)
            if patient_interview_status == UNABLE_TO_LOCATE_STATUS:
                case_sets['N'][worker_key].add(inv_local_id)
            if patient_interview_status == OTHER_STATUS:
                case_sets['O'][worker_key].add(inv_local_id)

            if ix_type == INITIAL_INTERVIEW and ix_location == 'Clinic':
                case_sets['F'][worker_key].add(inv_local_id)
            if ix_type == INITIAL_INTERVIEW and ix_location == 'Field':
                case_sets['G'][worker_key].add(inv_local_id)

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

    pp_seen_local_ids: set[str] = set()
    for row in activity_rows:
        (
            _inv_local_id,
            interview_local_id,
            ix_type,
            _ix_location,
            _investigation_key,
            investigator_interview_key,
            _investigator_interview_qc,
            _ix_date,
            _assign_dt,
            provider_quick_code,
            _cc_closed_dt,
            patient_interview_status,
            std_prtnrs_prd_trnsgndr_ttl,
            soc_prtnrs_prd_fml_ttl,
            soc_prtnrs_prd_male_ttl,
        ) = row
        if (
            interview_local_id is None
            or interview_local_id in pp_seen_local_ids
            or ix_type != INITIAL_INTERVIEW
            or patient_interview_status != INTERVIEWED_STATUS
        ):
            continue

        pp_seen_local_ids.add(interview_local_id)
        worker_key = (provider_quick_code, investigator_interview_key)
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
                interview_sets['P'][worker_key].add(d_interview_key)
                if d_contact_record_key in (None, 1):
                    interview_sets['Q'][worker_key].add(d_interview_key)
            elif ix_type == REINTERVIEW:
                interview_sets['T'][worker_key].add(d_interview_key)
                if d_contact_record_key in (None, 1):
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
                contact_sets['S'][worker_key].add(contact_key)
            elif ix_type == REINTERVIEW:
                contact_sets['V'][worker_key].add(contact_key)
        if (
            ctt_referral_basis in CLUSTER_BASES
            and ix_type in {INITIAL_INTERVIEW, REINTERVIEW}
        ):
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
        for _, _, metric_key, _, _ in METRICS
    }

    rows: list[Pa05Row] = []
    for metric_group, metric_label, metric_key, denominator_key, rate_type in METRICS:
        count = overall_counts.get(metric_key, 0)
        denominator = overall_counts.get(denominator_key, 0) if denominator_key else 0
        rows.append(
            (
                'Overall',
                None,
                None,
                metric_group,
                metric_label,
                count,
                _rate(count, denominator, rate_type),
                rate_type,
            )
        )

    for provider_quick_code, investigator_interview_key in worker_keys:
        worker_key = (provider_quick_code, investigator_interview_key)
        for (
            metric_group,
            metric_label,
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
            rows.append(
                (
                    'Worker',
                    provider_quick_code,
                    investigator_interview_key,
                    metric_group,
                    metric_label,
                    count,
                    _rate(count, denominator, rate_type),
                    rate_type,
                )
            )

    content = Table(
        columns=[
            'Scope',
            'Provider Quick Code',
            'Investigator Interview Key',
            'Metric Group',
            'Metric',
            'Count',
            'Rate',
            'Rate Type',
        ],
        data=rows,
    )

    return ReportResult(content_type='table', content=content)
