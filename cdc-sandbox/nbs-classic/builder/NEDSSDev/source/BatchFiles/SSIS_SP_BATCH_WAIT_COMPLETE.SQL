USE [RDB]
GO

DECLARE @RC int

EXECUTE @RC = [dbo].[sp_nbs_batch_wait_complete] 
GO