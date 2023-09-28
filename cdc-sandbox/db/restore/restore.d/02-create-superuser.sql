use NBS_ODSE
GO
--- Create superuser
-- get next avaialbe nedss_entry_id
DECLARE @nedss_entry_id AS bigint 
SET 
  @nedss_entry_id = (
    select 
      [seed_value_nbr]
    from 
      [dbo].[Local_UID_generator] 
    where 
      [type_cd] = 'NBS')
-- increment id tracker
UPDATE [dbo].[Local_UID_generator] SET [seed_value_nbr] = [seed_value_nbr] + 1 WHERE [type_cd] = 'NBS'
-- create user
INSERT INTO [dbo].[Auth_user] (
  [user_id], [user_type],  [user_first_nm], 
  [user_last_nm], 
  [master_sec_admin_ind], [prog_area_admin_ind], 
  [nedss_entry_id], [user_comments], 
  [add_time], [add_user_id], [last_chg_time], 
  [last_chg_user_id], [record_status_cd], 
  [record_status_time]
) 
VALUES 
  (
    'superuser', 
    'internalUser', 
    'super', 
    'user', 
    'F', 
    'F', 
    @nedss_entry_id, 
    '', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  )
GO


-- Add permissions to superuser
DECLARE @user_id AS bigint 
SET 
  @user_id = (
    select 
      auth_user_uid 
    from 
      auth_user 
    where 
      user_id = 'superuser'
  ) 
INSERT INTO [dbo].[Auth_user_role] (
    [auth_role_nm], [prog_area_cd], [jurisdiction_cd], 
    [auth_user_uid], [auth_perm_set_uid], 
    [role_guest_ind], [read_only_ind], 
    [disp_seq_nbr], [add_time], [add_user_id], 
    [last_chg_time], [last_chg_user_id], 
    [record_status_cd], [record_status_time]
  ) 
VALUES 
  (
    'SUPERUSER', 
    'ARBO', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'BMIRD', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'GCD', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'HEP', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'HEPC', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'HIV', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'STD', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'TB', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  ), 
  (
    'SUPERUSER', 
    'VPD', 
    'ALL', 
    @user_id, 
    '22', 
    'F', 
    'T', 
    '0', 
    GETDATE(), 
    '10000000', 
    GETDATE(), 
    '10000000', 
    'ACTIVE', 
    GETDATE()
  )
GO
