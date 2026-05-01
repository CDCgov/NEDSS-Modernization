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
    * Remove references to "Bar Graph" since export is a table
    * Did not sort by PATIENT_NAME
    * Did not convert NULL to empty string
    """

    sql_query = """
    SELECT
        shd.investigation_key as "INVESTIGATION_KEY",
        inv.add_user_id as "ADD_USER_ID",
        inv.provider_quick_code as "PROVIDER_QUICK_CODE",
        shd.patient_name as "PATIENT_NAME",
        shd.patient_age_reported as "PATIENT_AGE_REPORTED",
        shd.patient_sex as "PATIENT_SEX",
        shd.patient_race as "PATIENT_RACE"
        shd.diagnosis_cd as "DIAGNOSIS_CD",
        shd.field_record_number as "RECORD_FIELD_NUMBER",
        shd.investigator_interview_qc as "INVESTIGATOR_INTERVIEW_QC",
        CASE 
            WHEN dm.CC_CLOSED_DT IS NULL THEN 'Y' 
            ELSE 'N' 
        END AS Open_Status,
        CAST(shd.ca_interviewer_assign_dt as DATE) as "ASSIGNED_DT",
        CAST(shd.cc_closed_dt as DATE) as "CLOSED_DT"
    FROM rdb.dbo.std_hiv_datamart shd
        LEFT OUTER JOIN 
            (SELECT i.investigation_key, em.add_user_id, up.provider_quick_code
            FROM rdb.dbo.investigation i 
                JOIN rdb.dbo.event_metric em 
                    ON i.case_uid = em.event_uid
                JOIN rdb.dbo.user_profile up
                    ON em.add_user_id = up.nedss_entry_id) inv
        ON shd.investigation_key = inv.investigation_key
    WHERE shd.ca_patient_inv_status = 'I - Interviewed'
        AND shd.diagnosis_cd IS NOT NULL
        AND shd.ix.date_oi IS NOT NULL
    """

    content = trx.query(sql_query)

    description = 'TODO'

    return ReportResult(
        content_type='table',
        content=content,
        subheader=None,
        description=description,
    )
