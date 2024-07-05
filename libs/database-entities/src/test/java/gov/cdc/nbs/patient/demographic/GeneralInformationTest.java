package gov.cdc.nbs.patient.demographic;



import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class GeneralInformationTest {

  @Test
  void should_update_general_information_fields() {

    GeneralInformation actual = new GeneralInformation();

    actual.update(
        new PatientCommand.UpdateGeneralInfo(
            121L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            12L,
            Instant.parse("2019-03-03T10:15:30.00Z")
        )
    );

    assertThat(actual)
        .returns(Instant.parse("2010-03-03T10:15:30.00Z"), GeneralInformation::asOf)
        .returns("marital status", GeneralInformation::maritalStatus)
        .returns("mothers maiden name", GeneralInformation::mothersMaidenName)
        .returns(1, GeneralInformation::adultsInHouse)
        .returns(2, GeneralInformation::childrenInHouse)
        .returns("occupation code", GeneralInformation::occupation)
        .returns("education level", GeneralInformation::educationLevel)
        .returns("prim language", GeneralInformation::primaryLanguage)
        .returns("speaks english", GeneralInformation::speaksEnglish)
        .returns("eharsId", GeneralInformation::stateHIVCase);
  }

  @Test
  void should_update_state_HIV_Case_fields_when_allowed() {

    GeneralInformation actual = new GeneralInformation();

    actual.associate(
        new PatientCommand.AssociateStateHIVCase(
            263L,
            "case-number",
            12L,
            Instant.parse("2019-03-03T10:15:30.00Z")
        )
    );

    assertThat(actual)
        .returns("case-number", GeneralInformation::stateHIVCase);
  }
}
