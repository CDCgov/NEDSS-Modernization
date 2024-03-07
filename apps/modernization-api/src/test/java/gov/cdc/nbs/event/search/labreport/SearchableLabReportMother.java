package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchActId;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchObservation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.PatientMother;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.search.support.ElasticsearchIndexCleaner;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Component
@ScenarioScope
class SearchableLabReportMother {

  private static final String LAB_REPORT_CLASS_CODE = "OBS";
  private static final Long PATIENT_ID = 8888888L;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final ElasticsearchIndexCleaner cleaner;

  private final Active<JurisdictionIdentifier> activeJurisdiction;
  private final Active<ProgramAreaIdentifier> activeProgramArea;
  private final PatientMother patientMother;
  private final LabReportRepository repository;

  private final Active<LabReport> active;
  private final Available<LabReport> available;

  SearchableLabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final ElasticsearchIndexCleaner cleaner,
      final Active<JurisdictionIdentifier> activeJurisdiction,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final PatientMother patientMother,
      final LabReportRepository repository,
      final Active<LabReport> active,
      final Available<LabReport> available
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.cleaner = cleaner;
    this.activeJurisdiction = activeJurisdiction;
    this.activeProgramArea = activeProgramArea;
    this.patientMother = patientMother;
    this.repository = repository;
    this.active = active;
    this.available = available;
  }

  @PostConstruct
  void shutdown() {
    //  triggered when the bean is destroyed by the Spring IoC container
    this.cleaner.clean("lab_report");
  }

  void create(final PatientIdentifier patient) {
    PatientIdentifier revision = patientMother.revise(patient);

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

    var now = Instant.now();

    JurisdictionIdentifier jurisdiction = this.activeJurisdiction.active();
    ProgramAreaIdentifier programArea = this.activeProgramArea.active();

    LabReport created = LabReport.builder()
        .id(String.valueOf(identifier))
        .observationUid(identifier)
        .classCd(LAB_REPORT_CLASS_CODE)
        .moodCd("EVN")
        .programJurisdictionOid(programArea.oid(jurisdiction))
        .programAreaCd(programArea.code())
        .jurisdictionCd(Long.valueOf(jurisdiction.code()))
        .pregnantIndCd("Y")
        .localId(local)
        .activityToTime(now)
        .effectiveFromTime(now)
        .rptToStateTime(now)
        .observationLastChgTime(now)
        .electronicInd("E")
        .addTime(this.settings.createdOn())
        .addUserId(this.settings.createdBy())
        .lastChange(this.settings.createdOn())
        .lastChgUserId(this.settings.createdBy())
        .versionCtrlNbr(1L)
        .recordStatusCd("UNPROCESSED")
        .actIds(
            Collections.singletonList(
                ElasticsearchActId.builder()
                    .actIdSeq(2)
                    .typeDescTxt("Filler Number")
                    .rootExtensionTxt("accession number")
                    .build()
            )
        )
        .organizationParticipations(
            List.of(
                // ordering facility
                ElasticsearchOrganizationParticipation.builder()
                    .typeCd("ORD")
                    .subjectClassCd("ORG")
                    .entityId(PATIENT_ID)
                    .build(),
                //  reporting facility
                ElasticsearchOrganizationParticipation.builder()
                    .subjectClassCd("ORG")
                    .typeCd("AUT")
                    .entityId(PATIENT_ID)
                    .build()
            )
        )
        .personParticipations(
            List.of(
                //  patient
                ElasticsearchPersonParticipation.builder()
                    .entityId(revision.id())
                    .personCd("PAT")
                    .typeCd("PATSBJ")
                    .personRecordStatus("ACTIVE")
                    .personParentUid(patient.id())
                    .build(),
                //  ordering provider
                ElasticsearchPersonParticipation.builder()
                    .typeCd("ORD")
                    .subjectClassCd("PSN")
                    .personRecordStatus("ACTIVE")
                    .entityId(PATIENT_ID)
                    .build()
            )
        )
        .observations(
            Collections.singletonList(
                //  lab test
                ElasticsearchObservation.builder()
                    .cdDescTxt("Acid-Fast Stain")
                    .displayName("abnormal")
                    .build()
            )
        )
        .build();

    this.repository.save(created);
    this.active.active(created);
    this.available.available(created);
  }

}
