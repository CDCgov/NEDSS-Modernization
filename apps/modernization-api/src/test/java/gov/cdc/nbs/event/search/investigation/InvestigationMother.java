package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class InvestigationMother {

    private static final String INVESTIGATION_CODE = "CAS";
    private static final String PERSON_CODE = "PSN";
    private static final String INVESTIGATION_CLASS = "CASE";
    private final TestUniqueIdGenerator idGenerator;

    private final MotherSettings settings;
    private final EntityManager entityManager;
    private final TestInvestigations investigations;
    private final TestInvestigationCleaner cleaner;

    public InvestigationMother(
            final MotherSettings settings,
            final TestUniqueIdGenerator idGenerator,
            final EntityManager entityManager,
            final TestInvestigations investigations,
            final TestInvestigationCleaner cleaner) {
        this.idGenerator = idGenerator;
        this.settings = settings;
        this.entityManager = entityManager;
        this.investigations = investigations;
        this.cleaner = cleaner;
    }

    void reset() {
        this.cleaner.clean(this.settings.starting());
        this.investigations.reset();
    }

    public PublicHealthCase investigation(final long subject) {
        long identifier = idGenerator.next();
        String local = idGenerator.nextLocal(INVESTIGATION_CODE);

        PublicHealthCase investigation = new PublicHealthCase(identifier, local);

        investigation.setRecordStatusTime(settings.createdOn());
        investigation.setAddTime(settings.createdOn());
        investigation.setAddUserId(settings.createdBy());

        investigation.setCd("42060"); // other injury
        investigation.setJurisdictionCd("999999"); // out of system
        investigation.setProgramJurisdictionOid(1300600015L); // Clayton county + STD

        subjectOf(investigation.getAct(), subject);

        this.entityManager.persist(investigation);

        this.investigations.available(identifier);
        return investigation;
    }

    private void subjectOf(final Act act, final long patient) {

        // create the participation
        ParticipationId identifier = new ParticipationId(patient, act.getId(), "SubjOfPHC");

        Participation participation = new Participation();
        participation.setId(identifier);
        participation.setActClassCd(INVESTIGATION_CLASS);
        participation.setSubjectClassCd(PERSON_CODE);

        participation.setRecordStatusCd(RecordStatus.ACTIVE);
        participation.setRecordStatusTime(settings.createdOn());
        participation.setAddTime(settings.createdOn());
        participation.setAddUserId(settings.createdBy());
        participation.setActUid(act);

        act.addParticipation(participation);

    }
}
