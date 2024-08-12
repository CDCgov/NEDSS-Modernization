package gov.cdc.nbs.search.redirect.simple;

import gov.cdc.nbs.option.Option;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;


class SimplePatientSearchCriteriaMergerTest {

  @Test
  void should_merge_last_name_preferring_the_first_value() {

    SimplePatientSearchCriteria actual = SimplePatientSearchCriteriaMerger.merge(
        new SimplePatientSearchCriteria("first-last-name", null, null, null, null),
        new SimplePatientSearchCriteria("second-last-name", null, null, null, null)
    );

    assertThat(actual.lastName()).isEqualTo("first-last-name");
  }

  @Test
  void should_merge_first_name_preferring_the_first_value() {

    SimplePatientSearchCriteria actual = SimplePatientSearchCriteriaMerger.merge(
        new SimplePatientSearchCriteria(null, "first-first-name", null, null, null),
        new SimplePatientSearchCriteria(null, "second-first-name", null, null, null)
    );

    assertThat(actual.firstName()).isEqualTo("first-first-name");
  }

  @Test
  void should_merge_date_of_birth_preferring_the_first_value() {

    SimplePatientSearchCriteria actual = SimplePatientSearchCriteriaMerger.merge(
        new SimplePatientSearchCriteria(null, null, LocalDate.of(2014, Month.JULY, 13), null, null),
        new SimplePatientSearchCriteria(null, null, LocalDate.of(2014, Month.FEBRUARY, 2), null, null)
    );

    assertThat(actual.dateOfBirth()).isEqualTo("2014-07-13");
  }


  @Test
  void should_merge_gender_preferring_the_first_value() {

    SimplePatientSearchCriteria actual = SimplePatientSearchCriteriaMerger.merge(
        new SimplePatientSearchCriteria(null, null, null, new Option("first-gender"), null),
        new SimplePatientSearchCriteria(null, null, null, new Option("second-gender"), null)
    );

    assertThat(actual.gender()).isEqualTo(new Option("first-gender"));
  }


  @Test
  void should_merge_id_preferring_the_first_value() {

    SimplePatientSearchCriteria actual = SimplePatientSearchCriteriaMerger.merge(
        new SimplePatientSearchCriteria(null, null, null, null, "first-id-value"),
        new SimplePatientSearchCriteria(null, null, null, null, "second-id-value")
    );

    assertThat(actual.id()).isEqualTo("first-id-value");
  }
}
