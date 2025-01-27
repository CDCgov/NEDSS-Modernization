package gov.cdc.nbs.patient.profile.ethnicity;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientEthnicityMergerTest {

    @Test
    void should_merge_with_values_of_left_only() {

        PatientEthnicityMerger merger = new PatientEthnicityMerger();

        PatientEthnicity actual = merger.merge(
            new PatientEthnicity(
                2L,
                73L,
                (short) 283,
                LocalDate.parse("1970-04-09"),
                new PatientEthnicity.EthnicGroup("ethnic-group-id", "ethnic-group-description"),
                new PatientEthnicity.UnknownReason("unknown-reason-id", "unknown-reason-description"),
                List.of()
            ),
            new PatientEthnicity(
                3L,
                79L,
                (short) 293,
                LocalDate.parse("2003-09-05"),
                new PatientEthnicity.EthnicGroup("ethnic-group-other-id", "ethnic-group-other-description"),
                new PatientEthnicity.UnknownReason("unknown-reason-other-id", "unknown-reason-other-description"),
                List.of()

            )
        );

        assertThat(actual.patient()).isEqualTo(2L);
        assertThat(actual.id()).isEqualTo(73L);
        assertThat(actual.version()).isEqualTo((short) 283);
        assertThat(actual.asOf()).isEqualTo("1970-04-09");

        assertThat(actual.ethnicGroup().id()).isEqualTo("ethnic-group-id");
        assertThat(actual.ethnicGroup().description()).isEqualTo("ethnic-group-description");

        assertThat(actual.unknownReason().id()).isEqualTo("unknown-reason-id");
        assertThat(actual.unknownReason().description()).isEqualTo("unknown-reason-description");

    }

    @Test
    void should_merge_with_merged_detailed_values_of_left_and_right() {

        PatientEthnicityMerger merger = new PatientEthnicityMerger();

        PatientEthnicity merged = merger.merge(
            new PatientEthnicity(
                2L,
                73L,
                (short) 283,
                null,
                null,
                null,
                List.of(
                    new PatientEthnicity.Ethnicity(
                        "ethnicity-one-id",
                        "ethnicity-one-description"
                    ),
                    new PatientEthnicity.Ethnicity(
                        "ethnicity-seven-id",
                        "ethnicity-seven-description"
                    ),
                    new PatientEthnicity.Ethnicity(
                        "ethnicity-two-id",
                        "ethnicity-two-description"
                    )
                )
            ),
            new PatientEthnicity(
                3L,
                79L,
                (short) 293,
                null,
                null,
                null,
                List.of(
                    new PatientEthnicity.Ethnicity(
                        "ethnicity-three-id",
                        "ethnicity-three-description"
                    ),
                    new PatientEthnicity.Ethnicity(
                        "ethnicity-four-id",
                        "ethnicity-four-description"
                    )
                )
            )
        );

        assertThat(merged.detailed())
            .extracting(PatientEthnicity.Ethnicity::id)
            .contains(
                "ethnicity-one-id",
                "ethnicity-two-id",
                "ethnicity-three-id",
                "ethnicity-four-id",
                "ethnicity-seven-id"
            );

    }
}
