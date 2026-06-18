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
    # Define disposition sets based on report_type
    if report_type.upper() == 'STD':
        examd_dispos = ["'A - Preventative Treatment'",
                        "'B - Refused Preventative Treatment'",
                        "'C - Infected, Brought to Treatment'",
                        "'D - Infected, Not Treated'",
                        "'F - Not Infected'"]
        dispo_labels = {
            '2': "'A - Preventative Treatment'",
            '3': "'B - Refused Preventative Treatment'",
            '4': "'C - Infected, Brought to Treatment'",
            '5': "'D - Infected, Not Treated'",
            '6': "'F - Not Infected'",
            '7': None,
            'E': "'E - Previously Treated for This Infection'"
        }
        not_examined_dispos = ["'G - Insufficient Info to Begin Investigation'",
                               "'H - Unable to Locate'",
                               "'J - Located, Not Examined and/or Interviewed'",
                               "'K - Sent Out Of Jurisdiction'",
                               "'L - Other'",
                               "'V - Domestic Violence Risk'",
                               "'X - Patient Deceased'",
                               "'Z - Previous Preventative Treatment'"]
        template_name = 'templatePA02'
        dispo_col_prefix = 'Dispo '
        dispo_codes = ['A','B','C','D','F','E']
    else:  # HIV
        examd_dispos = ["'2 - Prev. Neg, New Pos'",
                        "'3 - Prev. Neg, Still Neg'",
                        "'4 - Prev. Neg, No Test'",
                        "'5 - No Prev Test, New Pos'",
                        "'6 - No Prev Test, New Neg'",
                        "'7 - No Prev Test, No Test'"]
        dispo_labels = {
            '2': "'2 - Prev. Neg, New Pos'",
            '3': "'3 - Prev. Neg, Still Neg'",
            '4': "'4 - Prev. Neg, No Test'",
            '5': "'5 - No Prev Test, New Pos'",
            '6': "'6 - No Prev Test, New Neg'",
            '7': "'7 - No Prev Test, No Test'",
            'E': None
        }
        not_examined_dispos = ["'G - Insufficient Info to Begin Investigation'",
                               "'H - Unable to Locate'",
                               "'J - Located, Not Examined and/or Interviewed'",
                               "'K - Sent Out Of Jurisdiction'",
                               "'L - Other'",
                               "'V - Domestic Violence Risk'",
                               "'X - Patient Deceased'",
                               "'Z - Previous Preventative Treatment'"]
        template_name = 'templatePA02_HIV'
        dispo_col_prefix = 'Dispo '
        dispo_codes = ['2','3','4','5','6','7']

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
        ), pa02_dt as (
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
            from a 
            inner join [rdb].[dbo].INVESTIGATION b on 
            a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
            inner join [rdb].[dbo].D_PROVIDER c on
            a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
            where a.INVESTIGATOR_FL_FUP_KEY != 1 
            and a.FL_FUP_DISPOSITION >= a.FL_FUP_INVESTIGATOR_ASSGN_DT
            order by a.INVESTIGATOR_FL_FUP_KEY
    )
    select distinct * 
from pa02  
where REFERRAL_BASIS in ('P1 - Partner, Sex', 'P2 - Partner, Needle-Sharing','P3 - Partner, Both') ; 
    '''
    content = trx.query(sql)

    return ReportResult(content_type='table', content=content)
