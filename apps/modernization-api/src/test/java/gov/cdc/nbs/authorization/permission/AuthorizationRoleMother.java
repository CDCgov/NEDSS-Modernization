package gov.cdc.nbs.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.entity.AuthUserRole;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.support.TestAvailable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class AuthorizationRoleMother {

    private final MotherSettings settings;
    private final EntityManager entityManager;
    private final TestAuthorizationRoleCleaner cleaner;
    private final TestAvailable<Long> available;

    AuthorizationRoleMother(
        final MotherSettings settings,
        final EntityManager entityManager,
        final TestAuthorizationRoleCleaner cleaner
    ) {
        this.settings = settings;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.available = new TestAvailable<>();
    }

    void reset() {
        this.available.all().forEach(this.cleaner::clean);
        this.available.reset();
    }

    void allowAny(final long user, final long set) {
        allow(user, set, 'F', null, null);
    }

    void allowAny(final long user, final long set, final String programArea, final String jurisdiction) {
        allow(user, set, 'F', programArea, jurisdiction);
    }

    void allowShared(final long user, final long set) {
        allow(user, set, 'T', null, null);
    }

    void allowShared(final long user, final long set, final String programArea, final String jurisdiction) {
        allow(user, set, 'T', programArea.toUpperCase(), jurisdiction.toUpperCase());
    }

    private void allow(
        final long user,
        final long set,
        final char guest,
        final String programArea,
        final String jurisdiction
    ) {
        AuthUser authUser = this.entityManager.find(AuthUser.class, user);
        AuthPermSet authPermSet = this.entityManager.find(AuthPermSet.class, set);

        AuthUserRole role = new AuthUserRole(authUser, authPermSet)
            .guest(guest)
            .programArea(programArea)
            .jurisdiction(jurisdiction)
            .audit(resolveAudit());

        this.entityManager.persist(role);
    }

    private AuthAudit resolveAudit() {
        return new AuthAudit(settings.createdBy(), settings.createdOn());
    }
}
