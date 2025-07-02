package gov.cdc.nbs.authentication.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
class GrantedAuthorityFinder {

  private static final String QUERY = """
      SELECT
        grantedAuthority
      FROM
        (
          SELECT
            'ADMINISTRATE-SYSTEM' grantedAuthority
          FROM
            auth_user
          WHERE
            nedss_entry_id = :identifier
            AND master_sec_admin_ind = 'T'
          UNION
          SELECT
            'ADMINISTRATE-SECURITY' grantedAuthority
          FROM
            auth_user
          WHERE
            nedss_entry_id = :identifier
            AND prog_area_admin_ind = 'T'
          UNION
          SELECT
            DISTINCT operationType.bus_op_nm + '-' + objectType.bus_obj_nm
          FROM
            auth_user authUser
            JOIN auth_user_role role ON role.auth_user_uid = authUser.auth_user_uid
            JOIN auth_perm_set permissionSet ON role.auth_perm_set_uid = permissionSet.auth_perm_set_uid
            JOIN auth_bus_obj_rt objectRight ON objectRight.auth_perm_set_uid = permissionSet.auth_perm_set_uid
            JOIN auth_bus_obj_type objectType ON objectRight.auth_bus_obj_type_uid = objectType.auth_bus_obj_type_uid
            JOIN auth_bus_op_rt operationRight ON operationRight.auth_bus_obj_rt_uid = objectRight.auth_bus_obj_rt_uid
            JOIN auth_bus_op_type operationType ON operationType.auth_bus_op_type_uid = operationRight.auth_bus_op_type_uid
          WHERE
            authUser.nedss_entry_id = :identifier
            AND NOT (
              role.role_guest_ind = 'T'
              AND isNull(operationRight.bus_op_guest_rt, 'F') = 'F'
            )
        ) AS permQuery
      WHERE
        grantedAuthority IS NOT NULL;
        """;

  private final JdbcClient client;

  GrantedAuthorityFinder(final JdbcClient client) {
    this.client = client;
  }

  Set<GrantedAuthority> find(final long user) {
    return client.sql(QUERY)
        .param("identifier", user)
        .query(this::map)
        .set();
  }

  private GrantedAuthority map(final ResultSet rs, final int row) throws SQLException {
    String authority = rs.getString(1);
    return new SimpleGrantedAuthority(authority);
  }
}
