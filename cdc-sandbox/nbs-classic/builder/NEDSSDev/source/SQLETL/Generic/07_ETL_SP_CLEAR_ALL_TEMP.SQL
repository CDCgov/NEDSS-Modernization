USE [RDB];
GO
SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_ADMINISTRATIVE]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.sp_CLEAR_INV_ADMINISTRATIVE;
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_ADMINISTRATIVE] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ADMINISTRATIVE', 
             'RDB.CLEAR_INV_ADMINISTRATIVE', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ADMINISTRATIVE_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ADMINISTRATIVE_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_ADMINISTRATIVE_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_ADMINISTRATIVE_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_ADMINISTRATIVE_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_ADMINISTRATIVE_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_ADMINISTRATIVE_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_ADMINISTRATIVE_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_ADMINISTRATIVE_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_ADMINISTRATIVE_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_ADMINISTRATIVE_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_ADMINISTRATIVE_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_ADMINISTRATIVE', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_ADMINISTRATIVE;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_ADMINISTRATIVE_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_ADMINISTRATIVE_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ADMINISTRATIVE', 
             'RDB.S_INV_ADMINISTRATIVE', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ADMINISTRATIVE', 
             'RDB.S_INV_ADMINISTRATIVE', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_CLINICAL]    Script Date: 1/16/2019 9:28:33 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_CLINICAL]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.sp_CLEAR_INV_CLINICAL;
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_CLINICAL] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CLINICAL', 
             'RDB.CLEAR_INV_CLINICAL', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CLINICAL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CLINICAL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_CLINICAL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_CLINICAL_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_CLINICAL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_CLINICAL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_CLINICAL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_CLINICAL_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_CLINICAL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_CLINICAL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_CLINICAL_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_CLINICAL_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_CLINICAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_CLINICAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_CLINICAL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_CLINICAL_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CLINICAL', 
             'RDB.S_INV_CLINICAL', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CLINICAL', 
             'RDB.S_INV_CLINICAL', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_COMPLICATION]    Script Date: 1/16/2019 9:28:34 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_COMPLICATION]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_COMPLICATION];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_COMPLICATION] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_COMPLICATION', 
             'RDB.CLEAR_INV_COMPLICATION', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_COMPLICATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_COMPLICATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_COMPLICATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_COMPLICATION_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_COMPLICATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_COMPLICATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_COMPLICATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_COMPLICATION_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_COMPLICATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_COMPLICATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_COMPLICATION_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_COMPLICATION_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_COMPLICATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_COMPLICATION;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_COMPLICATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_COMPLICATION_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_COMPLICATION', 
             'RDB.S_INV_COMPLICATION', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_COMPLICATION', 
             'RDB.S_INV_COMPLICATION', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_CONTACT]    Script Date: 1/16/2019 9:28:34 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_CONTACT]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_CONTACT];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_CONTACT] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CONTACT', 
             'RDB.CLEAR_INV_CONTACT', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CONTACT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_CONTACT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_CONTACT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_CONTACT_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_CONTACT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_CONTACT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_CONTACT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_CONTACT_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_CONTACT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_CONTACT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_CONTACT_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_CONTACT_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_CONTACT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_CONTACT;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_CONTACT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_CONTACT_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CONTACT', 
             'RDB.S_INV_CONTACT', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_CONTACT', 
             'RDB.S_INV_CONTACT', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_DEATH]    Script Date: 1/16/2019 9:28:35 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_DEATH]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_DEATH];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_DEATH] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_DEATH', 
             'RDB.CLEAR_INV_DEATH', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_DEATH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_DEATH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_DEATH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_DEATH_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_DEATH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_DEATH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_DEATH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_DEATH_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_DEATH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_DEATH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_DEATH_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_DEATH_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_DEATH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_DEATH;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_DEATH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_DEATH_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_DEATH', 
             'RDB.S_INV_DEATH', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_DEATH', 
             'RDB.S_INV_DEATH', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_EPIDEMIOLOGY]    Script Date: 1/16/2019 9:28:36 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_EPIDEMIOLOGY]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_EPIDEMIOLOGY];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_EPIDEMIOLOGY] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_EPIDEMIOLOGY', 
             'RDB.CLEAR_INV_EPIDEMIOLOGY', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_EPIDEMIOLOGY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_EPIDEMIOLOGY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_EPIDEMIOLOGY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_EPIDEMIOLOGY_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_EPIDEMIOLOGY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_EPIDEMIOLOGY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_EPIDEMIOLOGY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_EPIDEMIOLOGY_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_EPIDEMIOLOGY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_EPIDEMIOLOGY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_EPIDEMIOLOGY_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_EPIDEMIOLOGY_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_EPIDEMIOLOGY;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_EPIDEMIOLOGY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_EPIDEMIOLOGY_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_EPIDEMIOLOGY', 
             'RDB.S_INV_EPIDEMIOLOGY', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_EPIDEMIOLOGY', 
             'RDB.S_INV_EPIDEMIOLOGY', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_HIV]    Script Date: 1/16/2019 9:28:37 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_HIV]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_HIV];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_HIV] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_HIV', 
             'RDB.CLEAR_INV_HIV', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_HIV_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_HIV_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_HIV_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_HIV_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_HIV_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_HIV_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_HIV_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_HIV_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_HIV_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_HIV_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_HIV_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_HIV_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_HIV', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_HIV;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_HIV_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_HIV_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_HIV', 
             'RDB.S_INV_HIV', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_HIV', 
             'RDB.S_INV_HIV', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_ISOLATE_TRACKING]    Script Date: 1/16/2019 9:28:38 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_ISOLATE_TRACKING]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_ISOLATE_TRACKING];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_ISOLATE_TRACKING] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ISOLATE_TRACKING', 
             'RDB.CLEAR_INV_ISOLATE_TRACKING', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ISOLATE_TRACKING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_ISOLATE_TRACKING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_ISOLATE_TRACKING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_ISOLATE_TRACKING_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_ISOLATE_TRACKING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_ISOLATE_TRACKING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_ISOLATE_TRACKING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_ISOLATE_TRACKING_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_ISOLATE_TRACKING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_ISOLATE_TRACKING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_ISOLATE_TRACKING_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_ISOLATE_TRACKING_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_ISOLATE_TRACKING;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_ISOLATE_TRACKING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_ISOLATE_TRACKING_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ISOLATE_TRACKING', 
             'RDB.S_INV_ISOLATE_TRACKING', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_ISOLATE_TRACKING', 
             'RDB.S_INV_ISOLATE_TRACKING', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_LAB_FINDING]    Script Date: 1/16/2019 9:28:38 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_LAB_FINDING]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_LAB_FINDING];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_LAB_FINDING] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_LAB_FINDING', 
             'RDB.CLEAR_INV_LAB_FINDING', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_LAB_FINDING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_LAB_FINDING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_LAB_FINDING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_LAB_FINDING_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_LAB_FINDING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_LAB_FINDING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_LAB_FINDING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_LAB_FINDING_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_LAB_FINDING_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_LAB_FINDING_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_LAB_FINDING_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_LAB_FINDING_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_LAB_FINDING', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_LAB_FINDING;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_LAB_FINDING_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_LAB_FINDING_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_LAB_FINDING', 
             'RDB.S_INV_LAB_FINDING', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_LAB_FINDING', 
             'RDB.S_INV_LAB_FINDING', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_MEDICAL_HISTORY]    Script Date: 1/16/2019 9:28:39 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_MEDICAL_HISTORY]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_MEDICAL_HISTORY];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_MEDICAL_HISTORY] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MEDICAL_HISTORY', 
             'RDB.CLEAR_INV_MEDICAL_HISTORY', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MEDICAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MEDICAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_MEDICAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_MEDICAL_HISTORY_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_MEDICAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_MEDICAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_MEDICAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_MEDICAL_HISTORY_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_MEDICAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_MEDICAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_MEDICAL_HISTORY_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_MEDICAL_HISTORY_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_MEDICAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_MEDICAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_MEDICAL_HISTORY_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MEDICAL_HISTORY', 
             'RDB.S_INV_MEDICAL_HISTORY', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MEDICAL_HISTORY', 
             'RDB.S_INV_MEDICAL_HISTORY', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_MOTHER]    Script Date: 1/16/2019 9:28:40 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_MOTHER]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_MOTHER];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_MOTHER] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MOTHER', 
             'RDB.CLEAR_INV_MOTHER', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MOTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_MOTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_MOTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_MOTHER_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_MOTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_MOTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_MOTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_MOTHER_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_MOTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_MOTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_MOTHER_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_MOTHER_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_MOTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_MOTHER;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_MOTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_MOTHER_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MOTHER', 
             'RDB.S_INV_MOTHER', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_MOTHER', 
             'RDB.S_INV_MOTHER', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_OTHER]    Script Date: 1/16/2019 9:28:40 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_OTHER]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_OTHER];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_OTHER] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_OTHER', 
             'RDB.CLEAR_INV_OTHER', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_OTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_OTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_OTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_OTHER_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_OTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_OTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_OTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_OTHER_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_OTHER_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_OTHER_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_OTHER_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_OTHER_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_OTHER', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_OTHER;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_OTHER_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_OTHER_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_OTHER', 
             'RDB.S_INV_OTHER', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_OTHER', 
             'RDB.S_INV_OTHER', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_PATIENT_OBS]    Script Date: 1/16/2019 9:28:41 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_PATIENT_OBS]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_PATIENT_OBS];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_PATIENT_OBS] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PATIENT_OBS', 
             'RDB.CLEAR_INV_PATIENT_OBS', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PATIENT_OBS_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PATIENT_OBS_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_PATIENT_OBS_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_PATIENT_OBS_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_PATIENT_OBS_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_PATIENT_OBS_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_PATIENT_OBS_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_PATIENT_OBS_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_PATIENT_OBS_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_PATIENT_OBS_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_PATIENT_OBS_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_PATIENT_OBS_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_PATIENT_OBS', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_PATIENT_OBS;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_PATIENT_OBS_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_PATIENT_OBS_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PATIENT_OBS', 
             'RDB.S_INV_PATIENT_OBS', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PATIENT_OBS', 
             'RDB.S_INV_PATIENT_OBS', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_PREGNANCY_BIRTH]    Script Date: 1/16/2019 9:28:42 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_PREGNANCY_BIRTH]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_PREGNANCY_BIRTH];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_PREGNANCY_BIRTH] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PREGNANCY_BIRTH', 
             'RDB.CLEAR_INV_PREGNANCY_BIRTH', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PREGNANCY_BIRTH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_PREGNANCY_BIRTH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_PREGNANCY_BIRTH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_PREGNANCY_BIRTH_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_PREGNANCY_BIRTH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_PREGNANCY_BIRTH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_PREGNANCY_BIRTH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_PREGNANCY_BIRTH_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_PREGNANCY_BIRTH_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_PREGNANCY_BIRTH_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_PREGNANCY_BIRTH_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_PREGNANCY_BIRTH_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_PREGNANCY_BIRTH;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_PREGNANCY_BIRTH_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_PREGNANCY_BIRTH_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PREGNANCY_BIRTH', 
             'RDB.S_INV_PREGNANCY_BIRTH', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_PREGNANCY_BIRTH', 
             'RDB.S_INV_PREGNANCY_BIRTH', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_RESIDENCY]    Script Date: 1/16/2019 9:28:43 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_RESIDENCY]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_RESIDENCY];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_RESIDENCY] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RESIDENCY', 
             'RDB.CLEAR_INV_RESIDENCY', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RESIDENCY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RESIDENCY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_RESIDENCY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_RESIDENCY_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_RESIDENCY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_RESIDENCY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_RESIDENCY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_RESIDENCY_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_RESIDENCY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_RESIDENCY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_RESIDENCY_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_RESIDENCY_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_RESIDENCY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_RESIDENCY;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_RESIDENCY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_RESIDENCY_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RESIDENCY', 
             'RDB.S_INV_RESIDENCY', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RESIDENCY', 
             'RDB.S_INV_RESIDENCY', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_RISK_FACTOR]    Script Date: 1/16/2019 9:28:44 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_RISK_FACTOR]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_RISK_FACTOR];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_RISK_FACTOR] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RISK_FACTOR', 
             'RDB.CLEAR_INV_RISK_FACTOR', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RISK_FACTOR_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_RISK_FACTOR_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_RISK_FACTOR_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_RISK_FACTOR_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_RISK_FACTOR_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_RISK_FACTOR_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_RISK_FACTOR_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_RISK_FACTOR_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_RISK_FACTOR_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_RISK_FACTOR_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_RISK_FACTOR_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_RISK_FACTOR_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_RISK_FACTOR', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_RISK_FACTOR;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_RISK_FACTOR_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_RISK_FACTOR_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RISK_FACTOR', 
             'RDB.S_INV_RISK_FACTOR', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_RISK_FACTOR', 
             'RDB.S_INV_RISK_FACTOR', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_SOCIAL_HISTORY]    Script Date: 1/16/2019 9:28:44 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_SOCIAL_HISTORY]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_SOCIAL_HISTORY];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_SOCIAL_HISTORY] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SOCIAL_HISTORY', 
             'RDB.CLEAR_INV_SOCIAL_HISTORY', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SOCIAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SOCIAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_SOCIAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_SOCIAL_HISTORY_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_SOCIAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_SOCIAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_SOCIAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_SOCIAL_HISTORY_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_SOCIAL_HISTORY_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_SOCIAL_HISTORY_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_SOCIAL_HISTORY_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_SOCIAL_HISTORY_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_SOCIAL_HISTORY;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_SOCIAL_HISTORY_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_SOCIAL_HISTORY_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SOCIAL_HISTORY', 
             'RDB.S_INV_SOCIAL_HISTORY', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SOCIAL_HISTORY', 
             'RDB.S_INV_SOCIAL_HISTORY', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_SYMPTOM]    Script Date: 1/16/2019 9:28:45 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_SYMPTOM]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_SYMPTOM];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_SYMPTOM] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SYMPTOM', 
             'RDB.CLEAR_INV_SYMPTOM', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SYMPTOM_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_SYMPTOM_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_SYMPTOM_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_SYMPTOM_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_SYMPTOM_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_SYMPTOM_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_SYMPTOM_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_SYMPTOM_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_SYMPTOM_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_SYMPTOM_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_SYMPTOM_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_SYMPTOM_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_SYMPTOM', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_SYMPTOM;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_SYMPTOM_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_SYMPTOM_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SYMPTOM', 
             'RDB.S_INV_SYMPTOM', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_SYMPTOM', 
             'RDB.S_INV_SYMPTOM', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_TRAVEL]    Script Date: 1/16/2019 9:28:46 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_TRAVEL]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_TRAVEL];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_TRAVEL] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TRAVEL', 
             'RDB.CLEAR_INV_TRAVEL', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TRAVEL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TRAVEL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_TRAVEL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_TRAVEL_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_TRAVEL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_TRAVEL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_TRAVEL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_TRAVEL_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_TRAVEL_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_TRAVEL_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_TRAVEL_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_TRAVEL_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_TRAVEL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_TRAVEL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_TRAVEL_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_TRAVEL_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TRAVEL', 
             'RDB.S_INV_TRAVEL', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TRAVEL', 
             'RDB.S_INV_TRAVEL', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_TREATMENT]    Script Date: 1/16/2019 9:28:47 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_TREATMENT]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_TREATMENT];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_TREATMENT] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TREATMENT', 
             'RDB.CLEAR_INV_TREATMENT', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TREATMENT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_TREATMENT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_TREATMENT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_TREATMENT_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_TREATMENT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_TREATMENT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_TREATMENT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_TREATMENT_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_TREATMENT_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_TREATMENT_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_TREATMENT_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_TREATMENT_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_TREATMENT', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_TREATMENT;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_TREATMENT_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_TREATMENT_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TREATMENT', 
             'RDB.S_INV_TREATMENT', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_TREATMENT', 
             'RDB.S_INV_TREATMENT', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_UNDER_CONDITION]    Script Date: 1/16/2019 9:28:48 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_UNDER_CONDITION]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_UNDER_CONDITION];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_UNDER_CONDITION] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_UNDER_CONDITION', 
             'RDB.CLEAR_INV_UNDER_CONDITION', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_UNDER_CONDITION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_UNDER_CONDITION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_UNDER_CONDITION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_UNDER_CONDITION_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_UNDER_CONDITION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_UNDER_CONDITION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_UNDER_CONDITION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_UNDER_CONDITION_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_UNDER_CONDITION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_UNDER_CONDITION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_UNDER_CONDITION_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_UNDER_CONDITION_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_UNDER_CONDITION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_UNDER_CONDITION;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_UNDER_CONDITION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_UNDER_CONDITION_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_UNDER_CONDITION', 
             'RDB.S_INV_UNDER_CONDITION', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_UNDER_CONDITION', 
             'RDB.S_INV_UNDER_CONDITION', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

/****** Object:  StoredProcedure [dbo].[sp_CLEAR_INV_VACCINATION]    Script Date: 1/16/2019 9:28:48 AM ******/

SET ANSI_NULLS ON;
GO
SET QUOTED_IDENTIFIER ON;
GO
IF OBJECT_ID('rdb.dbo.[sp_CLEAR_INV_VACCINATION]', 'P') IS NOT NULL
    BEGIN
        DROP PROCEDURE dbo.[sp_CLEAR_INV_VACCINATION];
END;
GO
CREATE PROCEDURE [dbo].[sp_CLEAR_INV_VACCINATION] @Batch_id BIGINT
AS
    BEGIN
        DECLARE @RowCount_no INT;
        DECLARE @Proc_Step_no FLOAT= 0;
        DECLARE @Proc_Step_Name VARCHAR(200)= '';
        DECLARE @batch_start_time DATETIME2(7)= NULL;
        DECLARE @batch_end_time DATETIME2(7)= NULL;
        BEGIN TRY
            SET @Proc_Step_no = 1;
            SET @Proc_Step_Name = 'SP_Start';
            BEGIN TRANSACTION;
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_VACCINATION', 
             'RDB.CLEAR_INV_VACCINATION', 
             'START', 
             @Proc_Step_no, 
             @Proc_Step_Name, 
             0
            );
            COMMIT TRANSACTION;
            SELECT @batch_start_time = batch_start_dttm, 
                   @batch_end_time = batch_end_dttm
            FROM [dbo].[job_batch_log]
            WHERE status_type = 'start';
            BEGIN TRANSACTION;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_VACCINATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_DESC_INV_VACCINATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_COUNTY_TABLE_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_COUNTY_TABLE_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_DATA_INV_VACCINATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_DATA_INV_VACCINATION_out;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_DESC_INV_VACCINATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_DESC_INV_VACCINATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_MERGED_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_MERGED_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_EMPTY_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_OTHER_NONEMPTY_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_A_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_SNTEMP_TRANS_CODE_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_STD_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_STD_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.CODED_TABLE_TEMP_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.CODED_TABLE_TEMP_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.DATE_DATA_INV_VACCINATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.DATE_DATA_INV_VACCINATION_out;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_BASE_DATA_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_BASE_DATA_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_2_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_2_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_MERGED_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_MERGED_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_OUT_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_OUT_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_PIVOT_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_PIVOT_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.NUMERIC_DATA_TRANS1_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.NUMERIC_DATA_TRANS1_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.PAGE_DATE_TABLE_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.PAGE_DATE_TABLE_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.RDB_UI_METADATA_INV_VACCINATION_TEMP', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.RDB_UI_METADATA_INV_VACCINATION_TEMP;
            END;
            IF OBJECT_ID('rdb.dbo.Stageing_key_metadata_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.Stageing_key_metadata_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.STAGING_KEY_INV_VACCINATION_FINAL', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.STAGING_KEY_INV_VACCINATION_FINAL;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_VACCINATION', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_VACCINATION;
            END;
            IF OBJECT_ID('rdb.dbo.text_data_INV_VACCINATION_out', 'U') IS NOT NULL
                BEGIN
                    DROP TABLE rdb.dbo.text_data_INV_VACCINATION_out;
            END;
            COMMIT TRANSACTION;
            BEGIN TRANSACTION;
            SET @Proc_Step_no = 999;
            SET @Proc_Step_Name = 'SP_COMPLETE';
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_VACCINATION', 
             'RDB.S_INV_VACCINATION', 
             'COMPLETE', 
             @Proc_Step_no, 
             @Proc_Step_name, 
             @RowCount_no
            );
            COMMIT TRANSACTION;
        END TRY
        BEGIN CATCH
            IF @@TRANCOUNT > 0
                ROLLBACK TRANSACTION;
            DECLARE @ErrorNumber INT= ERROR_NUMBER();
            DECLARE @ErrorLine INT= ERROR_LINE();
            DECLARE @ErrorMessage NVARCHAR(4000)= ERROR_MESSAGE();
            DECLARE @ErrorSeverity INT= ERROR_SEVERITY();
            DECLARE @ErrorState INT= ERROR_STATE();
            INSERT INTO rdb.[dbo].[job_flow_log]
            (batch_id, 
             [Dataflow_Name], 
             [package_Name], 
             [Status_Type], 
             [step_number], 
             [step_name], 
             [Error_Description], 
             [row_count]
            )
            VALUES
            (@Batch_id, 
             'INV_VACCINATION', 
             'RDB.S_INV_VACCINATION', 
             'ERROR', 
             @Proc_Step_no, 
             'ERROR - ' + @Proc_Step_name, 
             'Step -' + CAST(@Proc_Step_no AS VARCHAR(3)) + ' -' + CAST(@ErrorMessage AS VARCHAR(500)), 
             0
            );
            RETURN -1;
        END CATCH;
    END;
GO

		EXEC  RDB.[dbo].sp_CLEAR_INV_ADMINISTRATIVE 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_CLINICAL 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_COMPLICATION 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_CONTACT 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_DEATH 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_EPIDEMIOLOGY 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_HIV 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_ISOLATE_TRACKING 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_LAB_FINDING 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_MEDICAL_HISTORY 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_MOTHER 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_OTHER 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_PATIENT_OBS 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_PREGNANCY_BIRTH 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_RESIDENCY 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_RISK_FACTOR 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_SOCIAL_HISTORY 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_SYMPTOM 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_TRAVEL 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_TREATMENT 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_UNDER_CONDITION 1;
		EXEC  RDB.[dbo].sp_CLEAR_INV_VACCINATION 1;

		delete from rdb..job_flow_log where batch_id=1
