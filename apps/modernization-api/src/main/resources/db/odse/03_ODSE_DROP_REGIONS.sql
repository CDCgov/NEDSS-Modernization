USE [NBS_ODSE]

-- Drop all report filters with `Regions`/`Regions (Including NULLS)` filter codes
DELETE rf FROM dbo.Report_Filter rf
    LEFT JOIN Filter_code fc ON rf.filter_uid = fc.filter_uid
         WHERE fc.filter_code IN ('J_R01', 'J_R01_N');

-- Drop `Regions`/`Regions (Including NULLS)` filter codes
DELETE FROM dbo.Filter_code WHERE filter_code IN ('J_R01', 'J_R01_N');