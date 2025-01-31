package gov.cdc.nbs.patient.profile.race;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientRaceMergerTest {

    @Test
    void should_merge_with_values_of_left_only() {

        PatientRaceMerger merger = new PatientRaceMerger();

        PatientRace actual = merger.merge(
            new PatientRace(
                2L,
                73L,
                (short) 283,
                LocalDate.parse("1970-04-09"),
                new PatientRace.Race("category-id", "category-description"),
                List.of()
            ),
            new PatientRace(
                3L,
                79L,
                (short) 293,
                LocalDate.parse("2003-09-05"),
                new PatientRace.Race("category-other-id", "category-other-description"),
                List.of()

            )
        );

        assertThat(actual.patient()).isEqualTo(2L);
        assertThat(actual.id()).isEqualTo(73L);
        assertThat(actual.version()).isEqualTo((short) 283);
        assertThat(actual.asOf()).isEqualTo("1970-04-09");

        assertThat(actual.category().id()).isEqualTo("category-id");
        assertThat(actual.category().description()).isEqualTo("category-description");

    }


    @Test
    void should_merge_with_merged_detailed_values_of_left_and_right() {

        PatientRaceMerger merger = new PatientRaceMerger();

        PatientRace merged = merger.merge(
            new PatientRace(
                2L,
                73L,
                (short) 283,
                LocalDate.parse("1970-04-09"),
                new PatientRace.Race("category-id", "category-description"),
                List.of(
                    new PatientRace.Race("race-one-id", "race-one-description"),
                    new PatientRace.Race("race-three-id", "race-three-description")
                )
            ),
            new PatientRace(
                3L,
                79L,
                (short) 293,
                LocalDate.parse("2003-09-05"),
                new PatientRace.Race("category-other-id", "category-other-description"),
                List.of(
                    new PatientRace.Race("race-five-id", "race-five-description"),
                    new PatientRace.Race("race-seven-id", "race-seven-description"),
                    new PatientRace.Race("race-two-id", "race-two-description")
                )

            )
        );

        assertThat(merged.detailed())
            .extracting(PatientRace.Race::id)
            .contains(
                "race-one-id",
                "race-two-id",
                "race-three-id",
                "race-five-id",
                "race-seven-id"
            );
    }
}
