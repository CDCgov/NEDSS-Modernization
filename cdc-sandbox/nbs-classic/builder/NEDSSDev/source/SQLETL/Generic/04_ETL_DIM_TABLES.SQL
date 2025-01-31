USE RDB;

GO

IF OBJECT_ID('rdb.dbo.D_INV_ADMINISTRATIVE', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_ADMINISTRATIVE]
	( 
				 [D_INV_ADMINISTRATIVE_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_CLINICAL', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_CLINICAL]
	( 
				 [D_INV_CLINICAL_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_COMPLICATION', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_COMPLICATION]
	( 
				 [D_INV_COMPLICATION_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_CONTACT', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_CONTACT]
	( 
				 [D_INV_CONTACT_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_DEATH', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_DEATH]
	( 
				 [D_INV_DEATH_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_EPIDEMIOLOGY', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_EPIDEMIOLOGY]
	( 
				 [D_INV_EPIDEMIOLOGY_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_HIV', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_HIV]
	( 
				 [D_INV_HIV_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_ISOLATE_TRACKING', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_ISOLATE_TRACKING]
	( 
				 [D_INV_ISOLATE_TRACKING_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_LAB_FINDING', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_LAB_FINDING]
	( 
				 [D_INV_LAB_FINDING_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_MEDICAL_HISTORY', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_MEDICAL_HISTORY]
	( 
				 [D_INV_MEDICAL_HISTORY_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_MOTHER', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_MOTHER]
	( 
				 [D_INV_MOTHER_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_OTHER', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_OTHER]
	( 
				 [D_INV_OTHER_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_PATIENT_OBS', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_PATIENT_OBS]
	( 
				 [D_INV_PATIENT_OBS_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_PREGNANCY_BIRTH', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_PREGNANCY_BIRTH]
	( 
				 [D_INV_PREGNANCY_BIRTH_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_RESIDENCY', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_RESIDENCY]
	( 
				 [D_INV_RESIDENCY_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_RISK_FACTOR', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_RISK_FACTOR]
	( 
				 [D_INV_RISK_FACTOR_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_SOCIAL_HISTORY', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_SOCIAL_HISTORY]
	( 
				 [D_INV_SOCIAL_HISTORY_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_SYMPTOM', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_SYMPTOM]
	( 
				 [D_INV_SYMPTOM_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_TRAVEL', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_TRAVEL]
	( 
				 [D_INV_TRAVEL_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_TREATMENT', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_TREATMENT]
	( 
				 [D_INV_TREATMENT_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_UNDER_CONDITION', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_UNDER_CONDITION]
	( 
				 [D_INV_UNDER_CONDITION_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INV_VACCINATION', 'U') IS NULL
BEGIN
	CREATE TABLE [dbo].[D_INV_VACCINATION]
	( 
				 [D_INV_VACCINATION_KEY] [float] NULL, [nbs_case_answer_uid] [numeric](21, 0) NULL
	)
	ON [PRIMARY]
END;

IF OBJECT_ID('rdb.dbo.D_INVESTIGATION_REPEAT', 'U') IS  NULL  
BEGIN
	CREATE TABLE rdb.[dbo].[D_INVESTIGATION_REPEAT]
	(
				[D_INVESTIGATION_REPEAT_KEY] [float] NULL, [PAGE_CASE_UID] [float] NULL
	) 
	ON [PRIMARY]
END;