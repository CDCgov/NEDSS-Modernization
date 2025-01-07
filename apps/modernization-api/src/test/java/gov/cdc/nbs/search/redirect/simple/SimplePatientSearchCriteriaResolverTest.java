package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.search.criteria.date.DateCriteria;
import gov.cdc.nbs.search.criteria.text.TextCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SimplePatientSearchCriteriaResolverTest {

  @Test
  void should_resolve_first_name_starting_with_value() {
    Map<String, String> parameters = Map.of("patientSearchVO.firstName", "first-name-value");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .extracting(SimplePatientSearchCriteria::name)
            .extracting(SimplePatientSearchNameCriteria::first)
            .returns("first-name-value", TextCriteria::startsWith)
    );
  }

  @Test
  void should_resolve_first_name_containing_value() {
    Map<String, String> parameters = Map.of("patientSearchVO.firstName", "%first-name-value");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .extracting(SimplePatientSearchCriteria::name)
            .extracting(SimplePatientSearchNameCriteria::first)
            .returns("first-name-value", TextCriteria::contains)
    );
  }

  @Test
  void should_resolve_last_name_starting_with_value() {
    Map<String, String> parameters = Map.of("patientSearchVO.lastName", "last-name-value");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .extracting(SimplePatientSearchCriteria::name)
            .extracting(SimplePatientSearchNameCriteria::last)
            .returns("last-name-value", TextCriteria::startsWith)
    );
  }

  @Test
  void should_resolve_last_name_contains_value() {
    Map<String, String> parameters = Map.of("patientSearchVO.lastName", "%last-name-value");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .extracting(SimplePatientSearchCriteria::name)
            .extracting(SimplePatientSearchNameCriteria::last)
            .returns("last-name-value", TextCriteria::contains)
    );
  }

  @Test
  void should_resolve_date_of_birth() {
    Map<String, String> parameters = Map.of("patientSearchVO.birthTime", "01/01/1990");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .satisfies(value -> assertThat(value.bornOn()).isEqualTo(DateCriteria.equals(1, 1, 1990)))
    );
  }

  @Test
  void should_resolve_patient_id() {
    Map<String, String> parameters = Map.of("patientSearchVO.localID", "local-id-value");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .returns("local-id-value", SimplePatientSearchCriteria::id)
    );
  }

  @Test
  void should_not_resolve_empty_gender() {
    Map<String, String> parameters = Map.of("patientSearchVO.currentSex", "");


    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);

    assertThat(resolved).isNotPresent();
  }

  @ParameterizedTest
  @MethodSource("searchableGenders")
  void should_resolve_gender(final String gender, final Gender expected) {
    Map<String, String> parameters = Map.of("patientSearchVO.currentSex", gender);

    Optional<SimplePatientSearchCriteria> resolved = new SimplePatientSearchCriteriaResolver().resolve(parameters);


    assertThat(resolved).hasValueSatisfying(
        actual -> assertThat(actual)
            .asInstanceOf(type(SimplePatientSearchCriteria.class))
            .satisfies(value -> assertThat(value.gender())
                .returns(expected.value(), Option::value)
                .returns(expected.display(), Option::name)
                .returns(expected.display(), Option::label)
            )
    );
  }

  public static Stream<Arguments> searchableGenders() {
    return Stream.of(
        arguments("F", Gender.F),
        arguments("f", Gender.F),
        arguments("M", Gender.M),
        arguments("m", Gender.M),
        arguments("U", Gender.U),
        arguments("u", Gender.U)
    );
  }
}
