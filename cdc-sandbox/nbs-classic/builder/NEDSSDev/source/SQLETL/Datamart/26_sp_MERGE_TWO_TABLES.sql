USE [RDB]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER OFF
GO

/* 

	It merges two table columns. 
	
	i.e 
	INPUT_TABLE1 has columns - a, b, c
	INPUT_TABLE2 has columns - a, b, d, e
	
	These two table merged to OUTPUT_TABLE which has columns - a, b, c, d, e
	
	
	Following stored procedure use it to merge two tables at step 9,
	
	sp_LDF_GENERIC_DATAMART
	sp_LDF_HEPATITIS_DATAMART
	sp_LDF_BMIRD_DATAMART
	sp_LDF_TETANUS_DATAMART
	sp_LDF_FOODBORNE_DATAMART
	sp_LDF_MUMPS_DATAMART
	sp_LDF_VACCINE_PREVENT_DISEASES_DATAMART
 
*/

IF OBJECT_ID('dbo.sp_MERGE_TWO_TABLES', 'P') IS NOT NULL
    DROP PROCEDURE [dbo].[sp_MERGE_TWO_TABLES];
GO

CREATE PROCEDURE [dbo].[sp_MERGE_TWO_TABLES]
	@INPUT_TABLE1		VARCHAR(150) = '',
	@INPUT_TABLE2		VARCHAR(150) = '',
	@OUTPUT_TABLE       VARCHAR(150) = '',
	@JOIN_ON_COLUMN       VARCHAR(150) = ''
AS
BEGIN

	DECLARE  @alterDynamicColumnList VARCHAR(MAX)='';
	DECLARE  @dynamicColumnUpdate VARCHAR(MAX)='';
	DECLARE  @dynamicColumnInsert VARCHAR(MAX)='';

	BEGIN TRY
	
		SELECT   @alterDynamicColumnList  = @alterDynamicColumnList+ 'ALTER TABLE '+@OUTPUT_TABLE+' ADD [' + name   +  '] VARCHAR(4000) ',
				 @dynamicColumnUpdate= @dynamicColumnUpdate + @OUTPUT_TABLE+'.[' +  name  + ']='  + @INPUT_TABLE1+'.['  +name  + '] ,'
		FROM     RDB.Sys.Columns WHERE Object_ID = Object_ID(@INPUT_TABLE1)
		AND name NOT IN  ( SELECT name FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID(@INPUT_TABLE2));

		SELECT @dynamicColumnInsert= @dynamicColumnInsert +'['+  name  + '] ,'
		FROM  RDB.Sys.Columns WHERE Object_ID = Object_ID(@INPUT_TABLE1);
							
			--PRINT '@alterDynamicColumnList -----------	'+CAST(@alterDynamicColumnList AS NVARCHAR(MAX))
			--PRINT '@dynamicColumnUpdate -----------	'+CAST(@dynamicColumnUpdate AS NVARCHAR(MAX))
			--PRINT '@dynamicColumnInsert -----------	'+CAST(@dynamicColumnInsert AS NVARCHAR(MAX))

		EXEC( 'SELECT  * INTO  '+ @OUTPUT_TABLE +'  FROM  '+ @INPUT_TABLE2);

		IF @alterDynamicColumnList IS NOT NULL AND @alterDynamicColumnList!=''
		BEGIN
			EXEC( @alterDynamicColumnList);
		END
		
		IF @dynamicColumnUpdate IS NOT NULL AND @dynamicColumnUpdate!=''
		BEGIN
			SET  @dynamicColumnUpdate=substring(@dynamicColumnUpdate,1,len(@dynamicColumnUpdate)-1);

			EXEC ('UPDATE  '+@OUTPUT_TABLE+'  SET ' +   @dynamicColumnUpdate + ' FROM '+@OUTPUT_TABLE     
				+' INNER JOIN '+  @INPUT_TABLE1  +' ON '+  @OUTPUT_TABLE+'.['+@JOIN_ON_COLUMN+'] =' +  @INPUT_TABLE1+'.['+@JOIN_ON_COLUMN+']');
		END
		
		IF @dynamicColumnInsert IS NOT NULL AND @dynamicColumnInsert!=''
		BEGIN
			SET  @dynamicColumnInsert=substring(@dynamicColumnInsert,1,len(@dynamicColumnInsert)-1);
			
			EXEC ('INSERT INTO  '+@OUTPUT_TABLE+' (' +@dynamicColumnInsert + ') SELECT  ' + @dynamicColumnInsert + ' FROM '+@INPUT_TABLE1 +' WHERE '+ @JOIN_ON_COLUMN +' NOT IN (SELECT  '+@JOIN_ON_COLUMN+' FROM '+@OUTPUT_TABLE+')');
		END
		
	END TRY

	BEGIN CATCH
		SELECT  
			ERROR_NUMBER() AS ErrorNumber  
			,ERROR_SEVERITY() AS ErrorSeverity  
			,ERROR_STATE() AS ErrorState  
			,ERROR_PROCEDURE() AS ErrorProcedure  
			,ERROR_LINE() AS ErrorLine  
			,ERROR_MESSAGE() AS ErrorMessage;
	
	END CATCH
END;
