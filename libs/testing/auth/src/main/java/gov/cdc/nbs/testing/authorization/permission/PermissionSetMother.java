package gov.cdc.nbs.testing.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthBusObjType;
import gov.cdc.nbs.authentication.entity.AuthBusOpType;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.testing.authorization.AuthenticationSupportSettings;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Component
public class PermissionSetMother {

    private final AuthenticationSupportSettings settings;
    private final EntityManager entityManager;
    private final PermissionSetCleaner cleaner;
    private final Available<Long> available;

    private final AuthorizationObjectTypeFinder objectFinder;
    private final AuthorizationOperationTypeFinder operationFinder;

    PermissionSetMother(
            final AuthenticationSupportSettings settings,
            final EntityManager entityManager,
            final PermissionSetCleaner cleaner,
            final AuthorizationObjectTypeFinder objectFinder,
            final AuthorizationOperationTypeFinder operationFinder) {
        this.settings = settings;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.objectFinder = objectFinder;
        this.operationFinder = operationFinder;
        this.available = new Available<>();
    }

    @Transactional
    public void reset() {
        this.available.all().forEach(this.cleaner::clean);
        this.available.reset();
    }

    private AuthAudit resolveAudit() {
        return new AuthAudit(settings.createdBy(), settings.createdOn());
    }

    public long allow(final String operation, final String object) {

        AuthPermSet set = createPermissionSet();

        createRight(set, operation, object);

        return set.id();
    }

    private AuthPermSet createPermissionSet() {

        AuthAudit audit = resolveAudit();

        AuthPermSet permissionSet = new AuthPermSet()
                .name("Test-Permission")
                .description("Permission set used for integration testing")
                .audit(audit);

        this.entityManager.persist(permissionSet);

        this.available.available(permissionSet.id());

        return permissionSet;
    }

    private void createRight(
            final AuthPermSet set,
            final String operation,
            final String object
    ) {
        AuthBusObjType objectType = this.objectFinder.find(object);
        AuthBusOpType operationType = this.operationFinder.find(operation);
        set.addObjectRight(operationType, objectType);
    }

}
