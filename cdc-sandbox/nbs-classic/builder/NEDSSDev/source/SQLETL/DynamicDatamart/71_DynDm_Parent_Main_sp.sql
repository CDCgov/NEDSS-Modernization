
USE rdb;
GO

SET XACT_ABORT ON
go

--exec  rdb.dbo.DyDM_REPEATDATEDATA_sp  231115011922 ,'GENERIC_V2' ;
		
/*   
    commit;


	--delete   from job_batch_log ;
	--select * from job_batch_log order by 1 desc;

	-- 
 		delete from rdb.dbo.job_flow_log ;

		exec  rdb.dbo.DynDM_CLEAR_sp 999;

		exec rdb.dbo.DynDm_Parent_Main_sp;


select *
from rdb.dbo.job_batch_Log
order by 1 desc;



*/


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';


SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO


			 IF OBJECT_ID('rdb.dbo.DynDm_Parent_Main_sp ', 'P') IS NOT NULL
				BEGIN
				DROP PROCEDURE dbo.DynDm_Parent_Main_sp ;
				END;

GO



CREATE PROCEDURE dbo.DynDm_Parent_Main_sp 
 
  		--,@DATAMART_TABLE_NAME VARCHAR(100)
	AS
BEGIN  
	DECLARE @RowCount_no INT;
	DECLARE @batch_id BIGINT = 999;

	DECLARE @type_code VARCHAR(200) = 'DynamicDatamart';
	DECLARE @type_description VARCHAR(200) = 'DynamicDatamart Process';
	DECLARE @return_value INT = 0;
	DECLARE @Proc_Step_no FLOAT = 0 ;
	DECLARE @Proc_Step_Name VARCHAR(200) = '' ;
	DECLARE @batch_start_time datetime = null ;
	DECLARE @batch_end_time datetime = null ;



	 BEGIN TRY


	


--DECLARE  @batch_id BIGINT = 999;  DECLARE @DATAMART_TABLE_NAME  VARCHAR(100) = 'GENERIC_V2';


			SET @Proc_Step_no = 1;
	SET @Proc_Step_Name = 'SP_Start';

	

	
	BEGIN TRANSACTION; 
	
    INSERT INTO rdb.dbo.[job_flow_log] (
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
           ,'RDB.DBO.DynDm_Parent_Main_sp'
		   ,'START'
		   ,@Proc_Step_no
		   ,@Proc_Step_Name
           ,0
		   );
  
    COMMIT TRANSACTION;
	
	

	--BEGIN TRANSACTION;

	SET @Proc_Step_no = @Proc_Step_no + 1;
	SET @Proc_Step_Name = 'Executing DYNDM_MAIN_SP';
	


	   	  	 EXEC rdb.dbo.DYNDM_MAIN_SP;

		
				SELECT @ROWCOUNT_NO = 0;

  BEGIN TRANSACTION;

 INSERT INTO rdb.[dbo].[job_flow_log] ( batch_id ,[Dataflow_Name] ,[package_Name] ,[Status_Type] ,[step_number] ,[step_name] ,[row_count] ) 
 VALUES ( @batch_id ,'DYNAMIC_DATAMART' ,'RDB.DBO.DynDm_Parent_Main_sp  '  ,'START' ,@Proc_Step_no ,@Proc_Step_Name ,@ROWCOUNT_NO );
  
	
	
	select ' i am back 4',@@TRANCOUNT;

	
    	IF @@TRANCOUNT > 0
		BEGIN
							COMMIT  TRANSACTION;
		END;

		
	
 	-- BEGIN TRANSACTION;

		--EXEC  RDB.[dbo].DynDM_CLEAR_sp @batch_id;

  --  COMMIT TRANSACTION;
	 
 	 BEGIN TRANSACTION;

					SET @Proc_Step_no = @Proc_Step_no + 1;
					SET @Proc_Step_Name = 'SP_COMPLETE'; 


					INSERT INTO rdb.dbo.[job_flow_log] (
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
						   ,'RDB.DBO.DynDm_Parent_Main_sp'
						   ,'COMPLETE'
						   ,@Proc_Step_no
						   ,@Proc_Step_name
						   ,0
						   );
  
	
	 COMMIT TRANSACTION ;


	 PRINT 'DYNAMIC DATAMART PARENT BATCH complete '

    END TRY
            BEGIN CATCH
						IF @@TRANCOUNT > 0
						BEGIN
							ROLLBACK TRANSACTION;
							-- COMMIT TRANSACTION
						END;
						DECLARE @ErrorNumber int= ERROR_NUMBER();
						DECLARE @ErrorLine int= ERROR_LINE();
						DECLARE @ErrorMessage nvarchar(4000)= ERROR_MESSAGE();
						DECLARE @ErrorSeverity int= ERROR_SEVERITY();
						DECLARE @ErrorState int= ERROR_STATE();

                        select @ErrorMessage;

						INSERT INTO rdb.dbo.[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [Error_Description], [row_count] )
						VALUES( @Batch_id, 'DYNAMIC_DATAMART', 'RDB.DBO.DynDm_Parent_Main_sp', 'ERROR', @Proc_Step_no, 'ERROR - '+@Proc_Step_name, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
						RETURN -1;
	        END CATCH;
END;

GO


