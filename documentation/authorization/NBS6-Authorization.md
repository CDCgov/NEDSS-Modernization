# NBS 6.0.X Authorization

NBS 6.0.X applies roles at the Program Area / Jurisdiction level however, not all permissions are this granular. There
are two main concepts that arise from the permissions; What a user can do, and what a user can see.

A Role is the assignment of a Permission Set to a User. Roles can limit the Rights of a Permission Set to allow viewing
of all data or only shared data as a `Guest`.

Permission Sets are a collection of Object Rights that define the Operation, Type, Operation Right, and Operation Type
that together define the granted Permission.

| Name            | Function                                                                        |
|-----------------|---------------------------------------------------------------------------------|
| Object Right    | Assigns Object types to a Permission Set                                        |
| Object Type     | Defines the data object and if it applies to a Program Area and/or Jurisdiction |
| Operation Right | Assigns an Object Type to an Object Right                                       |
| Operations Type | Defines the operation that can be applied to an Object.                         |

## Feature Level

What a user can do is determined by Feature level permissions. These permissions are used to enabled / disable
functionality within the user interface. Can be implemented via role based access control however, in NBS 6.0.X the
feature level roles are derived from the data level roles.

### Resolving Feature Level Permissions

The Feature level permissions granted to a user can be derived from Program Area / Jurisdiction Specific Permission
Sets. Feature level permissions are not granted when a Role assigns a Guest Permission Set containing an Object Right
that does not apply to guests.

Feature level permissions for all/specific users can be view with the following query;

```sql
declare @user varchar(256) = null

use [NBS_ODSE]

select distinct [user].[user_id],
                [set].perm_set_nm,
                [operation_type].bus_op_nm        as [operation_type],
                [object_type].bus_obj_nm          as [object_type],
                [role].role_guest_ind             as [guest],
                [operation_right].bus_op_guest_rt as [guest_right],
                [operation_right].bus_op_user_rt  as [user_right]
from nbs_odse.dbo.auth_user [user]
         join nbs_odse.dbo.auth_user_role [role] on
    [role].auth_user_uid = [user].auth_user_uid

         join nbs_odse.dbo.auth_perm_set [set] on
    [role].auth_perm_set_uid = [set].auth_perm_set_uid

         join nbs_odse.dbo.auth_bus_obj_rt [object_right] on
    [object_right].auth_perm_set_uid = [set].auth_perm_set_uid

         join nbs_odse.dbo.auth_bus_obj_type [object_type] on
    [object_right].auth_bus_obj_type_uid = [object_type].auth_bus_obj_type_uid

         join nbs_odse.dbo.auth_bus_op_rt [operation_right] on
    [operation_right].auth_bus_obj_rt_uid = [object_right].auth_bus_obj_rt_uid

         join nbs_odse.dbo.auth_bus_op_type [operation_type] on
    [operation_type].auth_bus_op_type_uid = [operation_right].auth_bus_op_type_uid

where [user].user_id = isNull(@user, [user].user_id)
  and not (
-- guests for a non-guest right do not get the permission
    [role].role_guest_ind = 'T'
        and isNull([operation_right].bus_op_guest_rt, 'F') = 'F'
    )
```

---

## Data Level

What a user can see is determine by Data level permission. Essentially, attribute based access control where permissions
define the scope and classification of data a user is allowed to view or interact with.

### Scope

The data scope is defined by the Program Area and Jurisdiction assigned to a particular Permission Set. Users are
granted roles by assigning a Permission Set to one Program Area, and either a specific Jurisdiction or ALL
Jurisdictions. There are object types that represent data that does not contain Jurisdictional information. In these
cases the Jurisdiction aspect of the scope is ignored.

| Object Type                  | Program Area | Jurisdiction |
|------------------------------|--------------|--------------|
| Investigation                | ✅            | ✅            |
| Summary Report               | ✅            | ❌            |
| Observation Lab Report       | ✅            | ✅            |
| Observation Morbidity Report | ✅            | ✅            |
| Reporting                    | ✅            | ✅            |
| Notification                 | ✅            | ✅            |
| Treatment                    | ✅            | ❌            |
| Case Reporting               | ✅            | ✅            |
| Document                     | ✅            | ✅            |
| Contact Tracing              | ✅            | ✅            |
| Queues                       | ✅            | ✅            |

#### Program Area / Jurisdiction OID

The data for Object types that are affected by Program Area and Jurisdiction have a `programareajurisdiction_oid` field
which contains a hash of the Program Area and Jurisdiction NBS unique identifiers. The hash is computed as `(100000 *
jurisdiction.nbs_uid) + program_area.nbs_uid`.

Not all rights given by a role are affected by the scope. There is a subset of object types that act on data is not
assigned to a specific Program Area or Jurisdiction.

- System
- Global
- Public Queue
- Patient
- Provider
- Organization
- Place
- Intervention / Vaccine
- Birth Record
- Interview
- SRT (Value Sets?)

### Classification

In addition to Scope a role can be given a classification of "Guest". This means that a user is only allowed to view or
interact with explicitly shared data within the Scope. Data that can be shared has a `shared_ind_cd` field where a value
of `T` denotes that the data is shared.

The "Guest" classification can also affect the interactions a role provides. For example, a user is prevented from
Adding Patients when the role providing the permissions is classified as a guest.

Data level permissions for all/specific users can be view with the following query;

```sql
declare @user varchar(256) = 'clayton.clerical'

use [NBS_ODSE];


select [user].[user_id]                                               as [user_name],
       [role].prog_area_cd                                            as [program_area],
       [jurisdiction].[code_desc_txt]                                 as [jurisdiction],
       [operation_type].bus_op_nm                                     as [operation_type],
       [object_type].bus_obj_nm                                       as [object_type],
       [role].role_guest_ind                                          as [shared_only],
       (100000 * [jurisdiction].[nbs_uid]) + [program_area].[nbs_uid] as [oid]
from nbs_odse.dbo.auth_user [user]
         join nbs_odse.dbo.auth_user_role [role] on
    [role].auth_user_uid = [user].auth_user_uid

         join nbs_odse.dbo.auth_perm_set [set] on
    [role].auth_perm_set_uid = [set].auth_perm_set_uid

         join nbs_odse.dbo.auth_bus_obj_rt [object_right] on
    [object_right].auth_perm_set_uid = [set].auth_perm_set_uid

         join nbs_odse.dbo.auth_bus_obj_type [object_type] on
    [object_right].auth_bus_obj_type_uid = [object_type].auth_bus_obj_type_uid

         join nbs_odse.dbo.auth_bus_op_rt [operation_right] on
    [operation_right].auth_bus_obj_rt_uid = [object_right].auth_bus_obj_rt_uid

         join nbs_odse.dbo.auth_bus_op_type [operation_type] on
    [operation_type].auth_bus_op_type_uid = [operation_right].auth_bus_op_type_uid

         join NBS_SRTE..Program_area_code [program_area] on
    [program_area].[prog_area_cd] = [role].prog_area_cd

         join NBS_SRTE..Jurisdiction_code [jurisdiction] on
    [jurisdiction].code = case
                              when [role].jurisdiction_cd = 'ALL'
                                  then [jurisdiction].code
                              else [role].jurisdiction_cd
        end

where [user].user_id = isNull(@user, [user].user_id)
```
