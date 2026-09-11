/**
* Remove all reports that use a Report_Library row with the `runner` column set to `sas`.
* This includes removal of the impacted reports' filter values, filter validation rows,
* report filters, sort columns, and display columns before removing the reports and
* their Report_Library rows.
*/
USE [NBS_ODSE]

-- Drop impacted filter values
DELETE fv FROM dbo.Filter_Value fv
    LEFT JOIN dbo.Report_Filter rf ON fv.report_filter_uid = rf.report_filter_uid
    LEFT JOIN dbo.Report r ON rf.report_uid = r.report_uid
        AND rf.data_source_uid = r.data_source_uid
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Drop impacted report filter validation rows
DELETE rfv FROM dbo.Report_Filter_Validation rfv
    LEFT JOIN dbo.Report_Filter rf ON rfv.report_filter_uid = rf.report_filter_uid
    LEFT JOIN dbo.Report r ON rf.report_uid = r.report_uid
        AND rf.data_source_uid = r.data_source_uid
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Drop impacted report filters
DELETE rf FROM dbo.Report_Filter rf
    LEFT JOIN dbo.Report r ON rf.report_uid = r.report_uid
        AND rf.data_source_uid = r.data_source_uid
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Drop impacted report sort columns
DELETE rsc FROM dbo.Report_Sort_Column rsc
    LEFT JOIN dbo.Report r ON rsc.report_uid = r.report_uid
        AND rsc.data_source_uid = r.data_source_uid
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Drop impacted display columns
DELETE dc FROM dbo.Display_Column dc
    LEFT JOIN dbo.Report r ON dc.report_uid = r.report_uid
        AND dc.data_source_uid = r.data_source_uid
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Drop impacted reports
DELETE r FROM dbo.Report r
    LEFT JOIN dbo.Report_Library rl ON r.library_uid = rl.library_uid
        WHERE rl.runner = 'sas';

-- Finally, drop SAS report libraries
DELETE rl FROM dbo.Report_Library rl WHERE rl.runner = 'sas';