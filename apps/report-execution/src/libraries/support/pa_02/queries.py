
def global_date_sql(subset_query: str) -> str:
    return f"""
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

def disp_keys_sql(subset_query: str) -> str:
    return f"""
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

def assignment_sql(
        subset_query: str,
        referral_in: str,
        examined_in: str,
        not_examined_in: str,
        specific_clauses: list[str],
        not_examined_clauses: list[str],
    ) -> str:
    return f"""
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
            COUNT(
                DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IS NOT NULL
                THEN base.INV_LOCAL_ID END
            ) AS var_h_p,
            COUNT(
                DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IN ({examined_in})
                THEN base.INV_LOCAL_ID END
            ) AS var_i_p,
            {", ".join(specific_clauses)},
            COUNT(
                DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IN ({not_examined_in})
                THEN base.INV_LOCAL_ID END
            ) AS var_t_p,
            {", ".join(not_examined_clauses)},
            COUNT(
                DISTINCT CASE WHEN base.FL_FUP_DISPOSITION IS NULL
                THEN base.INV_LOCAL_ID END
            ) AS var_ad_p,
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

def ae_sql(
        subset_query: str,
        referral_in: str,
        keys_in: str,
        min_dispo_str: str,
        max_dispo_str: str,
        min_assign_str: str,
        max_assign_str: str
    ) -> str:
    return f"""
    WITH a AS ({subset_query}),
    base_dispo AS (
        SELECT DISTINCT
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
            AND CAST(a.FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) >= CAST('{
                min_assign_str
            }' AS DATE)
            AND CAST(a.FL_FUP_INVESTIGATOR_ASSGN_DT AS DATE) <= CAST('{
                max_assign_str
            }' AS DATE)
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