USE [RDB]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[job_batch_log](
	[record_id] [int] IDENTITY(1,1) NOT NULL,
	[batch_id] [bigint] NULL,
	[batch_start_dttm] [datetime2](7) NULL DEFAULT (getdate()),
	[batch_end_dttm] [datetime2](7) NULL DEFAULT (getdate()),
	[Status_Type] [nvarchar](500) NULL,
	[Msg_Description1] [nvarchar](500) NULL,
	[Msg_Description2] [nvarchar](500) NULL,
	[create_dttm] [datetime2](7) NULL DEFAULT (getdate()),
	[update_dttm] [datetime2](7) NULL DEFAULT (getdate()),
 CONSTRAINT [pk_job_batch_log] PRIMARY KEY CLUSTERED 
(
	[record_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[job_flow_log](
	[record_id] [int] IDENTITY(1,1) NOT NULL,
	[batch_id] [bigint] NULL,
	[Dataflow_Name] [nvarchar](199) NULL,
	[package_Name] [nvarchar](199) NULL,
	[Table_Schema_Name] [nvarchar](199) NULL,
	[Step_Number] [float] NULL,
	[Step_Name] [nvarchar](199) NULL,
	[Column_Name] [nvarchar](199) NULL,
	[Status_Type] [nvarchar](500) NULL,
	[row_count] [numeric](18, 0) NULL,
	[Error_Description] [nvarchar](500) NULL,
	[Msg_Description1] [nvarchar](500) NULL,
	[Msg_Description2] [nvarchar](500) NULL,
	[create_dttm] [datetime2](7) NULL DEFAULT (getdate()),
	[update_dttm] [datetime2](7) NULL DEFAULT (getdate()),
 CONSTRAINT [pk_job_flow_log] PRIMARY KEY CLUSTERED 
(
	[record_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


