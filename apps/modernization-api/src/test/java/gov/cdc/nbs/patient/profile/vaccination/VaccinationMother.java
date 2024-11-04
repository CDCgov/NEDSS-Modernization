package gov.cdc.nbs.patient.profile.vaccination;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.Act;
import gov.cdc.nbs.entity.odse.Intervention;
import gov.cdc.nbs.entity.odse.Participation;
import gov.cdc.nbs.entity.odse.ParticipationId;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

@Component
class VaccinationMother {

    private static final String VACCINATION_CLASS = "INTV";
    private static final String PERSON_CLASS = "PAT";

    private final MotherSettings settings;
    private final SequentialIdentityGenerator idGenerator;
    private final EntityManager entityManager;
    private final Active<VaccinationIdentifier> activeVaccination;
    private final TestVaccinationCleaner cleaner;
    private final TestVaccinations vaccinations;

    VaccinationMother(
        final MotherSettings settings,
        final SequentialIdentityGenerator idGenerator,
        final EntityManager entityManager,
        final TestVaccinationCleaner cleaner,
        final TestVaccinations vaccinations,
        final Active<VaccinationIdentifier> activeVaccination
    ) {
        this.settings = settings;
        this.idGenerator = idGenerator;
        this.entityManager = entityManager;
        this.cleaner = cleaner;
        this.vaccinations = vaccinations;
        this.activeVaccination = activeVaccination;
    }

    void reset() {
        this.cleaner.clean(this.settings.starting());
        this.vaccinations.reset();
    }

    /**
     * Creates a Vaccination for the given {@code patient}
     *
     * @param patient The identifier of the patient.
     * @return An {@link Intervention} representing a vaccination of a patient.
     */
    Intervention vaccinate(final long patient) {
        //  create a vaccination
        long identifier = idGenerator.next();
        String local = idGenerator.nextLocal(VACCINATION_CLASS);

        Intervention vaccination = new Intervention(identifier, local);

        vaccination.setRecordStatusTime(settings.createdOn());
        vaccination.setAddTime(settings.createdOn());
        vaccination.setAddUserId(settings.createdBy());

        vaccination.setActivityFromTime(RandomUtil.getRandomDateInPast());
        vaccination.setMaterialCd("115"); // Tdap

        subjectOfVaccination(patient, vaccination.getAct());

        entityManager.persist(vaccination);

        this.vaccinations.available(vaccination.getId());
        activeVaccination.active(new VaccinationIdentifier(identifier, local));

        return vaccination;
    }

    private Participation subjectOfVaccination(final long patient, final Act act) {

        // create the participation
        ParticipationId identifier = new ParticipationId(patient, act.getId(), "SubOfVacc");

        Participation participation = new Participation();
        participation.setId(identifier);
        participation.setActClassCd(VACCINATION_CLASS);
        participation.setSubjectClassCd(PERSON_CLASS);

        participation.setRecordStatusCd(RecordStatus.ACTIVE);
        participation.setRecordStatusTime(settings.createdOn());
        participation.setAddTime(settings.createdOn());
        participation.setAddUserId(settings.createdBy());
        participation.setActUid(act);

        act.addParticipation(participation);

        entityManager.persist(act);

        return participation;
    }

}
