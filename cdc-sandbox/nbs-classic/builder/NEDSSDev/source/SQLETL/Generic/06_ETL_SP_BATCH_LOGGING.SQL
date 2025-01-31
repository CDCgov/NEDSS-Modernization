USE [RDB]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


IF OBJECT_ID('rdb.dbo.[sp_nbs_batch_complete]', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE dbo.sp_nbs_batch_complete;
END;
GO
CREATE PROCEDURE [dbo].[sp_nbs_batch_complete]
@typeCode varchar(100)
as

BEGIN
    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no INT = 0 ;
    DECLARE @batch_id BIGINT = 0 ;
	DECLARE @BATCH_COMPLETE_time datetime2(7) = current_timestamp ;
	DECLARE @batch_end_time datetime2(7) = current_timestamp ;
	 DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	
  BEGIN TRY
    
	
   SELECT @batch_id = cast((format(getdate(),'yyMMddHHmmss')) as bigint)
	
	
	BEGIN TRANSACTION;

	
	
	  SET @Proc_Step_no = 1


	
	select @batch_id = batch_id
	from rdb.[dbo].[job_batch_log]
	 where status_type = 'start';

	 update rdb.[dbo].[job_batch_log]
			  set status_type = 'error', update_dttm = CURRENT_TIMESTAMP
	   where (
				select count(*)
				from  rdb.[dbo].[job_flow_log]
				where batch_id = (select  batch_id
					from rdb.[dbo].[job_batch_log]
					 where status_type = 'start' 
					 and type_code=@typeCode)
		 			 and lower(Status_Type) = 'error'
		    		) > 0 
		 and  status_type = 'start'
				 ;

	 
	 update rdb.[dbo].[job_batch_log]
			  set status_type = 'complete', update_dttm = CURRENT_TIMESTAMP
	   where (
				select count(*)
				from  rdb.[dbo].[job_flow_log]
				where batch_id = (select  batch_id
					from rdb.[dbo].[job_batch_log]
					 where status_type = 'start' 
					 and type_code=@typeCode)
		 			 and lower(Status_Type) = 'error'
					 and type_code=@typeCode
		    		) = 0 
		 and  status_type = 'start'
		 and type_code=@typeCode
				 ;

	 
	 select @RowCount_no = @@ROWCOUNT;
	 if @typeCode='MasterETL'
	 begin
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
           ,'BATCH_COMPLETE'
           ,'nbs_BATCH_COMPLETE'
		   ,'start'
		   ,@Proc_Step_no
		   ,'Creating PHC_CASE_UIDS' 
           ,@RowCount_no
		   );
	 end
     COMMIT TRANSACTION;
  
	
    BEGIN TRANSACTION ;
	
	SET @Proc_Step_no = 999;
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
           'BATCH_COMPLETE'
           ,'sp_nbs_batch_complete'
		   ,'COMPLETE'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,@RowCount_no
		   );
  
	
	COMMIT TRANSACTION;
	
	
  END TRY

 
BEGIN CATCH
  
     IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;
 
    DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();
    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();
 
	
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
           ,'BATCH_COMPLETE'
           ,'RDB.BATCH_COMPLETE'
		   ,'ERROR'
		   ,@Proc_Step_no
		   , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
           ,0
		   );
  
       return -1;

	END CATCH
	

END




SET ANSI_PADDING ON


GO

/****** Object:  StoredProcedure [dbo].[sp_nbs_batch_start]    Script Date: 1/21/2019 11:41:01 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO






IF OBJECT_ID('rdb.dbo.[sp_nbs_batch_start]', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE dbo.sp_nbs_batch_start;
END;
GO

CREATE PROCEDURE [dbo].[sp_nbs_batch_start]
@typeCode varchar(100),
@typeDescription varchar(100)


as

BEGIN
    DECLARE @RowCount_no INT ;
    DECLARE @Proc_Step_no INT = 0 ;
    DECLARE @batch_id BIGINT = 0 ;
	DECLARE @batch_start_time datetime2(7) = current_timestamp ;
	DECLARE @batch_end_time datetime2(7) = current_timestamp ;
	 DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	
  BEGIN TRY
    
	
   SELECT @batch_id = cast((format(getdate(),'yyMMddHHmmss')) as bigint)
	
	
	BEGIN TRANSACTION;

	
	SET @Proc_Step_no = 1;
	
	select @RowCount_no = count(*) 
	from [dbo].[job_batch_log]
	 where lower(status_type) in ( 'start', 'error') and type_code=@typeCode
     ;

	
	 if ( @RowCount_no > 0 ) begin

       INSERT INTO [dbo].[job_batch_log]
           ([batch_id]
           ,[batch_start_dttm]
           ,[batch_end_dttm]
           ,[Status_Type]
		   ,Msg_description1
		   ,type_code
		   , type_Description
		   )
        VALUES
           (@batch_id
           ,null
           ,null
		   ,'WARNING'
		   ,'SP_Complete Step - 1 - Another Batch in Progress or Error in a batch job' 
		   ,@typeCode
		   ,@typeDescription
           );
  
         COMMIT TRANSACTION;
	
	     return -1;
	 
	  end ;

	select @batch_start_time = max(batch_end_dttm) 
	from [dbo].[job_batch_log]
	 where lower(status_type) = 'complete' and type_code=@typeCode
     ;

	
	 if ( @batch_start_time is null ) 
	   begin
	   set @batch_start_time = CURRENT_TIMESTAMP;
	   set @batch_end_time = @batch_start_time ;
	   end
 

     INSERT INTO [dbo].[job_batch_log]
           ([batch_id]
           ,[batch_start_dttm]
           ,[batch_end_dttm]
           ,[Status_Type]
		   ,[type_code]
		   ,[type_description]
		   )
     VALUES 
           (@batch_id
           ,@batch_start_time
           ,CURRENT_TIMESTAMP
           ,'start'
		   ,@typeCode
		   ,@typeDescription
           )
		    ; 

      COMMIT TRANSACTION ;

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
           ,'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'START'
		   ,@Proc_Step_no
		   ,'start'
           ,0
		   );
  
    COMMIT TRANSACTION;
if @typeCode='DynamicDatamart'
	 begin
       BEGIN TRANSACTION ;
	   
	  SET @Proc_Step_no = 2
	   select @batch_start_time,@batch_end_time;

 
		  SELECT 
			count(*)
		  FROM NBS_ODSE.dbo.PUBLIC_HEALTH_CASE phc  
		 where LAST_CHG_TIME >= @batch_start_time
			and LAST_CHG_TIME < @batch_end_time
		   ;
		   select @RowCount_no = @@ROWCOUNT;
	 if ( @RowCount_no = 0 ) begin
	 

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
           ,'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'WARNING'
		   ,@Proc_Step_no
		   ,'SP_Complete Step - 1 - No Rows to Update' 
           ,0
		   );

		   update  [RDB].[dbo].[job_batch_log]
              set [Status_Type] = 'Complete',
			      [Msg_Description1] =  'No Rows to Update'
			where lower([Status_Type]) = 'start'
           ;

end
		 COMMIT TRANSACTION;
return 0;
END
if @typeCode='PHCMartETL'
	 begin
       BEGIN TRANSACTION ;
	   
	  SET @Proc_Step_no = 2
	   select @batch_start_time,@batch_end_time;

 
		  SELECT 
			count(*)
		  FROM NBS_ODSE.dbo.PUBLIC_HEALTH_CASE phc  
		 where LAST_CHG_TIME >= @batch_start_time
			and LAST_CHG_TIME < @batch_end_time
		   ;
		   select @RowCount_no = @@ROWCOUNT;
	 if ( @RowCount_no = 0 ) begin
	 

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
           ,'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'WARNING'
		   ,@Proc_Step_no
		   ,'SP_Complete Step - 1 - No Rows to Update' 
           ,0
		   );

		   update  [RDB].[dbo].[job_batch_log]
              set [Status_Type] = 'Complete',
			      [Msg_Description1] =  'No Rows to Update'
			where lower([Status_Type]) = 'start'
           ;

end
		 COMMIT TRANSACTION;
return 0;
END
		      BEGIN TRANSACTION ;
	  SET @Proc_Step_no = 2

	IF OBJECT_ID('rdb.dbo.PHC_UIDS', 'U') IS NOT NULL
		DROP TABLE rdb.dbo.PHC_UIDS;

	/* BEGIN - Check to see if there is an entry in job_batch_rebuild_log table to rebuild PB dimensions*/

	IF OBJECT_ID('job_batch_rebuild_log') IS NOT NULL
		BEGIN
			DECLARE @count INT;
			SET @count =
			(
				SELECT COUNT(*)
				FROM rdb..job_batch_rebuild_log
				WHERE Status_Type = 'start'
					  AND type_code = 'PB_DIMENSIONS'
			);
			IF(@count > 0)
				set @batch_start_time = (select MAX(batch_start_dttm)
				FROM rdb..job_batch_rebuild_log
				WHERE type_code = 'PB_DIMENSIONS'
					  AND status_type = 'start');
			UPDATE rdb..job_batch_rebuild_log
			  SET 
				  batch_end_dttm = @batch_end_time, 
				  status_type = 'complete'
			WHERE type_code = 'PB_DIMENSIONS'
				  AND status_type = 'start';
	END;
   /* END - Check to see if there is an entry in job_batch_rebuild_log table to rebuild PB dimensions*/
 
      select @batch_start_time,@batch_end_time
	  ;
		 IF OBJECT_ID('rdb.dbo.PHC_UIDS', 'U') IS NOT NULL  
							drop table rdb.dbo.PHC_UIDS;
	 
		  select @batch_start_time,@batch_end_time
		  ;

 
		  SELECT 
			phc.PUBLIC_HEALTH_CASE_UID  AS PAGE_CASE_UID , 
			CASE_MANAGEMENT_UID, 
			INVESTIGATION_FORM_CD, 
			CD, 
			LAST_CHG_TIME 
		  into RDB.dbo.PHC_UIDS
		  FROM NBS_ODSE.dbo.PUBLIC_HEALTH_CASE phc  
			   LEFT OUTER JOIN NBS_ODSE..CASE_MANAGEMENT cm ON phc.PUBLIC_HEALTH_CASE_UID= cm.PUBLIC_HEALTH_CASE_UID
			   LEFT OUTER JOIN NBS_SRTE..CONDITION_CODE cc  ON cc.CONDITION_CD= phc.CD
											  AND INVESTIGATION_FORM_CD   NOT IN ( 'INV_FORM_BMDGAS','INV_FORM_BMDGBS','INV_FORM_BMDGEN',
											  'INV_FORM_BMDNM','INV_FORM_BMDSP','INV_FORM_GEN','INV_FORM_HEPA','INV_FORM_HEPBV','INV_FORM_HEPCV',
											  'INV_FORM_HEPGEN','INV_FORM_MEA','INV_FORM_PER','INV_FORM_RUB','INV_FORM_RVCT','INV_FORM_VAR')
		 where LAST_CHG_TIME >= @batch_start_time
			and LAST_CHG_TIME < @batch_end_time
		   ;
		   select @RowCount_no = @@ROWCOUNT;
		   
		--   CREATE INDEX  PHC_UIDS ON [dbo].[PHC_UIDS](PAGE_CASE_UID);
	-- TODO: This is a work around to handle the situation where changes made to any page will not reflect in page dimensions unless atleast one investigation is processed through

	IF NOT EXISTS
	(
		SELECT 1
		FROM RDB.dbo.PHC_UIDS
	)
	BEGIN
		DROP TABLE rdb.dbo.PHC_UIDS;
		SELECT TOP 1 phc.PUBLIC_HEALTH_CASE_UID AS PAGE_CASE_UID, CASE_MANAGEMENT_UID, INVESTIGATION_FORM_CD, CD, GETDATE() AS LAST_CHG_TIME INTO RDB.dbo.PHC_UIDS
		FROM NBS_ODSE.dbo.PUBLIC_HEALTH_CASE AS phc
			 LEFT OUTER JOIN
			 NBS_ODSE..CASE_MANAGEMENT AS cm
			 ON phc.PUBLIC_HEALTH_CASE_UID = cm.PUBLIC_HEALTH_CASE_UID
			 LEFT OUTER JOIN
			 NBS_SRTE..CONDITION_CODE AS cc
			 ON cc.CONDITION_CD = phc.CD AND INVESTIGATION_FORM_CD is not null and case_type_cd='I' and
				INVESTIGATION_FORM_CD NOT IN( 'INV_FORM_BMDGAS', 'INV_FORM_BMDGBS', 'INV_FORM_BMDGEN', 'INV_FORM_BMDNM', 'INV_FORM_BMDSP', 'INV_FORM_GEN', 'INV_FORM_HEPA', 'INV_FORM_HEPBV', 'INV_FORM_HEPCV', 'INV_FORM_HEPGEN', 'INV_FORM_MEA', 'INV_FORM_PER', 'INV_FORM_RUB', 'INV_FORM_RVCT', 'INV_FORM_VAR' );
			select @RowCount_no = @@ROWCOUNT;
	END;
	
	 if ( @RowCount_no = 0 ) begin
	 

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
           ,'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'WARNING'
		   ,@Proc_Step_no
		   ,'SP_Complete Step - 1 - No Rows to Update' 
           ,0
		   );

		   update  [RDB].[dbo].[job_batch_log]
              set [Status_Type] = 'Complete',
			      [Msg_Description1] =  'No Rows to Update'
			where lower([Status_Type]) = 'start'
           ;

		 COMMIT TRANSACTION;

	    return 2;
	  end
		  IF OBJECT_ID('PHC_UIDS') IS NOT NULL
		  BEGIN
				CREATE INDEX  PHC_UIDS ON [dbo].[PHC_UIDS](PAGE_CASE_UID);  
			END		
	  
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
           ,'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'start'
		   ,@Proc_Step_no
		   ,'Create PHC_UIDS' 
           ,@RowCount_no
		   );
  
     

	 COMMIT TRANSACTION;
  
	 BEGIN TRANSACTION ;

	  SET @Proc_Step_no = 3

	 IF OBJECT_ID('rdb.dbo.PHC_CASE_UIDS', 'U') IS NOT NULL  
                        drop table rdb.dbo.PHC_CASE_UIDS;
 
 
    --CREATE TABLE 	PHC_CASE_UIDS AS 
     SELECT 
	     PAGE_CASE_UID,
	     INVESTIGATION_FORM_CD,
	     CD,
	     LAST_CHG_TIME
      into rdb..PHC_CASE_UIDS
      FROM RDB.dbo.PHC_UIDS
        WHERE CASE_MANAGEMENT_UID IS NULL;
    

	 select @RowCount_no = @@ROWCOUNT;

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
           ,'BATCH_START'
           ,'nbs_batch_start'
		   ,'start'
		   ,@Proc_Step_no
		   ,'Creating PHC_CASE_UIDS' 
           ,@RowCount_no
		   );

		   DELETE FROM RDB.DBO.ETL_DQ_LOG WHERE EVENT_UID IN (SELECT PAGE_CASE_UID FROM RDB.DBO.PHC_UIDS);
  
     COMMIT TRANSACTION;
  
	
    BEGIN TRANSACTION ;
	
	SET @Proc_Step_no = 999;
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
           'BATCH_START'
           ,'sp_nbs_batch_start'
		   ,'COMPLETE'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,@RowCount_no
		   );
  
	
	COMMIT TRANSACTION;
	
	
  END TRY

 
BEGIN CATCH
  
     IF @@TRANCOUNT > 0   ROLLBACK TRANSACTION;
 
    DECLARE @ErrorNumber INT = ERROR_NUMBER();
    DECLARE @ErrorLine INT = ERROR_LINE();
    DECLARE @ErrorMessage NVARCHAR(4000) = ERROR_MESSAGE();
    DECLARE @ErrorSeverity INT = ERROR_SEVERITY();
    DECLARE @ErrorState INT = ERROR_STATE();
 
	
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
           ,'BATCH_START'
           ,'RDB.BATCH_START'
		   ,'ERROR'
		   ,@Proc_Step_no
		   , 'Step -' +CAST(@Proc_Step_no AS VARCHAR(3))+' -' +CAST(@ErrorMessage AS VARCHAR(500))
           ,0
		   );
  
       return -1;

	END CATCH
	

END




GO

 SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

IF OBJECT_ID('rdb.dbo.[sp_nbs_batch_wait_complete]', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE dbo.sp_nbs_batch_wait_complete;
END;
GO

CREATE PROCEDURE [dbo].[sp_nbs_batch_wait_complete]
as

BEGIN
		DECLARE @status_counter INT = 1;


		select   @status_counter = count(*)
		 from rdb.[dbo].[job_batch_log]
							 where status_type = 'start' ;

 
		WHILE @status_counter > 0 
		BEGIN
 	
			WAITFOR DELAY '00:01:00';

		   select   @status_counter = count(*)
		      from rdb.[dbo].[job_batch_log]
			  where status_type = 'start' ;
		END
END
GO


