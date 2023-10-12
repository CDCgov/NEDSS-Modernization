package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.UUID;

@Component
public class AuthUserMother {

    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final EntityManager entityManager;
    private final TestAuthUserCleaner cleaner;
    private final Available<TestAuthorizedUser> users;


    AuthUserMother(
        final MotherSettings settings,
        final TestUniqueIdGenerator idGenerator,
        final EntityManager entityManager,
        final TestAuthUserCleaner cleaner,
        final Available<TestAuthorizedUser> users
    ) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.users = users;
    }

    public void reset() {
        this.users.all().forEach(this.cleaner::clean);
        this.users.reset();
    }

    public AuthUser create(final String name) {
        long identifier = idGenerator.next();

        AuthUser user = new AuthUser();
        user.setUserId(name);
        user.setUserType("internalUser");
        user.setUserFirstNm("test");
        user.setUserLastNm("user");
        user.setMasterSecAdminInd('F');
        user.setProgAreaAdminInd('F');
        user.setNedssEntryId(identifier);

        AuthAudit audit = new AuthAudit(this.settings.createdBy(), this.settings.createdOn());

        user.setAudit(audit);

        entityManager.persist(user);

        users.available(new TestAuthorizedUser(user.getId(), user.getUserId()));

        return user;
    }

    AuthUser create() {
       return create(UUID.randomUUID().toString());
    }
}
