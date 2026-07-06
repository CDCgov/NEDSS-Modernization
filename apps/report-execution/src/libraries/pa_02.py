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
    # 4. Helper to build SQL for one referral group
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
    # 5. Execute queries for all groups and merge results
    # ------------------------------------------------------------------
    provider_data = {}  # key: (INVESTIGATOR_FL_FUP_KEY, PROVIDER_QUICK_CODE) -> dict

    for group_name, referral_list in referral_groups.items():
        assign_sql, ae_sql = build_group_sql(group_name, referral_list)

        assign_result = trx.query(assign_sql)  # returns Table
        ae_result = trx.query(ae_sql)          # returns Table

        # Extract columns and data
        assign_columns = assign_result.columns
        assign_rows = assign_result.data       # list of tuples
        ae_columns = ae_result.columns
        ae_rows = ae_result.data

        # Build index maps for quick access
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
            # All variable names that appear in assignment query
            var_names = ['var_g_p', 'var_h_p', 'var_i_p', 'var_j_p', 'var_k_p', 'var_l_p', 'var_m_p',
                         'var_t_p', 'var_ad_p'] + specific_var_order + not_examined_var_order
            for var in var_names:
                if var in assign_col_idx:
                    provider_data[key][group_name][var] = row[assign_col_idx[var]]
                else:
                    provider_data[key][group_name][var] = 0

        # Process ae rows (var_ae_p only)
        for row in ae_rows:
            provider_key = row[ae_col_idx['INVESTIGATOR_FL_FUP_KEY']]
            quick_code = row[ae_col_idx['PROVIDER_QUICK_CODE']]
            key = (provider_key, quick_code)
            if key not in provider_data:
                provider_data[key] = {'PROVIDER_QUICK_CODE': quick_code}
            if group_name not in provider_data[key]:
                provider_data[key][group_name] = {}
            provider_data[key][group_name]['var_ae_p'] = row[ae_col_idx['var_ae_p']]

    # If no data, return empty Table with the expected columns
    if not provider_data:
        return ReportResult(
            content_type='table',
            content=Table(columns=['provider', 'metric', 'partners', 'clus', 'reac', 'cohort', 'total'], data=[])
        )

    # ------------------------------------------------------------------
    # 6. Define final metric labels
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
    # Add specific dispos
    for var_name in specific_var_order:
        dispo_str = specific_var_map[var_name]
        if report_type == 'STD':
            letter = dispo_str.split(" - ")[0]
            label = f"Dispo {letter}:"
        else:
            num = dispo_str.split(" - ")[0]
            label = f"Dispo {num}:"
        metric_labels.append((label, var_name))

    metric_labels.append(("Not Examined:", "var_t_p"))
    for var_name in not_examined_var_order:
        dispo_str = not_examined_var_map[var_name]
        letter = dispo_str.split(" - ")[0]
        metric_labels.append((f"Dispo {letter}:", var_name))

    metric_labels.append(("Open:", "var_ad_p"))
    metric_labels.append(("Non-assigned Dispos:", "var_ae_p"))

    # ------------------------------------------------------------------
    # 7. Build final rows: overall + per provider
    # ------------------------------------------------------------------
    group_names = list(referral_groups.keys())  # ['partners', 'clus', 'reac', 'cohort']
    all_rows = []  # list of dicts

    # Overall sums
    overall = {metric: {g: 0 for g in group_names} for metric, _ in metric_labels}

    # Provider rows
    for (provider_key, quick_code), data in provider_data.items():
        for metric_label, var_name in metric_labels:
            row = {'provider': quick_code, 'metric': metric_label}
            total = 0
            for g in group_names:
                val = data.get(g, {}).get(var_name, 0)
                row[g] = val
                total += val
                overall[metric_label][g] += val
            row['total'] = total
            all_rows.append(row)

    # Overall rows
    for metric_label, var_name in metric_labels:
        row = {'provider': 'ALL', 'metric': metric_label}
        total = 0
        for g in group_names:
            val = overall[metric_label][g]
            row[g] = val
            total += val
        row['total'] = total
        all_rows.append(row)

    # Sort: ALL first, then providers alphabetically, then metrics in defined order
    metric_index = {label: idx for idx, (label, _) in enumerate(metric_labels)}
    all_rows.sort(key=lambda r: (r['provider'] != 'ALL', r['provider'], metric_index[r['metric']]))

    # Convert to Table
    columns = ['provider', 'metric'] + group_names + ['total']
    table_data = [
        (
            row['provider'],
            row['metric'],
            row['partners'],
            row['clus'],
            row['reac'],
            row['cohort'],
            row['total']
        )
        for row in all_rows
    ]

    return ReportResult(
        content_type='table',
        content=Table(columns=columns, data=table_data)
    )