from collections import defaultdict

from src.libraries.support.pa_04.models import (
    BUCKETS,
    EXAMINED_DISPOSITION_LABELS,
    EXAMINED_DISPOSITIONS,
    HIV_POSITIVE_RESULTS,
    NOT_EXAMINED_DISPOSITION_LABELS,
    NOT_EXAMINED_DISPOSITIONS,
    REINTERVIEW,
    SCOPES,
    Pa04Row,
)


def _sum_ignoring_none(*values: int | None) -> int | None:
    """Mirrors SAS's SUM() function: ignores missing arguments, only returning
    missing (None) if *every* argument is missing.
    """
    present = [value for value in values if value is not None]
    if not present:
        return None
    return sum(present)


def _percentage(numerator: int, denominator: int) -> str | None:
    """Format a fraction as a SAS `percent8.1`-style string, e.g. '78.1%'."""
    if denominator == 0:
        return None
    return f'{numerator / denominator * 100:.1f}%'


def _index(numerator: int, denominator: int, ndigits: int = 2) -> float | None:
    """Round a fraction, matching SAS's `round(val, 0.01)` / `round(val, 0.1)`."""
    if denominator == 0:
        return None
    return round(numerator / denominator, ndigits)


def build_case_metrics(
    case_rows: list[tuple],
) -> tuple[dict[str, int], list[Pa04Row]]:
    """Reproduces Val_A through Val_K (PA04_HIV.sas:60-123).

    KNOWN SAS QUIRK: Val_A and Val_B are computed from the exact same
    unfiltered query in the source SAS (lines 61-66) -- 'Cases Closed' and
    'Cases Interviewed' are always equal. Preserved as two rows (not merged)
    so the output shape -- and the quirk -- matches the original report.
    """
    all_cases: set[str] = set()
    nci_cases: set[str] = set()
    reinterview_cases: set[str] = set()
    reinterview_nci_cases: set[str] = set()
    hiv_tested_cases: set[str] = set()
    hiv_positive_cases: set[str] = set()
    post_test_counseled_cases: set[str] = set()
    period_partner_totals: dict[str, int] = {}

    for row in case_rows:
        (
            inv_local_id,
            ix_type,
            contact_investigation_key,
            hiv_900_test_ind,
            hiv_900_result,
            hiv_post_test_900_counseling,
            std_prtnrs_trnsgndr_ttl,
            soc_prtnrs_fml_ttl,
            soc_prtnrs_male_ttl,
        ) = row
        if inv_local_id is None:
            continue

        all_cases.add(inv_local_id)  # Val_A/Val_B, PA04_HIV.sas:61-66
        if contact_investigation_key == 1:
            nci_cases.add(inv_local_id)  # Val_C, PA04_HIV.sas:68-71
        if ix_type == REINTERVIEW:
            reinterview_cases.add(inv_local_id)  # Val_D, PA04_HIV.sas:73-77
            if contact_investigation_key == 1:
                reinterview_nci_cases.add(inv_local_id)  # Val_E, :80-84
        if hiv_900_test_ind == 'Yes':
            hiv_tested_cases.add(inv_local_id)  # Val_G, PA04_HIV.sas:87-91
        if hiv_900_result in HIV_POSITIVE_RESULTS:
            hiv_positive_cases.add(inv_local_id)  # Val_H, PA04_HIV.sas:93-97
        if hiv_post_test_900_counseling == 'Yes':
            post_test_counseled_cases.add(inv_local_id)  # Val_I, :100-104

        count_q = _sum_ignoring_none(
            std_prtnrs_trnsgndr_ttl, soc_prtnrs_fml_ttl, soc_prtnrs_male_ttl
        )
        if count_q is not None:
            # distinct(inv_local_id, count_q) in the source (PA04_HIV.sas:
            # 106-111) collapses to one entry per case since count_q is
            # constant for a given case regardless of join fan-out.
            period_partner_totals[inv_local_id] = count_q

    val_a = len(all_cases)
    val_b = val_a
    val_c = len(nci_cases)
    val_d = len(reinterview_cases)
    val_e = len(reinterview_nci_cases)
    val_g = len(hiv_tested_cases)
    val_h = len(hiv_positive_cases)
    val_i = len(post_test_counseled_cases)
    val_j = sum(period_partner_totals.values())  # Val_J, PA04_HIV.sas:106-111

    totals = {'A': val_a, 'B': val_b}

    rows: list[Pa04Row] = [
        (None, None, 'Cases Closed', val_a, None, None),
        (None, None, 'Cases Interviewed', val_b, None, None),
        (None, None, 'Cases NCI', val_c, _percentage(val_c, val_b), None),
        (None, None, 'Cases Re-interviewed', val_d, _percentage(val_d, val_b), None),
        (
            None,
            None,
            'Cases NCI with Re-interview',
            val_e,
            _percentage(val_e, val_d),
            None,
        ),
        (None, None, 'Cases HIV Tested', val_g, _percentage(val_g, val_a), None),
        (
            None,
            None,
            'Cases HIV SeroPositive',
            val_h,
            _percentage(val_h, val_a),
            None,
        ),
        (
            None,
            None,
            'Cases PostTest Counseled',
            val_i,
            _percentage(val_i, val_g),
            None,
        ),
        (None, None, 'Total Period Partners', val_j, None, None),
        # Val_K, PA04_HIV.sas:123 -- rounded to 0.1, unlike every other index
        # in this report (which round to 0.01).
        (None, None, 'Period Partner Index', None, None, _index(val_j, val_b, 1)),
    ]
    return totals, rows


def build_bucket_metrics(
    contact_rows: list[tuple], index_rows: list[tuple], val_b: int
) -> list[Pa04Row]:
    """Reproduces the Partner ('P') and Cluster ('C') blocks, each computed
    for 3 scopes: Initial/Original (PA04_HIV.sas:291-500), Re-Interview
    (:681-890), and Combined (:989-1198).

    contact_rows: (contact_inv_local_id, ix_type, ctt_referral_basis,
    fl_fup_disposition) -- see queries.contact_query.
    index_rows: (case_inv_local_id, ix_type, ctt_referral_basis,
    fl_fup_disposition) -- see queries.index_query.
    """
    rows: list[Pa04Row] = []

    for bucket_label, bases in BUCKETS:
        bucket_contacts = [row for row in contact_rows if row[2] in bases]
        bucket_index = [row for row in index_rows if row[2] in bases]

        # KNOWN SAS QUIRK: pix/testindex (and pix1/testindex1 for the cluster
        # bucket) are identical datasets in HIV -- see queries.index_query --
        # so Notification Index and Testing Index are always the same value.
        index_by_ix_type: dict[str, set[str]] = defaultdict(set)
        for case_inv_local_id, ix_type, _basis, _disposition in bucket_index:
            if case_inv_local_id is not None and ix_type is not None:
                index_by_ix_type[ix_type].add(case_inv_local_id)

        for scope_label, ix_types in SCOPES:
            scoped = [row for row in bucket_contacts if row[1] in ix_types]

            initiated = {row[0] for row in scoped if row[0] is not None}
            examined = {
                row[0]
                for row in scoped
                if row[0] is not None and row[3] in EXAMINED_DISPOSITIONS
            }
            not_examined = {
                row[0]
                for row in scoped
                if row[0] is not None and row[3] in NOT_EXAMINED_DISPOSITIONS
            }

            val_m = len(initiated)
            val_n = len(examined)
            val_q = len(not_examined)

            # KNOWN SAS QUIRK (PA04_HIV.sas:1086-1092): the Combined scope
            # sums the already-grouped-by-IX_TYPE index counts rather than
            # taking a fresh distinct count across both IX_TYPEs, so a case
            # present under both Initial/Original and Re-Interview is
            # double-counted here, unlike every other Combined metric (which
            # re-queries as a single distinct count). Preserved as-is.
            index_sum = sum(
                len(index_by_ix_type.get(ix_type, set())) for ix_type in ix_types
            )

            rows.append(
                (
                    scope_label,
                    bucket_label,
                    'Initiated',
                    val_m,
                    _percentage(val_m, val_b),
                    None,
                )
            )
            rows.append(
                (
                    scope_label,
                    bucket_label,
                    'Examined',
                    val_n,
                    _percentage(val_n, val_m),
                    None,
                )
            )
            for code, label in EXAMINED_DISPOSITION_LABELS:
                count = len(
                    {row[0] for row in scoped if row[0] is not None and row[3] == code}
                )
                rows.append(
                    (
                        scope_label,
                        bucket_label,
                        label,
                        count,
                        _percentage(count, val_n),
                        None,
                    )
                )

            rows.append(
                (
                    scope_label,
                    bucket_label,
                    'Not Examined',
                    val_q,
                    _percentage(val_q, val_m),
                    None,
                )
            )
            for code, label in NOT_EXAMINED_DISPOSITION_LABELS:
                count = len(
                    {row[0] for row in scoped if row[0] is not None and row[3] == code}
                )
                rows.append(
                    (
                        scope_label,
                        bucket_label,
                        label,
                        count,
                        _percentage(count, val_q),
                        None,
                    )
                )

            rows.append(
                (
                    scope_label,
                    bucket_label,
                    'Notification Index',
                    None,
                    None,
                    _index(index_sum, val_b),
                )
            )
            rows.append(
                (
                    scope_label,
                    bucket_label,
                    'Testing Index',
                    None,
                    None,
                    _index(index_sum, val_b),
                )
            )

    return rows
