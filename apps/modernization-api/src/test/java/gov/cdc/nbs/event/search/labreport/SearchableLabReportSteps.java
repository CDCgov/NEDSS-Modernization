package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.entity.srte.JurisdictionCode;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.repository.JurisdictionCodeRepository;
import gov.cdc.nbs.repository.elasticsearch.LabReportRepository;
import gov.cdc.nbs.support.EventMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;

import java.util.List;

public class SearchableLabReportSteps {

  private final Active<PatientIdentifier> patient;
  private final LabReportRepository labReportRepository;
  private final JurisdictionCodeRepository jurisdictionCodeRepository;

  SearchableLabReportSteps(
      final Active<PatientIdentifier> patient,
      final LabReportRepository labReportRepository,
      final JurisdictionCodeRepository jurisdictionCodeRepository
  ) {
    this.patient = patient;
    this.labReportRepository = labReportRepository;
    this.jurisdictionCodeRepository = jurisdictionCodeRepository;
  }

  @Before("@lab_report_search")
  public void reset() {
    List<JurisdictionCode> jurisdictions = EventMother.getJurisdictionCodes();
    jurisdictionCodeRepository.saveAll(jurisdictions);
  }

  @Given("A lab report exist")
  public void lab_report_exist() {

    this.patient.maybeActive()
        .map(found -> EventMother.labReport_acidFastStain(found.id()))
        .ifPresent(labReportRepository::save);

  }

}
