from src.db_transaction import Transaction
from src.models import ReportResult


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
):
    """
    PA02: Field Investigation Outcomes - STD and HIV.
    """
    content = trx.query(subset_query)
    if not isinstance(library_params, dict):
        raise ValueError(
            f'''
            library_params must be a dictionary with a "report_type" key indicating 
            either "STD" or "HIV", got {library_params} of type {type(library_params)}
            ''')
    report_type = library_params.get('report_type')
    if report_type not in ['STD', 'HIV']:
        raise ValueError(
            f'''
            report_type must be either "STD" or "HIV", got {report_type} of type
            {type(report_type)}
            '''
        )
    sql = f'''
    with a as ({subset_query}), pa02 as (
        SELECT DISTINCT
            a.INV_LOCAL_ID,
            b.REFERRAL_BASIS,
            a.FL_FUP_INVESTIGATOR_ASSGN_DT,
            a.FL_FUP_DISPO_DT,
            CAST(a.FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) AS assign_dt,
            a.FL_FUP_DISPOSITION,
            CAST(a.FL_FUP_DISPO_DT AS DATE) AS disp_dt,
            DATEDIFF(day, a.FL_FUP_INVESTIGATOR_ASSGN_DT, a.FL_FUP_DISPO_DT) AS days,
            a.INVESTIGATOR_FL_FUP_KEY,
            a.INVESTIGATOR_FL_FUP_QC,
            c.PROVIDER_QUICK_CODE,
            c.PROVIDER_FIRST_NAME,
            a.INVESTIGATOR_DISP_FL_FUP_KEY
        FROM a 
        INNER JOIN [RDB].[dbo].INVESTIGATION b
        ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        INNER JOIN [RDB].[dbo].D_PROVIDER c
        ON a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
        WHERE a.INVESTIGATOR_FL_FUP_KEY !=1
    ), min_max_dates AS (
        SELECT 
            MIN(FL_FUP_DISPO_DT) AS MIN_DISPO,
            MAX(FL_FUP_DISPO_DT) AS MAX_DISPO,
            MIN(FL_FUP_INVESTIGATOR_ASSGN_DT) AS MIN_ASSGN,
            MAX(FL_FUP_INVESTIGATOR_ASSGN_DT) AS MAX_ASSGN
        FROM pa02
    ), pa02_dispo as (
        select 
            a.INV_LOCAL_ID,
            b.REFERRAL_BASIS,
            a.FL_FUP_INVESTIGATOR_ASSGN_DT,
            a.FL_FUP_DISPO_DT,
            a.FL_FUP_DISPOSITION,
            a.INVESTIGATOR_FL_FUP_KEY,
            a.INVESTIGATOR_FL_FUP_QC,
            c.PROVIDER_QUICK_CODE,
            c.PROVIDER_FIRST_NAME,
            INVESTIGATOR_DISP_FL_FUP_KEY
        from a inner join [rdb].[dbo].investigation b on
        a.investigation_key = b.investigation_key
        inner join [rdb].[dbo].D_PROVIDER c
        on a.INVESTIGATOR_DISP_FL_FUP_KEY = c.PROVIDER_KEY
            WHERE INVESTIGATOR_DISP_FL_FUP_KEY in (select INVESTIGATOR_DISP_FL_FUP_KEY from PA02)
            AND INVESTIGATOR_DISP_FL_FUP_KEY != 1
            AND CAST(FL_FUP_DISPO_DT AS DATE) >= (SELECT MIN_DISPO FROM min_max_dates)
            AND CAST(FL_FUP_DISPO_DT AS DATE) <= (SELECT MAX_DISPO FROM min_max_dates)
            AND CAST(FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) >= (SELECT MIN_ASSGN FROM min_max_dates)
            AND CAST(FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) <= (SELECT MAX_ASSGN FROM min_max_dates)
        )
select distinct 
    a.INV_LOCAL_ID,
    b.REFERRAL_BASIS, 
    CAST(FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) as assign_dt,
    a.FL_FUP_DISPOSITION, 
    CAST(FL_FUP_DISPO_DT AS DATE) as disp_dt,
    DATEDIFF(day, a.FL_FUP_INVESTIGATOR_ASSGN_DT, a.FL_FUP_DISPO_DT) AS days,
    a.INVESTIGATOR_FL_FUP_KEY,
    a.INVESTIGATOR_FL_FUP_QC,
    c.PROVIDER_QUICK_CODE,
    c.PROVIDER_FIRST_NAME
    from subset a 
    inner join [rdb].[dbo].INVESTIGATION b on 
    a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
    inner join [rdb].[dbo].D_PROVIDER c on
    a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
    where a.INVESTIGATOR_FL_FUP_KEY ~=1 
    and a.FL_FUP_DISPOSITION >= a.FL_FUP_INVESTIGATOR_ASSGN_DT
    order by a.INVESTIGATOR_FL_FUP_KEY;       
    '''
    content = trx.query(sql)

    return ReportResult(content_type='table', content=content)
