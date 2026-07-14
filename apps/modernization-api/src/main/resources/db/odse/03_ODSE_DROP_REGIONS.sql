USE [NBS_ODSE]

-- Drop report filters for "Regions" code
DECLARE @Region_Filter_Code_Id INT =
    (SELECT filter_uid FROM dbo.Filter_code WHERE filter_code = 'J_R01');

DELETE FROM dbo.Report_Filter WHERE filter_uid =
                                    (SELECT report_filter_uid from dbo.Report_Filter where filter_uid = @Region_Filter_Code_Id);

-- Drop report filters for "Regions (Including Nulls)"
DECLARE @Region_Filter_Code_Nulls_Id INT =
    (SELECT filter_uid FROM dbo.Filter_code WHERE filter_code = 'J_R01_N');

DELETE FROM dbo.Report_Filter WHERE filter_uid =
                                    (SELECT report_filter_uid FROM dbo.Report_Filter WHERE filter_uid = @Region_Filter_Code_Nulls_Id);

-- Drop both filter codes
DELETE FROM dbo.Filter_code WHERE filter_code IN ('J_R01', 'J_R01_N');