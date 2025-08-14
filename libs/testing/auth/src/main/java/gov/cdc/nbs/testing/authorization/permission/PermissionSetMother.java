package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.testing.authorization.AuthenticationSupportSettings;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@ScenarioScope
public class PermissionSetMother {

  private static final String CREATE = """
      insert into Auth_perm_set(
        perm_set_nm,
        perm_set_desc,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      ) values (
        :name,
        :description,
        :addedBy,
        :addedOn,
        :addedBy,
        :addedOn,
        'ACTIVE',
        :addedOn
      );
      
      select @@identity
      """;

  private static final String ASSIGN = """
      -- assign the object right
      insert into Auth_bus_obj_rt (
        auth_perm_set_uid,
        auth_bus_obj_type_uid,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      )
      select
          :set,
          [object].auth_bus_obj_type_uid,
          :addedBy,
          :addedOn,
          :addedBy,
          :addedOn,
          'ACTIVE',
          :addedOn
      from Auth_bus_obj_type [object]
      where [object].[bus_obj_nm] = :object;
      
      -- assign the operation right
      insert into Auth_bus_op_rt (
        auth_bus_obj_rt_uid,
        auth_bus_op_type_uid,
        bus_op_user_rt,
        bus_op_guest_rt,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      )
      select
        @@identity,
        [operation].auth_bus_op_type_uid,
        'T',
        'T',
        :addedBy,
        :addedOn,
        :addedBy,
        :addedOn,
        'ACTIVE',
        :addedOn
      from Auth_bus_op_type [operation]
      where [operation].[bus_op_nm] = :operation;
      """;

  private static final String DELETE = """
      delete from Auth_user_role where auth_perm_set_uid in (:identifiers);
      delete from Auth_bus_op_rt where auth_bus_obj_rt_uid in (select auth_bus_obj_rt_uid from Auth_bus_obj_rt where auth_perm_set_uid in (:identifiers));
      delete from Auth_bus_obj_rt where auth_perm_set_uid in (:identifiers);
      delete from Auth_perm_set where auth_perm_set_uid in (:identifiers);
      """;

  private final AuthenticationSupportSettings settings;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;



  PermissionSetMother(
      final AuthenticationSupportSettings settings,
      final JdbcClient client
  ) {
    this.settings = settings;
    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE, "identifiers");
  }

  @PreDestroy
  public void reset() {
    this.cleaner.clean();
  }

  public long allow(final String operation, final String object) {

    long id = this.client.sql(CREATE)
        .param("name", "")
        .param("description", "")
        .param("addedOn", Timestamp.from(this.settings.createdOn()))
        .param("addedBy", this.settings.createdBy())
        .query(Long.class)
        .single();

    this.client.sql(ASSIGN)
        .param("set", id)
        .param("operation", operation)
        .param("object", object)
        .param("addedOn", Timestamp.from(this.settings.createdOn()))
        .param("addedBy", this.settings.createdBy())
        .update();


    return id;
  }
}
