package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.option.Option;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class SimpleLaboratoryReportSearchCriteriaResolverTest {

  @Test
  void should_resolve_accession_number() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "accession-number-value",
        "patientSearchVO.actType", "P10009"
    );


    Optional<SimpleLaboratoryReportSearchCriteria> resolved =
        new SimpleLaboratoryReportSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleLaboratoryReportSearchCriteria.class))
            .extracting(SimpleLaboratoryReportSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("accession-number-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER.value(), Option::value)
            .returns(LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER.display(), Option::name)
            .returns(LabReportFilter.LaboratoryEventIdType.ACCESSION_NUMBER.display(), Option::label)
    );
  }

  @Test
  void should_resolve_laboratory_identifier() {
    Map<String, String> parameters = Map.of(
        "patientSearchVO.actId", "laboratory-identifier-value",
        "patientSearchVO.actType", "P10002"
    );

    Optional<SimpleLaboratoryReportSearchCriteria> resolved =
        new SimpleLaboratoryReportSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimpleLaboratoryReportSearchCriteria.class))
            .extracting(SimpleLaboratoryReportSearchCriteria::identification, type(SimpleSearchIdentification.class))
            .returns("laboratory-identifier-value", SimpleSearchIdentification::value)
            .extracting(SimpleSearchIdentification::type, type(Option.class))
            .returns(LabReportFilter.LaboratoryEventIdType.LAB_ID.value(), Option::value)
            .returns(LabReportFilter.LaboratoryEventIdType.LAB_ID.display(), Option::name)
            .returns(LabReportFilter.LaboratoryEventIdType.LAB_ID.display(), Option::label)
    );
  }

}
