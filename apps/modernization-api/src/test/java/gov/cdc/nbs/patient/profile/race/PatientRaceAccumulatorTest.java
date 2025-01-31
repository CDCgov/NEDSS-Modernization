package gov.cdc.nbs.patient.profile.race;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PatientRaceAccumulatorTest {

    @Test
    void should_accumulate_patient_race_into_one_instance_per_category() {

        List<PatientRace> actual = Stream.of(
                new PatientRace(
                    1087L,
                    1699L,
                    (short) 3,
                    LocalDate.parse("2010-08-07"),
                    new PatientRace.Race(
                        "category-id",
                        "category-description"
                    ),
                    List.of(
                        new PatientRace.Race(
                            "a", "a"
                        ),
                        new PatientRace.Race(
                            "c", "c"
                        )
                    )
                ),
                new PatientRace(
                    1087L,
                    1699L,
                    (short) 4,
                    LocalDate.parse("2008-07-10"),
                    new PatientRace.Race(
                        "category-id",
                        "category-description"
                    ),
                    List.of(
                        new PatientRace.Race(
                            "b", "b"
                        )
                    )
                ),
                new PatientRace(
                    1087L,
                    1699L,
                    (short) 4,
                    LocalDate.parse("2010-07-08"),
                    new PatientRace.Race(
                        "category-id-other",
                        "category-description-other"
                    ),
                    List.of(
                        new PatientRace.Race(
                            "d", "d"
                        )
                    )
                )
            )
            .collect(PatientRaceAccumulator.accumulate(new PatientRaceMerger()));


        assertThat(actual).satisfiesExactlyInAnyOrder(
            actualRace -> assertAll(
                () -> assertThat(actualRace.category()).extracting(PatientRace.Race::id).isEqualTo("category-id"),
                () -> assertThat(actualRace.detailed()).extracting(PatientRace.Race::id)
                    .contains(
                        "a",
                        "b",
                        "c"
                    )
            ),
            actualRace -> assertAll(
                () -> assertThat(actualRace.category()).extracting(PatientRace.Race::id)
                    .isEqualTo("category-id-other"),
                () -> assertThat(actualRace.detailed()).extracting(PatientRace.Race::id)
                    .contains(
                        "d"
                    )
            )
        );
    }
}
