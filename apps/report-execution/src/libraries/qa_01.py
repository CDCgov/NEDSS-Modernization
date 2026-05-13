from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
        trx: Transaction,
        subset_query: str,
        data_source_name: str,
        **kwargs,
):

    """QA01 STD Program Report: Interview Record Listing

    Conversion notes:
    * Did not convert columns: name_l, race, sex, i, age
        * all dropped columns were either duplicate or remnants of errant SAS code
    * Did not sort by PATIENT_NAME
    * Did not convert NULL to empty string
    * Add IX_DATE_OI to the output
    """

    sql_query = f"""
    SELECT
        shd.investigation_key as "INVESTIGATION_KEY",
        inv.add_user_id as "ADD_USER_ID",
        inv.provider_quick_code as "PROVIDER_QUICK_CODE",
        shd.patient_name as "PATIENT_NAME",
        shd.patient_age_reported as "PATIENT_AGE_REPORTED",
        shd.patient_sex as "PATIENT_SEX",
        shd.patient_race as "PATIENT_RACE",
        shd.diagnosis_cd as "DIAGNOSIS_CD",
        shd.field_record_number as "RECORD_FIELD_NUMBER",
        shd.investigator_interview_qc as "INVESTIGATOR_INTERVIEW_QC",
        CASE
            WHEN shd.CC_CLOSED_DT IS NULL THEN 'Y'
            ELSE 'N'
        END AS Open_Status,
        CAST(shd.ca_interviewer_assign_dt as DATE) as "ASSIGNED_DT",
        CAST(shd.cc_closed_dt as DATE) as "CLOSED_DT",
        LOWER(shd.patient_name) as name_l,
        CASE
            WHEN shd.patient_race IS NOT NULL THEN 'XXX'
            ELSE ''
        END AS race,
        '' as sex,
        '13' as i,
        shd.patient_age_reported as age
    FROM ({subset_query}) shd
        LEFT OUTER JOIN
            (SELECT i.investigation_key, em.add_user_id, up.provider_quick_code
            FROM rdb.dbo.investigation i
                JOIN rdb.dbo.event_metric em
                    ON i.case_uid = em.event_uid
                JOIN rdb.dbo.user_profile up
                    ON em.add_user_id = up.nedss_entry_id) inv
        ON shd.investigation_key = inv.investigation_key
    WHERE shd.ca_patient_intv_status = 'I - Interviewed'
        AND shd.diagnosis_cd IS NOT NULL
        AND shd.ix_date_oi IS NOT NULL
    ORDER BY shd.patient_name
    """

    content = trx.query(sql_query)

    description = 'NEDSS STD Program Activity Report - QA01 Interview Record Listing Report'

    return ReportResult(
        content_type='table',
        content=content,
        subheader=None,
        description=description,
    )
