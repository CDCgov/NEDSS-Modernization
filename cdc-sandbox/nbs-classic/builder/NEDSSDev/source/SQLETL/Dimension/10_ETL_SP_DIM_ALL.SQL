USE [RDB];
GO

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_ADMINISTRATIVE', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_ADMINISTRATIVE;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_ADMINISTRATIVE] 
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
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.D_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_ADMINISTRATIVE'; 

		-- create table rdb_ui_metadata_INV_ADMINISTRATIVE as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_ADMINISTRATIVE ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								  WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								  ELSE ''
																								  END+CASE
																									  WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									  ELSE ' NULL'
																									  END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_ADMINISTRATIVE' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_ADMINISTRATIVE' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.D_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_ADMINISTRATIVE';


		IF NOT EXISTS
		(
			SELECT d_inv_administrative_key
			FROM [RDB].[dbo].[D_INV_ADMINISTRATIVE]
			WHERE d_inv_administrative_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_ADMINISTRATIVE]( [D_INV_ADMINISTRATIVE_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_ADMINISTRATIVE]( [D_INV_ADMINISTRATIVE_KEY] ,'+STUFF(
																									  (
																										  SELECT ', ['+name+']'
																										  FROM syscolumns
																										  WHERE id = OBJECT_ID('S_INV_ADMINISTRATIVE') AND 
																												LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																										  FOR XML PATH('')
																									  ), 1, 1, '')+' ) select [D_INV_ADMINISTRATIVE_KEY] , '+STUFF(
																																								  (
																																									  SELECT ', ['+name+']'
																																									  FROM syscolumns
																																									  WHERE id = OBJECT_ID('S_INV_ADMINISTRATIVE') AND 
																																											LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																									  FOR XML PATH('')
																																								  ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_ADMINISTRATIVE_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_ADMINISTRATIVE SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_ADMINISTRATIVE_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.D_INV_ADMINISTRATIVE', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );


		COMMIT TRANSACTION;


		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.D_INV_ADMINISTRATIVE', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_ADMINISTRATIVE', 'RDB.D_INV_ADMINISTRATIVE', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_CLINICAL]    Script Date: 1/21/2019 11:03:23 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_CLINICAL', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_CLINICAL;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_CLINICAL] 
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
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.D_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_CLINICAL'; 

		-- create table rdb_ui_metadata_INV_CLINICAL as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_CLINICAL ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							ELSE ''
																							END+CASE
																								WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								ELSE ' NULL'
																								END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_CLINICAL' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_CLINICAL' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );
		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.D_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_CLINICAL';

		IF NOT EXISTS
		(
			SELECT d_inv_CLINICAL_key
			FROM [RDB].[dbo].[D_INV_CLINICAL]
			WHERE d_inv_CLINICAL_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_CLINICAL]( [D_INV_CLINICAL_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_CLINICAL]( [D_INV_CLINICAL_KEY] ,'+STUFF(
																						  (
																							  SELECT ', ['+name+']'
																							  FROM syscolumns
																							  WHERE id = OBJECT_ID('S_INV_CLINICAL') AND 
																									LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																							  FOR XML PATH('')
																						  ), 1, 1, '')+' ) select [D_INV_CLINICAL_KEY] , '+STUFF(
																																				(
																																					SELECT ', ['+name+']'
																																					FROM syscolumns
																																					WHERE id = OBJECT_ID('S_INV_CLINICAL') AND 
																																						  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																					FOR XML PATH('')
																																				), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_CLINICAL_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_CLINICAL SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_CLINICAL_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.D_INV_CLINICAL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.D_INV_CLINICAL', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_CLINICAL', 'RDB.D_INV_CLINICAL', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_COMPLICATION]    Script Date: 1/21/2019 11:03:23 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_COMPLICATION', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_COMPLICATION;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_COMPLICATION] 
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
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.D_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_COMPLICATION'; 

		-- create table rdb_ui_metadata_INV_COMPLICATION as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_COMPLICATION ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								ELSE ''
																								END+CASE
																									WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									ELSE ' NULL'
																									END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_COMPLICATION' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_COMPLICATION' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.D_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_COMPLICATION';

		IF NOT EXISTS
		(
			SELECT d_inv_COMPLICATION_key
			FROM [RDB].[dbo].[D_INV_COMPLICATION]
			WHERE d_inv_COMPLICATION_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_COMPLICATION]( [D_INV_COMPLICATION_KEY] )
			VALUES( 1 );
		END;


		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_COMPLICATION]( [D_INV_COMPLICATION_KEY] ,'+STUFF(
																								  (
																									  SELECT ', ['+name+']'
																									  FROM syscolumns
																									  WHERE id = OBJECT_ID('S_INV_COMPLICATION') AND 
																											LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									  FOR XML PATH('')
																								  ), 1, 1, '')+' ) select [D_INV_COMPLICATION_KEY] , '+STUFF(
																																							(
																																								SELECT ', ['+name+']'
																																								FROM syscolumns
																																								WHERE id = OBJECT_ID('S_INV_COMPLICATION') AND 
																																									  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																								FOR XML PATH('')
																																							), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_COMPLICATION_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_COMPLICATION SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_COMPLICATION_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.D_INV_COMPLICATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.D_INV_COMPLICATION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_COMPLICATION', 'RDB.D_INV_COMPLICATION', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_CONTACT]    Script Date: 1/21/2019 11:03:24 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_CONTACT', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_CONTACT;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_CONTACT] 
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
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.D_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_CONTACT'; 

		-- create table rdb_ui_metadata_INV_CONTACT as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_CONTACT ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						   ELSE ''
																						   END+CASE
																							   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							   ELSE ' NULL'
																							   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_CONTACT' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_CONTACT' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.D_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_CONTACT';

		IF NOT EXISTS
		(
			SELECT d_inv_CONTACT_key
			FROM [RDB].[dbo].[D_INV_CONTACT]
			WHERE d_inv_CONTACT_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_CONTACT]( [D_INV_CONTACT_KEY] )
			VALUES( 1 );
		END;


		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_CONTACT]( [D_INV_CONTACT_KEY] ,'+STUFF(
																						(
																							SELECT ', ['+name+']'
																							FROM syscolumns
																							WHERE id = OBJECT_ID('S_INV_CONTACT') AND 
																								  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																							FOR XML PATH('')
																						), 1, 1, '')+' ) select [D_INV_CONTACT_KEY] , '+STUFF(
																																			 (
																																				 SELECT ', ['+name+']'
																																				 FROM syscolumns
																																				 WHERE id = OBJECT_ID('S_INV_CONTACT') AND 
																																					   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																				 FOR XML PATH('')
																																			 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_CONTACT_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_CONTACT SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_CONTACT_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.D_INV_CONTACT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.D_INV_CONTACT', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_CONTACT', 'RDB.D_INV_CONTACT', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_DEATH]    Script Date: 1/21/2019 11:03:24 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_DEATH', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_DEATH;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_DEATH] 
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
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.D_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_DEATH'; 

		-- create table rdb_ui_metadata_INV_DEATH as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_DEATH ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						 WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						 ELSE ''
																						 END+CASE
																							 WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							 ELSE ' NULL'
																							 END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_DEATH' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_DEATH' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.D_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_DEATH';

		IF NOT EXISTS
		(
			SELECT d_inv_DEATH_key
			FROM [RDB].[dbo].[D_INV_DEATH]
			WHERE d_inv_DEATH_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_DEATH]( [D_INV_DEATH_KEY] )
			VALUES( 1 );
		END;


		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_DEATH]( [D_INV_DEATH_KEY] ,'+STUFF(
																					(
																						SELECT ', ['+name+']'
																						FROM syscolumns
																						WHERE id = OBJECT_ID('S_INV_DEATH') AND 
																							  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																						FOR XML PATH('')
																					), 1, 1, '')+' ) select [D_INV_DEATH_KEY] , '+STUFF(
																																	   (
																																		   SELECT ', ['+name+']'
																																		   FROM syscolumns
																																		   WHERE id = OBJECT_ID('S_INV_DEATH') AND 
																																				 LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																		   FOR XML PATH('')
																																	   ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_DEATH_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_DEATH SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_DEATH_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.D_INV_DEATH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.D_INV_DEATH', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_DEATH', 'RDB.D_INV_DEATH', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_EPIDEMIOLOGY]    Script Date: 1/21/2019 11:03:24 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_EPIDEMIOLOGY', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_EPIDEMIOLOGY;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_EPIDEMIOLOGY] 
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
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.D_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_EPIDEMIOLOGY'; 

		-- create table rdb_ui_metadata_INV_EPIDEMIOLOGY as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_EPIDEMIOLOGY ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								ELSE ''
																								END+CASE
																									WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									ELSE ' NULL'
																									END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_EPIDEMIOLOGY' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_EPIDEMIOLOGY' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.D_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_EPIDEMIOLOGY';

		IF NOT EXISTS
		(
			SELECT d_inv_EPIDEMIOLOGY_key
			FROM [RDB].[dbo].[D_INV_EPIDEMIOLOGY]
			WHERE d_inv_EPIDEMIOLOGY_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_EPIDEMIOLOGY]( [D_INV_EPIDEMIOLOGY_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_EPIDEMIOLOGY]( [D_INV_EPIDEMIOLOGY_KEY] ,'+STUFF(
																								  (
																									  SELECT ', ['+name+']'
																									  FROM syscolumns
																									  WHERE id = OBJECT_ID('S_INV_EPIDEMIOLOGY') AND 
																											LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									  FOR XML PATH('')
																								  ), 1, 1, '')+' ) select [D_INV_EPIDEMIOLOGY_KEY] , '+STUFF(
																																							(
																																								SELECT ', ['+name+']'
																																								FROM syscolumns
																																								WHERE id = OBJECT_ID('S_INV_EPIDEMIOLOGY') AND 
																																									  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																								FOR XML PATH('')
																																							), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_EPIDEMIOLOGY_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_EPIDEMIOLOGY SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_EPIDEMIOLOGY_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.D_INV_EPIDEMIOLOGY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.D_INV_EPIDEMIOLOGY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_EPIDEMIOLOGY', 'RDB.D_INV_EPIDEMIOLOGY', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_HIV]    Script Date: 1/21/2019 11:03:25 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_HIV', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_HIV;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_HIV] 
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
		VALUES( @Batch_id, 'INV_HIV', 'RDB.D_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_HIV'; 

		-- create table rdb_ui_metadata_INV_HIV as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_HIV ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																					   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																					   ELSE ''
																					   END+CASE
																						   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																						   ELSE ' NULL'
																						   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_HIV' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_HIV' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.D_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_HIV';

		IF NOT EXISTS
		(
			SELECT d_inv_HIV_key
			FROM [RDB].[dbo].[D_INV_HIV]
			WHERE d_inv_HIV_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_HIV]( [D_INV_HIV_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_HIV]( [D_INV_HIV_KEY] ,'+STUFF(
																				(
																					SELECT ', ['+name+']'
																					FROM syscolumns
																					WHERE id = OBJECT_ID('S_INV_HIV') AND 
																						  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																					FOR XML PATH('')
																				), 1, 1, '')+' ) select [D_INV_HIV_KEY] , '+STUFF(
																																 (
																																	 SELECT ', ['+name+']'
																																	 FROM syscolumns
																																	 WHERE id = OBJECT_ID('S_INV_HIV') AND 
																																		   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																	 FOR XML PATH('')
																																 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_HIV_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_HIV SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_HIV_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.D_INV_HIV', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_HIV', 'RDB.D_INV_HIV', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_HIV', 'RDB.D_INV_HIV', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_ISOLATE_TRACKING]    Script Date: 1/21/2019 11:03:25 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_ISOLATE_TRACKING', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_ISOLATE_TRACKING;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_ISOLATE_TRACKING] 
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
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.D_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_ISOLATE_TRACKING'; 

		-- create table rdb_ui_metadata_INV_ISOLATE_TRACKING as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_ISOLATE_TRACKING ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																									WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																									ELSE ''
																									END+CASE
																										WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																										ELSE ' NULL'
																										END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_ISOLATE_TRACKING' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_ISOLATE_TRACKING' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.D_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_ISOLATE_TRACKING';

		IF NOT EXISTS
		(
			SELECT d_inv_ISOLATE_TRACKING_key
			FROM [RDB].[dbo].[D_INV_ISOLATE_TRACKING]
			WHERE d_inv_ISOLATE_TRACKING_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_ISOLATE_TRACKING]( [D_INV_ISOLATE_TRACKING_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_ISOLATE_TRACKING]( [D_INV_ISOLATE_TRACKING_KEY] ,'+STUFF(
																										  (
																											  SELECT ', ['+name+']'
																											  FROM syscolumns
																											  WHERE id = OBJECT_ID('S_INV_ISOLATE_TRACKING') AND 
																													LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																											  FOR XML PATH('')
																										  ), 1, 1, '')+' ) select [D_INV_ISOLATE_TRACKING_KEY] , '+STUFF(
																																										(
																																											SELECT ', ['+name+']'
																																											FROM syscolumns
																																											WHERE id = OBJECT_ID('S_INV_ISOLATE_TRACKING') AND 
																																												  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																											FOR XML PATH('')
																																										), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_ISOLATE_TRACKING_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_ISOLATE_TRACKING SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_ISOLATE_TRACKING_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.D_INV_ISOLATE_TRACKING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.D_INV_ISOLATE_TRACKING', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_ISOLATE_TRACKING', 'RDB.D_INV_ISOLATE_TRACKING', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_LAB_FINDING]    Script Date: 1/21/2019 11:03:25 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_LAB_FINDING', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_LAB_FINDING;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_LAB_FINDING] 
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
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.D_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_LAB_FINDING'; 

		-- create table rdb_ui_metadata_INV_LAB_FINDING as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_LAB_FINDING ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							   ELSE ''
																							   END+CASE
																								   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								   ELSE ' NULL'
																								   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_LAB_FINDING' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_LAB_FINDING' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.D_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_LAB_FINDING';

		IF NOT EXISTS
		(
			SELECT d_inv_LAB_FINDING_key
			FROM [RDB].[dbo].[D_INV_LAB_FINDING]
			WHERE d_inv_LAB_FINDING_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_LAB_FINDING]( [D_INV_LAB_FINDING_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_LAB_FINDING]( [D_INV_LAB_FINDING_KEY] ,'+STUFF(
																								(
																									SELECT ', ['+name+']'
																									FROM syscolumns
																									WHERE id = OBJECT_ID('S_INV_LAB_FINDING') AND 
																										  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									FOR XML PATH('')
																								), 1, 1, '')+' ) select [D_INV_LAB_FINDING_KEY] , '+STUFF(
																																						 (
																																							 SELECT ', ['+name+']'
																																							 FROM syscolumns
																																							 WHERE id = OBJECT_ID('S_INV_LAB_FINDING') AND 
																																								   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																							 FOR XML PATH('')
																																						 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_LAB_FINDING_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_LAB_FINDING SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_LAB_FINDING_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.D_INV_LAB_FINDING', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.D_INV_LAB_FINDING', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_LAB_FINDING', 'RDB.D_INV_LAB_FINDING', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;
END;
GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_MEDICAL_HISTORY]    Script Date: 1/21/2019 11:03:26 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_MEDICAL_HISTORY', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_MEDICAL_HISTORY;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_MEDICAL_HISTORY] 
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
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.D_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_MEDICAL_HISTORY'; 

		-- create table rdb_ui_metadata_INV_MEDICAL_HISTORY as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_MEDICAL_HISTORY ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								   ELSE ''
																								   END+CASE
																									   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									   ELSE ' NULL'
																									   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_MEDICAL_HISTORY' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_MEDICAL_HISTORY' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.D_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_MEDICAL_HISTORY';

		IF NOT EXISTS
		(
			SELECT d_inv_MEDICAL_HISTORY_key
			FROM [RDB].[dbo].[D_INV_MEDICAL_HISTORY]
			WHERE d_inv_MEDICAL_HISTORY_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_MEDICAL_HISTORY]( [D_INV_MEDICAL_HISTORY_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_MEDICAL_HISTORY]( [D_INV_MEDICAL_HISTORY_KEY] ,'+STUFF(
																										(
																											SELECT ', ['+name+']'
																											FROM syscolumns
																											WHERE id = OBJECT_ID('S_INV_MEDICAL_HISTORY') AND 
																												  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																											FOR XML PATH('')
																										), 1, 1, '')+' ) select [D_INV_MEDICAL_HISTORY_KEY] , '+STUFF(
																																									 (
																																										 SELECT ', ['+name+']'
																																										 FROM syscolumns
																																										 WHERE id = OBJECT_ID('S_INV_MEDICAL_HISTORY') AND 
																																											   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																										 FOR XML PATH('')
																																									 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_MEDICAL_HISTORY_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_MEDICAL_HISTORY SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_MEDICAL_HISTORY_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.D_INV_MEDICAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.D_INV_MEDICAL_HISTORY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_MEDICAL_HISTORY', 'RDB.D_INV_MEDICAL_HISTORY', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_MOTHER]    Script Date: 1/21/2019 11:03:26 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_MOTHER', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_MOTHER;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_MOTHER] 
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
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.D_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_MOTHER'; 

		-- create table rdb_ui_metadata_INV_MOTHER as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_MOTHER ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						  WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						  ELSE ''
																						  END+CASE
																							  WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							  ELSE ' NULL'
																							  END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_MOTHER' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_MOTHER' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.D_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_MOTHER';

		IF NOT EXISTS
		(
			SELECT d_inv_MOTHER_key
			FROM [RDB].[dbo].[D_INV_MOTHER]
			WHERE d_inv_MOTHER_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_MOTHER]( [D_INV_MOTHER_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_MOTHER]( [D_INV_MOTHER_KEY] ,'+STUFF(
																					  (
																						  SELECT ', ['+name+']'
																						  FROM syscolumns
																						  WHERE id = OBJECT_ID('S_INV_MOTHER') AND 
																								LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																						  FOR XML PATH('')
																					  ), 1, 1, '')+' ) select [D_INV_MOTHER_KEY] , '+STUFF(
																																		  (
																																			  SELECT ', ['+name+']'
																																			  FROM syscolumns
																																			  WHERE id = OBJECT_ID('S_INV_MOTHER') AND 
																																					LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																			  FOR XML PATH('')
																																		  ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_MOTHER_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_MOTHER SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_MOTHER_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.D_INV_MOTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.D_INV_MOTHER', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_MOTHER', 'RDB.D_INV_MOTHER', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;
END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_OTHER]    Script Date: 1/21/2019 11:03:26 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_OTHER', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_OTHER;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_OTHER] 
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
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.D_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_OTHER'; 

		-- create table rdb_ui_metadata_INV_OTHER as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_OTHER ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						 WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						 ELSE ''
																						 END+CASE
																							 WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							 ELSE ' NULL'
																							 END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_OTHER' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_OTHER' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.D_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_OTHER';

		IF NOT EXISTS
		(
			SELECT d_inv_OTHER_key
			FROM [RDB].[dbo].[D_INV_OTHER]
			WHERE d_inv_OTHER_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_OTHER]( [D_INV_OTHER_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_OTHER]( [D_INV_OTHER_KEY] ,'+STUFF(
																					(
																						SELECT ', ['+name+']'
																						FROM syscolumns
																						WHERE id = OBJECT_ID('S_INV_OTHER') AND 
																							  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																						FOR XML PATH('')
																					), 1, 1, '')+' ) select [D_INV_OTHER_KEY] , '+STUFF(
																																	   (
																																		   SELECT ', ['+name+']'
																																		   FROM syscolumns
																																		   WHERE id = OBJECT_ID('S_INV_OTHER') AND 
																																				 LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																		   FOR XML PATH('')
																																	   ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_OTHER_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_OTHER SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_OTHER_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.D_INV_OTHER', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.D_INV_OTHER', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_OTHER', 'RDB.D_INV_OTHER', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );
		RETURN -1;
	END CATCH;

END;
GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_PATIENT_OBS]    Script Date: 1/21/2019 11:03:27 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_PATIENT_OBS', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_PATIENT_OBS;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_PATIENT_OBS] 
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
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.D_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_PATIENT_OBS'; 

		-- create table rdb_ui_metadata_INV_PATIENT_OBS as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_PATIENT_OBS ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							   ELSE ''
																							   END+CASE
																								   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								   ELSE ' NULL'
																								   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_PATIENT_OBS' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_PATIENT_OBS' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.D_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_PATIENT_OBS';

		IF NOT EXISTS
		(
			SELECT d_inv_PATIENT_OBS_key
			FROM [RDB].[dbo].[D_INV_PATIENT_OBS]
			WHERE d_inv_PATIENT_OBS_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_PATIENT_OBS]( [D_INV_PATIENT_OBS_KEY] )
			VALUES( 1 );
		END;


		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_PATIENT_OBS]( [D_INV_PATIENT_OBS_KEY] ,'+STUFF(
																								(
																									SELECT ', ['+name+']'
																									FROM syscolumns
																									WHERE id = OBJECT_ID('S_INV_PATIENT_OBS') AND 
																										  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									FOR XML PATH('')
																								), 1, 1, '')+' ) select [D_INV_PATIENT_OBS_KEY] , '+STUFF(
																																						 (
																																							 SELECT ', ['+name+']'
																																							 FROM syscolumns
																																							 WHERE id = OBJECT_ID('S_INV_PATIENT_OBS') AND 
																																								   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																							 FOR XML PATH('')
																																						 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_PATIENT_OBS_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_PATIENT_OBS SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_PATIENT_OBS_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.D_INV_PATIENT_OBS', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.D_INV_PATIENT_OBS', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_PATIENT_OBS', 'RDB.D_INV_PATIENT_OBS', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_PREGNANCY_BIRTH]    Script Date: 1/21/2019 11:03:27 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_PREGNANCY_BIRTH', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_PREGNANCY_BIRTH;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_PREGNANCY_BIRTH] 
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
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.D_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_PREGNANCY_BIRTH'; 

		-- create table rdb_ui_metadata_INV_PREGNANCY_BIRTH as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_PREGNANCY_BIRTH ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								   ELSE ''
																								   END+CASE
																									   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									   ELSE ' NULL'
																									   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_PREGNANCY_BIRTH' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_PREGNANCY_BIRTH' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.D_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_PREGNANCY_BIRTH';

		IF NOT EXISTS
		(
			SELECT d_inv_PREGNANCY_BIRTH_key
			FROM [RDB].[dbo].[D_INV_PREGNANCY_BIRTH]
			WHERE d_inv_PREGNANCY_BIRTH_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_PREGNANCY_BIRTH]( [D_INV_PREGNANCY_BIRTH_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_PREGNANCY_BIRTH]( [D_INV_PREGNANCY_BIRTH_KEY] ,'+STUFF(
																										(
																											SELECT ', ['+name+']'
																											FROM syscolumns
																											WHERE id = OBJECT_ID('S_INV_PREGNANCY_BIRTH') AND 
																												  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																											FOR XML PATH('')
																										), 1, 1, '')+' ) select [D_INV_PREGNANCY_BIRTH_KEY] , '+STUFF(
																																									 (
																																										 SELECT ', ['+name+']'
																																										 FROM syscolumns
																																										 WHERE id = OBJECT_ID('S_INV_PREGNANCY_BIRTH') AND 
																																											   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																										 FOR XML PATH('')
																																									 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_PREGNANCY_BIRTH_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_PREGNANCY_BIRTH SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_PREGNANCY_BIRTH_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.D_INV_PREGNANCY_BIRTH', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.D_INV_PREGNANCY_BIRTH', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_PREGNANCY_BIRTH', 'RDB.D_INV_PREGNANCY_BIRTH', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );


		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_RESIDENCY]    Script Date: 1/21/2019 11:03:28 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_RESIDENCY', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_RESIDENCY;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_RESIDENCY] 
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
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.D_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_RESIDENCY'; 

		-- create table rdb_ui_metadata_INV_RESIDENCY as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_RESIDENCY ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							 WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							 ELSE ''
																							 END+CASE
																								 WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								 ELSE ' NULL'
																								 END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_RESIDENCY' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_RESIDENCY' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.D_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_RESIDENCY';

		IF NOT EXISTS
		(
			SELECT d_inv_RESIDENCY_key
			FROM [RDB].[dbo].[D_INV_RESIDENCY]
			WHERE d_inv_RESIDENCY_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_RESIDENCY]( [D_INV_RESIDENCY_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_RESIDENCY]( [D_INV_RESIDENCY_KEY] ,'+STUFF(
																							(
																								SELECT ', ['+name+']'
																								FROM syscolumns
																								WHERE id = OBJECT_ID('S_INV_RESIDENCY') AND 
																									  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																								FOR XML PATH('')
																							), 1, 1, '')+' ) select [D_INV_RESIDENCY_KEY] , '+STUFF(
																																				   (
																																					   SELECT ', ['+name+']'
																																					   FROM syscolumns
																																					   WHERE id = OBJECT_ID('S_INV_RESIDENCY') AND 
																																							 LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																					   FOR XML PATH('')
																																				   ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_RESIDENCY_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_RESIDENCY SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_RESIDENCY_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.D_INV_RESIDENCY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.D_INV_RESIDENCY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_RESIDENCY', 'RDB.D_INV_RESIDENCY', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_RISK_FACTOR]    Script Date: 1/21/2019 11:03:28 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_RISK_FACTOR', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_RISK_FACTOR;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_RISK_FACTOR] 
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
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.D_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_RISK_FACTOR'; 

		-- create table rdb_ui_metadata_INV_RISK_FACTOR as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_RISK_FACTOR ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							   ELSE ''
																							   END+CASE
																								   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								   ELSE ' NULL'
																								   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_RISK_FACTOR' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_RISK_FACTOR' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.D_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_RISK_FACTOR';

		IF NOT EXISTS
		(
			SELECT d_inv_RISK_FACTOR_key
			FROM [RDB].[dbo].[D_INV_RISK_FACTOR]
			WHERE d_inv_RISK_FACTOR_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_RISK_FACTOR]( [D_INV_RISK_FACTOR_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_RISK_FACTOR]( [D_INV_RISK_FACTOR_KEY] ,'+STUFF(
																								(
																									SELECT ', ['+name+']'
																									FROM syscolumns
																									WHERE id = OBJECT_ID('S_INV_RISK_FACTOR') AND 
																										  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									FOR XML PATH('')
																								), 1, 1, '')+' ) select [D_INV_RISK_FACTOR_KEY] , '+STUFF(
																																						 (
																																							 SELECT ', ['+name+']'
																																							 FROM syscolumns
																																							 WHERE id = OBJECT_ID('S_INV_RISK_FACTOR') AND 
																																								   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																							 FOR XML PATH('')
																																						 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_RISK_FACTOR_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_RISK_FACTOR SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_RISK_FACTOR_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.D_INV_RISK_FACTOR', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.D_INV_RISK_FACTOR', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_RISK_FACTOR', 'RDB.D_INV_RISK_FACTOR', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_SOCIAL_HISTORY]    Script Date: 1/21/2019 11:03:28 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_SOCIAL_HISTORY', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_SOCIAL_HISTORY;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_SOCIAL_HISTORY] 
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
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.D_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_SOCIAL_HISTORY'; 

		-- create table rdb_ui_metadata_INV_SOCIAL_HISTORY as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_SOCIAL_HISTORY ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								  WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								  ELSE ''
																								  END+CASE
																									  WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									  ELSE ' NULL'
																									  END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_SOCIAL_HISTORY' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_SOCIAL_HISTORY' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.D_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_SOCIAL_HISTORY';

		IF NOT EXISTS
		(
			SELECT d_inv_SOCIAL_HISTORY_key
			FROM [RDB].[dbo].[D_INV_SOCIAL_HISTORY]
			WHERE d_inv_SOCIAL_HISTORY_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_SOCIAL_HISTORY]( [D_INV_SOCIAL_HISTORY_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_SOCIAL_HISTORY]( [D_INV_SOCIAL_HISTORY_KEY] ,'+STUFF(
																									  (
																										  SELECT ', ['+name+']'
																										  FROM syscolumns
																										  WHERE id = OBJECT_ID('S_INV_SOCIAL_HISTORY') AND 
																												LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																										  FOR XML PATH('')
																									  ), 1, 1, '')+' ) select [D_INV_SOCIAL_HISTORY_KEY] , '+STUFF(
																																								  (
																																									  SELECT ', ['+name+']'
																																									  FROM syscolumns
																																									  WHERE id = OBJECT_ID('S_INV_SOCIAL_HISTORY') AND 
																																											LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																									  FOR XML PATH('')
																																								  ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_SOCIAL_HISTORY_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_SOCIAL_HISTORY SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_SOCIAL_HISTORY_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.D_INV_SOCIAL_HISTORY', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.D_INV_SOCIAL_HISTORY', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_SOCIAL_HISTORY', 'RDB.D_INV_SOCIAL_HISTORY', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_SYMPTOM]    Script Date: 1/21/2019 11:03:29 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_SYMPTOM', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_SYMPTOM;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_SYMPTOM] 
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
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.D_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_SYMPTOM'; 

		-- create table rdb_ui_metadata_INV_SYMPTOM as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_SYMPTOM ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						   ELSE ''
																						   END+CASE
																							   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							   ELSE ' NULL'
																							   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_SYMPTOM' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_SYMPTOM' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;


		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.D_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_SYMPTOM';

		IF NOT EXISTS
		(
			SELECT d_inv_SYMPTOM_key
			FROM [RDB].[dbo].[D_INV_SYMPTOM]
			WHERE d_inv_SYMPTOM_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_SYMPTOM]( [D_INV_SYMPTOM_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_SYMPTOM]( [D_INV_SYMPTOM_KEY] ,'+STUFF(
																						(
																							SELECT ', ['+name+']'
																							FROM syscolumns
																							WHERE id = OBJECT_ID('S_INV_SYMPTOM') AND 
																								  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																							FOR XML PATH('')
																						), 1, 1, '')+' ) select [D_INV_SYMPTOM_KEY] , '+STUFF(
																																			 (
																																				 SELECT ', ['+name+']'
																																				 FROM syscolumns
																																				 WHERE id = OBJECT_ID('S_INV_SYMPTOM') AND 
																																					   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																				 FOR XML PATH('')
																																			 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_SYMPTOM_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_SYMPTOM SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_SYMPTOM_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.D_INV_SYMPTOM', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.D_INV_SYMPTOM', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_SYMPTOM', 'RDB.D_INV_SYMPTOM', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;
GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_TRAVEL]    Script Date: 1/21/2019 11:03:29 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_TRAVEL', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_TRAVEL;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_TRAVEL] 
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
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.D_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_TRAVEL'; 

		-- create table rdb_ui_metadata_INV_TRAVEL as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_TRAVEL ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																						  WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																						  ELSE ''
																						  END+CASE
																							  WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																							  ELSE ' NULL'
																							  END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_TRAVEL' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_TRAVEL' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.D_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_TRAVEL';

		IF NOT EXISTS
		(
			SELECT d_inv_TRAVEL_key
			FROM [RDB].[dbo].[D_INV_TRAVEL]
			WHERE d_inv_TRAVEL_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_TRAVEL]( [D_INV_TRAVEL_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_TRAVEL]( [D_INV_TRAVEL_KEY] ,'+STUFF(
																					  (
																						  SELECT ', ['+name+']'
																						  FROM syscolumns
																						  WHERE id = OBJECT_ID('S_INV_TRAVEL') AND 
																								LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																						  FOR XML PATH('')
																					  ), 1, 1, '')+' ) select [D_INV_TRAVEL_KEY] , '+STUFF(
																																		  (
																																			  SELECT ', ['+name+']'
																																			  FROM syscolumns
																																			  WHERE id = OBJECT_ID('S_INV_TRAVEL') AND 
																																					LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																			  FOR XML PATH('')
																																		  ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_TRAVEL_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_TRAVEL SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_TRAVEL_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.D_INV_TRAVEL', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.D_INV_TRAVEL', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_TRAVEL', 'RDB.D_INV_TRAVEL', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_TREATMENT]    Script Date: 1/21/2019 11:03:29 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_TREATMENT', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_TREATMENT;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_TREATMENT] 
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
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.D_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_TREATMENT'; 

		-- create table rdb_ui_metadata_INV_TREATMENT as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_TREATMENT ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							 WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							 ELSE ''
																							 END+CASE
																								 WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								 ELSE ' NULL'
																								 END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_TREATMENT' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_TREATMENT' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.D_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_TREATMENT';

		IF NOT EXISTS
		(
			SELECT d_inv_TREATMENT_key
			FROM [RDB].[dbo].[D_INV_TREATMENT]
			WHERE d_inv_TREATMENT_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_TREATMENT]( [D_INV_TREATMENT_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_TREATMENT]( [D_INV_TREATMENT_KEY] ,'+STUFF(
																							(
																								SELECT ', ['+name+']'
																								FROM syscolumns
																								WHERE id = OBJECT_ID('S_INV_TREATMENT') AND 
																									  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																								FOR XML PATH('')
																							), 1, 1, '')+' ) select [D_INV_TREATMENT_KEY] , '+STUFF(
																																				   (
																																					   SELECT ', ['+name+']'
																																					   FROM syscolumns
																																					   WHERE id = OBJECT_ID('S_INV_TREATMENT') AND 
																																							 LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																					   FOR XML PATH('')
																																				   ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_TREATMENT_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_TREATMENT SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_TREATMENT_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.D_INV_TREATMENT', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.D_INV_TREATMENT', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_TREATMENT', 'RDB.D_INV_TREATMENT', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;
GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_UNDER_CONDITION]    Script Date: 1/21/2019 11:03:30 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_UNDER_CONDITION', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_UNDER_CONDITION;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_UNDER_CONDITION] 
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
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.D_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_UNDER_CONDITION'; 

		-- create table rdb_ui_metadata_INV_UNDER_CONDITION as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_UNDER_CONDITION ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																								   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																								   ELSE ''
																								   END+CASE
																									   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																									   ELSE ' NULL'
																									   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_UNDER_CONDITION' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_UNDER_CONDITION' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );


		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.D_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_UNDER_CONDITION';

		IF NOT EXISTS
		(
			SELECT d_inv_UNDER_CONDITION_key
			FROM [RDB].[dbo].[D_INV_UNDER_CONDITION]
			WHERE d_inv_UNDER_CONDITION_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_UNDER_CONDITION]( [D_INV_UNDER_CONDITION_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_UNDER_CONDITION]( [D_INV_UNDER_CONDITION_KEY] ,'+STUFF(
																										(
																											SELECT ', ['+name+']'
																											FROM syscolumns
																											WHERE id = OBJECT_ID('S_INV_UNDER_CONDITION') AND 
																												  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																											FOR XML PATH('')
																										), 1, 1, '')+' ) select [D_INV_UNDER_CONDITION_KEY] , '+STUFF(
																																									 (
																																										 SELECT ', ['+name+']'
																																										 FROM syscolumns
																																										 WHERE id = OBJECT_ID('S_INV_UNDER_CONDITION') AND 
																																											   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																										 FOR XML PATH('')
																																									 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_UNDER_CONDITION_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_UNDER_CONDITION SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_UNDER_CONDITION_KEY != 1'
		);

		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.D_INV_UNDER_CONDITION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.D_INV_UNDER_CONDITION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_UNDER_CONDITION', 'RDB.D_INV_UNDER_CONDITION', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO

/****** Object:  StoredProcedure [dbo].[sp_D_INV_VACCINATION]    Script Date: 1/21/2019 11:03:30 PM ******/

SET ANSI_NULLS ON;
GO

SET QUOTED_IDENTIFIER ON;
GO

IF OBJECT_ID('rdb.dbo.sp_D_INV_VACCINATION', 'P') IS NOT NULL
BEGIN
	DROP PROCEDURE sp_D_INV_VACCINATION;
END;
GO

CREATE PROCEDURE [dbo].[sp_D_INV_VACCINATION] 
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
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.D_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, 0 );

		COMMIT TRANSACTION;

		SELECT @batch_start_time = batch_start_dttm, @batch_end_time = batch_end_dttm
		FROM [dbo].[job_batch_log]
		WHERE status_type = 'start' and type_code='MasterETL';

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 2;
		SET @Proc_Step_Name = ' Add new columns to D_INV_VACCINATION'; 

		-- create table rdb_ui_metadata_INV_VACCINATION as 

		DECLARE @Temp_Query_Table TABLE
		( 
										ID int IDENTITY(1, 1), QUERY_stmt varchar(5000)
		);
		DECLARE @column_query varchar(5000);
		DECLARE @Max_Query_No int;
		DECLARE @Curr_Query_No int;
		DECLARE @ColumnList varchar(5000);

		INSERT INTO @Temp_Query_Table
			   SELECT 'ALTER TABLE rdb.dbo.D_INV_VACCINATION ADD ['+COLUMN_NAME+'] '+DATA_TYPE+CASE
																							   WHEN DATA_TYPE IN( 'char', 'varchar', 'nchar', 'nvarchar' ) THEN ' ('+COALESCE(CAST(NULLIF(CHARACTER_MAXIMUM_LENGTH, -1) AS varchar(10)), CAST(CHARACTER_MAXIMUM_LENGTH as varchar(10)))+')'
																							   ELSE ''
																							   END+CASE
																								   WHEN IS_NULLABLE = 'NO' THEN ' NOT NULL'
																								   ELSE ' NULL'
																								   END
			   FROM INFORMATION_SCHEMA.COLUMNS AS c
			   WHERE TABLE_NAME = 'S_INV_VACCINATION' AND 
					 NOT EXISTS
			   (
				   SELECT 1
				   FROM INFORMATION_SCHEMA.COLUMNS
				   WHERE TABLE_NAME = 'D_INV_VACCINATION' AND 
						 COLUMN_NAME = c.COLUMN_NAME
			   ) AND 
					 LOWER(COLUMN_NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' );

		SET @Max_Query_No =
		(
			SELECT MAX(ID)
			FROM @Temp_Query_Table AS t
		);

		SET @Curr_Query_No = 0;

		WHILE @Max_Query_No > @Curr_Query_No

		BEGIN
			SET @Curr_Query_No = @Curr_Query_No + 1;

			SET @column_query =
			(
				SELECT QUERY_stmt
				FROM @Temp_Query_Table AS t
				WHERE ID = @Curr_Query_No
			);

			--SELECT @column_query;

			EXEC (@column_query);

		END;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.D_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 3;
		SET @Proc_Step_Name = ' Inserting data in to D_INV_VACCINATION';

		IF NOT EXISTS
		(
			SELECT d_inv_VACCINATION_key
			FROM [RDB].[dbo].[D_INV_VACCINATION]
			WHERE d_inv_VACCINATION_key = 1
		)
		BEGIN
			INSERT INTO RDB.[dbo].[D_INV_VACCINATION]( [D_INV_VACCINATION_KEY] )
			VALUES( 1 );
		END;

		DECLARE @insert_query nvarchar(max);

		SET @insert_query =
		(
			SELECT 'INSERT INTO  RDB.[dbo].[D_INV_VACCINATION]( [D_INV_VACCINATION_KEY] ,'+STUFF(
																								(
																									SELECT ', ['+name+']'
																									FROM syscolumns
																									WHERE id = OBJECT_ID('S_INV_VACCINATION') AND 
																										  LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																									FOR XML PATH('')
																								), 1, 1, '')+' ) select [D_INV_VACCINATION_KEY] , '+STUFF(
																																						 (
																																							 SELECT ', ['+name+']'
																																							 FROM syscolumns
																																							 WHERE id = OBJECT_ID('S_INV_VACCINATION') AND 
																																								   LOWER(NAME) NOT IN( LOWER('PAGE_CASE_UID'), 'last_chg_time' )
																																							 FOR XML PATH('')
																																						 ), 1, 1, '')+' 
	   FROM  RDB.dbo.L_INV_VACCINATION_INC LINV 
	   INNER JOIN RDB.dbo.S_INV_VACCINATION SINV ON SINV.PAGE_CASE_UID=LINV.PAGE_CASE_UID 
	    where linv.D_INV_VACCINATION_KEY != 1'
		);


		SELECT @insert_query;

		EXEC sp_executesql @insert_query;

		SELECT @RowCount_no = @@ROWCOUNT;

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.D_INV_VACCINATION', 'START', @Proc_Step_no, @Proc_Step_Name, @RowCount_no );

		COMMIT TRANSACTION;

		BEGIN TRANSACTION;

		SET @Proc_Step_no = 999;
		SET @Proc_Step_Name = 'SP_COMPLETE';

		INSERT INTO rdb.[dbo].[job_flow_log]( batch_id, [Dataflow_Name], [package_Name], [Status_Type], [step_number], [step_name], [row_count] )
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.D_INV_VACCINATION', 'COMPLETE', @Proc_Step_no, @Proc_Step_name, @RowCount_no );

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
		VALUES( @Batch_id, 'INV_VACCINATION', 'RDB.D_INV_VACCINATION', 'ERROR', @Proc_Step_no, 'Step -'+CAST(@Proc_Step_no AS varchar(3))+' -'+CAST(@ErrorMessage AS varchar(500)), 0 );

		RETURN -1;
	END CATCH;

END;

GO