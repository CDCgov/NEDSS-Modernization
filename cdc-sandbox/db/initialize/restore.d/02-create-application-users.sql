use NBS_ODSE;

declare @user   varchar (100)     = 'superuser'
declare @first  varchar(100)      = 'Ariella'
declare @last   varchar(100)      = 'Kent'
declare @permissions varchar(100) = 'superuser'

---------------------------------------------------------------------------------------------------
declare @next_id bigint
declare @changedBy  bigint        = -1;
declare @changedOn  datetime      = getDate();
declare @guest      char          = 'F'
declare @read_only  char          = 'T'

-- find the NBS Class
declare @class varchar(20);
select 
    @class = config_value
from [nbs_configuration]
where config_key = 'NBS_CLASS_CODE'

IF NOT EXISTS (SELECT 1 FROM [dbo].[Auth_user] where [user_id] = @user)
begin

  begin transaction
    
    --  get the next NBS identifier
    select 
        @next_id = seed_value_nbr
    from local_uid_generator
    where class_name_cd = @class  

    --  Add the user
    insert into Auth_user (
        nedss_entry_id,
        user_id,
        user_type,
        user_first_nm,
        user_last_nm,
        master_sec_admin_ind,
        prog_area_admin_ind,
        user_comments,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time    
    ) values (
        @next_id,
        @user,
        'internalUser',
        @first,
        @last,
        'F',
        'F',
        'Auto-generated user.',
        @changedBy,
        @changedOn,
        @changedBy,
        @changedOn,
        'ACTIVE',
        @changedOn
    )

    -- update NBS identifier
    update local_uid_generator
        set seed_value_nbr = @next_id + 1
    where class_name_cd = @class;

commit transaction

---------------------------------------------------------------------------------------------------
-- Add permissions to all Jurisdictions

  ;with users (identifier) as (
      select 
          auth_user_uid 
      from Auth_user 
      where user_id in (
          @user
      )
  )
  insert into Auth_user_role (
      auth_user_uid,
      auth_role_nm,
      prog_area_cd,
      jurisdiction_cd,
      auth_perm_set_uid,
      role_guest_ind,
      read_only_ind,
      disp_seq_nbr,
      add_time,
      add_user_id,
      last_chg_time,
      last_chg_user_id,
      record_status_cd,
      record_status_time
  )
  select 
      [user].[identifier]                 as [auth_user_uid],
      [permission_set].perm_set_nm        as [auth_role_nm],
      [program_area].[prog_area_cd],
      'ALL'                               as [jurisdiction_cd],
      [permission_set].auth_perm_set_uid,
      @guest                              as [role_guest_ind],
      @read_only                          as [read_only_ind],
      0                                   as [disp_seq_nbr],
      @changedOn                          as [add_time],
      @changedBy                          as [add_user_id],
      @changedOn                          as [last_chg_time],
      @changedBy                          as [last_chg_user_id],
      'ACTIVE'                            as [record_status_cd],
      @changedOn                          as [record_status_time]
  from Auth_perm_set [permission_set],
      users [user],
      NBS_SRTE..Program_area_code [program_area]
              
  where   [permission_set].perm_set_nm    = @permissions

end
