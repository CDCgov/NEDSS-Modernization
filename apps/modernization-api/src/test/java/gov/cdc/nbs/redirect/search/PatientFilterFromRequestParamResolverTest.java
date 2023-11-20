package gov.cdc.nbs.redirect.search;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.search.PatientFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

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

  @ParameterizedTest
  @MethodSource("searchableGenders")
  void should_resolve_gender(final String gender, final String expected) {

    Map<String, String> parameters = Map.of("patientSearchVO.currentSex", gender);

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getGender()).isEqualTo(expected);

  }

  public static Stream<Arguments> searchableGenders() {
    return Stream.of(
        arguments(Gender.F.value(), "F"),
        arguments("f", "F"),
        arguments(Gender.M.value(), "M"),
        arguments("m", "M"),
        arguments(Gender.U.value(), "U"),
        arguments("u", "U")
    );
  }

  @ParameterizedTest
  @MethodSource("unknownGenders")
  void should_not_resolve_unknown_gender(final String gender) {

    Map<String, String> parameters = Map.of("patientSearchVO.currentSex", gender);

    PatientFilter actual = new PatientFilterFromRequestParamResolver().resolve(parameters);

    assertThat(actual.getGender()).isNull();

  }

  public static Stream<Arguments> unknownGenders() {
    return Stream.of(
        arguments(""),
        arguments((String) null)
    );
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
