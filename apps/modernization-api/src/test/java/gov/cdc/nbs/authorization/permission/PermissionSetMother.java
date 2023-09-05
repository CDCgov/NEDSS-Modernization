package gov.cdc.nbs.authorization.permission;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthBusObjType;
import gov.cdc.nbs.authentication.entity.AuthBusOpType;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.support.TestAvailable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class PermissionSetMother {

    private final MotherSettings settings;
    private final EntityManager entityManager;
    private final TestPermissionSetCleaner cleaner;
    private final TestAvailable<Long> available;

    private final AuthObjectTypeFinder objectFinder;
    private final AuthOperationTypeFinder operationFinder;

    PermissionSetMother(
        final MotherSettings settings,
        final EntityManager entityManager,
        final TestPermissionSetCleaner cleaner,
        final AuthObjectTypeFinder objectFinder,
        final AuthOperationTypeFinder operationFinder
    ) {
        this.settings = settings;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.objectFinder = objectFinder;
        this.operationFinder = operationFinder;
        this.available = new TestAvailable<>();
    }

    void reset() {
        this.available.all().forEach(this.cleaner::clean);
        this.available.reset();
    }


    private AuthAudit resolveAudit() {
        return new AuthAudit(settings.createdBy(), settings.createdOn());
    }

    long allow(final String operation, final String object) {

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
        set.add(operationType, objectType);
    }

}
