package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthProgAreaAdmin;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NBSUserDetailsResolver {

    private final UserPermissionFinder finder;

    public NBSUserDetailsResolver(final UserPermissionFinder finder) {
        this.finder = finder;
    }

    public NbsUserDetails resolve(final AuthUser authUser, final String token) {
        return NbsUserDetails
            .builder()
            .id(authUser.getNedssEntryId())
            .firstName(authUser.getUserFirstNm())
            .lastName(authUser.getUserLastNm())
            .isMasterSecurityAdmin(authUser.getMasterSecAdminInd().equals('T'))
            .isProgramAreaAdmin(authUser.getProgAreaAdminInd().equals('T'))
            .adminProgramAreas(authUser.getAdminProgramAreas()
                .stream()
                .map(AuthProgAreaAdmin::getProgAreaCd)
                .collect(Collectors.toSet()))
            .username(authUser.getUserId())
            .password(null)
            .authorities(finder.getUserPermissions(authUser))
            .isEnabled(authUser.getAudit().recordStatus().equals(AuthRecordStatus.ACTIVE))
            .token(token)
            .build();
    }

}
