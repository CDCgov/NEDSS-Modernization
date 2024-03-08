package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.event.report.lab.LabReportIdentifier;
import gov.cdc.nbs.event.search.labreport.indexing.SearchableLabReportIndexer;
import gov.cdc.nbs.event.search.labreport.indexing.SearchableLabReportResolver;
import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.search.support.ElasticsearchIndexCleaner;
import gov.cdc.nbs.support.jurisdiction.JurisdictionIdentifier;
import gov.cdc.nbs.support.programarea.ProgramAreaIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@ScenarioScope
class SearchableLabReportMother {

  private static final String LAB_REPORT_CLASS_CODE = "OBS";


  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final ElasticsearchIndexCleaner cleaner;

  private final Active<JurisdictionIdentifier> activeJurisdiction;
  private final Active<ProgramAreaIdentifier> activeProgramArea;
  final SearchableLabReportResolver resolver;
  final SearchableLabReportIndexer indexer;
  private final Active<SearchableLabReport> active;
  private final Available<SearchableLabReport> available;

  SearchableLabReportMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final ElasticsearchIndexCleaner cleaner,
      final Active<JurisdictionIdentifier> activeJurisdiction,
      final Active<ProgramAreaIdentifier> activeProgramArea,
      final SearchableLabReportResolver resolver,
      final SearchableLabReportIndexer indexer,
      final Active<SearchableLabReport> active,
      final Available<SearchableLabReport> available
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.cleaner = cleaner;
    this.activeJurisdiction = activeJurisdiction;
    this.activeProgramArea = activeProgramArea;
    this.resolver = resolver;
    this.indexer = indexer;
    this.active = active;
    this.available = available;
  }

  @PostConstruct
  void shutdown() {
    //  triggered when the bean is destroyed by the Spring IoC container
    this.cleaner.clean("lab_report");
  }

  void create(final PatientIdentifier patient) {

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal(LAB_REPORT_CLASS_CODE);

    LocalDate now = LocalDate.now();
    LocalDate createdOn = LocalDate.ofInstant(this.settings.createdOn(), ZoneId.systemDefault());

    JurisdictionIdentifier jurisdiction = this.activeJurisdiction.active();
    ProgramAreaIdentifier programArea = this.activeProgramArea.active();

    SearchableLabReport created = new SearchableLabReport(
        identifier,
        LAB_REPORT_CLASS_CODE,
        "EVN",
        programArea.code(),
        jurisdiction.code(),
        programArea.oid(jurisdiction),
        "Y",
        local,
        now,
        now,
        now,
        this.settings.createdBy(),
        createdOn,
        this.settings.createdBy(),
        createdOn,
        1,
        "UNPROCESSED",
        "E",
        List.of(
            new SearchableLabReport.Person.Patient(
                patient.id(),
                patient.local(),
                "PATSBJ",
                "PSN",
                "First",
                "Last",
                RandomUtil.dateInPast()
            ),
            new SearchableLabReport.Person.Provider(
                157L,
                "ORD",
                "PSN",
                "Ordering",
                "Provider"
            )
        ),
        List.of(
            new SearchableLabReport.Organization(
                73L,
                "ORD",
                "ORG",
                "Ordering Facility"
            ),
            new SearchableLabReport.Organization(
                107L,
                "AUT",
                "ORG",
                "Reporting Facility"
            )
        ),
        List.of(
            new SearchableLabReport.LabTest(
                "Acid-Fast Stain",
                "abnormal",
                "ACTIVE",
                "1000"
            )
        ),
        List.of(
            new SearchableLabReport.Identifier(
                "FN",
                "Filler Number",
                "accession number"
            )
        ),
        List.of(
            new SearchableLabReport.Investigation(
                "INV-LOCAL",
                "Investigated condition"
            )
        )
    );

    this.indexer.index(created);
    this.available.available(created);
    this.active.active(created);
  }


  void searchable(final Stream<LabReportIdentifier> identifiers) {

    List<SearchableLabReport> reports = identifiers
        .map(identifier -> this.resolver.resolve(identifier.identifier()))
        .flatMap(Optional::stream)
        .toList();

    this.indexer.index(reports);
    this.available.include(reports);
  }
}
