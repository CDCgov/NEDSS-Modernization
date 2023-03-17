package gov.cdc.nbs.redirect.search;

import gov.cdc.nbs.graphql.filter.PatientFilter;
import gov.cdc.nbs.message.enums.Gender;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PatientFilterFromRequestParamResolverTest {

  @Test
  void should_resolve_first_name() {

    Map<String, String> parameters = Map.of("patientSearchVO.firstName", "first-name-value");

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getFirstName()).isEqualTo("first-name-value");

  }

  @Test
  void should_resolve_last_name() {

    Map<String, String> parameters = Map.of("patientSearchVO.lastName", "last-name-value");

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getLastName()).isEqualTo("last-name-value");

  }

  @Test
  void should_resolve_birth_date() {

    Map<String, String> parameters = Map.of("patientSearchVO.birthTime", "01/01/1990");

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getDateOfBirth()).isEqualTo("1990-01-01");

  }

  @Test
  void should_resolve_gender() {

    Map<String, String> parameters = Map.of("patientSearchVO.currentSex", "F");

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getGender()).isEqualTo(Gender.F);

  }

  @Test
  void should_resolve_id() {

    Map<String, String> parameters = Map.of("patientSearchVO.localID", "local-id-value");

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getId()).isEqualTo("local-id-value");

  }

  @Test
  void should_resolve_treatment() {

    Map<String, String> parameters = Map.of(
        "patientSearchVO.actType", "P10005",
        "patientSearchVO.actId", "treatment-id-value"
    );

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getTreatmentId()).isEqualTo("treatment-id-value");

  }

  @Test
  void should_resolve_vaccine() {

    Map<String, String> parameters = Map.of(
        "patientSearchVO.actType", "P10006",
        "patientSearchVO.actId", "vaccine-id-value"
    );

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getVaccinationId()).isEqualTo("vaccine-id-value");

  }
}
