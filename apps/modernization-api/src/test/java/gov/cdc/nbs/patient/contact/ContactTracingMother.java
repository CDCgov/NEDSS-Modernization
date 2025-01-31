package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.entity.odse.CtContact;
import gov.cdc.nbs.entity.odse.NBSEntity;
import gov.cdc.nbs.entity.odse.PublicHealthCase;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;

import java.time.ZoneOffset;

@Component
@ScenarioScope
class ContactTracingMother {

  private static final String TRACING_CLASS = "CON";

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final EntityManager entityManager;
  private final TestContactTracings tracings;

  private final TestContactTracingCleaner cleaner;

  ContactTracingMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final EntityManager entityManager,
      final TestContactTracings tracings,
      final TestContactTracingCleaner cleaner
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.entityManager = entityManager;
    this.tracings = tracings;
    this.cleaner = cleaner;
  }

  @PostConstruct
  void reset() {
    this.cleaner.clean(settings.starting());
    this.tracings.reset();
  }

  void namedByPatient(final long investigation, final long patient, final long named) {
    CtContact tracing = new CtContact();
    long identifier = idGenerator.next();

    tracing.setId(identifier);
    tracing.setLocalId(idGenerator.nextLocal(TRACING_CLASS));

    tracing.setVersionCtrlNbr((short)1);
    tracing.setRecordStatusCd("ACTIVE");
    tracing.setRecordStatusTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    tracing.setAddUserId(settings.createdBy());
    tracing.setAddTime(settings.createdOn().toInstant(ZoneOffset.UTC));
    tracing.setLastChgUserId(settings.createdBy());
    tracing.setLastChgTime(settings.createdOn().toInstant(ZoneOffset.UTC));

    PublicHealthCase publicHealthCase = lookupInvestigation(investigation);

    tracing.setSubjectEntityPhcUid(publicHealthCase);
    tracing.setSubjectNBSEntityUid(lookupPatient(patient));
    tracing.setContactNBSEntityUid(lookupPatient(named));

    this.entityManager.persist(tracing);

    publicHealthCase.addSubjectContact(tracing);

    this.tracings.available(identifier);
  }

  private PublicHealthCase lookupInvestigation(final long identifier) {
    return this.entityManager.getReference(PublicHealthCase.class, identifier);
  }

  private NBSEntity lookupPatient(final long identifier) {
    return this.entityManager.getReference(NBSEntity.class, identifier);
  }
}
