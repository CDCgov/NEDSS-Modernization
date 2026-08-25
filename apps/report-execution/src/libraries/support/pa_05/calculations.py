from collections import defaultdict
from datetime import date, datetime
from decimal import Decimal

from src.libraries.contact_record import PARTNER_BASES, VALID_PROCESSING_DECISIONS
from src.libraries.support.pa_05.models import (
    CLUSTER_BASES,
    INITIAL_INTERVIEW,
    INTERVIEWED_STATUS,
    METRICS,
    OTHER_STATUS,
    PENDING_STATUS,
    REFUSED_STATUS,
    REINTERVIEW,
    UNABLE_TO_LOCATE_STATUS,
    Pa05Row,
    WorkerKey,
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


def build_metric_counts(
    activity_rows: list[tuple], ixs_rows: list[tuple]
) -> dict[str, dict[WorkerKey, int]]:
    """Aggregates the activity and IXS_INIT-equivalent rows into per-metric,
    per-worker distinct counts (Var_A through Var_W).
    """
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
        if ctt_referral_basis in CLUSTER_BASES and ix_type in {
            INITIAL_INTERVIEW,
            REINTERVIEW,
        }:
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
        {metric: _distinct_count_map(contact_sets[metric]) for metric in contact_sets}
    )
    metric_counts['R'] = dict(period_partner_totals)

    return metric_counts


def build_rows(metric_counts: dict[str, dict[WorkerKey, int]]) -> list[Pa05Row]:
    """Builds the 'ALL' summary row block plus one row block per worker key,
    in METRICS order, computing each row's Percentage/Index from its
    metric/denominator counts.
    """
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

    return rows
