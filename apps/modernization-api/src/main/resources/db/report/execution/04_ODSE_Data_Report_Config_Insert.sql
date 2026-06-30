/*
====================================================================================================
SCRIPT:       04_ODSE_Data_Report_Config_Init.sql
DESCRIPTION:  Seeds runtime configuration variables required by the report execution module
              into the `NBS_ODSE..NBS_Configuration` table

SCOPE:
  - REPORT_DB_* : Database catalog aliases used for routing reports dynamically.
  - REPORT_MAX_ROW_LIMIT_* : Maximum row thresholds enforced to prevent out-of-memory errors
                                during run or export lifecycles.

Insert If Not Exists. This script compares target records against existing database keys. If a
configuration key is already present, it skips insertion entirely.
====================================================================================================
*/

USE [NBS_ODSE]

INSERT INTO [dbo].[NBS_configuration] (
    config_key, config_value, default_value,
    version_ctrl_nbr, add_user_id, add_time,
    last_chg_user_id, last_chg_time, status_cd, status_time
)
SELECT
    src.config_key, src.config_value, src.default_value,
    src.version_ctrl_nbr, src.add_user_id, src.add_time,
    src.last_chg_user_id, src.last_chg_time, src.status_cd, src.status_time
FROM (
    VALUES
    ('REPORT_DB_NBS_RDB', 'RDB', 'RDB', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
    ('REPORT_DB_NBS_ODS', 'NBS_ODSE', 'NBS_ODSE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
    ('REPORT_DB_NBS_SRT', 'NBS_SRTE', 'NBS_SRTE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
    ('REPORT_DB_NBS_MSGOUT', 'NBS_MSGOUTE', 'NBS_MSGOUTE', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
    ('REPORT_MAX_ROW_LIMIT_EXPORT', '100000', '100000', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE()),
    ('REPORT_MAX_ROW_LIMIT_RUN', '10000', '10000', 1, 99999999, GETDATE(), 99999999, GETDATE(), 'A', GETDATE())
) AS src (
    config_key, config_value, default_value,
    version_ctrl_nbr, add_user_id, add_time,
    last_chg_user_id, last_chg_time, status_cd, status_time
)
WHERE NOT EXISTS (
    SELECT 1
    FROM [dbo].[NBS_configuration] AS config
    WHERE config.config_key = src.config_key
);
