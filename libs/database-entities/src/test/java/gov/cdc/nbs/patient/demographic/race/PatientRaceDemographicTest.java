package gov.cdc.nbs.patient.demographic.race;

import gov.cdc.nbs.audit.Added;
import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.PatientRaceDemographic;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.type;

class PatientRaceDemographicTest {

  @Test
  void should_add_race() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .describedAs("Expected Race Category")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-05-12"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );

  }

  @Test
  @SuppressWarnings("squid:S5961")
  void should_add_race_with_details() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of("race-one", "race-two"),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .describedAs("Expected Race Category")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-05-12"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-category-value", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            ),
        actual -> assertThat(actual)
            .describedAs("Expected Race Detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-05-12"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            ),
        actual -> assertThat(actual)
            .describedAs("Expected Race Detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-05-12"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-two", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );

  }

  @Test
  void should_add_already_present_race_category_when_existing_is_inactive() {
    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "another-race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.delete(
        new PatientCommand.DeleteRaceInfo(
            117L,
            "race-category-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2023-06-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2023-04-12T00:00:00")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd),
        actual -> assertThat(actual)
            .returns("race-category-value", PersonRace::getRaceCategoryCd)
    );

  }

  @Test
  void should_not_add_race_if_the_category_is_already_present() {
    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    PatientCommand.AddRace duplicate = new PatientCommand.AddRace(
        117L,
        LocalDate.parse("2022-05-13"),
        "race-category-value",
        List.of(),
        131L,
        LocalDateTime.parse("2020-03-03T10:15:30")
    );
    assertThatThrownBy(() -> raceDemographic.add(duplicate))
        .hasMessageContaining("race demographic for race-category-value already exists")
        .asInstanceOf(type(ExistingPatientRaceException.class))
        .returns("race-category-value", ExistingPatientRaceException::category)
    ;
  }

  @Test
  void should_update_race() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "another-race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            LocalDate.parse("2022-06-09"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactlyInAnyOrder(
        unchanged -> assertThat(unchanged)
            .describedAs("Other Race Category remains unchanged")
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
            .returns("another-race-category-value", PersonRace::getRaceCd),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-06-09"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );
  }

  @Test
  void should_update_race_with_details() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of("race-one", "race-two"),
            171L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "another-race-category-value",
            List.of(),
            191L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            LocalDate.parse("2022-06-09"),
            "race-category-value",
            List.of("race-one", "race-two"),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactlyInAnyOrder(
        unchanged -> assertThat(unchanged)
            .describedAs("Other Race Category remains unchanged")
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
            .returns("another-race-category-value", PersonRace::getRaceCd),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-06-09"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            ),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category detail changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-06-09"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            ),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category detail changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-06-09"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-two", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );
  }

  @Test
  void should_add_race_detail_when_updating_race_to_include_details() {
    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            171L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of("race-one"),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactlyInAnyOrder(
        updated -> assertThat(updated)
            .describedAs("Expected Race Category changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            ),
        added -> assertThat(added)
            .describedAs("Expected new race detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(LocalDate.parse("2022-05-12"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", r -> r.recordStatus().status())
                    .returns(LocalDateTime.parse("2020-03-03T10:15:30"), r -> r.recordStatus().appliedOn())
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );
  }

  @Test
  void should_remove_race_detail_when_updating_race_to_include_details() {
    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of("race-one"),
            171L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactlyInAnyOrder(
        updated -> assertThat(updated)
            .describedAs("Expected Race Category changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(LocalDateTime.parse("2020-03-03T10:15:30"), Changed::changedOn)
                    )
            )
    );
  }

  @Test
  void should_delete_race_category() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "another-race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.delete(
        new PatientCommand.DeleteRaceInfo(
            117L,
            "race-category-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
    );
  }

  @Test
  void should_delete_race_category_with_details() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "race-category-value",
            List.of("race-category-one", "race-category-two"),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            LocalDate.parse("2022-05-12"),
            "another-race-category-value",
            List.of(),
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    raceDemographic.delete(
        new PatientCommand.DeleteRaceInfo(
            117L,
            "race-category-value",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
    );
  }

}
