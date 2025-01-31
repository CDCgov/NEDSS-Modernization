
USE rdb;
GO

--		exec rdb.dbo.DynDm_Clear_sp 999;

--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'CONG_SYPHILIS';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_Clear_sp', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_Clear_sp;
				END;

GO



CREATE PROCEDURE [dbo].DynDm_Clear_sp
 
            @batch_id BIGINT = 999

	AS
BEGIN  
	 BEGIN TRY
	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'CONG_SYPHILIS';

		

		
		DECLARE @RowCount_no INT ;
		DECLARE @Proc_Step_no FLOAT = 0 ;
		DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
		DECLARE @batch_start_time datetime = null ;
		DECLARE @batch_end_time datetime = null ;



			SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION;
	
    INSERT INTO rdb.[dbo].[job_flow_log] (
	        batch_id
		   ,[Dataflow_Name]
		   ,[package_Name]
		    ,[Status_Type] 
           ,[step_number]
           ,[step_name]
          ,[row_count]
           )
		   VALUES
           (
		   @batch_id
           ,'DYNAMIC_DATAMART'
           ,'RDB.DBO.DynDm_Clear_sp'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	

	BEGIN TRANSACTION;

		
		
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Provider_Metadata', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Provider_Metadata;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_ProvPart_Table_temp', 'U') IS NOT NULL   		drop table rdb.dbo.tmp_DynDm_ProvPart_Table_temp;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Investigation_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Investigation_Data;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_OrgPart_Table_temp', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_OrgPart_Table_temp;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization', 'U') IS NOT NULL   	drop table rdb.dbo.tmp_DynDm_Organization;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Investigation_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Investigation_Data;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Patient_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Patient_Data;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Case_Management_Data', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Case_Management_Data;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_Administrative', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_Administrative;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_CLINICAL', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_CLINICAL;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_COMPLICATION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_COMPLICATION;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_CONTACT', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_CONTACT;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_DEATH', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_DEATH;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_EPIDEMIOLOGY;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_HIV', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_HIV;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_PATIENT_OBS;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_ISOLATE_TRACKING;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_LAB_FINDING;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_MEDICAL_HISTORY;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_MOTHER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_MOTHER;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_OTHER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_OTHER;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL drop table rdb.dbo.tmp_DynDm_D_INV_PREGNANCY_BIRTH;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_RESIDENCY', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_RESIDENCY;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_RISK_FACTOR;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_D_INV_SOCIAL_HISTORY;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_SYMPTOM', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_SYMPTOM;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_TREATMENT', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_TREATMENT;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_TRAVEL', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_D_INV_TRAVEL;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_UNDER_CONDITION;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_VACCINATION', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_VACCINATION;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_STD', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_D_INV_STD;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_Organization;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_PROVIDER', 'U') IS NOT NULL   drop table rdb.dbo.tmp_DynDm_PROVIDER;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART', 'U') IS NOT NULL  DROP TABLE rdb.dbo.tmp_DynDm_INV_SUMM_DATAMART  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_PAT_METADATA', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_PAT_METADATA  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_D_INV_METADATA  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_D_INV_METADATA_distinct  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_D_INV_METADATA_OTH  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_D_INV_METADATA_UNIT  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Organization_METADATA', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_Organization_METADATA  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_DISCRETE_ALL', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_DISCRETE_ALL  ;

			IF OBJECT_ID('rdb.dbo.tmp_INIT', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_INIT  ;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_INIT', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_METADATA_INIT  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_METADATA_UNIT  ;
			IF OBJECT_ID('rdb.dbo.tmp_NBS_PAGE', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_NBS_PAGE  ;

			IF OBJECT_ID('rdb.dbo.tmp_INIT_FORM_SET', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_INIT_FORM_SET  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_SUMM_DATAMART', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_SUMM_DATAMART  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INV_METADATA', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_INV_METADATA  ;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_Case_Management_Metadata', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_Case_Management_Metadata  ;

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_VARCHAR', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_VARCHAR  ;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_VARCHAR_ALL', 'U') IS NOT NULL     DROP TABLE rdb.dbo.tmp_DynDm_REPEAT_BLOCK_VARCHAR_ALL  ;

 
 
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL    				drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;

			IF OBJECT_ID('[dbo].[tmp_DynDm_BLOCK_DATA_OTH]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_BLOCK_DATA_OTH];
			IF OBJECT_ID('[dbo].[tmp_DynDm_BLOCK_DATA_PL]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_BLOCK_DATA_PL];
			IF OBJECT_ID('[dbo].[tmp_DynDm_BLOCK_DATA_UNIT]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_BLOCK_DATA_UNIT];
			IF OBJECT_ID('[dbo].[tmp_DynDm_D_INV_REPEAT_METADATA]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_D_INV_REPEAT_METADATA];
			IF OBJECT_ID('[dbo].[tmp_DynDm_DYNINVLISTING]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_DYNINVLISTING];
			IF OBJECT_ID('[dbo].[tmp_DynDm_INVESTIGATION_REPEAT_ALL]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_INVESTIGATION_REPEAT_ALL];
			IF OBJECT_ID('[dbo].[tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_INVESTIGATION_REPEAT_NUMERIC];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA_MERGED_INIT]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA_MERGED_INIT];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA_OUT]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA_OUT];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA_OUT_TMP]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA_OUT_TMP];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA_OUT1]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA_OUT1];
			IF OBJECT_ID('[dbo].[tmp_DynDm_METADATA_OUT2]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_METADATA_OUT2];
			IF OBJECT_ID('[dbo].[tmp_DynDm_REPEAT_BLOCK_DATE_ALL]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_REPEAT_BLOCK_DATE_ALL];
			IF OBJECT_ID('[dbo].[tmp_DynDm_REPEAT_BLOCK_METADATA_OUT]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_REPEAT_BLOCK_METADATA_OUT];
			IF OBJECT_ID('[dbo].[tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_REPEAT_BLOCK_NUMERIC_ALL];
			IF OBJECT_ID('[dbo].[tmp_DynDm_REPEAT_BLOCK_OUT]', 'U') IS NOT NULL DROP TABLE [dbo].[tmp_DynDm_REPEAT_BLOCK_OUT];

			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_ALL', 'U') IS NOT NULL 	drop table rdb.dbo.tmp_DynDm_REPEAT_ALL;

  
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_BLOCK_DATA', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_BLOCK_DATA;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_INVESTIGATION_REPEAT_DATE;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;



			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_ALL;
 			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_OUT_BASE;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_OUT_final', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_METADATA_OUT_final;
    		IF OBJECT_ID('rdb.dbo.tmp_DynDm_Metadata', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_Metadata;
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_METADATA_UNIT', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_METADATA_UNIT;
			
			IF OBJECT_ID('rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT_FINAL', 'U') IS NOT NULL  drop table rdb.dbo.tmp_DynDm_REPEAT_BLOCK_METADATA_OUT_FINAL;


			
			IF OBJECT_ID('rdb.dbo.NBS_PAGE', 'U') IS NOT NULL  drop table rdb.dbo.NBS_PAGE;
			IF OBJECT_ID('rdb.dbo.INIT_BLANK', 'U') IS NOT NULL  drop table rdb.dbo.INIT_BLANK;
			IF OBJECT_ID('rdb.dbo.INIT_FORM_BLANK_SET', 'U') IS NOT NULL  drop table rdb.dbo.INIT_FORM_BLANK_SET;


			update rdb.dbo.job_batch_log
				set status_type = 'complete', Msg_Description1 = 'ERROR: This execution of  Dynamic Datmart had Errors' 
				where status_type = 'error' and   type_description = 'DynamicDatamart Process'
				;

         COMMIT TRANSACTION;

		 BEGIN TRANSACTION;

	               	SET @Proc_Step_no = @Proc_Step_no + 1;
				SET @Proc_Step_Name = 'SP_COMPLETE'; 


					INSERT INTO rdb.[dbo].[job_flow_log] (
							batch_id
							,[Dataflow_Name]
						   ,[package_Name]
							,[Status_Type] 
						   ,[step_number]
						   ,[step_name]
						   ,[row_count]
						   )
						   VALUES
						   (
						   @batch_id,
						   'DYNAMIC_DATAMART'
						   ,'RDB.DBO.DynDm_Clear_sp'
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,0
						   );
  
	
	 COMMIT TRANSACTION ;

    END TRY
            BEGIN CATCH
						IF @@TRANCOUNT > 0
						BEGIN
							ROLLBACK TRANSACTION;
						END;
						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_Clear_sp' , 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO


