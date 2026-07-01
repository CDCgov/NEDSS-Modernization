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
GO

INSERT INTO [dbo].[NBS_configuration] (
    config_key,
    config_value,
    short_name,
    desc_txt,
    default_value,
    valid_values,
    category,
    add_release,
    version_ctrl_nbr,
    add_user_id,
    add_time,
    last_chg_user_id,
    last_chg_time,
    status_cd,
    status_time,
    admin_comment,
    system_usage
)
SELECT
    src.config_key,
    src.config_value,
    src.short_name,
    src.desc_txt,
    src.default_value,
    src.valid_values,
    src.category,
    src.add_release,
    src.version_ctrl_nbr,
    src.add_user_id,
    src.add_time,
    src.last_chg_user_id,
    src.last_chg_time,
    src.status_cd,
    src.status_time,
    src.admin_comment,
    src.system_usage
FROM (
     VALUES
     (
     'REPORT_DB_NBS_RDB',
     'RDB',
     'RDB Database Name',
     'Database name or catalog alias for the relational tracking DB.',
     'RDB',
     'Database name for RDB',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Initial provisioning for report execution routing metadata.',
     NULL
     ),
     (
     'REPORT_DB_NBS_ODS',
     'NBS_ODSE',
     'ODS Database Name',
     'Database name or catalog alias for the Operational Data Store.',
     'NBS_ODSE',
     'Database name for ODS',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Initial provisioning for report execution routing metadata.',
     NULL
     ),
     (
     'REPORT_DB_NBS_SRT',
     'NBS_SRTE',
     'SRT Database Name',
     'Database name or catalog alias for the reference tables database.',
     'NBS_SRTE',
     'Database name for SRT',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Initial provisioning for report execution routing metadata.',
     NULL
     ),
     (
     'REPORT_DB_NBS_MSGOUT',
     'NBS_MSGOUTE',
     'MSG Database Name',
     'Database name or catalog alias for the message out database.',
     'NBS_MSGOUTE',
     'Database name for MSGOUT',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Initial provisioning for report execution routing metadata.',
     NULL
     ),
     (
     'REPORT_MAX_ROW_LIMIT_EXPORT',
     '100000',
     'Max Row Limit for Report Export',
     'Maximum row threshold enforced during report data exports.',
     '100000',
     'Positive integer value',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Governs safety boundaries against massive tabular data exports.',
     NULL
     ),
     (
     'REPORT_MAX_ROW_LIMIT_RUN',
     '10000',
     'Max Row Limit for Report Run',
     'Maximum row threshold enforced during active dashboard queries.',
     '10000',
     'Positive integer value',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Governs safety boundaries against interactive runtime overflows.',
     NULL
     ),
     (
     'REPORT_EXPORT_DATE_FORMAT',
     '%m/%d/%Y',
     'Report Export Date Format',
     'Python strftime template used when sanitizing Date elements.',
     '%m/%d/%Y',
     'Strftime Pattern String',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Defines localized formatting target for date fields.',
     NULL
     ),
     (
     'REPORT_EXPORT_DATETIME_FORMAT',
     '%m/%d/%Y %H:%M:%S',
     'Report Export Datetime Format',
     'Python strftime template used when sanitizing Datetime elements.',
     '%m/%d/%Y %H:%M:%S',
     'Strftime Pattern String',
     'RPT',
     '7.0.13',
     1,
     99999999,
     GETDATE(),
     99999999,
     GETDATE(),
     'A',
     GETDATE(),
     'Defines localized formatting target for datetime fields.',
     NULL
     )
    ) AS src (
    config_key,
    config_value,
    short_name,
    desc_txt,
    default_value,
    valid_values,
    category,
    add_release,
    version_ctrl_nbr,
    add_user_id,
    add_time,
    last_chg_user_id,
    last_chg_time,
    status_cd,
    status_time,
    admin_comment,
    system_usage
    )
WHERE NOT EXISTS (
    SELECT 1
    FROM [dbo].[NBS_configuration] AS config
    WHERE config.config_key = src.config_key
);
GO
