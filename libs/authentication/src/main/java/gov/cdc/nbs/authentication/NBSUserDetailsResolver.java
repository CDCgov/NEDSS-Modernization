package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import org.springframework.stereotype.Component;

@Component
public class NBSUserDetailsResolver {

  private final UserPermissionFinder finder;

  public NBSUserDetailsResolver(final UserPermissionFinder finder) {
    this.finder = finder;
  }

  public NbsUserDetails resolve(final AuthUser authUser) {
    return NbsUserDetails
        .builder()
        .id(authUser.getNedssEntryId())
        .firstName(authUser.getUserFirstNm())
        .lastName(authUser.getUserLastNm())
        .username(authUser.getUserId())
        .authorities(finder.getUserPermissions(authUser))
        .isEnabled(authUser.getAudit().recordStatus().equals(AuthRecordStatus.ACTIVE))
        .build();
  }

}
