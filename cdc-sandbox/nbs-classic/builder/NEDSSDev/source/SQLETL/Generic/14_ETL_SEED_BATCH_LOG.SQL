USE [RDB]
GO

declare @dateValue datetime
begin
set @dateValue ='2000-01-01';
INSERT INTO [dbo].[job_batch_log]
           ([batch_id]
           ,[batch_start_dttm]
           ,[batch_end_dttm]
           ,[Status_Type]
		   ,[type_code]
		   ,[type_description]
           )
     VALUES
           (1
           ,@dateValue
           ,@dateValue
           ,'complete'
		   ,'MasterETL'
		   ,'MasterETL Process Completed.'
           )
end
GO



