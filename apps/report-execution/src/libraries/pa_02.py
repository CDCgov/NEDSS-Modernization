from src.db_transaction import Transaction
from src.models import ReportResult, Table
from typing import List, Tuple


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
) -> ReportResult:
    """
    PA02: Field Investigation Outcomes - STD and HIV.
    """
    # ------------------------------------------------------------------
    # 1. Validate input
    # ------------------------------------------------------------------
    if not isinstance(library_params, dict):
        raise ValueError(
            f"library_params must be a dictionary with a 'report_type' key "
            f"indicating either 'STD' or 'HIV', got {library_params}"
        )
    report_type = library_params.get('report_type')
    if report_type is None:
        raise ValueError("library_params must contain 'report_type'")
    if report_type not in ['STD', 'HIV']:
        raise ValueError(f"report_type must be 'STD' or 'HIV', got {report_type}")

    # ------------------------------------------------------------------
    # 2. Define disposition sets based on report_type
    # ------------------------------------------------------------------
    if report_type == 'STD':
        examined_dispos = [
            "A - Preventative Treatment",
            "B - Refused Preventative Treatment",
            "C - Infected, Brought to Treatment",
            "D - Infected, Not Treated",
            "F - Not Infected"
        ]
        not_examined_dispos = [
            "G - Insufficient Info to Begin Investigation",
            "H - Unable to Locate",
            "J - Located, Not Examined and/or Interviewed",
            "K - Sent Out Of Jurisdiction",
            "L - Other",
            "V - Domestic Violence Risk",
            "X - Patient Deceased",
            "Z - Previous Preventative Treatment"
        ]
        specific_var_map = {
            "var_n_p": "A - Preventative Treatment",
            "var_o_p": "B - Refused Preventative Treatment",
            "var_p_p": "C - Infected, Brought to Treatment",
            "var_q_p": "D - Infected, Not Treated",
            "var_r_p": "F - Not Infected",
            "var_ac_p": "E - Previously Treated for This Infection"
        }
        specific_var_order = ["var_n_p", "var_o_p", "var_p_p", "var_q_p", "var_r_p", "var_ac_p"]
    else:  # HIV
        examined_dispos = [
            "2 - Prev. Neg, New Pos",
            "3 - Prev. Neg, Still Neg",
            "4 - Prev. Neg, No Test",
            "5 - No Prev Test, New Pos",
            "6 - No Prev Test, New Neg",
            "7 - No Prev Test, No Test"
        ]
        not_examined_dispos = [
            "G - Insufficient Info to Begin Investigation",
            "H - Unable to Locate",
            "J - Located, Not Examined and/or Interviewed",
            "K - Sent Out Of Jurisdiction",
            "L - Other",
            "V - Domestic Violence Risk",
            "X - Patient Deceased",
            "Z - Previous Preventative Treatment"
        ]
        specific_var_map = {
            "var_n_p": "2 - Prev. Neg, New Pos",
            "var_o_p": "3 - Prev. Neg, Still Neg",
            "var_p_p": "4 - Prev. Neg, No Test",
            "var_q_p": "5 - No Prev Test, New Pos",
            "var_r_p": "6 - No Prev Test, New Neg",
            "var_s_p": "7 - No Prev Test, No Test"
        }
        specific_var_order = ["var_n_p", "var_o_p", "var_p_p", "var_q_p", "var_r_p", "var_s_p"]

    # Individual not examined codes (same for both types)
    not_examined_var_map = {
        "var_u_p": "G - Insufficient Info to Begin Investigation",
        "var_v_p": "H - Unable to Locate",
        "var_w_p": "J - Located, Not Examined and/or Interviewed",
        "var_x_p": "K - Sent Out Of Jurisdiction",
        "var_y_p": "L - Other",
        "var_z_p": "V - Domestic Violence Risk",
        "var_aa_p": "X - Patient Deceased",
        "var_ab_p": "Z - Previous Preventative Treatment"
    }
    not_examined_var_order = ["var_u_p", "var_v_p", "var_w_p", "var_x_p", "var_y_p", "var_z_p", "var_aa_p", "var_ab_p"]

    # ------------------------------------------------------------------
    # 3. Referral basis groups
    # ------------------------------------------------------------------
    referral_groups = {
        "partners": ["P1 - Partner, Sex", "P2 - Partner, Needle-Sharing", "P3 - Partner, Both"],
        "clus": ["A1 - Associate 1", "A2 - Associate 2", "A3 - Associate 3",
                 "S1 - Social Contact 1", "S2 - Social Contact 2", "S3 - Social Contact 3"],
        "reac": ["T1 - Positive Test", "T2 - Morbidity Report"],
        "cohort": ["C1- Cohort"]
    }

    # ------------------------------------------------------------------
    # 4. Compute global min/max dates from the unfiltered base
    # ------------------------------------------------------------------
    global_date_sql = f"""
    WITH a AS ({subset_query}),
    base AS (
        SELECT
            a.FL_FUP_INVESTIGATOR_ASSGN_DT,
            a.FL_FUP_DISPO_DT
        FROM a
        INNER JOIN [RDB].[dbo].INVESTIGATION b
            ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        WHERE a.INVESTIGATOR_FL_FUP_KEY != 1
    )
    SELECT
        MIN(FL_FUP_DISPO_DT) AS min_dispo,
        MAX(FL_FUP_DISPO_DT) AS max_dispo,
        MIN(FL_FUP_INVESTIGATOR_ASSGN_DT) AS min_assign,
        MAX(FL_FUP_INVESTIGATOR_ASSGN_DT) AS max_assign
    FROM base
    """
    date_result = trx.query(global_date_sql)
    if not date_result.data:
        # No data – return empty table
        return ReportResult(
            content_type='table',
            content=Table(columns=['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i'], data=[])
        )
    min_dispo, max_dispo, min_assign, max_assign = date_result.data[0]
    # Convert to date strings for SQL
    min_dispo_str = min_dispo.strftime('%Y-%m-%d') if hasattr(min_dispo, 'strftime') else str(min_dispo)
    max_dispo_str = max_dispo.strftime('%Y-%m-%d') if hasattr(max_dispo, 'strftime') else str(max_dispo)
    min_assign_str = min_assign.strftime('%Y-%m-%d') if hasattr(min_assign, 'strftime') else str(min_assign)
    max_assign_str = max_assign.strftime('%Y-%m-%d') if hasattr(max_assign, 'strftime') else str(max_assign)

    # Get the set of INVESTIGATOR_DISP_FL_FUP_KEY that appear in the base
    disp_keys_sql = f"""
    WITH a AS ({subset_query}),
    base AS (
        SELECT DISTINCT a.INVESTIGATOR_DISP_FL_FUP_KEY
        FROM a
        INNER JOIN [RDB].[dbo].INVESTIGATION b
            ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
        WHERE a.INVESTIGATOR_FL_FUP_KEY != 1
          AND a.INVESTIGATOR_DISP_FL_FUP_KEY IS NOT NULL
    )
    SELECT INVESTIGATOR_DISP_FL_FUP_KEY FROM base
    """
    keys_result = trx.query(disp_keys_sql)
    disp_keys = [row[0] for row in keys_result.data]
    if not disp_keys:
        return ReportResult(
            content_type='table',
            content=Table(columns=['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i'], data=[])
        )
    keys_in = ", ".join(str(k) for k in disp_keys)

    # ------------------------------------------------------------------
    # 5. Helper to build SQL for one referral group
    # ------------------------------------------------------------------
    def build_group_sql(group_name: str, referral_list: List[str]) -> Tuple[str, str]:
        """Returns (assignment_metrics_sql, ae_metrics_sql)."""
        def fmt_list(lst):
            return ", ".join(f"'{item}'" for item in lst)

        referral_in = fmt_list(referral_list)
        examined_in = fmt_list(examined_dispos)
        not_examined_in = fmt_list(not_examined_dispos)

        # Build specific dispo CASE clauses
        specific_clauses = []
        for var_name, dispo_str in specific_var_map.items():
            specific_clauses.append(
                f"COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION = '{dispo_str}' THEN base.INV_LOCAL_ID END) AS {var_name}"
            )

        # Build individual not examined CASE clauses
        not_examined_clauses = []
        for var_name, dispo_str in not_examined_var_map.items():
            not_examined_clauses.append(
                f"COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION = '{dispo_str}' THEN base.INV_LOCAL_ID END) AS {var_name}"
            )

        # Assignment metrics SQL
        assignment_sql = f"""
        WITH a AS ({subset_query}),
        base AS (
            SELECT
                a.INV_LOCAL_ID,
                b.REFERRAL_BASIS,
                a.FL_FUP_INVESTIGATOR_ASSGN_DT,
                a.FL_FUP_DISPO_DT,
                a.FL_FUP_DISPOSITION,
                a.INVESTIGATOR_FL_FUP_KEY,
                a.INVESTIGATOR_FL_FUP_QC,
                c.PROVIDER_QUICK_CODE,
                c.PROVIDER_FIRST_NAME,
                DATEDIFF(day, a.FL_FUP_INVESTIGATOR_ASSGN_DT, a.FL_FUP_DISPO_DT) AS days
            FROM a
            INNER JOIN [RDB].[dbo].INVESTIGATION b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
            INNER JOIN [RDB].[dbo].D_PROVIDER c
                ON a.INVESTIGATOR_FL_FUP_KEY = c.PROVIDER_KEY
            WHERE a.INVESTIGATOR_FL_FUP_KEY != 1
              AND b.REFERRAL_BASIS IN ({referral_in})
        ),
        base_dt AS (
            SELECT DISTINCT
                INV_LOCAL_ID,
                INVESTIGATOR_FL_FUP_KEY,
                days
            FROM base
            WHERE FL_FUP_DISPOSITION IN ({examined_in})
              AND days >= 0
        ),
        dt_agg AS (
            SELECT
                INVESTIGATOR_FL_FUP_KEY,
                COUNT(CASE WHEN days <= 3 THEN 1 END) AS var_j_p,
                COUNT(CASE WHEN days <= 5 THEN 1 END) AS var_k_p,
                COUNT(CASE WHEN days <= 7 THEN 1 END) AS var_l_p,
                COUNT(CASE WHEN days <= 14 THEN 1 END) AS var_m_p
            FROM base_dt
            GROUP BY INVESTIGATOR_FL_FUP_KEY
        )
        SELECT
            base.INVESTIGATOR_FL_FUP_KEY,
            base.PROVIDER_QUICK_CODE,
            COUNT(DISTINCT base.INV_LOCAL_ID) AS var_g_p,
            COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IS NOT NULL THEN base.INV_LOCAL_ID END) AS var_h_p,
            COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IN ({examined_in}) THEN base.INV_LOCAL_ID END) AS var_i_p,
            {", ".join(specific_clauses)},
            COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IN ({not_examined_in}) THEN base.INV_LOCAL_ID END) AS var_t_p,
            {", ".join(not_examined_clauses)},
            COUNT(DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IS NULL THEN base.INV_LOCAL_ID END) AS var_ad_p,
            COALESCE(dt.var_j_p, 0) AS var_j_p,
            COALESCE(dt.var_k_p, 0) AS var_k_p,
            COALESCE(dt.var_l_p, 0) AS var_l_p,
            COALESCE(dt.var_m_p, 0) AS var_m_p
        FROM base
        LEFT JOIN dt_agg dt ON base.INVESTIGATOR_FL_FUP_KEY = dt.INVESTIGATOR_FL_FUP_KEY
        GROUP BY
            base.INVESTIGATOR_FL_FUP_KEY,
            base.PROVIDER_QUICK_CODE,
            dt.var_j_p,
            dt.var_k_p,
            dt.var_l_p,
            dt.var_m_p
        """

        # Non-assigned dispositions (var_ae_p) - with global date filters and key restriction
        ae_sql = f"""
        WITH a AS ({subset_query}),
        base_dispo AS (
            SELECT
                a.INV_LOCAL_ID,
                a.INVESTIGATOR_FL_FUP_KEY,
                a.INVESTIGATOR_DISP_FL_FUP_KEY,
                a.FL_FUP_DISPOSITION,
                c.PROVIDER_QUICK_CODE
            FROM a
            INNER JOIN [RDB].[dbo].INVESTIGATION b
                ON a.INVESTIGATION_KEY = b.INVESTIGATION_KEY
            INNER JOIN [RDB].[dbo].D_PROVIDER c
                ON a.INVESTIGATOR_DISP_FL_FUP_KEY = c.PROVIDER_KEY
            WHERE a.INVESTIGATOR_DISP_FL_FUP_KEY != 1
              AND b.REFERRAL_BASIS IN ({referral_in})
              AND a.INVESTIGATOR_DISP_FL_FUP_KEY IN ({keys_in})
              AND CAST(a.FL_FUP_DISPO_DT AS DATE) >= CAST('{min_dispo_str}' AS DATE)
              AND CAST(a.FL_FUP_DISPO_DT AS DATE) <= CAST('{max_dispo_str}' AS DATE)
              AND CAST(a.FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) >= CAST('{min_assign_str}' AS DATE)
              AND CAST(a.FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) <= CAST('{max_assign_str}' AS DATE)
              AND a.INVESTIGATOR_DISP_FL_FUP_KEY != a.INVESTIGATOR_FL_FUP_KEY
              AND a.FL_FUP_DISPOSITION IS NOT NULL
        )
        SELECT
            INVESTIGATOR_DISP_FL_FUP_KEY AS INVESTIGATOR_FL_FUP_KEY,
            PROVIDER_QUICK_CODE,
            COUNT(DISTINCT INV_LOCAL_ID) AS var_ae_p
        FROM base_dispo
        GROUP BY INVESTIGATOR_DISP_FL_FUP_KEY, PROVIDER_QUICK_CODE
        """

        return assignment_sql, ae_sql

    # ------------------------------------------------------------------
    # 6. Execute queries for all groups and merge results
    # ------------------------------------------------------------------
    provider_data = {}  # key: (INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE) -> dict

    for group_name, referral_list in referral_groups.items():
        assign_sql, ae_sql = build_group_sql(group_name, referral_list)

        assign_result = trx.query(assign_sql)
        ae_result = trx.query(ae_sql)

        assign_columns = assign_result.columns
        assign_rows = assign_result.data
        ae_columns = ae_result.columns
        ae_rows = ae_result.data

        assign_col_idx = {col: i for i, col in enumerate(assign_columns)}
        ae_col_idx = {col: i for i, col in enumerate(ae_columns)}

        # Process assignment rows
        for row in assign_rows:
            provider_key = row[assign_col_idx['INVESTIGATOR_FL_FUP_KEY']]
            quick_code = row[assign_col_idx['PROVIDER_QUICK_CODE']]
            key = (provider_key, quick_code)
            if key not in provider_data:
                provider_data[key] = {'PROVIDER_QUICK_CODE': quick_code}
            provider_data[key][group_name] = {}
            var_names = ['var_g_p', 'var_h_p', 'var_i_p', 'var_j_p', 'var_k_p', 'var_l_p', 'var_m_p',
                         'var_t_p', 'var_ad_p'] + specific_var_order + not_examined_var_order
            for var in var_names:
                if var in assign_col_idx:
                    provider_data[key][group_name][var] = row[assign_col_idx[var]]
                else:
                    provider_data[key][group_name][var] = 0

        # Process ae rows
        for row in ae_rows:
            provider_key = row[ae_col_idx['INVESTIGATOR_FL_FUP_KEY']]
            quick_code = row[ae_col_idx['PROVIDER_QUICK_CODE']]
            key = (provider_key, quick_code)
            if key not in provider_data:
                provider_data[key] = {'PROVIDER_QUICK_CODE': quick_code}
            if group_name not in provider_data[key]:
                provider_data[key][group_name] = {}
            provider_data[key][group_name]['var_ae_p'] = row[ae_col_idx['var_ae_p']]

    # If no data, return empty table
    if not provider_data:
        return ReportResult(
            content_type='table',
            content=Table(columns=['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i'], data=[])
        )

    # ------------------------------------------------------------------
    # 7. Define final metric labels
    # ------------------------------------------------------------------
    metric_labels = [
        ("Assigned:", "var_g_p"),
        ("Dispositioned:", "var_h_p"),
        ("Exam'd:", "var_i_p"),
        ("Exam'd w/in 3:", "var_j_p"),
        ("Exam'd w/in 5:", "var_k_p"),
        ("Exam'd w/in 7:", "var_l_p"),
        ("Exam'd w/in 14:", "var_m_p"),
    ]

    # Specific dispositions (without E for STD, with all for HIV)
    if report_type == 'STD':
        # STD: A, B, C, D, F (no E yet)
        std_specific_vars = ['var_n_p', 'var_o_p', 'var_p_p', 'var_q_p', 'var_r_p']
        for var_name in std_specific_vars:
            dispo_str = specific_var_map[var_name]
            letter = dispo_str.split(" - ")[0]
            metric_labels.append((f"Dispo {letter}:", var_name))
    else:  # HIV
        for var_name in specific_var_order:
            dispo_str = specific_var_map[var_name]
            num = dispo_str.split(" - ")[0]
            metric_labels.append((f"Dispo {num}:", var_name))

    # Not Examined
    metric_labels.append(("Not Examined:", "var_t_p"))

    # Individual not examined (G, H, J, K, L, V, X, Z)
    for var_name in not_examined_var_order:
        dispo_str = not_examined_var_map[var_name]
        letter = dispo_str.split(" - ")[0]
        metric_labels.append((f"Dispo {letter}:", var_name))

    # For STD, add Dispo E after Z
    if report_type == 'STD':
        metric_labels.append(("Dispo E:", "var_ac_p"))

    # Open and Non-assigned
    metric_labels.append(("Open:", "var_ad_p"))
    metric_labels.append(("Non-assigned Dispos:", "var_ae_p"))

    # ------------------------------------------------------------------
    # 8. Build final rows: provider-level only (no ALL rows)
    # ------------------------------------------------------------------
    provider_rows = []
    for (provider_key, quick_code), data in provider_data.items():
        for metric_label, var_name in metric_labels:
            row = {
                'PROVIDER_QUICK_CODE_new': quick_code,
                'colname': metric_label,
                'colval': data.get('partners', {}).get(var_name, 0),
                'colval2': data.get('clus', {}).get(var_name, 0),
                'colval3': data.get('reac', {}).get(var_name, 0),
                'colval4': data.get('cohort', {}).get(var_name, 0),
            }
            provider_rows.append(row)

    # Sort by pname_l (lowercase provider) and metric order
    metric_index = {label: idx for idx, (label, _) in enumerate(metric_labels)}
    provider_rows.sort(key=lambda r: (r['PROVIDER_QUICK_CODE_new'].lower(), metric_index[r['colname']]))

    # Build final table data
    table_data = []
    for row in provider_rows:
        table_data.append((
            row['PROVIDER_QUICK_CODE_new'],
            row['colname'],
            row['colval'],
            row['colval2'],
            row['colval3'],
            row['colval4'],
            row['PROVIDER_QUICK_CODE_new'].lower(),
            5  # i constant - matches SAS
        ))

    columns = ['PROVIDER_QUICK_CODE_new', 'colname', 'colval', 'colval2', 'colval3', 'colval4', 'pname_l', 'i']

    return ReportResult(
        content_type='table',
        content=Table(columns=columns, data=table_data)
    )