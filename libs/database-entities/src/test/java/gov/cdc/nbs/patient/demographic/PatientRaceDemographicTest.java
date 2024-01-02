package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.audit.Added;
import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonRace;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PatientRaceDemographicTest {

  @Test
  void should_add_race() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .describedAs("Expected Race Category")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-05-12T11:15:17Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of("race-one", "race-two"),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .describedAs("Expected Race Category")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-05-12T11:15:17Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-category-value", PersonRace::getRaceCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            ),
        actual -> assertThat(actual)
            .describedAs("Expected Race Detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-05-12T11:15:17Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            ),
        actual -> assertThat(actual)
            .describedAs("Expected Race Detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-05-12T11:15:17Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-two", PersonRace::getRaceCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            )
    );

  }

  @Test
  void should_update_race() {

    Person patient = new Person(117L, "local-id-value");

    PatientRaceDemographic raceDemographic = new PatientRaceDemographic(patient);

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "another-race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            Instant.parse("2022-06-09T13:00:03Z"),
            "race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
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
                    .returns(Instant.parse("2022-06-09T13:00:03Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of("race-one", "race-two"),
            171L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "another-race-category-value",
            List.of(),
            191L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            Instant.parse("2022-06-09T13:00:03Z"),
            "race-category-value",
            List.of("race-one", "race-two"),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
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
                    .returns(Instant.parse("2022-06-09T13:00:03Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCd)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            ),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category detail changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-06-09T13:00:03Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            ),
        updated -> assertThat(updated)
            .describedAs("Expected Race Category detail changed")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-06-09T13:00:03Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-two", PersonRace::getRaceCd)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of(),
            171L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of("race-one"),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
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
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            ),
        added -> assertThat(added)
            .describedAs("Expected new race detail")
            .satisfies(
                race -> assertThat(race)
                    .describedAs("expected race data")
                    .returns(Instant.parse("2022-05-12T11:15:17Z"), PersonRace::getAsOfDate)
                    .returns("race-category-value", PersonRace::getRaceCategoryCd)
                    .returns("race-one", PersonRace::getRaceCd)
            )
            .satisfies(
                race_is_active -> assertThat(race_is_active)
                    .describedAs("expected race record status state")
                    .returns("ACTIVE", PersonRace::getRecordStatusCd)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), PersonRace::getRecordStatusTime)
            )
            .satisfies(
                race -> assertThat(race.getAudit())
                    .describedAs("expected race audit state")
                    .satisfies(
                        audit -> assertThat(audit.added())
                            .returns(131L, Added::addedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::addedOn)
                    )
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(131L, Changed::changedBy)
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of("race-one"),
            171L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.update(
        patient,
        new PatientCommand.UpdateRaceInfo(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
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
                            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::changedOn)
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "another-race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.delete(
        new PatientCommand.DeleteRaceInfo(
            117L,
            "race-category-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
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
            Instant.parse("2022-05-12T11:15:17Z"),
            "race-category-value",
            List.of("race-category-one", "race-category-two"),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.add(
        new PatientCommand.AddRace(
            117L,
            Instant.parse("2022-05-12T11:15:17Z"),
            "another-race-category-value",
            List.of(),
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    raceDemographic.delete(
        new PatientCommand.DeleteRaceInfo(
            117L,
            "race-category-value",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        )
    );

    assertThat(raceDemographic.races()).satisfiesExactly(
        actual -> assertThat(actual)
            .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
    );
  }

}
