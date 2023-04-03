package gov.cdc.nbs.investigation;

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
      final TestInvestigationCleaner cleaner
  ) {
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

    PublicHealthCase investigation = new PublicHealthCase();
    investigation.setId(identifier);
    investigation.setLocalId(idGenerator.nextLocal(INVESTIGATION_CODE));

    investigation.setRecordStatusCd("ACTIVE");
    investigation.setRecordStatusTime(settings.createdOn());
    investigation.setAddTime(settings.createdOn());
    investigation.setAddUserId(settings.createdBy());
    investigation.setVersionCtrlNbr((short) 1);
    investigation.setSharedInd('T');

    //  create the act
    Act act = new Act();
    act.setId(identifier);
    act.setClassCd(INVESTIGATION_CLASS);
    act.setMoodCd("EVN");

    investigation.setAct(act);

    this.entityManager.persist(investigation);

    subjectOf(act, subject);

    this.investigations.available(identifier);
    return investigation;
  }

  private Participation subjectOf(final Act act, final long patient) {

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

    entityManager.persist(act);

    return participation;
  }
}
