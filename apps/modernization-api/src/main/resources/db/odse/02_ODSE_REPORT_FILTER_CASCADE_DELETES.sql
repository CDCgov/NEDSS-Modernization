/**
  Alters the two ReportFilter foreign key constraints in the Report_Filter_Validation and Filter_value
  tables, specifically to cascade deletes from the Report_Filter table.
 */
USE [NBS_ODSE]

-- Cascade deletes from Report_Filter to Report_Filter_Validation
IF OBJECT_ID('dbo.FK_ReportFilter_ReportFilterValidation', 'F') IS NULL
BEGIN
    -- Remove legacy auto-gen'd constraint name
    ALTER TABLE dbo.Report_Filter_Validation
        DROP CONSTRAINT IF EXISTS FK_ReportFilter_ReportFilterUid;

    ALTER TABLE dbo.Report_Filter_Validation
        ADD CONSTRAINT FK_ReportFilter_ReportFilterValidation
            FOREIGN KEY (report_filter_uid) REFERENCES dbo.Report_Filter(report_filter_uid)
                ON DELETE CASCADE;
END

-- Cascade deletes from Report_Filter to Filter_value
IF OBJECT_ID('dbo.FK_ReportFilter_FilterValue', 'F') IS NULL
BEGIN
    -- Remove legacy auto-gen'd constraint name
    ALTER TABLE dbo.Filter_value
        DROP CONSTRAINT IF EXISTS FK__Filter_va__repor__70A8B9AE;

    ALTER TABLE dbo.Filter_value
        ADD CONSTRAINT FK_ReportFilter_FilterValue
            FOREIGN KEY (report_filter_uid) REFERENCES dbo.Report_Filter(report_filter_uid)
                ON DELETE CASCADE;
END