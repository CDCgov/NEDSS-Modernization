package gov.cdc.nbs.authentication.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
class GrantedAuthorityFinder {

  private static final String QUERY = """
      select distinct
              [operation_type].bus_op_nm + '-' + [object_type].bus_obj_nm
      from     auth_user [user]
              join auth_user_role [role] on
                      [role].auth_user_uid=[user].auth_user_uid

              join auth_perm_set [set] on
                      [role].auth_perm_set_uid=[set].auth_perm_set_uid

              join auth_bus_obj_rt [object_right] on
                      [object_right].auth_perm_set_uid=[set].auth_perm_set_uid

              join auth_bus_obj_type [object_type] on
                      [object_right].auth_bus_obj_type_uid=[object_type].auth_bus_obj_type_uid

              join auth_bus_op_rt [operation_right] on
                      [operation_right].auth_bus_obj_rt_uid=[object_right].auth_bus_obj_rt_uid

              join auth_bus_op_type [operation_type] on
                      [operation_type].auth_bus_op_type_uid=[operation_right].auth_bus_op_type_uid

      where   [user].nedss_entry_id = ?
              and not (
                      [role].role_guest_ind = 'T'
                      and isNull([operation_right].bus_op_guest_rt, 'F') = 'F'
              )
      """;
  private static final int NEDSS_ENTRY_PARAMETER = 1;
  private static final int AUTHORITY_COLUMN = 1;

  private final JdbcTemplate template;

  GrantedAuthorityFinder(final DataSource dataSource) {
    this.template = new JdbcTemplate(dataSource);
  }

  Set<GrantedAuthority> find(final long user) {
    return this.template.queryForStream(
        QUERY,
        setter -> setter.setLong(NEDSS_ENTRY_PARAMETER, user),
        this::map
    ).collect(Collectors.toSet());
  }

  private GrantedAuthority map(final ResultSet rs, final int row) throws SQLException {
    String authority = rs.getString(AUTHORITY_COLUMN);
    return new SimpleGrantedAuthority(authority);
  }
}
