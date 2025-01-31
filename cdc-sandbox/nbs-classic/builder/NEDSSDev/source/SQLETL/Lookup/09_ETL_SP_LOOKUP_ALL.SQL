
USE [RDB];
GO

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_ADMINISTRATIVE', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_ADMINISTRATIVE];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_ADMINISTRATIVE] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_ADMINISTRATIVE'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_ADMINISTRATIVE AS
		--*** Keys for New Page case where no Stgaing Administrative enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE;
		END;
		IF OBJECT_ID('rdb.dbo.S_INV_ADMINISTRATIVE', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_ADMINISTRATIVE]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];


			CREATE INDEX S_INV_ADMINISTRATIVE
ON [dbo].S_INV_ADMINISTRATIVE
			( PAGE_CASE_UID
			);
		END;

		IF OBJECT_ID('rdb.dbo.L_INV_ADMINISTRATIVE', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_ADMINISTRATIVE]
			( 
						 [D_INV_ADMINISTRATIVE_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_ADMINISTRATIVE
ON [dbo].L_INV_ADMINISTRATIVE
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_ADMINISTRATIVE]
			( [D_INV_ADMINISTRATIVE_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;

		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_ADMINISTRATIVE
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_ADMINISTRATIVE
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_ADMINISTRATIVE';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_ADMINISTRATIVE_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_ADMINISTRATIVE AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_ADMINISTRATIVE_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_ADMINISTRATIVE AS
		--*** Keys for New Page case where no Stgaing Administrative enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_ADMINISTRATIVE_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_ADMINISTRATIVE AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_ADMINISTRATIVE_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE]
		( [D_INV_ADMINISTRATIVE_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_ADMINISTRATIVE'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_ADMINISTRATIVE ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_ADMINISTRATIVE;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_ADMINISTRATIVE
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_ADMINISTRATIVE];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_ADMINISTRATIVE_INC'; 

		--CREATE TABLE L_INV_ADMINISTRATIVE_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_ADMINISTRATIVE_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_ADMINISTRATIVE_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_ADMINISTRATIVE_KEY AS D_INV_ADMINISTRATIVE_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_ADMINISTRATIVE_KEY
		INTO rdb.dbo.L_INV_ADMINISTRATIVE_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_ADMINISTRATIVE AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;

		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_ADMINISTRATIVE_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_ADMINISTRATIVE_INC]
		( [D_INV_ADMINISTRATIVE_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_ADMINISTRATIVE_INC';

		UPDATE rdb.dbo.L_INV_ADMINISTRATIVE_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_ADMINISTRATIVE_INC
		  SET D_INV_ADMINISTRATIVE_KEY = COALESCE(D_INV_ADMINISTRATIVE_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_ADMINISTRATIVE where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_ADMINISTRATIVE ladmin
			 JOIN
			 rdb.dbo.L_INV_ADMINISTRATIVE_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_ADMINISTRATIVE_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_ADMINISTRATIVE new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_ADMINISTRATIVE( [PAGE_CASE_UID], [D_INV_ADMINISTRATIVE_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_ADMINISTRATIVE_KEY
			   FROM rdb.dbo.L_INV_ADMINISTRATIVE_INC
			   WHERE D_INV_ADMINISTRATIVE_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_ADMINISTRATIVE new key entry';

		INSERT INTO [RDB].[dbo].L_INV_ADMINISTRATIVE( [PAGE_CASE_UID], [D_INV_ADMINISTRATIVE_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_ADMINISTRATIVE_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_ADMINISTRATIVE;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_ADMINISTRATIVE where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_ADMINISTRATIVE', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_ADMINISTRATIVE]
			( 
						 [D_INV_ADMINISTRATIVE_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_ADMINISTRATIVE dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE ladminI
			 ON dadmin.d_inv_administrative_key = ladminI.d_inv_administrative_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_ADMINISTRATIVE;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ADMINISTRATIVE;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_ADMINISTRATIVE];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.L_INV_ADMINISTRATIVE', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_CLINICAL]    Script Date: 1/21/2019 11:01:50 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_CLINICAL', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_CLINICAL];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_CLINICAL] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_CLINICAL'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_CLINICAL AS
		--*** Keys for New Page case where no Stgaing CLINICAL enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_CLINICAL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_CLINICAL]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_CLINICAL
ON [dbo].S_INV_CLINICAL
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_CLINICAL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_CLINICAL]
			( 
						 [D_INV_CLINICAL_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_CLINICAL
ON [dbo].[L_INV_CLINICAL]
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_CLINICAL]
			( [D_INV_CLINICAL_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );

		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_CLINICAL
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_CLINICAL
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_CLINICAL';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_CLINICAL_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_CLINICAL AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_CLINICAL_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_CLINICAL'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_CLINICAL AS
		--*** Keys for New Page case where no Stgaing CLINICAL enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_CLINICAL_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_CLINICAL AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_CLINICAL_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_CLINICAL]
		( [D_INV_CLINICAL_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_CLINICAL'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_CLINICAL ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_CLINICAL;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_CLINICAL
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_CLINICAL];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_CLINICAL_INC'; 

		--CREATE TABLE L_INV_CLINICAL_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_CLINICAL_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_CLINICAL_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_CLINICAL_KEY AS D_INV_CLINICAL_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_CLINICAL_KEY
		INTO rdb.dbo.L_INV_CLINICAL_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_CLINICAL AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;



		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_CLINICAL_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_CLINICAL_INC]
		( [D_INV_CLINICAL_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_CLINICAL_INC';

		UPDATE rdb.dbo.L_INV_CLINICAL_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_CLINICAL_INC
		  SET D_INV_CLINICAL_KEY = COALESCE(D_INV_CLINICAL_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_CLINICAL where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_CLINICAL ladmin
			 JOIN
			 rdb.dbo.L_INV_CLINICAL_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_CLINICAL_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_CLINICAL new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_CLINICAL( [PAGE_CASE_UID], [D_INV_CLINICAL_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_CLINICAL_KEY
			   FROM rdb.dbo.L_INV_CLINICAL_INC
			   WHERE D_INV_CLINICAL_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_CLINICAL new key entry';

		INSERT INTO [RDB].[dbo].L_INV_CLINICAL( [PAGE_CASE_UID], [D_INV_CLINICAL_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_CLINICAL_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_CLINICAL;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_CLINICAL where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_CLINICAL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_CLINICAL]
			( 
						 [D_INV_CLINICAL_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_CLINICAL dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL ladminI
			 ON dadmin.d_inv_CLINICAL_key = ladminI.d_inv_CLINICAL_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_CLINICAL;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CLINICAL;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_CLINICAL];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.L_INV_CLINICAL', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_COMPLICATION]    Script Date: 1/21/2019 11:01:50 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_COMPLICATION', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_COMPLICATION];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_COMPLICATION] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_COMPLICATION'; 

    
	IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_COMPLICATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_COMPLICATION]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_COMPLICATION
ON [dbo].S_INV_COMPLICATION
			( PAGE_CASE_UID
			);
		END;

		IF OBJECT_ID('rdb.dbo.L_INV_COMPLICATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_COMPLICATION]
			( 
						 [D_INV_COMPLICATION_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_COMPLICATION
ON [dbo].L_INV_COMPLICATION
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_COMPLICATION]
			( [D_INV_COMPLICATION_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );

		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_COMPLICATION
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_COMPLICATION
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_COMPLICATION';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_COMPLICATION_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_COMPLICATION AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_COMPLICATION_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_COMPLICATION AS
		--*** Keys for New Page case where no Stgaing COMPLICATION enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_COMPLICATION_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_COMPLICATION AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_COMPLICATION_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION]
		( [D_INV_COMPLICATION_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_COMPLICATION'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_COMPLICATION 

/*
CREATE TABLE [dbo].[LOOKUP_TABLE_N_INV_COMPLICATION](
	[PAGE_CASE_UID] [numeric](20, 0) NULL,
	[D_INV_COMPLICATION_KEY] [int] IDENTITY(1,1) NOT NULL
) ON [PRIMARY]

GO
*/


		--truncate table  dbo.LOOKUP_TABLE_N_INV_COMPLICATION ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_COMPLICATION;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_COMPLICATION
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_COMPLICATION];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_COMPLICATION_INC'; 

		--CREATE TABLE L_INV_COMPLICATION_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_COMPLICATION_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_COMPLICATION_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_COMPLICATION_KEY AS D_INV_COMPLICATION_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_COMPLICATION_KEY
		INTO rdb.dbo.L_INV_COMPLICATION_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_COMPLICATION AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_COMPLICATION_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_COMPLICATION_INC]
		( [D_INV_COMPLICATION_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_COMPLICATION_INC';

		UPDATE rdb.dbo.L_INV_COMPLICATION_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_COMPLICATION_INC
		  SET D_INV_COMPLICATION_KEY = COALESCE(D_INV_COMPLICATION_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_COMPLICATION where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_COMPLICATION ladmin
			 JOIN
			 rdb.dbo.L_INV_COMPLICATION_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_COMPLICATION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_COMPLICATION new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_COMPLICATION( [PAGE_CASE_UID], [D_INV_COMPLICATION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_COMPLICATION_KEY
			   FROM rdb.dbo.L_INV_COMPLICATION_INC
			   WHERE D_INV_COMPLICATION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_COMPLICATION new key entry';

		INSERT INTO [RDB].[dbo].L_INV_COMPLICATION( [PAGE_CASE_UID], [D_INV_COMPLICATION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_COMPLICATION_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_COMPLICATION;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_COMPLICATION where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_COMPLICATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_COMPLICATION]
			( 
						 [D_INV_COMPLICATION_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_COMPLICATION dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION ladminI
			 ON dadmin.d_inv_COMPLICATION_key = ladminI.d_inv_COMPLICATION_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_COMPLICATION;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_COMPLICATION;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_COMPLICATION];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.L_INV_COMPLICATION', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_CONTACT]    Script Date: 1/21/2019 11:01:50 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_CONTACT', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_CONTACT];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_CONTACT] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_CONTACT'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_CONTACT AS
		--*** Keys for New Page case where no Stgaing CONTACT enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_CONTACT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_CONTACT;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_CONTACT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_CONTACT]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_CONTACT
ON [dbo].S_INV_CONTACT
			( PAGE_CASE_UID
			);
		END;

		IF OBJECT_ID('rdb.dbo.L_INV_CONTACT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_CONTACT]
			( 
						 [D_INV_CONTACT_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_CONTACT
ON [dbo].L_INV_CONTACT
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_CONTACT]
			( [D_INV_CONTACT_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;

		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_CONTACT
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_CONTACT
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_CONTACT
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_CONTACT';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_CONTACT
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_CONTACT_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_CONTACT AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_CONTACT_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_CONTACT'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_CONTACT_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_CONTACT AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_CONTACT_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_CONTACT]
		( [D_INV_CONTACT_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_CONTACT'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_CONTACT 

/*
CREATE TABLE [dbo].[LOOKUP_TABLE_N_INV_CONTACT](
	[PAGE_CASE_UID] [numeric](20, 0) NULL,
	[D_INV_CONTACT_KEY] [int] IDENTITY(1,1) NOT NULL
) ON [PRIMARY]

GO
*/


		--truncate table  dbo.LOOKUP_TABLE_N_INV_CONTACT ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_CONTACT;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_CONTACT
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_CONTACT];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_CONTACT_INC'; 

		--CREATE TABLE L_INV_CONTACT_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_CONTACT_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_CONTACT_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_CONTACT_KEY AS D_INV_CONTACT_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_CONTACT_KEY
		INTO rdb.dbo.L_INV_CONTACT_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_CONTACT AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_CONTACT AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;



		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_CONTACT_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_CONTACT_INC]
		( [D_INV_CONTACT_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_CONTACT_INC';

		UPDATE rdb.dbo.L_INV_CONTACT_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_CONTACT_INC
		  SET D_INV_CONTACT_KEY = COALESCE(D_INV_CONTACT_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_CONTACT where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_CONTACT ladmin
			 JOIN
			 rdb.dbo.L_INV_CONTACT_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_CONTACT_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_CONTACT new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_CONTACT( [PAGE_CASE_UID], [D_INV_CONTACT_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_CONTACT_KEY
			   FROM rdb.dbo.L_INV_CONTACT_INC
			   WHERE D_INV_CONTACT_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_CONTACT new key entry';

		INSERT INTO [RDB].[dbo].L_INV_CONTACT( [PAGE_CASE_UID], [D_INV_CONTACT_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_CONTACT_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_CONTACT;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_CONTACT where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_CONTACT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_CONTACT]
			( 
						 [D_INV_CONTACT_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_CONTACT dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT ladminI
			 ON dadmin.d_inv_CONTACT_key = ladminI.d_inv_CONTACT_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_CONTACT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_CONTACT;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_CONTACT;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_CONTACT];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.L_INV_CONTACT', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_DEATH]    Script Date: 1/21/2019 11:01:51 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_DEATH', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_DEATH];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_DEATH] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_DEATH'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_DEATH AS
		--*** Keys for New Page case where no Stgaing DEATH enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_DEATH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_DEATH;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_DEATH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_DEATH]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_DEATH
ON [dbo].S_INV_DEATH_INC
			( PAGE_CASE_UID
			);
		END;

		IF OBJECT_ID('rdb.dbo.L_INV_DEATH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_DEATH]
			( 
						 [D_INV_DEATH_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_DEATH
ON [dbo].L_INV_DEATH
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_DEATH]
			( [D_INV_DEATH_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;

		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_DEATH
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_DEATH
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_DEATH
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_DEATH';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_DEATH
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_DEATH_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_DEATH AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_DEATH_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_DEATH'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_DEATH AS
		--*** Keys for New Page case where no Stgaing DEATH enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_DEATH_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_DEATH AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_DEATH_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_DEATH]
		( [D_INV_DEATH_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_DEATH'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_DEATH ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_DEATH;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_DEATH
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_DEATH];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_DEATH_INC'; 

		--CREATE TABLE L_INV_DEATH_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_DEATH_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_DEATH_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_DEATH_KEY AS D_INV_DEATH_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_DEATH_KEY
		INTO rdb.dbo.L_INV_DEATH_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_DEATH AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_DEATH AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_DEATH_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_DEATH_INC]
		( [D_INV_DEATH_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_DEATH_INC';

		UPDATE rdb.dbo.L_INV_DEATH_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_DEATH_INC
		  SET D_INV_DEATH_KEY = COALESCE(D_INV_DEATH_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_DEATH where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_DEATH ladmin
			 JOIN
			 rdb.dbo.L_INV_DEATH_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_DEATH_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_DEATH new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_DEATH( [PAGE_CASE_UID], [D_INV_DEATH_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_DEATH_KEY
			   FROM rdb.dbo.L_INV_DEATH_INC
			   WHERE D_INV_DEATH_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_DEATH new key entry';

		INSERT INTO [RDB].[dbo].L_INV_DEATH( [PAGE_CASE_UID], [D_INV_DEATH_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_DEATH_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_DEATH;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_DEATH where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_DEATH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_DEATH]
			( 
						 [D_INV_DEATH_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_DEATH dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH ladminI
			 ON dadmin.d_inv_DEATH_key = ladminI.d_inv_DEATH_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_DEATH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_DEATH;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_DEATH;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_DEATH];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.L_INV_DEATH', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_EPIDEMIOLOGY]    Script Date: 1/21/2019 11:01:51 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_EPIDEMIOLOGY', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_EPIDEMIOLOGY];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_EPIDEMIOLOGY] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_EPIDEMIOLOGY'; 

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_EPIDEMIOLOGY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_EPIDEMIOLOGY]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_EPIDEMIOLOGY
ON [dbo].S_INV_EPIDEMIOLOGY
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_EPIDEMIOLOGY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_EPIDEMIOLOGY]
			( 
						 [D_INV_EPIDEMIOLOGY_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_EPIDEMIOLOGY
ON [dbo].L_INV_EPIDEMIOLOGY
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_EPIDEMIOLOGY]
			( [D_INV_EPIDEMIOLOGY_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_EPIDEMIOLOGY
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_EPIDEMIOLOGY
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_EPIDEMIOLOGY';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_EPIDEMIOLOGY_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_EPIDEMIOLOGY AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_EPIDEMIOLOGY_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_EPIDEMIOLOGY_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_EPIDEMIOLOGY AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_EPIDEMIOLOGY_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY]
		( [D_INV_EPIDEMIOLOGY_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_EPIDEMIOLOGY'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_EPIDEMIOLOGY ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_EPIDEMIOLOGY;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_EPIDEMIOLOGY
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_EPIDEMIOLOGY];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_EPIDEMIOLOGY_INC'; 

		--CREATE TABLE L_INV_EPIDEMIOLOGY_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_EPIDEMIOLOGY_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_EPIDEMIOLOGY_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_EPIDEMIOLOGY_KEY AS D_INV_EPIDEMIOLOGY_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_EPIDEMIOLOGY_KEY
		INTO rdb.dbo.L_INV_EPIDEMIOLOGY_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_EPIDEMIOLOGY AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;



		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_EPIDEMIOLOGY_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_EPIDEMIOLOGY_INC]
		( [D_INV_EPIDEMIOLOGY_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_EPIDEMIOLOGY_INC';

		UPDATE rdb.dbo.L_INV_EPIDEMIOLOGY_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_EPIDEMIOLOGY_INC
		  SET D_INV_EPIDEMIOLOGY_KEY = COALESCE(D_INV_EPIDEMIOLOGY_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_EPIDEMIOLOGY where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_EPIDEMIOLOGY ladmin
			 JOIN
			 rdb.dbo.L_INV_EPIDEMIOLOGY_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_EPIDEMIOLOGY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_EPIDEMIOLOGY new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_EPIDEMIOLOGY( [PAGE_CASE_UID], [D_INV_EPIDEMIOLOGY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_EPIDEMIOLOGY_KEY
			   FROM rdb.dbo.L_INV_EPIDEMIOLOGY_INC
			   WHERE D_INV_EPIDEMIOLOGY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_EPIDEMIOLOGY new key entry';

		INSERT INTO [RDB].[dbo].L_INV_EPIDEMIOLOGY( [PAGE_CASE_UID], [D_INV_EPIDEMIOLOGY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_EPIDEMIOLOGY_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_EPIDEMIOLOGY;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_EPIDEMIOLOGY where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_EPIDEMIOLOGY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_EPIDEMIOLOGY]
			( 
						 [D_INV_EPIDEMIOLOGY_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_EPIDEMIOLOGY dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY ladminI
			 ON dadmin.d_inv_EPIDEMIOLOGY_key = ladminI.d_inv_EPIDEMIOLOGY_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_EPIDEMIOLOGY;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_EPIDEMIOLOGY;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_EPIDEMIOLOGY];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.L_INV_EPIDEMIOLOGY', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_HIV]    Script Date: 1/21/2019 11:01:52 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_HIV', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_HIV];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_HIV] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_HIV'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_HIV AS
		--*** Keys for New Page case where no Stgaing HIV enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_HIV', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_HIV;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_HIV', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_HIV]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX [S_INV_HIV]
ON [dbo].[S_INV_HIV]
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_HIV', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_HIV]
			( 
						 [D_INV_HIV_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_HIV
ON [dbo].L_INV_HIV
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_HIV]
			( [D_INV_HIV_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_HIV
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_HIV
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_HIV
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_HIV';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_HIV
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_HIV_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_HIV AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_HIV_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_HIV'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_HIV AS
		--*** Keys for New Page case where no Stgaing HIV enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_HIV_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_HIV AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_HIV_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_HIV]
		( [D_INV_HIV_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_HIV'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_HIV ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_HIV;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_HIV
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_HIV];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_HIV_INC'; 

		--CREATE TABLE L_INV_HIV_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_HIV_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_HIV_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_HIV_KEY AS D_INV_HIV_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_HIV_KEY
		INTO rdb.dbo.L_INV_HIV_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_HIV AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_HIV AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;

		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_HIV_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_HIV_INC]
		( [D_INV_HIV_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_HIV_INC';

		UPDATE rdb.dbo.L_INV_HIV_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_HIV_INC
		  SET D_INV_HIV_KEY = COALESCE(D_INV_HIV_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_HIV where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_HIV ladmin
			 JOIN
			 rdb.dbo.L_INV_HIV_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_HIV_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_HIV new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_HIV( [PAGE_CASE_UID], [D_INV_HIV_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_HIV_KEY
			   FROM rdb.dbo.L_INV_HIV_INC
			   WHERE D_INV_HIV_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_HIV new key entry';

		INSERT INTO [RDB].[dbo].L_INV_HIV( [PAGE_CASE_UID], [D_INV_HIV_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_HIV_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_HIV;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_HIV where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_HIV', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_HIV]
			( 
						 [D_INV_HIV_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_HIV dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV ladminI
			 ON dadmin.d_inv_HIV_key = ladminI.d_inv_HIV_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_HIV', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_HIV;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_HIV;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_HIV];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.L_INV_HIV', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_ISOLATE_TRACKING]    Script Date: 1/21/2019 11:01:52 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_ISOLATE_TRACKING', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_ISOLATE_TRACKING];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_ISOLATE_TRACKING] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_ISOLATE_TRACKING'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_ISOLATE_TRACKING AS
		--*** Keys for New Page case where no Stgaing ISOLATE_TRACKING enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_ISOLATE_TRACKING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_ISOLATE_TRACKING]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX [S_INV_ISOLATE_TRACKING]
ON [dbo].[S_INV_ISOLATE_TRACKING]
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_ISOLATE_TRACKING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_ISOLATE_TRACKING]
			( 
						 [D_INV_ISOLATE_TRACKING_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_ISOLATE_TRACKING
ON [dbo].L_INV_ISOLATE_TRACKING
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_ISOLATE_TRACKING]
			( [D_INV_ISOLATE_TRACKING_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;

		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_ISOLATE_TRACKING
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_ISOLATE_TRACKING
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_ISOLATE_TRACKING';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_ISOLATE_TRACKING_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_ISOLATE_TRACKING AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_ISOLATE_TRACKING_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_ISOLATE_TRACKING AS
		--*** Keys for New Page case where no Stgaing ISOLATE_TRACKING enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_ISOLATE_TRACKING_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_ISOLATE_TRACKING AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_ISOLATE_TRACKING_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING]
		( [D_INV_ISOLATE_TRACKING_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_ISOLATE_TRACKING'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_ISOLATE_TRACKING ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_ISOLATE_TRACKING;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_ISOLATE_TRACKING
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_ISOLATE_TRACKING];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_ISOLATE_TRACKING_INC'; 

		--CREATE TABLE L_INV_ISOLATE_TRACKING_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_ISOLATE_TRACKING_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_ISOLATE_TRACKING_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_ISOLATE_TRACKING_KEY AS D_INV_ISOLATE_TRACKING_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_ISOLATE_TRACKING_KEY
		INTO rdb.dbo.L_INV_ISOLATE_TRACKING_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_ISOLATE_TRACKING AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_ISOLATE_TRACKING_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_ISOLATE_TRACKING_INC]
		( [D_INV_ISOLATE_TRACKING_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_ISOLATE_TRACKING_INC';

		UPDATE rdb.dbo.L_INV_ISOLATE_TRACKING_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_ISOLATE_TRACKING_INC
		  SET D_INV_ISOLATE_TRACKING_KEY = COALESCE(D_INV_ISOLATE_TRACKING_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_ISOLATE_TRACKING where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_ISOLATE_TRACKING ladmin
			 JOIN
			 rdb.dbo.L_INV_ISOLATE_TRACKING_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_ISOLATE_TRACKING_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_ISOLATE_TRACKING new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_ISOLATE_TRACKING( [PAGE_CASE_UID], [D_INV_ISOLATE_TRACKING_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_ISOLATE_TRACKING_KEY
			   FROM rdb.dbo.L_INV_ISOLATE_TRACKING_INC
			   WHERE D_INV_ISOLATE_TRACKING_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_ISOLATE_TRACKING new key entry';

		INSERT INTO [RDB].[dbo].L_INV_ISOLATE_TRACKING( [PAGE_CASE_UID], [D_INV_ISOLATE_TRACKING_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_ISOLATE_TRACKING_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_ISOLATE_TRACKING;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_ISOLATE_TRACKING where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_ISOLATE_TRACKING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_ISOLATE_TRACKING]
			( 
						 [D_INV_ISOLATE_TRACKING_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_ISOLATE_TRACKING dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING ladminI
			 ON dadmin.d_inv_ISOLATE_TRACKING_key = ladminI.d_inv_ISOLATE_TRACKING_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_ISOLATE_TRACKING;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_ISOLATE_TRACKING;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_ISOLATE_TRACKING];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.L_INV_ISOLATE_TRACKING', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_LAB_FINDING]    Script Date: 1/21/2019 11:01:52 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_LAB_FINDING', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_LAB_FINDING];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_LAB_FINDING] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_LAB_FINDING'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_LAB_FINDING AS
		--*** Keys for New Page case where no Stgaing LAB_FINDING enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_LAB_FINDING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_LAB_FINDING]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX [S_INV_LAB_FINDING]
ON [dbo].[S_INV_LAB_FINDING]
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_LAB_FINDING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_LAB_FINDING]
			( 
						 [D_INV_LAB_FINDING_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_LAB_FINDING
ON [dbo].L_INV_LAB_FINDING
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_LAB_FINDING]
			( [D_INV_LAB_FINDING_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_LAB_FINDING
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_LAB_FINDING
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_LAB_FINDING';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_LAB_FINDING_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_LAB_FINDING AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_LAB_FINDING_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_LAB_FINDING AS
		--*** Keys for New Page case where no Stgaing LAB_FINDING enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_LAB_FINDING_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_LAB_FINDING AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_LAB_FINDING_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING]
		( [D_INV_LAB_FINDING_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_LAB_FINDING'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_LAB_FINDING ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_LAB_FINDING;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_LAB_FINDING
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_LAB_FINDING];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_LAB_FINDING_INC'; 

		--CREATE TABLE L_INV_LAB_FINDING_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_LAB_FINDING_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_LAB_FINDING_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_LAB_FINDING_KEY AS D_INV_LAB_FINDING_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_LAB_FINDING_KEY
		INTO rdb.dbo.L_INV_LAB_FINDING_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_LAB_FINDING AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_LAB_FINDING_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_LAB_FINDING_INC]
		( [D_INV_LAB_FINDING_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_LAB_FINDING_INC';

		UPDATE rdb.dbo.L_INV_LAB_FINDING_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_LAB_FINDING_INC
		  SET D_INV_LAB_FINDING_KEY = COALESCE(D_INV_LAB_FINDING_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_LAB_FINDING where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_LAB_FINDING ladmin
			 JOIN
			 rdb.dbo.L_INV_LAB_FINDING_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_LAB_FINDING_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_LAB_FINDING new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_LAB_FINDING( [PAGE_CASE_UID], [D_INV_LAB_FINDING_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_LAB_FINDING_KEY
			   FROM rdb.dbo.L_INV_LAB_FINDING_INC
			   WHERE D_INV_LAB_FINDING_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_LAB_FINDING new key entry';

		INSERT INTO [RDB].[dbo].L_INV_LAB_FINDING( [PAGE_CASE_UID], [D_INV_LAB_FINDING_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_LAB_FINDING_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_LAB_FINDING;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_LAB_FINDING where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_LAB_FINDING', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_LAB_FINDING]
			( 
						 [D_INV_LAB_FINDING_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_LAB_FINDING dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING ladminI
			 ON dadmin.d_inv_LAB_FINDING_key = ladminI.d_inv_LAB_FINDING_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_LAB_FINDING;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_LAB_FINDING;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_LAB_FINDING];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.L_INV_LAB_FINDING', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_MEDICAL_HISTORY]    Script Date: 1/21/2019 11:01:53 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_MEDICAL_HISTORY', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_MEDICAL_HISTORY];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_MEDICAL_HISTORY] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_MEDICAL_HISTORY'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_MEDICAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_MEDICAL_HISTORY]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX [S_INV_MEDICAL_HISTORY]
ON [dbo].[S_INV_MEDICAL_HISTORY]
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_MEDICAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_MEDICAL_HISTORY]
			( 
						 [D_INV_MEDICAL_HISTORY_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX [L_INV_MEDICAL_HISTORY]
ON [dbo].[L_INV_MEDICAL_HISTORY]
			( PAGE_CASE_UID
			);
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_MEDICAL_HISTORY
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_MEDICAL_HISTORY
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_MEDICAL_HISTORY';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_MEDICAL_HISTORY_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_MEDICAL_HISTORY AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_MEDICAL_HISTORY_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY'; 

	
		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_MEDICAL_HISTORY_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_MEDICAL_HISTORY AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_MEDICAL_HISTORY_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY]
		( [D_INV_MEDICAL_HISTORY_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_MEDICAL_HISTORY'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_MEDICAL_HISTORY ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_MEDICAL_HISTORY;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_MEDICAL_HISTORY
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_MEDICAL_HISTORY];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_MEDICAL_HISTORY_INC'; 

		--CREATE TABLE L_INV_MEDICAL_HISTORY_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_MEDICAL_HISTORY_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_MEDICAL_HISTORY_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_MEDICAL_HISTORY_KEY AS D_INV_MEDICAL_HISTORY_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_MEDICAL_HISTORY_KEY
		INTO rdb.dbo.L_INV_MEDICAL_HISTORY_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_MEDICAL_HISTORY AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;

		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_MEDICAL_HISTORY_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_MEDICAL_HISTORY_INC]
		( [D_INV_MEDICAL_HISTORY_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_MEDICAL_HISTORY_INC';

		UPDATE rdb.dbo.L_INV_MEDICAL_HISTORY_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_MEDICAL_HISTORY_INC
		  SET D_INV_MEDICAL_HISTORY_KEY = COALESCE(D_INV_MEDICAL_HISTORY_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_MEDICAL_HISTORY where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_MEDICAL_HISTORY ladmin
			 JOIN
			 rdb.dbo.L_INV_MEDICAL_HISTORY_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_MEDICAL_HISTORY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_MEDICAL_HISTORY new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_MEDICAL_HISTORY( [PAGE_CASE_UID], [D_INV_MEDICAL_HISTORY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_MEDICAL_HISTORY_KEY
			   FROM rdb.dbo.L_INV_MEDICAL_HISTORY_INC
			   WHERE D_INV_MEDICAL_HISTORY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_MEDICAL_HISTORY new key entry';

		INSERT INTO [RDB].[dbo].L_INV_MEDICAL_HISTORY( [PAGE_CASE_UID], [D_INV_MEDICAL_HISTORY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_MEDICAL_HISTORY_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_MEDICAL_HISTORY;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_MEDICAL_HISTORY where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_MEDICAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_MEDICAL_HISTORY]
			( 
						 [D_INV_MEDICAL_HISTORY_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_MEDICAL_HISTORY dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY ladminI
			 ON dadmin.d_inv_MEDICAL_HISTORY_key = ladminI.d_inv_MEDICAL_HISTORY_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_MEDICAL_HISTORY;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MEDICAL_HISTORY;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_MEDICAL_HISTORY];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.L_INV_MEDICAL_HISTORY', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_MOTHER]    Script Date: 1/21/2019 11:01:53 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_MOTHER', 'P') IS NOT NULL










BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_MOTHER];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_MOTHER] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_MOTHER'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_MOTHER AS
		--*** Keys for New Page case where no Stgaing MOTHER enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_MOTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_MOTHER;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_MOTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_MOTHER]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_MOTHER
ON [dbo].S_INV_MOTHER
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_MOTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_MOTHER]
			( 
						 [D_INV_MOTHER_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_MOTHER
ON [dbo].L_INV_MOTHER
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_MOTHER]
			( [D_INV_MOTHER_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_MOTHER
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_MOTHER
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_MOTHER
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_MOTHER';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_MOTHER
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_MOTHER_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_MOTHER AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_MOTHER_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_MOTHER'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_MOTHER AS
		--*** Keys for New Page case where no Stgaing MOTHER enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_MOTHER_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_MOTHER AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_MOTHER_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_MOTHER]
		( [D_INV_MOTHER_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_MOTHER'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_MOTHER ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_MOTHER;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_MOTHER
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_MOTHER];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_MOTHER_INC'; 

		--CREATE TABLE L_INV_MOTHER_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_MOTHER_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_MOTHER_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_MOTHER_KEY AS D_INV_MOTHER_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_MOTHER_KEY
		INTO rdb.dbo.L_INV_MOTHER_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_MOTHER AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_MOTHER AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;


		SELECT @RowCount_no = @@ROWCOUNT;

		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_MOTHER_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_MOTHER_INC]
		( [D_INV_MOTHER_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_MOTHER_INC';

		UPDATE rdb.dbo.L_INV_MOTHER_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_MOTHER_INC
		  SET D_INV_MOTHER_KEY = COALESCE(D_INV_MOTHER_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_MOTHER where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_MOTHER ladmin
			 JOIN
			 rdb.dbo.L_INV_MOTHER_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_MOTHER_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_MOTHER new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_MOTHER( [PAGE_CASE_UID], [D_INV_MOTHER_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_MOTHER_KEY
			   FROM rdb.dbo.L_INV_MOTHER_INC
			   WHERE D_INV_MOTHER_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_MOTHER new key entry';

		INSERT INTO [RDB].[dbo].L_INV_MOTHER( [PAGE_CASE_UID], [D_INV_MOTHER_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_MOTHER_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_MOTHER;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_MOTHER where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_MOTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_MOTHER]
			( 
						 [D_INV_MOTHER_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_MOTHER dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER ladminI
			 ON dadmin.d_inv_MOTHER_key = ladminI.d_inv_MOTHER_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_MOTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_MOTHER;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_MOTHER;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_MOTHER];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.L_INV_MOTHER', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_OTHER]    Script Date: 1/21/2019 11:01:53 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_OTHER', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_OTHER];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_OTHER] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_OTHER'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_OTHER AS
		--*** Keys for New Page case where no Stgaing OTHER enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_OTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_OTHER;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_OTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_OTHER]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_OTHER
ON [dbo].S_INV_OTHER
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_OTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_OTHER]
			( 
						 [D_INV_OTHER_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_OTHER
ON [dbo].L_INV_OTHER
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_OTHER]
			( [D_INV_OTHER_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_OTHER
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_OTHER
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_OTHER
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_OTHER';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_OTHER
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_OTHER_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_OTHER AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_OTHER_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_OTHER'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_OTHER AS
		--*** Keys for New Page case where no Stgaing OTHER enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_OTHER_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_OTHER AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_OTHER_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_OTHER]
		( [D_INV_OTHER_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_OTHER'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_OTHER 

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_OTHER;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_OTHER
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_OTHER];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_OTHER_INC'; 

		--CREATE TABLE L_INV_OTHER_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_OTHER_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_OTHER_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_OTHER_KEY AS D_INV_OTHER_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_OTHER_KEY
		INTO rdb.dbo.L_INV_OTHER_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_OTHER AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_OTHER AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_OTHER_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_OTHER_INC]
		( [D_INV_OTHER_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_OTHER_INC';

		UPDATE rdb.dbo.L_INV_OTHER_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_OTHER_INC
		  SET D_INV_OTHER_KEY = COALESCE(D_INV_OTHER_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_OTHER where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_OTHER ladmin
			 JOIN
			 rdb.dbo.L_INV_OTHER_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_OTHER_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_OTHER new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_OTHER( [PAGE_CASE_UID], [D_INV_OTHER_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_OTHER_KEY
			   FROM rdb.dbo.L_INV_OTHER_INC
			   WHERE D_INV_OTHER_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_OTHER new key entry';

		INSERT INTO [RDB].[dbo].L_INV_OTHER( [PAGE_CASE_UID], [D_INV_OTHER_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_OTHER_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_OTHER;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_OTHER where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_OTHER', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_OTHER]
			( 
						 [D_INV_OTHER_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_OTHER dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER ladminI
			 ON dadmin.d_inv_OTHER_key = ladminI.d_inv_OTHER_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_OTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_OTHER;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_OTHER;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_OTHER];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.L_INV_OTHER', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_PATIENT_OBS]    Script Date: 1/21/2019 11:01:54 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_PATIENT_OBS', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_PATIENT_OBS];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_PATIENT_OBS] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_PATIENT_OBS'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_PATIENT_OBS AS
		--*** Keys for New Page case where no Stgaing PATIENT_OBS enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_PATIENT_OBS', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_PATIENT_OBS]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_PATIENT_OBS
ON [dbo].S_INV_PATIENT_OBS
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_PATIENT_OBS', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_PATIENT_OBS]
			( 
						 [D_INV_PATIENT_OBS_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_PATIENT_OBS
ON [dbo].L_INV_PATIENT_OBS
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_PATIENT_OBS]
			( [D_INV_PATIENT_OBS_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_PATIENT_OBS
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_PATIENT_OBS
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_PATIENT_OBS';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_PATIENT_OBS_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_PATIENT_OBS AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_PATIENT_OBS_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_PATIENT_OBS AS
		--*** Keys for New Page case where no Stgaing PATIENT_OBS enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_PATIENT_OBS_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_PATIENT_OBS AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_PATIENT_OBS_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS]
		( [D_INV_PATIENT_OBS_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_PATIENT_OBS'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_PATIENT_OBS ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_PATIENT_OBS;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_PATIENT_OBS
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_PATIENT_OBS];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_PATIENT_OBS_INC'; 

		--CREATE TABLE L_INV_PATIENT_OBS_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_PATIENT_OBS_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_PATIENT_OBS_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_PATIENT_OBS_KEY AS D_INV_PATIENT_OBS_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_PATIENT_OBS_KEY
		INTO rdb.dbo.L_INV_PATIENT_OBS_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_PATIENT_OBS AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_PATIENT_OBS_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_PATIENT_OBS_INC]
		( [D_INV_PATIENT_OBS_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_PATIENT_OBS_INC';

		UPDATE rdb.dbo.L_INV_PATIENT_OBS_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_PATIENT_OBS_INC
		  SET D_INV_PATIENT_OBS_KEY = COALESCE(D_INV_PATIENT_OBS_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_PATIENT_OBS where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_PATIENT_OBS ladmin
			 JOIN
			 rdb.dbo.L_INV_PATIENT_OBS_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_PATIENT_OBS_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_PATIENT_OBS new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_PATIENT_OBS( [PAGE_CASE_UID], [D_INV_PATIENT_OBS_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_PATIENT_OBS_KEY
			   FROM rdb.dbo.L_INV_PATIENT_OBS_INC
			   WHERE D_INV_PATIENT_OBS_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_PATIENT_OBS new key entry';

		INSERT INTO [RDB].[dbo].L_INV_PATIENT_OBS( [PAGE_CASE_UID], [D_INV_PATIENT_OBS_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_PATIENT_OBS_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_PATIENT_OBS;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_PATIENT_OBS where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_PATIENT_OBS', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_PATIENT_OBS]
			( 
						 [D_INV_PATIENT_OBS_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_PATIENT_OBS dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS ladminI
			 ON dadmin.d_inv_PATIENT_OBS_key = ladminI.d_inv_PATIENT_OBS_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_PATIENT_OBS;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PATIENT_OBS;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_PATIENT_OBS];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.L_INV_PATIENT_OBS', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_PREGNANCY_BIRTH]    Script Date: 1/21/2019 11:01:54 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_PREGNANCY_BIRTH', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_PREGNANCY_BIRTH];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_PREGNANCY_BIRTH] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_PREGNANCY_BIRTH'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_PREGNANCY_BIRTH AS
		--*** Keys for New Page case where no Stgaing PREGNANCY_BIRTH enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_PREGNANCY_BIRTH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_PREGNANCY_BIRTH]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_PREGNANCY_BIRTH
ON [dbo].S_INV_PREGNANCY_BIRTH
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_PREGNANCY_BIRTH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_PREGNANCY_BIRTH]
			( 
						 [D_INV_PREGNANCY_BIRTH_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_PREGNANCY_BIRTH
ON [dbo].L_INV_PREGNANCY_BIRTH
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_PREGNANCY_BIRTH]
			( [D_INV_PREGNANCY_BIRTH_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_PREGNANCY_BIRTH
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_PREGNANCY_BIRTH
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_PREGNANCY_BIRTH';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_PREGNANCY_BIRTH_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_PREGNANCY_BIRTH AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_PREGNANCY_BIRTH_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_PREGNANCY_BIRTH AS
		--*** Keys for New Page case where no Stgaing PREGNANCY_BIRTH enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_PREGNANCY_BIRTH_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_PREGNANCY_BIRTH AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_PREGNANCY_BIRTH_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH]
		( [D_INV_PREGNANCY_BIRTH_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_PREGNANCY_BIRTH];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_PREGNANCY_BIRTH_INC'; 

		--CREATE TABLE L_INV_PREGNANCY_BIRTH_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_PREGNANCY_BIRTH_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_PREGNANCY_BIRTH_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_PREGNANCY_BIRTH_KEY AS D_INV_PREGNANCY_BIRTH_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_PREGNANCY_BIRTH_KEY
		INTO rdb.dbo.L_INV_PREGNANCY_BIRTH_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_PREGNANCY_BIRTH_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_PREGNANCY_BIRTH_INC]
		( [D_INV_PREGNANCY_BIRTH_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_PREGNANCY_BIRTH_INC';

		UPDATE rdb.dbo.L_INV_PREGNANCY_BIRTH_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_PREGNANCY_BIRTH_INC
		  SET D_INV_PREGNANCY_BIRTH_KEY = COALESCE(D_INV_PREGNANCY_BIRTH_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_PREGNANCY_BIRTH where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_PREGNANCY_BIRTH ladmin
			 JOIN
			 rdb.dbo.L_INV_PREGNANCY_BIRTH_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_PREGNANCY_BIRTH_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_PREGNANCY_BIRTH new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_PREGNANCY_BIRTH( [PAGE_CASE_UID], [D_INV_PREGNANCY_BIRTH_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_PREGNANCY_BIRTH_KEY
			   FROM rdb.dbo.L_INV_PREGNANCY_BIRTH_INC
			   WHERE D_INV_PREGNANCY_BIRTH_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_PREGNANCY_BIRTH new key entry';

		INSERT INTO [RDB].[dbo].L_INV_PREGNANCY_BIRTH( [PAGE_CASE_UID], [D_INV_PREGNANCY_BIRTH_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_PREGNANCY_BIRTH_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_PREGNANCY_BIRTH where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_PREGNANCY_BIRTH', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_PREGNANCY_BIRTH]
			( 
						 [D_INV_PREGNANCY_BIRTH_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_PREGNANCY_BIRTH dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH ladminI
			 ON dadmin.d_inv_PREGNANCY_BIRTH_key = ladminI.d_inv_PREGNANCY_BIRTH_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_PREGNANCY_BIRTH;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_PREGNANCY_BIRTH;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_PREGNANCY_BIRTH];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.L_INV_PREGNANCY_BIRTH', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_RESIDENCY]    Script Date: 1/21/2019 11:01:55 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_RESIDENCY', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_RESIDENCY];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_RESIDENCY] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_RESIDENCY'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_RESIDENCY AS
		--*** Keys for New Page case where no Stgaing RESIDENCY enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_RESIDENCY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_RESIDENCY]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_RESIDENCY
ON [dbo].S_INV_RESIDENCY
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_RESIDENCY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_RESIDENCY]
			( 
						 [D_INV_RESIDENCY_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_RESIDENCY
ON [dbo].L_INV_RESIDENCY
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_RESIDENCY]
			( [D_INV_RESIDENCY_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_RESIDENCY
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_RESIDENCY
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_RESIDENCY';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_RESIDENCY_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_RESIDENCY AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_RESIDENCY_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_RESIDENCY AS
		--*** Keys for New Page case where no Stgaing RESIDENCY enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_RESIDENCY_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_RESIDENCY AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_RESIDENCY_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY]
		( [D_INV_RESIDENCY_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_RESIDENCY'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_RESIDENCY 

		--truncate table  dbo.LOOKUP_TABLE_N_INV_RESIDENCY ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_RESIDENCY;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_RESIDENCY
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_RESIDENCY];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_RESIDENCY_INC'; 

		--CREATE TABLE L_INV_RESIDENCY_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_RESIDENCY_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_RESIDENCY_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_RESIDENCY_KEY AS D_INV_RESIDENCY_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_RESIDENCY_KEY
		INTO rdb.dbo.L_INV_RESIDENCY_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_RESIDENCY AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_RESIDENCY_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_RESIDENCY_INC]
		( [D_INV_RESIDENCY_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_RESIDENCY_INC';

		UPDATE rdb.dbo.L_INV_RESIDENCY_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_RESIDENCY_INC
		  SET D_INV_RESIDENCY_KEY = COALESCE(D_INV_RESIDENCY_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_RESIDENCY where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_RESIDENCY ladmin
			 JOIN
			 rdb.dbo.L_INV_RESIDENCY_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_RESIDENCY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_RESIDENCY new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_RESIDENCY( [PAGE_CASE_UID], [D_INV_RESIDENCY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_RESIDENCY_KEY
			   FROM rdb.dbo.L_INV_RESIDENCY_INC
			   WHERE D_INV_RESIDENCY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_RESIDENCY new key entry';

		INSERT INTO [RDB].[dbo].L_INV_RESIDENCY( [PAGE_CASE_UID], [D_INV_RESIDENCY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_RESIDENCY_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_RESIDENCY;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_RESIDENCY where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_RESIDENCY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_RESIDENCY]
			( 
						 [D_INV_RESIDENCY_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_RESIDENCY dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY ladminI
			 ON dadmin.d_inv_RESIDENCY_key = ladminI.d_inv_RESIDENCY_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_RESIDENCY;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RESIDENCY;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_RESIDENCY];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.L_INV_RESIDENCY', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_RISK_FACTOR]    Script Date: 1/21/2019 11:01:55 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_RISK_FACTOR', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_RISK_FACTOR];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_RISK_FACTOR] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_RISK_FACTOR'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_RISK_FACTOR AS
		--*** Keys for New Page case where no Stgaing RISK_FACTOR enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_RISK_FACTOR', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_RISK_FACTOR]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_RISK_FACTOR
ON [dbo].S_INV_RISK_FACTOR
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_RISK_FACTOR', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_RISK_FACTOR]
			( 
						 [D_INV_RISK_FACTOR_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_RISK_FACTOR
ON [dbo].L_INV_RISK_FACTOR
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_RISK_FACTOR]
			( [D_INV_RISK_FACTOR_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_RISK_FACTOR
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_RISK_FACTOR
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_RISK_FACTOR';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_RISK_FACTOR_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_RISK_FACTOR AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_RISK_FACTOR_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_RISK_FACTOR AS
		--*** Keys for New Page case where no Stgaing RISK_FACTOR enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_RISK_FACTOR_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_RISK_FACTOR AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_RISK_FACTOR_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR]
		( [D_INV_RISK_FACTOR_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_RISK_FACTOR'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_RISK_FACTOR 


		DELETE FROM dbo.LOOKUP_TABLE_N_INV_RISK_FACTOR;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_RISK_FACTOR
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_RISK_FACTOR];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_RISK_FACTOR_INC'; 

		--CREATE TABLE L_INV_RISK_FACTOR_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_RISK_FACTOR_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_RISK_FACTOR_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_RISK_FACTOR_KEY AS D_INV_RISK_FACTOR_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_RISK_FACTOR_KEY
		INTO rdb.dbo.L_INV_RISK_FACTOR_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_RISK_FACTOR AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_RISK_FACTOR_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_RISK_FACTOR_INC]
		( [D_INV_RISK_FACTOR_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_RISK_FACTOR_INC';

		UPDATE rdb.dbo.L_INV_RISK_FACTOR_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_RISK_FACTOR_INC
		  SET D_INV_RISK_FACTOR_KEY = COALESCE(D_INV_RISK_FACTOR_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_RISK_FACTOR where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_RISK_FACTOR ladmin
			 JOIN
			 rdb.dbo.L_INV_RISK_FACTOR_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_RISK_FACTOR_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_RISK_FACTOR new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_RISK_FACTOR( [PAGE_CASE_UID], [D_INV_RISK_FACTOR_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_RISK_FACTOR_KEY
			   FROM rdb.dbo.L_INV_RISK_FACTOR_INC
			   WHERE D_INV_RISK_FACTOR_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_RISK_FACTOR new key entry';

		INSERT INTO [RDB].[dbo].L_INV_RISK_FACTOR( [PAGE_CASE_UID], [D_INV_RISK_FACTOR_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_RISK_FACTOR_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_RISK_FACTOR;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_RISK_FACTOR where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_RISK_FACTOR', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_RISK_FACTOR]
			( 
						 [D_INV_RISK_FACTOR_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_RISK_FACTOR dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR ladminI
			 ON dadmin.d_inv_RISK_FACTOR_key = ladminI.d_inv_RISK_FACTOR_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_RISK_FACTOR;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_RISK_FACTOR;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_RISK_FACTOR];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.L_INV_RISK_FACTOR', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_SOCIAL_HISTORY]    Script Date: 1/21/2019 11:01:55 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_SOCIAL_HISTORY', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_SOCIAL_HISTORY];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_SOCIAL_HISTORY] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_SOCIAL_HISTORY'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_SOCIAL_HISTORY AS
		--*** Keys for New Page case where no Stgaing SOCIAL_HISTORY enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_SOCIAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_SOCIAL_HISTORY]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];
       CREATE INDEX  S_INV_SOCIAL_HISTORY ON [dbo].S_INV_SOCIAL_HISTORY(PAGE_CASE_UID);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_SOCIAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_SOCIAL_HISTORY]
			( 
						 [D_INV_SOCIAL_HISTORY_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_SOCIAL_HISTORY
ON [dbo].L_INV_SOCIAL_HISTORY
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_SOCIAL_HISTORY]
			( [D_INV_SOCIAL_HISTORY_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_SOCIAL_HISTORY
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_SOCIAL_HISTORY
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_SOCIAL_HISTORY';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_SOCIAL_HISTORY_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_SOCIAL_HISTORY AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_SOCIAL_HISTORY_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_SOCIAL_HISTORY AS
		--*** Keys for New Page case where no Stgaing SOCIAL_HISTORY enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_SOCIAL_HISTORY_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_SOCIAL_HISTORY AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_SOCIAL_HISTORY_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY]
		( [D_INV_SOCIAL_HISTORY_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_SOCIAL_HISTORY'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_SOCIAL_HISTORY 


		DELETE FROM dbo.LOOKUP_TABLE_N_INV_SOCIAL_HISTORY;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_SOCIAL_HISTORY
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_SOCIAL_HISTORY];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_SOCIAL_HISTORY_INC'; 

		--CREATE TABLE L_INV_SOCIAL_HISTORY_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_SOCIAL_HISTORY_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_SOCIAL_HISTORY_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_SOCIAL_HISTORY_KEY AS D_INV_SOCIAL_HISTORY_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_SOCIAL_HISTORY_KEY
		INTO rdb.dbo.L_INV_SOCIAL_HISTORY_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_SOCIAL_HISTORY AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_SOCIAL_HISTORY_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_SOCIAL_HISTORY_INC]
		( [D_INV_SOCIAL_HISTORY_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_SOCIAL_HISTORY_INC';

		UPDATE rdb.dbo.L_INV_SOCIAL_HISTORY_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_SOCIAL_HISTORY_INC
		  SET D_INV_SOCIAL_HISTORY_KEY = COALESCE(D_INV_SOCIAL_HISTORY_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_SOCIAL_HISTORY where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_SOCIAL_HISTORY ladmin
			 JOIN
			 rdb.dbo.L_INV_SOCIAL_HISTORY_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_SOCIAL_HISTORY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_SOCIAL_HISTORY new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_SOCIAL_HISTORY( [PAGE_CASE_UID], [D_INV_SOCIAL_HISTORY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_SOCIAL_HISTORY_KEY
			   FROM rdb.dbo.L_INV_SOCIAL_HISTORY_INC
			   WHERE D_INV_SOCIAL_HISTORY_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_SOCIAL_HISTORY new key entry';

		INSERT INTO [RDB].[dbo].L_INV_SOCIAL_HISTORY( [PAGE_CASE_UID], [D_INV_SOCIAL_HISTORY_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_SOCIAL_HISTORY_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_SOCIAL_HISTORY;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_SOCIAL_HISTORY where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_SOCIAL_HISTORY', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_SOCIAL_HISTORY]
			( 
						 [D_INV_SOCIAL_HISTORY_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_SOCIAL_HISTORY dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY ladminI
			 ON dadmin.d_inv_SOCIAL_HISTORY_key = ladminI.d_inv_SOCIAL_HISTORY_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_SOCIAL_HISTORY;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SOCIAL_HISTORY;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_SOCIAL_HISTORY];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.L_INV_SOCIAL_HISTORY', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_SYMPTOM]    Script Date: 1/21/2019 11:01:56 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_SYMPTOM', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_SYMPTOM];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_SYMPTOM] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_SYMPTOM'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_SYMPTOM', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_SYMPTOM]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_SYMPTOM
ON [dbo].S_INV_SYMPTOM
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_SYMPTOM', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_SYMPTOM]
			( 
						 [D_INV_SYMPTOM_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_SYMPTOM
ON [dbo].L_INV_SYMPTOM
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_SYMPTOM]
			( [D_INV_SYMPTOM_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_SYMPTOM
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_SYMPTOM
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_SYMPTOM';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_SYMPTOM_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_SYMPTOM AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_SYMPTOM_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_SYMPTOM_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_SYMPTOM AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_SYMPTOM_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM]
		( [D_INV_SYMPTOM_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_SYMPTOM'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_SYMPTOM 


		DELETE FROM dbo.LOOKUP_TABLE_N_INV_SYMPTOM;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_SYMPTOM
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_SYMPTOM];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_SYMPTOM_INC'; 

		--CREATE TABLE L_INV_SYMPTOM_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_SYMPTOM_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_SYMPTOM_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_SYMPTOM_KEY AS D_INV_SYMPTOM_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_SYMPTOM_KEY
		INTO rdb.dbo.L_INV_SYMPTOM_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_SYMPTOM AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_SYMPTOM_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_SYMPTOM_INC]
		( [D_INV_SYMPTOM_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_SYMPTOM_INC';

		UPDATE rdb.dbo.L_INV_SYMPTOM_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_SYMPTOM_INC
		  SET D_INV_SYMPTOM_KEY = COALESCE(D_INV_SYMPTOM_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_SYMPTOM where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_SYMPTOM ladmin
			 JOIN
			 rdb.dbo.L_INV_SYMPTOM_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_SYMPTOM_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_SYMPTOM new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_SYMPTOM( [PAGE_CASE_UID], [D_INV_SYMPTOM_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_SYMPTOM_KEY
			   FROM rdb.dbo.L_INV_SYMPTOM_INC
			   WHERE D_INV_SYMPTOM_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_SYMPTOM new key entry';

		INSERT INTO [RDB].[dbo].L_INV_SYMPTOM( [PAGE_CASE_UID], [D_INV_SYMPTOM_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_SYMPTOM_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_SYMPTOM;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_SYMPTOM where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_SYMPTOM', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_SYMPTOM]
			( 
						 [D_INV_SYMPTOM_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_SYMPTOM dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM ladminI
			 ON dadmin.d_inv_SYMPTOM_key = ladminI.d_inv_SYMPTOM_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_SYMPTOM;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_SYMPTOM;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_SYMPTOM];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.L_INV_SYMPTOM', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_TRAVEL]    Script Date: 1/21/2019 11:01:56 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_TRAVEL', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_TRAVEL];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_TRAVEL] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_TRAVEL'; 

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_TRAVEL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_TRAVEL]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_TRAVEL
ON [dbo].S_INV_TRAVEL
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_TRAVEL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_TRAVEL]
			( 
						 [D_INV_TRAVEL_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_TRAVEL
ON [dbo].L_INV_TRAVEL
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_TRAVEL]
			( [D_INV_TRAVEL_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_TRAVEL
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_TRAVEL
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_TRAVEL';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_TRAVEL_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_TRAVEL AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_TRAVEL_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_TRAVEL'; 

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_TRAVEL_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_TRAVEL AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_TRAVEL_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_TRAVEL]
		( [D_INV_TRAVEL_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_TRAVEL'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_TRAVEL 


		DELETE FROM dbo.LOOKUP_TABLE_N_INV_TRAVEL;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_TRAVEL
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_TRAVEL];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_TRAVEL_INC'; 

		--CREATE TABLE L_INV_TRAVEL_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_TRAVEL_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_TRAVEL_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_TRAVEL_KEY AS D_INV_TRAVEL_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_TRAVEL_KEY
		INTO rdb.dbo.L_INV_TRAVEL_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_TRAVEL AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_TRAVEL_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_TRAVEL_INC]
		( [D_INV_TRAVEL_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_TRAVEL_INC';

		UPDATE rdb.dbo.L_INV_TRAVEL_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_TRAVEL_INC
		  SET D_INV_TRAVEL_KEY = COALESCE(D_INV_TRAVEL_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_TRAVEL where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_TRAVEL ladmin
			 JOIN
			 rdb.dbo.L_INV_TRAVEL_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_TRAVEL_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_TRAVEL new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_TRAVEL( [PAGE_CASE_UID], [D_INV_TRAVEL_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_TRAVEL_KEY
			   FROM rdb.dbo.L_INV_TRAVEL_INC
			   WHERE D_INV_TRAVEL_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_TRAVEL new key entry';

		INSERT INTO [RDB].[dbo].L_INV_TRAVEL( [PAGE_CASE_UID], [D_INV_TRAVEL_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_TRAVEL_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_TRAVEL;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_TRAVEL where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_TRAVEL', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_TRAVEL]
			( 
						 [D_INV_TRAVEL_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_TRAVEL dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL ladminI
			 ON dadmin.d_inv_TRAVEL_key = ladminI.d_inv_TRAVEL_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_TRAVEL;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TRAVEL;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_TRAVEL];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.L_INV_TRAVEL', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_TREATMENT]    Script Date: 1/21/2019 11:01:56 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_TREATMENT', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_TREATMENT];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_TREATMENT] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_TREATMENT'; 

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_TREATMENT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_TREATMENT]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_TREATMENT
ON [dbo].S_INV_TREATMENT
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_TREATMENT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_TREATMENT]
			( 
						 [D_INV_TREATMENT_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_TREATMENT
ON [dbo].L_INV_TREATMENT
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_TREATMENT]
			( [D_INV_TREATMENT_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_TREATMENT
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_TREATMENT
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_TREATMENT';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_TREATMENT_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_TREATMENT AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_TREATMENT_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_TREATMENT'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_TREATMENT_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_TREATMENT AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_TREATMENT_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_TREATMENT]
		( [D_INV_TREATMENT_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_TREATMENT'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_TREATMENT 


		DELETE FROM dbo.LOOKUP_TABLE_N_INV_TREATMENT;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_TREATMENT
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_TREATMENT];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_TREATMENT_INC'; 

		--CREATE TABLE L_INV_TREATMENT_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_TREATMENT_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_TREATMENT_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_TREATMENT_KEY AS D_INV_TREATMENT_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_TREATMENT_KEY
		INTO rdb.dbo.L_INV_TREATMENT_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_TREATMENT AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_TREATMENT_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_TREATMENT_INC]
		( [D_INV_TREATMENT_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_TREATMENT_INC';

		UPDATE rdb.dbo.L_INV_TREATMENT_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_TREATMENT_INC
		  SET D_INV_TREATMENT_KEY = COALESCE(D_INV_TREATMENT_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_TREATMENT where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_TREATMENT ladmin
			 JOIN
			 rdb.dbo.L_INV_TREATMENT_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_TREATMENT_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_TREATMENT new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_TREATMENT( [PAGE_CASE_UID], [D_INV_TREATMENT_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_TREATMENT_KEY
			   FROM rdb.dbo.L_INV_TREATMENT_INC
			   WHERE D_INV_TREATMENT_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_TREATMENT new key entry';

		INSERT INTO [RDB].[dbo].L_INV_TREATMENT( [PAGE_CASE_UID], [D_INV_TREATMENT_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_TREATMENT_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_TREATMENT;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_TREATMENT where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_TREATMENT', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_TREATMENT]
			( 
						 [D_INV_TREATMENT_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_TREATMENT dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT ladminI
			 ON dadmin.d_inv_TREATMENT_key = ladminI.d_inv_TREATMENT_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_TREATMENT;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_TREATMENT;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_TREATMENT];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.L_INV_TREATMENT', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_UNDER_CONDITION]    Script Date: 1/21/2019 11:01:57 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_UNDER_CONDITION', 'P') IS NOT NULL








BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_UNDER_CONDITION];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_UNDER_CONDITION] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_UNDER_CONDITION'; 

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_UNDER_CONDITION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_UNDER_CONDITION]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_UNDER_CONDITION
ON [dbo].S_INV_UNDER_CONDITION
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_UNDER_CONDITION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_UNDER_CONDITION]
			( 
						 [D_INV_UNDER_CONDITION_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_UNDER_CONDITION
ON [dbo].L_INV_UNDER_CONDITION
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_UNDER_CONDITION]
			( [D_INV_UNDER_CONDITION_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_UNDER_CONDITION
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_UNDER_CONDITION
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_UNDER_CONDITION';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_UNDER_CONDITION_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_UNDER_CONDITION AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_UNDER_CONDITION_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION'; 


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_UNDER_CONDITION_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_UNDER_CONDITION AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_UNDER_CONDITION_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION]
		( [D_INV_UNDER_CONDITION_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_UNDER_CONDITION'; 


		--CREATE TABLE LOOKUP_TABLE_N_INV_UNDER_CONDITION 

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_UNDER_CONDITION;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_UNDER_CONDITION
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_UNDER_CONDITION];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_UNDER_CONDITION_INC'; 

		--CREATE TABLE L_INV_UNDER_CONDITION_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_UNDER_CONDITION_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_UNDER_CONDITION_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_UNDER_CONDITION_KEY AS D_INV_UNDER_CONDITION_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_UNDER_CONDITION_KEY
		INTO rdb.dbo.L_INV_UNDER_CONDITION_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_UNDER_CONDITION AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_UNDER_CONDITION_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_UNDER_CONDITION_INC]
		( [D_INV_UNDER_CONDITION_KEY]
		);

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_UNDER_CONDITION_INC';

		UPDATE rdb.dbo.L_INV_UNDER_CONDITION_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_UNDER_CONDITION_INC
		  SET D_INV_UNDER_CONDITION_KEY = COALESCE(D_INV_UNDER_CONDITION_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_UNDER_CONDITION where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_UNDER_CONDITION ladmin
			 JOIN
			 rdb.dbo.L_INV_UNDER_CONDITION_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_UNDER_CONDITION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_UNDER_CONDITION new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_UNDER_CONDITION( [PAGE_CASE_UID], [D_INV_UNDER_CONDITION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_UNDER_CONDITION_KEY
			   FROM rdb.dbo.L_INV_UNDER_CONDITION_INC
			   WHERE D_INV_UNDER_CONDITION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_UNDER_CONDITION new key entry';

		INSERT INTO [RDB].[dbo].L_INV_UNDER_CONDITION( [PAGE_CASE_UID], [D_INV_UNDER_CONDITION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_UNDER_CONDITION_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_UNDER_CONDITION;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_UNDER_CONDITION where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_UNDER_CONDITION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_UNDER_CONDITION]
			( 
						 [D_INV_UNDER_CONDITION_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_UNDER_CONDITION dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION ladminI
			 ON dadmin.d_inv_UNDER_CONDITION_key = ladminI.d_inv_UNDER_CONDITION_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_UNDER_CONDITION;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_UNDER_CONDITION;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_UNDER_CONDITION];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.L_INV_UNDER_CONDITION', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO

/****** Object:  StoredProcedure [dbo].[sp_L_INV_VACCINATION]    Script Date: 1/21/2019 11:01:57 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_L_INV_VACCINATION', 'P') IS NOT NULL









BEGIN
	DROP PROCEDURE dbo.[sp_L_INV_VACCINATION];
END;
GO

CREATE PROCEDURE [dbo].[sp_L_INV_VACCINATION] 
				 @Batch_id bigint
AS
BEGIN
	DECLARE @RowCount_no int;
	DECLARE @Proc_Step_no float= 0;
	DECLARE @Proc_Step_Name varchar(200)= '';
	DECLARE @batch_start_time datetime2(7)= NULL;
	DECLARE @batch_end_time datetime2(7)= NULL;

	BEGIN TRY

		SET @Proc_Step_no = 1;
		SET @Proc_Step_Name = 'SP_Start';


		BEGIN TRANSACTION;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE1_INV_VACCINATION'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_VACCINATION AS
		--*** Keys for New Page case where no Stgaing VACCINATION enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION;
		END;

		IF OBJECT_ID('rdb.dbo.S_INV_VACCINATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[S_INV_VACCINATION]
			( 
						 [PAGE_CASE_UID] [numeric](20, 0) NULL
			)
			ON [PRIMARY];

			CREATE INDEX S_INV_VACCINATION
ON [dbo].S_INV_VACCINATION
			( PAGE_CASE_UID
			);
		END;
		IF OBJECT_ID('rdb.dbo.L_INV_VACCINATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[L_INV_VACCINATION]
			( 
						 [D_INV_VACCINATION_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
			)
			ON [PRIMARY];

			CREATE INDEX L_INV_VACCINATION
ON [dbo].L_INV_VACCINATION
			( PAGE_CASE_UID
			);
			CREATE NONCLUSTERED INDEX [L_RDB_PERF_04082021_01]
ON [dbo].[L_INV_VACCINATION]
			( [D_INV_VACCINATION_KEY]
			) 
				   INCLUDE( [PAGE_CASE_UID] );
		END;
		WITH lst
			 AS (SELECT PAGE_CASE_UID
				 FROM rdb.dbo.PHC_UIDS
				 EXCEPT
				 (
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.S_INV_VACCINATION
					 UNION
					 SELECT PAGE_CASE_UID
					 FROM rdb.dbo.L_INV_VACCINATION
				 ))
			 SELECT *, 1 AS D_NE_KEY
			 INTO rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION
			 FROM lst;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' ADD TO  TABLE LOOKUP_TABLE1_INV_VACCINATION';






		INSERT INTO rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION
			   SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_VACCINATION_KEY
			   FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_VACCINATION AS ladmin
			   WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
					 ladmin.D_INV_VACCINATION_KEY != 1;



		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 4;
		SET @Proc_Step_Name = 'CREATE TABLE LOOKUP_TABLE_D_REMOVE_INV_VACCINATION'; 

		--CREATE TABLE LOOKUP_TABLE1_INV_VACCINATION AS
		--*** Keys for New Page case where no Stgaing VACCINATION enteries


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION;
		END;



		SELECT pcuid.PAGE_CASE_UID, ladmin.D_INV_VACCINATION_KEY
		INTO rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION
		FROM rdb.dbo.PHC_UIDS AS pcuid, rdb.dbo.L_INV_VACCINATION AS ladmin
		WHERE pcuid.PAGE_CASE_UID = ladmin.PAGE_CASE_UID AND 
			  ladmin.D_INV_VACCINATION_KEY != 1;






		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PREF_INTERNAL_03]
ON [dbo].[LOOKUP_TABLE_D_REMOVE_INV_VACCINATION]
		( [D_INV_VACCINATION_KEY]
		);


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;




		BEGIN TRANSACTION;

		SET @Proc_Step_no = 5;
		SET @Proc_Step_Name = ' CREATE TABLE LOOKUP_TABLE_N_INV_VACCINATION'; 


		--truncate table  dbo.LOOKUP_TABLE_N_INV_VACCINATION ;

		DELETE FROM dbo.LOOKUP_TABLE_N_INV_VACCINATION;


		INSERT INTO dbo.LOOKUP_TABLE_N_INV_VACCINATION
			   SELECT PAGE_CASE_UID
			   FROM rdb.dbo.PHC_UIDS
			   EXCEPT
			   SELECT PAGE_CASE_UID
			   FROM [RDB].[dbo].[LOOKUP_TABLE1_INV_VACCINATION];






		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 6;
		SET @Proc_Step_Name = 'CREATE TABLE L_INV_VACCINATION_INC'; 

		--CREATE TABLE L_INV_VACCINATION_INC AS 

		IF OBJECT_ID('rdb.dbo.L_INV_VACCINATION_INC', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.L_INV_VACCINATION_INC;
		END;



		SELECT ltn.PAGE_CASE_UID AS PAGE_CASE_UID_N, lt1.PAGE_CASE_UID AS PAGE_CASE_UID_NE, ltn.D_INV_VACCINATION_KEY AS D_INV_VACCINATION_KEY_N, lt1.D_NE_KEY, CAST(NULL AS bigint) AS PAGE_CASE_UID, CAST(NULL AS bigint) AS D_INV_VACCINATION_KEY
		INTO rdb.dbo.L_INV_VACCINATION_INC
		FROM rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION AS lt1
			 FULL JOIN
			 rdb.dbo.LOOKUP_TABLE_N_INV_VACCINATION AS ltn
			 ON lt1.PAGE_CASE_UID = ltn.PAGE_CASE_UID;




		SELECT @RowCount_no = @@ROWCOUNT;
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_01]
ON [dbo].[L_INV_VACCINATION_INC]
		( [PAGE_CASE_UID]
		);
		CREATE NONCLUSTERED INDEX [L_RDB_PERF_INTERNAL_02]
ON [dbo].[L_INV_VACCINATION_INC]
		( [D_INV_VACCINATION_KEY]
		);
		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 7;
		SET @Proc_Step_Name = 'UPDATE TABLE L_INV_VACCINATION_INC';

		UPDATE rdb.dbo.L_INV_VACCINATION_INC
		  SET PAGE_CASE_UID = COALESCE(PAGE_CASE_UID_N, PAGE_CASE_UID_NE);



		UPDATE rdb.dbo.L_INV_VACCINATION_INC
		  SET D_INV_VACCINATION_KEY = COALESCE(D_INV_VACCINATION_KEY_N, D_NE_KEY);



		SELECT @RowCount_no = -1;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;


		SET @Proc_Step_no = 8;
		SET @Proc_Step_Name = 'Delete from L_INV_VACCINATION where existing default entry';

		DELETE ladmin
		FROM rdb.dbo.L_INV_VACCINATION ladmin
			 JOIN
			 rdb.dbo.L_INV_VACCINATION_INC ladminI
			 ON ladmin.PAGE_CASE_UID = ladminI.PAGE_CASE_UID
		WHERE ladmin.D_INV_VACCINATION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 9;
		SET @Proc_Step_Name = 'Insert into L_INV_VACCINATION new default key entry';

		INSERT INTO [RDB].[dbo].L_INV_VACCINATION( [PAGE_CASE_UID], [D_INV_VACCINATION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_VACCINATION_KEY
			   FROM rdb.dbo.L_INV_VACCINATION_INC
			   WHERE D_INV_VACCINATION_KEY = 1;


		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;
   
		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 10;
		SET @Proc_Step_Name = 'Insert into L_INV_VACCINATION new key entry';

		INSERT INTO [RDB].[dbo].L_INV_VACCINATION( [PAGE_CASE_UID], [D_INV_VACCINATION_KEY] )
			   SELECT PAGE_CASE_UID, D_INV_VACCINATION_KEY
			   FROM rdb.dbo.LOOKUP_TABLE_N_INV_VACCINATION;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;



		BEGIN TRANSACTION;


		SET @Proc_Step_no = 11;
		SET @Proc_Step_Name = 'Delete from D_INV_VACCINATION where existing default entry';

		IF OBJECT_ID('rdb.dbo.D_INV_VACCINATION', 'U') IS NULL
		BEGIN
			CREATE TABLE rdb.[dbo].[D_INV_VACCINATION]
			( 
						 [D_INV_VACCINATION_KEY] [float] NULL
			)
			ON [PRIMARY];
		END;

		DELETE dadmin
		FROM rdb.dbo.D_INV_VACCINATION dadmin
			 JOIN
			 rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION ladminI
			 ON dadmin.d_inv_VACCINATION_key = ladminI.d_inv_VACCINATION_key;



		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE1_INV_VACCINATION;
		END;

		IF OBJECT_ID('rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION', 'U') IS NOT NULL
		BEGIN
			DROP TABLE rdb.dbo.LOOKUP_TABLE_D_REMOVE_INV_VACCINATION;
		END;


		DELETE FROM rdb.[dbo].[LOOKUP_TABLE_N_INV_VACCINATION];


		--    select 'I am here ';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );


		COMMIT TRANSACTION;
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


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.L_INV_VACCINATION', 'ERROR', @Proc_Step_no, 'Step -' + CAST(@Proc_Step_no AS varchar(3)) + ' -' + CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;


 
GO


