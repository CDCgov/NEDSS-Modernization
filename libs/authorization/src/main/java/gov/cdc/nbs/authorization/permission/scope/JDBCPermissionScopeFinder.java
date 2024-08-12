package gov.cdc.nbs.authorization.permission.scope;

import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.authorization.permission.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
class JDBCPermissionScopeFinder implements PermissionScopeFinder {

    private static final String QUERY = """
        select
                [role].role_guest_ind                                           as [shared_only],
                (CAST(100000 as bigint) * [jurisdiction].[nbs_uid]) + [program_area].[nbs_uid]  as [oid]
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

                join NBS_SRTE..Program_area_code [program_area] on
                        [program_area].[prog_area_cd] = [role].prog_area_cd

                join NBS_SRTE..Jurisdiction_code [jurisdiction] on
                        [jurisdiction].code =   case
                                                        when [role].jurisdiction_cd = 'ALL'
                                                        then [jurisdiction].code
                                                        else [role].jurisdiction_cd
                                                end
        where   [user].user_id = ?
            and [operation_type].bus_op_nm = ?
            and [object_type].bus_obj_nm  = ?
        """;
    private static final int USER_PARAMETER = 1;
    private static final int OPERATION_PARAMETER = 2;
    private static final int OBJECT_PARAMETER = 3;
    private static final int SHARED_COLUMN = 1;
    private static final int AREA_ROUTING = 2;

    private final JdbcTemplate template;
    private final PermissionScopeMerger merger;

    JDBCPermissionScopeFinder(final JdbcTemplate template) {
        this.template = template;
        this.merger = new PermissionScopeMerger();
    }

    @Override
    public Optional<PermissionScope> find(final String user, final Permission permission) {
        return this.template.query(
                QUERY,
                applyCriteria(user, permission),
                this::map
            ).stream()
            .collect(Accumulator.accumulating(this.merger::merge));
    }

    private PreparedStatementSetter applyCriteria(final String user, final Permission permission) {
        return statement -> {
            statement.setString(USER_PARAMETER, user);
            statement.setString(OPERATION_PARAMETER, permission.operation());
            statement.setString(OBJECT_PARAMETER, permission.object());
        };
    }

    private PermissionScope map(final ResultSet rs, final int row) throws SQLException {
        boolean shared = rs.getBoolean(SHARED_COLUMN);
        long area = rs.getLong(AREA_ROUTING);
        return shared ? PermissionScope.shared(area) : PermissionScope.any(area);
    }
}
