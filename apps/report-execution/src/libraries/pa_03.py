from src.db_transaction import Transaction
from src.models import ReportResult, Table

PARTNER_BASES = {
    'P1 - Partner, Sex',
    'P2 - Partner, Needle-Sharing',
    'P3 - Partner, Both',
}
SOCIAL_BASES = {
    'S1 - Social Contact 1',
    'S2 - Social Contact 2',
    'S3 - Social Contact 3',
}
ASSOCIATE_BASES = {
    'A1 - Associate 1',
    'A2 - Associate 2',
    'A3 - Associate 3',
}
VALID_PROCESSING_DECISIONS = {
    'Field Follow-up',
    'Secondary Referral',
    'Record Search Closure',
}
OUTCOME_CODES = ('I1', 'I2', 'I3', 'I4', 'I5', 'I6', 'I7')
VALID_REFERRAL_BASES = tuple(sorted(PARTNER_BASES | SOCIAL_BASES | ASSOCIATE_BASES))


def _sql_string_list(values: tuple[str, ...]) -> str:
    return ', '.join(f"'{value}'" for value in values)


def _case_local_ids(rows: list[tuple]) -> set[str]:
    # SAS counts distinct case local ids for Val_A / Val_G.
    return {row[0] for row in rows if row[0] is not None}


def _contact_local_ids(rows: list[tuple], referral_bases: set[str]) -> set[str]:
    # Contact-side counts are distinct concatenated contact+case ids, filtered to the
    # referral basis bucket and the three processing decisions PA03 keeps.
    return {
        row[0]
        for row in rows
        if row[0] is not None
        and row[3] in referral_bases
        and row[4] in VALID_PROCESSING_DECISIONS
    }


def _ips_contact_local_ids(rows: list[tuple], referral_bases: set[str]) -> set[str]:
    # IPS totals use the same contact buckets, but only for cases flagged with
    # INIT_FUP_INTERNET_FOLL_UP_CD = 'Y'.
    return {
        row[0]
        for row in rows
        if row[0] is not None
        and row[1] == 'Y'
        and row[3] in referral_bases
        and row[4] in VALID_PROCESSING_DECISIONS
    }


def _ips_both_contact_local_ids(
    rows: list[tuple], referral_bases: set[str]
) -> set[str]:
    # SAS Val_M / Val_N / Val_O require both the case and the contact to be flagged
    # for internet follow-up.
    return {
        row[0]
        for row in rows
        if row[0] is not None
        and row[1] == 'Y'
        and row[2] == 'Y'
        and row[3] in referral_bases
        and row[4] in VALID_PROCESSING_DECISIONS
    }


def _ips_outcome_counter(rows: list[tuple], referral_bases: set[str]) -> dict[str, int]:
    # SAS counts distinct concatenated contact ids within each outcome bucket, not
    # raw qualifying rows. Deduping here keeps the Python result aligned.
    outcome_ids: dict[str, set[str]] = {code: set() for code in OUTCOME_CODES}
    for row in rows:
        if (
            row[0] is not None
            and row[1] == 'Y'
            and row[2] == 'Y'
            and row[3] in referral_bases
            and row[4] in VALID_PROCESSING_DECISIONS
            and row[5] in OUTCOME_CODES
        ):
            outcome_ids[row[5]].add(row[0])

    return {code: len(ids) for code, ids in outcome_ids.items()}


def _ratio(numerator: int, denominator: int) -> float | None:
    if denominator == 0:
        return None
    return round(numerator / denominator, 4)


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    **kwargs,
):
    """PA03 STD Program Report: Internet Partner Services Report.

    Conversion notes:
    * Uses the subset_query built by modernization-api as the report's case population.
    * Keeps the SQL intentionally narrow and performs the SAS metric math in Python.
    * Returns a long-form table instead of reproducing the SAS PDF layout.
    """
    valid_referral_bases_sql = _sql_string_list(VALID_REFERRAL_BASES)

    cases_query = f"""
    WITH shd AS (
        {subset_query}
    )
    -- This matches the STD_HIV_DATAMART1 -> pa3_new case population used by SAS
    -- for Val_A and Val_G.
    SELECT DISTINCT
        shd.INV_LOCAL_ID,
        shd.INIT_FUP_INTERNET_FOLL_UP_CD
    FROM shd
    INNER JOIN RDB.DBO.INVESTIGATION i
        ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
    WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed');
    """

    contacts_query = f"""
    WITH shd AS (
        {subset_query}
    ),
    cases AS (
        -- Keep the case-side subset small, then join outward to contacts exactly
        -- once when building the pp03-equivalent rows.
        SELECT shd.INVESTIGATION_KEY, shd.INV_LOCAL_ID, shd.INIT_FUP_INTERNET_FOLL_UP_CD
        FROM shd
        INNER JOIN RDB.DBO.INVESTIGATION i
            ON i.INVESTIGATION_KEY = shd.INVESTIGATION_KEY
        WHERE i.INV_CASE_STATUS IN ('Probable', 'Confirmed')
    )
    -- The concatenated local id mirrors SAS cat(f.inv_local_id, a.inv_local_id),
    -- which is the distinct key used for contact counts.
    SELECT DISTINCT
        CONCAT(
            COALESCE(contacts.INV_LOCAL_ID, ''), COALESCE(cases.INV_LOCAL_ID, '')
        ) AS INV_LOCAL_ID,
        cases.INIT_FUP_INTERNET_FOLL_UP_CD,
        contacts.INIT_FUP_INTERNET_FOLL_UP_CD AS CON_INIT_FUP_INTERNET_FOLL_UP_CD,
        dcr.CTT_REFERRAL_BASIS,
        dcr.CTT_PROCESSING_DECISION,
        contacts.FL_FUP_INTERNET_OUTCOME_CD
    FROM cases
    INNER JOIN RDB.DBO.F_CONTACT_RECORD_CASE fcrc
        ON cases.INVESTIGATION_KEY = fcrc.SUBJECT_INVESTIGATION_KEY
    INNER JOIN RDB.DBO.STD_HIV_DATAMART contacts
        ON fcrc.CONTACT_INVESTIGATION_KEY = contacts.INVESTIGATION_KEY
    INNER JOIN RDB.DBO.D_CONTACT_RECORD dcr
        ON dcr.D_CONTACT_RECORD_KEY = fcrc.D_CONTACT_RECORD_KEY
       AND dcr.RECORD_STATUS_CD <> 'LOG_DEL'
    WHERE dcr.CTT_REFERRAL_BASIS IN (
        {valid_referral_bases_sql}
    );
    """

    case_rows = trx.query(
        cases_query
    ).data  # Equivalent input to SAS pa3_new for Val_A and Val_G.
    contact_rows = trx.query(
        contacts_query
    ).data  # Equivalent input to SAS pp03 for Val_B-Val_O and Val_Q1-Val_S7.

    all_cases = _case_local_ids(case_rows)  # See PA03.sas line 85 (val_a)
    ips_cases = {
        row[0] for row in case_rows if row[0] is not None and row[1] == 'Y'
    }  # See PA03.sas line 106 (val_G)

    partner_contacts = _contact_local_ids(
        contact_rows, PARTNER_BASES
    )  # see PA03.sas line 88 (val_b)
    social_contacts = _contact_local_ids(
        contact_rows, SOCIAL_BASES
    )  # see PA03.sas line 94 (val_c)
    associate_contacts = _contact_local_ids(
        contact_rows, ASSOCIATE_BASES
    )  # see PA03.sas line 100 (val_d)

    ips_partner_contacts = _ips_contact_local_ids(
        contact_rows, PARTNER_BASES
    )  # see PA03.sas line 112 (val_H)
    ips_social_contacts = _ips_contact_local_ids(
        contact_rows, SOCIAL_BASES
    )  # # see PA03.sas line 120 (val_I)
    ips_associate_contacts = _ips_contact_local_ids(
        contact_rows, ASSOCIATE_BASES
    )  # see PA03.sas line 128 (val_J)

    ips_partner_both = _ips_both_contact_local_ids(
        contact_rows, PARTNER_BASES
    )  # see PA03.sas line 136 (val_M)
    ips_social_both = _ips_both_contact_local_ids(
        contact_rows, SOCIAL_BASES
    )  # see PA03.sas line 143 (val_N)
    ips_associate_both = _ips_both_contact_local_ids(
        contact_rows, ASSOCIATE_BASES
    )  # see PA03.sas line 151 (val_O)

    outcome_counts = {
        'Sexual Contact:': _ips_outcome_counter(
            contact_rows, PARTNER_BASES
        ),  # see PA03.sas lines 158-212 (val_q*)
        'Social Contact:': _ips_outcome_counter(
            contact_rows, SOCIAL_BASES
        ),  # see PA03.sas lines 215-269 (val_r*)
        'Associate:': _ips_outcome_counter(
            contact_rows, ASSOCIATE_BASES
        ),  # see PA03.sas lines 271-325 (val_s*)
    }

    rows = [
        ('Total Number of Cases:', len(all_cases)),
        ('No. Cases w/Internet Follow-up:', len(ips_cases)),
        ("Total No. Partners Init'd:", len(partner_contacts)),
        ('Total No. Partners:', len(ips_partner_contacts)),
        ("Total No. Social Contacts Init'd:", len(social_contacts)),
        ('Total No. Social Contacts:', len(ips_social_contacts)),
        ("Total No. Associates Init'd:", len(associate_contacts)),
        ('Total No. Associates:', len(ips_associate_contacts)),
        ('Contact Index:', _ratio(len(partner_contacts), len(all_cases))),
        ('IPS Contact Index:', _ratio(len(ips_partner_contacts), len(ips_cases))),
        (
            'Cluster Index:',
            _ratio(len(social_contacts) + len(associate_contacts), len(all_cases)),
        ),
        (
            'IPS Cluster Index:',
            _ratio(
                len(ips_social_contacts) + len(ips_associate_contacts), len(ips_cases)
            ),
        ),
        ('Total No. IPS Partners:', len(ips_partner_both)),
        ('Total No. IPS Social Contacts:', len(ips_social_both)),
        ('Total No. IPS Associates:', len(ips_associate_both)),
    ]

    for label in ('Sexual Contact:', 'Social Contact:', 'Associate:'):
        counts = outcome_counts[label]
        for outcome_code in OUTCOME_CODES:
            rows.append(
                (
                    f'{label} {outcome_code}',
                    counts.get(outcome_code, 0),
                )
            )

    content = Table(
        columns=['LABEL', 'VALUE'],
        data=rows,
    )

    return ReportResult(content_type='table', content=content)
