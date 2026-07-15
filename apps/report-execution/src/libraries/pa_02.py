from src.db_transaction import Transaction
from src.libraries.support.pa_02 import constants, queries
from src.models import ReportResult, Table


def execute(
    trx: Transaction,
    subset_query: str,
    data_source_name: str,
    library_params: dict,
    **kwargs,
) -> ReportResult:
    """PA02: Field Investigation Outcomes - STD and HIV.
    Conversion notes:
    * The SAS implementation of this report contains a known precision issue:
    The global min/max datetimes are stored in macro variables using `SELECT ... INTO`.
    When SAS resolves these numeric datetimes into SQL, it writes them in scientific
    notation (e.g., `2.0197E9`). This rounding effectively shifts the date boundaries
    by several hours and can add or remove a full day from the filter range.
    Only the `Non-assigned Dispos` (`var_ae_p`) metric is affected, as it is the
    only count filtered using these macro variable boundaries in the `PA02_DISPO` table.
    This Python implementation avoids the rounding by using exact date strings
    (e.g., `CAST(... AS DATE) >= CAST('YYYY-MM-DD' AS DATE)`) for all date filters.
    Therefore, the Python output reflects the intended business logic and should be
    treated as the correct reference. Minor discrepancies in `var_ae_p` between
    Python and legacy SAS outputs are expected and documented.
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
    # 2. Define disposition sets
    # ------------------------------------------------------------------
    if report_type == 'STD':
        examined_dispos = constants.STD_EXAMINED_DISPOS
        not_examined_dispos = constants.STD_NOT_EXAMINED_DISPOS
        specific_var_map = constants.STD_SPECIFIC_VAR_MAP
        specific_var_order = constants.STD_SPECIFIC_VAR_ORDER
    else:
        examined_dispos = constants.HIV_EXAMINED_DISPOS
        not_examined_dispos = constants.HIV_NOT_EXAMINED_DISPOS
        specific_var_map = constants.HIV_SPECIFIC_VAR_MAP
        specific_var_order = constants.HIV_SPECIFIC_VAR_ORDER

    referral_groups = constants.REFERRAL_GROUPS
    not_examined_var_map = constants.NOT_EXAMINED_VAR_MAP
    not_examined_var_order = constants.NOT_EXAMINED_VAR_ORDER

    # ------------------------------------------------------------------
    # 3. Compute global min/max dates from the unfiltered base
    # ------------------------------------------------------------------
    global_date_sql = queries.global_date_sql(subset_query)
    date_result = trx.query(global_date_sql)
    if not date_result.data:
        return ReportResult(
            content_type='table',
            content=Table(
                columns=[
                    'PROVIDER_QUICK_CODE_new',
                    'colname',
                    'colval',
                    'colval2',
                    'colval3',
                    'colval4',
                    'pname_l',
                    'i',
                ],
                data=[],
            ),
        )

    min_dispo, max_dispo, min_assign, max_assign = date_result.data[0]

    def to_date_str(dt):
        if dt is None:
            return None
        if hasattr(dt, 'date'):
            dt = dt.date()
        return dt.strftime('%Y-%m-%d')

    min_dispo_str = to_date_str(min_dispo)
    max_dispo_str = to_date_str(max_dispo)
    min_assign_str = to_date_str(min_assign)
    max_assign_str = to_date_str(max_assign)

    # Get the set of INVESTIGATOR_DISP_FL_FUP_KEY that appear in the base (unfiltered)
    disp_keys_sql = queries.disp_keys_sql(subset_query)
    keys_result = trx.query(disp_keys_sql)
    disp_keys = [row[0] for row in keys_result.data]
    if not disp_keys:
        return ReportResult(
            content_type='table',
            content=Table(
                columns=[
                    'PROVIDER_QUICK_CODE_new',
                    'colname',
                    'colval',
                    'colval2',
                    'colval3',
                    'colval4',
                    'pname_l',
                    'i',
                ],
                data=[],
            ),
        )
    keys_in = ', '.join(str(k) for k in disp_keys)

    # ------------------------------------------------------------------
    # 4. Helper to build SQL for one referral group
    # ------------------------------------------------------------------
    def build_group_sql(
        group_name: str, referral_list: list[str]
    ) -> tuple[str, str | None]:
        def fmt_list(lst):
            return ', '.join(f"'{item}'" for item in lst)

        referral_in = fmt_list(referral_list)
        examined_in = fmt_list(examined_dispos)
        not_examined_in = fmt_list(not_examined_dispos)

        specific_clauses = []
        for var_name, dispo_str in specific_var_map.items():
            specific_clauses.append(
                f"""
                COUNT(
                    DISTINCT CASE WHEN base.FL_FUP_DISPOSITION = '{dispo_str}'
                    THEN base.INV_LOCAL_ID END
                ) AS {var_name}
                """
            )
        not_examined_clauses = []
        for var_name, dispo_str in not_examined_var_map.items():
            not_examined_clauses.append(
                f"""
                COUNT(
                    DISTINCT CASE WHEN base.FL_FUP_DISPOSITION = '{dispo_str}'
                    THEN base.INV_LOCAL_ID END
                ) AS {var_name}
                """
            )

        # Assignment metrics SQL – unchanged
        assignment_sql = queries.assignment_sql(
            subset_query=subset_query,
            referral_in=referral_in,
            examined_in=examined_in,
            not_examined_in=not_examined_in,
            specific_clauses=specific_clauses,
            not_examined_clauses=not_examined_clauses,
        )

        # Non‑assigned dispositions
        if (
            min_dispo_str is not None
            and max_dispo_str is not None
            and min_assign_str is not None
            and max_assign_str is not None
        ):
            ae_sql = queries.ae_sql(
                subset_query=subset_query,
                referral_in=referral_in,
                keys_in=keys_in,
                min_dispo_str=min_dispo_str,
                max_dispo_str=max_dispo_str,
                min_assign_str=min_assign_str,
                max_assign_str=max_assign_str,
            )
        else:
            ae_sql = None

        return assignment_sql, ae_sql

    # ------------------------------------------------------------------
    # 5. Execute queries for all groups and merge results
    # ------------------------------------------------------------------
    provider_data = {}

    for group_name, referral_list in referral_groups.items():
        assign_sql, ae_sql = build_group_sql(group_name, referral_list)

        assign_result = trx.query(assign_sql)
        ae_result = trx.query(ae_sql) if ae_sql else None

        assign_columns = assign_result.columns
        assign_rows = assign_result.data

        if ae_result:
            ae_columns = ae_result.columns
            ae_rows = ae_result.data
        else:
            ae_rows = None
            ae_columns = None

        assign_col_idx = {col: i for i, col in enumerate(assign_columns)}
        if ae_columns:
            ae_col_idx = {col: i for i, col in enumerate(ae_columns)}
        else:
            ae_col_idx = None

        for row in assign_rows:
            provider_key = row[assign_col_idx['INVESTIGATOR_FL_FUP_KEY']]
            quick_code = row[assign_col_idx['PROVIDER_QUICK_CODE']]
            key = (provider_key, quick_code)
            if key not in provider_data:
                provider_data[key] = {'PROVIDER_QUICK_CODE': quick_code}
            provider_data[key][group_name] = {}
            var_names = (
                [
                    'var_g_p',
                    'var_h_p',
                    'var_i_p',
                    'var_j_p',
                    'var_k_p',
                    'var_l_p',
                    'var_m_p',
                    'var_t_p',
                    'var_ad_p',
                ]
                + specific_var_order
                + not_examined_var_order
            )
            for var in var_names:
                if var in assign_col_idx:
                    provider_data[key][group_name][var] = row[assign_col_idx[var]]
                else:
                    provider_data[key][group_name][var] = 0

        if ae_rows and ae_col_idx:
            for row in ae_rows:
                provider_key = row[ae_col_idx['INVESTIGATOR_FL_FUP_KEY']]
                quick_code = row[ae_col_idx['PROVIDER_QUICK_CODE']]
                key = (provider_key, quick_code)
                if key not in provider_data:
                    provider_data[key] = {'PROVIDER_QUICK_CODE': quick_code}
                if group_name not in provider_data[key]:
                    provider_data[key][group_name] = {}
                provider_data[key][group_name]['var_ae_p'] = row[ae_col_idx['var_ae_p']]

    if not provider_data:
        return ReportResult(
            content_type='table',
            content=Table(
                columns=[
                    'PROVIDER_QUICK_CODE_new',
                    'colname',
                    'colval',
                    'colval2',
                    'colval3',
                    'colval4',
                    'pname_l',
                    'i',
                ],
                data=[],
            ),
        )

    # ------------------------------------------------------------------
    # 6. Metric labels
    # ------------------------------------------------------------------
    metric_labels = [
        ('Assigned:', 'var_g_p'),
        ('Dispositioned:', 'var_h_p'),
        ("Exam'd:", 'var_i_p'),
        ("Exam'd w/in 3:", 'var_j_p'),
        ("Exam'd w/in 5:", 'var_k_p'),
        ("Exam'd w/in 7:", 'var_l_p'),
        ("Exam'd w/in 14:", 'var_m_p'),
    ]

    if report_type == 'STD':
        std_specific_vars = ['var_n_p', 'var_o_p', 'var_p_p', 'var_q_p', 'var_r_p']
        for var_name in std_specific_vars:
            dispo_str = specific_var_map[var_name]
            letter = dispo_str.split(' - ')[0]
            metric_labels.append((f'Dispo {letter}:', var_name))
    else:
        for var_name in specific_var_order:
            dispo_str = specific_var_map[var_name]
            num = dispo_str.split(' - ')[0]
            metric_labels.append((f'Dispo {num}:', var_name))

    metric_labels.append(('Not Examined:', 'var_t_p'))
    for var_name in not_examined_var_order:
        dispo_str = not_examined_var_map[var_name]
        letter = dispo_str.split(' - ')[0]
        metric_labels.append((f'Dispo {letter}:', var_name))

    if report_type == 'STD':
        metric_labels.append(('Dispo E:', 'var_ac_p'))

    metric_labels.append(('Open:', 'var_ad_p'))
    metric_labels.append(('Non-assigned Dispos:', 'var_ae_p'))

    # ------------------------------------------------------------------
    # 7. Build final table (no ALL rows)
    # ------------------------------------------------------------------
    provider_rows = []
    for (_provider_key, quick_code), data in provider_data.items():
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

    metric_index = {label: idx for idx, (label, _) in enumerate(metric_labels)}

    def _quick_code_sort_key(row):
        quick_code = row['PROVIDER_QUICK_CODE_new']
        if quick_code is None:
            return ''
        return quick_code.lower()

    provider_rows.sort(
        key=lambda r: (_quick_code_sort_key(r), metric_index[r['colname']])
    )

    table_data = []
    for row in provider_rows:
        table_data.append(
            (
                row['PROVIDER_QUICK_CODE_new'],
                row['colname'],
                row['colval'],
                row['colval2'],
                row['colval3'],
                row['colval4'],
                _quick_code_sort_key(row),
                5,
            )
        )

    columns = [
        'PROVIDER_QUICK_CODE_new',
        'colname',
        'colval',
        'colval2',
        'colval3',
        'colval4',
        'pname_l',
        'i',
    ]

    return ReportResult(
        content_type='table', content=Table(columns=columns, data=table_data)
    )
