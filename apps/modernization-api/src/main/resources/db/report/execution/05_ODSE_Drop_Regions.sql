/**
* Remove all report filters and filter codes that use either the `Regions` or the
* `Regions (Including NULLS)` filter code.  This includes the removal of the impacted
* report filters' validation rules and filter values.
*/
USE [NBS_ODSE]

-- Drop relevant filter values
DELETE fv FROM dbo.Filter_Value fv
    LEFT JOIN Report_Filter rf ON fv.report_filter_uid = rf.report_filter_uid
    LEFT JOIN Filter_code fc ON rf.filter_uid = fc.filter_uid
         WHERE fc.filter_code IN ('J_R01', 'J_R01_N');

-- Drop relevant report filter validation rows
DELETE rfv FROM dbo.Report_Filter_Validation rfv
    LEFT JOIN Report_Filter rf ON rfv.report_filter_uid = rf.report_filter_uid
    LEFT JOIN Filter_code fc ON rf.filter_uid = fc.filter_uid
         WHERE fc.filter_code IN ('J_R01', 'J_R01_N');

-- Drop relevant report filters
DELETE rf FROM dbo.Report_Filter rf
    LEFT JOIN Filter_code fc ON rf.filter_uid = fc.filter_uid
         WHERE fc.filter_code IN ('J_R01', 'J_R01_N');

-- Finally, drop filter codes
DELETE FROM dbo.Filter_code WHERE filter_code IN ('J_R01', 'J_R01_N');