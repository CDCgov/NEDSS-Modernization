package gov.cdc.nbs.authorization;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import java.util.UUID;

@Component
class AuthUserMother {

    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final EntityManager entityManager;
    private final TestActiveUserCleaner cleaner;
    private final TestAuthUsers users;


    AuthUserMother(
            final MotherSettings settings,
            final TestUniqueIdGenerator idGenerator,
            final EntityManager entityManager,
            final TestActiveUserCleaner cleaner,
            final TestAuthUsers users) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.users = users;
    }

    void reset() {
        this.cleaner.clean(this.settings.starting());
        this.users.reset();
    }

    AuthUser create() {
        long identifier = idGenerator.next();

        AuthUser user = new AuthUser();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserType("internalUser");
        user.setUserFirstNm("test");
        user.setUserLastNm("user");
        user.setMasterSecAdminInd('F');
        user.setProgAreaAdminInd('F');
        user.setNedssEntryId(identifier);

        AuthAudit audit = new AuthAudit();

        audit.setRecordStatusCd(AuthRecordStatus.ACTIVE);
        audit.setRecordStatusTime(this.settings.createdOn());
        audit.setAddUserId(this.settings.createdBy());
        audit.setAddTime(this.settings.createdOn());
        audit.setLastChgUserId(this.settings.createdBy());
        audit.setLastChgTime(this.settings.createdOn());

        user.setAudit(audit);

        entityManager.persist(user);

        users.available(identifier);

        return user;
    }
}
