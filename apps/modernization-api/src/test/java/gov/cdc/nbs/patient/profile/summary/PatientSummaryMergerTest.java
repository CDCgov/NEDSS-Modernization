package gov.cdc.nbs.patient.profile.summary;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientSummaryMergerTest {

  @Test
  void should_merge_with_values_of_left_only() {

    PatientSummaryMerger merger = new PatientSummaryMerger();

    PatientSummary actual = merger.merge(
        new PatientSummary(
            LocalDate.parse("2017-08-17"),
            2459L,
            new PatientSummary.Name("left-prefix", "left-first", "left-middle", "left-last", "left-suffix"),
            LocalDate.of(2011, Month.FEBRUARY, 21),
            17,
            "left-gender",
            "left-ethnicity",
            List.of(),
            List.of()
        ),
        new PatientSummary(
            LocalDate.parse("2017-08-17"),
            2459L,
            new PatientSummary.Name("right-prefix", "right-first", "right-middle", "right-last", "right-suffix"),
            LocalDate.of(1987, Month.JULY, 17),
            23,
            "right-gender",
            "right-ethnicity",
            List.of(),
            List.of()
        )
    );


    assertThat(actual).extracting(PatientSummary::legalName)
        .returns("left-prefix", PatientSummary.Name::prefix)
        .returns("left-first", PatientSummary.Name::first)
        .returns("left-middle", PatientSummary.Name::middle)
        .returns("left-last", PatientSummary.Name::last)
        .returns("left-suffix", PatientSummary.Name::suffix);

    assertThat(actual.birthday()).isEqualTo("2011-02-21");
    assertThat(actual.age()).isEqualTo(17);
    assertThat(actual.gender()).isEqualTo("left-gender");
    assertThat(actual.ethnicity()).isEqualTo("left-ethnicity");

    assertThat(actual.phone()).isEmpty();
    assertThat(actual.email()).isEmpty();

  }

  @Test
  void should_merge_with_merged_phone_values_of_left_and_right() {

    PatientSummaryMerger merger = new PatientSummaryMerger();

    PatientSummary merged = merger.merge(
        new PatientSummary(
            LocalDate.now(),
            2459L,
            null,
            LocalDate.of(2011, Month.FEBRUARY, 21),
            17,
            "left-gender",
            "left-ethnicity",
            List.of(
                new PatientSummary.Phone("phone-use-one", "phone-number-one"),
                new PatientSummary.Phone("phone-use-three", "phone-number-three")
            ),
            List.of()
        ),
        new PatientSummary(
            LocalDate.now(),
            2459L,
            null,
            LocalDate.of(1987, Month.JULY, 17),
            23,
            "right-gender",
            "right-ethnicity",
            List.of(
                new PatientSummary.Phone("phone-use-two", "phone-number-two")
            ),
            List.of()
        )
    );

    assertThat(merged.phone())
        .extracting(PatientSummary.Phone::use)
        .contains(
            "phone-use-one",
            "phone-use-two",
            "phone-use-three"
        );

  }

  @Test
  void should_merge_with_merged_email_values_of_left_and_right() {

    PatientSummaryMerger merger = new PatientSummaryMerger();

    PatientSummary merged = merger.merge(
        new PatientSummary(
            LocalDate.now(),
            2459L,
            null,
            LocalDate.of(2011, Month.FEBRUARY, 21),
            17,
            "left-gender",
            "left-ethnicity",
            List.of(),
            List.of(
                new PatientSummary.Email("email-use-one", "email-number-one"),
                new PatientSummary.Email("email-use-three", "email-number-three")
            )
        ),
        new PatientSummary(
            LocalDate.now(),
            2459L,
            null,
            LocalDate.of(1987, Month.JULY, 17),
            23,
            "right-gender",
            "right-ethnicity",
            List.of(),
            List.of(
                new PatientSummary.Email("email-use-two", "email-number-two")
            )
        )
    );

    assertThat(merged.email())
        .extracting(PatientSummary.Email::use)
        .contains(
            "email-use-one",
            "email-use-two",
            "email-use-three"
        );

  }

}
