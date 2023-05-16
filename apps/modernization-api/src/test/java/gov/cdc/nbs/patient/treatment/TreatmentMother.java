package gov.cdc.nbs.patient.treatment;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.entity.odse.Treatment;
import gov.cdc.nbs.entity.odse.TreatmentAdministered;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.identity.TestUniqueIdGenerator;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
class TreatmentMother {

    private static final String PERSON_CLASS = "PSN";
    private static final String SUBJECT_OF_TREATMENT = "SubjOfTrmt";
    private static final String TREATMENT_OF_INVESTIGATION = "TreatmentToPHC";

    private final MotherSettings settings;
    private final TestUniqueIdGenerator idGenerator;
    private final EntityManager entityManager;
    private final TestTreatmentCleaner cleaner;

    private final TestTreatments treatments;

    TreatmentMother(
        final MotherSettings settings,
        final TestUniqueIdGenerator idGenerator,
        final EntityManager entityManager,
        final TestTreatmentCleaner cleaner,
        final TestTreatments treatments
    ) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.treatments = treatments;
    }

    void reset() {
        this.cleaner.clean(this.settings.starting());
        this.treatments.reset();
    }

    Treatment treated(final long patient, final long investigation) {
        long identifier = idGenerator.next();
        String localId = idGenerator.nextLocal("TRT");

        Treatment treatment = new Treatment(identifier, localId);
        treatment.setActivityToTime(RandomUtil.getRandomDateInPast());

        // Treatment: Other
        treatment.setCd("OTH");
        treatment.setCdDescTxt("Other");

        // Jurisdiction: Out of system
        treatment.setJurisdictionCd("999999");

        treatment.setRecordStatusCd("ACTIVE");
        treatment.setRecordStatusTime(settings.createdOn());
        treatment.setAddTime(settings.createdOn());
        treatment.setAddUserId(settings.createdBy());

        subjectOfTreatment(treatment, patient);

        administered(treatment);

        treatedWithInvestigation(treatment, investigation);

        this.entityManager.persist(treatment);

        this.treatments.available(identifier);


        return treatment;
    }

    private void subjectOfTreatment(final Treatment treatment, final long patient) {

        Act act = treatment.getAct();

        // create the participation
        ParticipationId identifier = new ParticipationId(patient, act.getId(), SUBJECT_OF_TREATMENT);

        Participation participation = new Participation();
        participation.setId(identifier);
        participation.setActClassCd(act.getClassCd());
        participation.setSubjectClassCd(PERSON_CLASS);

        participation.setRecordStatusCd(RecordStatus.ACTIVE);
        participation.setRecordStatusTime(settings.createdOn());
        participation.setAddTime(settings.createdOn());
        participation.setAddUserId(settings.createdBy());
        participation.setActUid(act);

        act.addParticipation(participation);

    }

    private void administered(final Treatment treatment) {

        TreatmentAdministered administered = treatment.administer();
        administered.setEffectiveFromTime(treatment.getAddTime());

    }

    private void treatedWithInvestigation(
        final Treatment treatment,
        final long investigation
    ) {

        treatment.getAct().addRelationship(
            resolve(investigation),
            TREATMENT_OF_INVESTIGATION
        );


    }

    private Act resolve(final long identifier) {
        return this.entityManager.find(Act.class, identifier);
    }
}
