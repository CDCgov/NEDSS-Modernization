USE [master];
GO

CREATE DATABASE deduplication;
GO

USE [deduplication];
GO


CREATE TABLE data_element_configuration (
	id int IDENTITY (1,1) PRIMARY KEY,
	configuration NVARCHAR(MAX) NOT NULL,
	add_time datetime NOT NULL default(current_timestamp));
GO