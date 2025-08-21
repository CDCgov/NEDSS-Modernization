package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.authorization.AuthenticationSupportSettings;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationRoleMother {

  private static final String ASSIGN = """
      insert into auth_user_role(
        auth_user_uid,
        auth_perm_set_uid,
        prog_area_cd,
        jurisdiction_cd,
        role_guest_ind,
        add_user_id,
        add_time,
        last_chg_user_id,
        last_chg_time,
        record_status_cd,
        record_status_time
      ) values (
        :user,
        :set,
        :programArea,
        :jurisdiction,
        :guest,
        :addedBy,
        :addedOn,
        :addedBy,
        :addedOn,
        'ACTIVE',
        :addedOn
      );
      """;

  private final AuthenticationSupportSettings settings;
  private final JdbcClient client;


  AuthorizationRoleMother(
      final AuthenticationSupportSettings settings,
      final JdbcClient client
  ) {
    this.settings = settings;
    this.client = client;

  }

  public void allowAny(final ActiveUser user, final long set) {
    allow(user, set, 'F', "STD", "all");
  }

  public void allowAny(final ActiveUser user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'F', programArea, jurisdiction);
  }

  public void allowShared(final ActiveUser user, final long set) {
    allow(user, set, 'T', "STD", "all");
  }

  public void allowShared(final ActiveUser user, final long set, final String programArea, final String jurisdiction) {
    allow(user, set, 'T', programArea.toUpperCase(), jurisdiction.toUpperCase());
  }

  public void systemAdmin(final ActiveUser user) {
    this.client.sql("update dbo.Auth_user set master_sec_admin_ind = 'T' where auth_user_uid = ?")
        .param(user.id())
        .update();
  }

  public void securityAdmin(final ActiveUser user) {
    this.client.sql("update dbo.Auth_user set prog_area_admin_ind = 'T' where auth_user_uid = ?")
        .param(user.id())
        .update();
  }

  private void allow(
      final ActiveUser user,
      final long set,
      final char guest,
      final String programArea,
      final String jurisdiction
  ) {

    this.client.sql(ASSIGN)
        .param("user", user.id())
        .param("set", set)
        .param("programArea", programArea)
        .param("jurisdiction", jurisdiction)
        .param("guest", String.valueOf(guest))
        .param("addedOn", this.settings.createdOn())
        .param("addedBy", this.settings.createdBy())
        .update();
  }

}
