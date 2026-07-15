from src.config import get_cached_config_value
from src.db_transaction import Transaction
from src.models import ReportResult, Table

def execute(
    trx: Transaction,
    subset_query: str,
    **kwargs,
):
    """TB Case Verification Report.

    Conversion notes:
    """
    nbs_ods = get_cached_config_value('REPORT_DB_NBS_ODS')
    nbs_srt = get_cached_config_value('REPORT_DB_NBS_SRT')

    # pull column names from NBS_UI_METADATA
    metadata_query = f"""
      SELECT QUESTION_IDENTIFIER,
             RDB_COLUMN_NM,
             RDB_TABLE_NM,
             USER_DEFINED_COLUMN_NM,
             DATAMART_NM
      FROM {nbs_ods}.dbo.NBS_UI_METADATA num
        INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
        INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
        INNER JOIN {nbs_ods}.dbo.NBS_PAGE np
                ON np.FORM_CD = cc.INVESTIGATION_FORM_CD
      WHERE QUESTION_IDENTIFIER IN ('INV1115','INV1133','INV111')
      AND   CONDITION_CD IN ('102201');
    """
    metadata = trx.query(metadata_query)
    disease_site_desc = None
    case_verification_desc = None
    inv_rpt_dt = None

    for row in metadata.data:
        if row[0] == 'INV1133':
            disease_site_desc = row[3]
        elif row[0] == 'INV1115':
            case_verification_desc = row[3]
        elif row[0] == 'INV111':
            inv_rpt_dt = row[3]

    if None in [disease_site_desc, case_verification_desc, inv_rpt_dt]:
        raise ValueError('column name metadata missing from initial query')

    # TB_CASE_VER
    tb_case_ver_query = f"""
      WITH DM_INV_TB AS (
        {subset_query}
      ),
      CASE_VERIFIC_CODED AS
      (
        SELECT cvg.CODE AS CASE_VERIFICATION_CODE,
               cvg.CODE_SHORT_DESC_TXT AS CASE_VERIFICATION_CODE_DESC
        FROM {nbs_ods}.dbo.NBS_UI_METADATA num
          INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                  ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
          INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                  ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
          INNER JOIN {nbs_srt}.dbo.CODESET cs
                  ON cs.CODE_SET_GROUP_ID = num.CODE_SET_GROUP_ID
          INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                  ON cs.CODE_SET_NM = cvg.CODE_SET_NM
        WHERE QUESTION_IDENTIFIER IN ('INV1115')
        AND   CONDITION_CD IN ('102201')
      ),
      DISEASE_SITE_CODED AS
      (
        SELECT cvg.CODE AS DISEASE_CODE,
               cvg.CODE_SHORT_DESC_TXT AS DISEASE_CODE_DESC
        FROM {nbs_ods}.dbo.NBS_UI_METADATA num
          INNER JOIN {nbs_ods}.dbo.NBS_RDB_METADATA nrm
                  ON num.NBS_UI_METADATA_UID = nrm.NBS_UI_METADATA_UID
          INNER JOIN {nbs_srt}.dbo.CONDITION_CODE cc
                  ON cc.INVESTIGATION_FORM_CD = num.INVESTIGATION_FORM_CD
          INNER JOIN {nbs_srt}.dbo.CODESET cs
                  ON cs.CODE_SET_GROUP_ID = num.CODE_SET_GROUP_ID
          INNER JOIN {nbs_srt}.dbo.CODE_VALUE_GENERAL cvg
                  ON cs.CODE_SET_NM = cvg.CODE_SET_NM
        WHERE QUESTION_IDENTIFIER IN ('INV1133')
        AND   CONDITION_CD IN ('102201')
      ),
      TB_CASE_VER AS (
        SELECT COALESCE(NULLIF({disease_site_desc}, ''), 'Unknown')
                 AS DISEASE_SITE_DESC,
               {case_verification_desc} AS CASE_VERIFICATION_DESC,
               INVESTIGATION_KEY
        FROM DM_INV_TB
        WHERE DISEASE_CD = '102201'
        AND   {inv_rpt_dt} IS NOT NULL
      )
      SELECT dsc.DISEASE_CODE,
             dsc.DISEASE_CODE_DESC,
             cvc.CASE_VERIFICATION_CODE,
             tcv.CASE_VERIFICATION_DESC,
             tcv.DISEASE_SITE_DESC,
             tcv.INVESTIGATION_KEY,
             CASE
                 WHEN dsc.DISEASE_CODE IS NULL OR dsc.DISEASE_CODE = 'PHC5'
                   THEN 'Unknown'
                 WHEN dsc.DISEASE_CODE IN (
                        '23451007', '362102006', '53505006', '66754008',
                        '87612001', '59820001', '110522009', '14016003',
                        '12738006', '76752008', '17401000', '71854001',
                        '38848004', '110547006', '32849002', '16014003',
                        'PHC4', 'C0230999', '28231008', '80891009',
                        '110611003', '48477009', '10200004',
                        'PHC2', 'PHC3', '1231004', '110708006',
                        '123851003', '45206002', '71836000',
                        'OTH', '15776009', '120228005', '76848001',
                        '83670000', '54066008', '56329008',
                        '110973009', '34402009', '385294005',
                        '39937001', '2748008', '78961009',
                        '69695003', '21514008', '281777001',
                        '69831007', '25087005', '71966008',
                        '9875009', '297261005', '21974007',
                        '303337002', '44567001')
                   THEN 'Extrapulmonary TB'
                 WHEN dsc.DISEASE_CODE IN ('281778006', '39607008', '3120008')
                   THEN 'Pulmonary TB'
                 ELSE NULL
             END AS DISEASE_SITE_VALUE
      FROM TB_CASE_VER tcv
      LEFT JOIN CASE_VERIFIC_CODED cvc
             ON cvc.CASE_VERIFICATION_CODE_DESC = tcv.CASE_VERIFICATION_DESC
      LEFT JOIN DISEASE_SITE_CODED dsc
             -- nb. STRING_SPLIT not available per SQL Server compatibility level
             ON CHARINDEX(dsc.DISEASE_CODE_DESC, tcv.DISEASE_SITE_DESC) > 0
      ORDER BY INVESTIGATION_KEY;
    """
    tb_case_ver = trx.query(tb_case_ver_query)
    breakpoint()

    return ReportResult(content_type='table', content=None)
