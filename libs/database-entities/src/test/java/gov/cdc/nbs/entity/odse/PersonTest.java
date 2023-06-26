package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Added;
import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Indicator;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.PatientAssociationCountFinder;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientHasAssociatedEventsException;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographic.PatientEthnicity;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonTest {

    @Test
    @SuppressWarnings("squid:S5961")
        // Allow more than 25 assertions
    void should_create_new_person_when_patient_added() {

        PatientCommand.AddPatient request = new PatientCommand.AddPatient(
            117L,
            "patient-local-id",
            LocalDate.parse("2000-09-03"),
            Gender.M,
            Gender.F,
            Deceased.N,
            null,
            "Marital Status",
            "EthCode",
            Instant.parse("2019-03-03T10:15:30.00Z"),
            "comments",
            "HIV-Case",
            131L,
            Instant.parse("2020-03-03T10:15:30.00Z")
        );

        Person actual = new Person(request);

        assertThat(actual.getId()).isEqualTo(117L);
        assertThat(actual.getLocalId()).isEqualTo("patient-local-id");

        assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 1);
        assertThat(actual.getCd()).isEqualTo("PAT");
        assertThat(actual.getElectronicInd()).isEqualTo('N');
        assertThat(actual.getEdxInd()).isEqualTo("Y");

        assertThat(actual.getAddUserId()).isEqualTo(131L);
        assertThat(actual.getAddTime()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getLastChgUserId()).isEqualTo(131L);
        assertThat(actual.getLastChgTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getStatusCd()).isEqualTo('A');
        assertThat(actual.getStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(actual.getRecordStatusCd()).isEqualTo(RecordStatus.ACTIVE);
        assertThat(actual.getRecordStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");

        assertThat(LocalDate.ofInstant(actual.getBirthTime(), ZoneOffset.UTC)).isEqualTo("2000-09-03");
        assertThat(actual.getBirthGenderCd()).isEqualTo(Gender.M);
        assertThat(actual.getCurrSexCd()).isEqualTo(Gender.F);
        assertThat(actual.getDeceasedIndCd()).isEqualTo(Deceased.N);
        assertThat(actual.getMaritalStatusCd()).isEqualTo("Marital Status");

        assertThat(actual.getAsOfDateGeneral()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateAdmin()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getAsOfDateSex()).isEqualTo("2019-03-03T10:15:30.00Z");
        assertThat(actual.getDescription()).isEqualTo("comments");

        assertThat(actual.getEharsId()).isEqualTo("HIV-Case");

        assertThat(actual)
            .extracting(Person::getEthnicity)
            .returns("EthCode", PatientEthnicity::ethnicGroup)
            .returns(Instant.parse("2019-03-03T10:15:30.00Z"), PatientEthnicity::asOf);

        assertThat(actual.getPersonParentUid())
            .as("Master Patient Record set itself as parent")
            .isSameAs(actual);

    }

    @Test
    void should_add_race() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactly(
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
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );

    }

    @Test
    @SuppressWarnings("squid:S5961")
    void should_add_race_with_details() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of("race-one", "race-two"),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactly(
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
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
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
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
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
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );

    }

    @Test
    void should_update_race() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "another-race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateRaceInfo(
                117L,
                Instant.parse("2022-06-09T13:00:03Z"),
                "race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactlyInAnyOrder(
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );
    }

    @Test
    void should_update_race_with_details() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of("race-one", "race-two"),
                171L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "another-race-category-value",
                List.of(),
                191L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateRaceInfo(
                117L,
                Instant.parse("2022-06-09T13:00:03Z"),
                "race-category-value",
                List.of("race-one", "race-two"),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactlyInAnyOrder(
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );
    }


    @Test
    void should_add_race_detail_when_updating_race_to_include_details() {
        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of(),
                171L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateRaceInfo(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of("race-one"),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactlyInAnyOrder(
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
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
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );
    }

    @Test
    void should_remove_race_detail_when_updating_race_to_include_details() {
        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of("race-one"),
                171L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateRaceInfo(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactlyInAnyOrder(
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
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
        );
    }

    @Test
    void should_delete_race_category() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "another-race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.delete(
            new PatientCommand.DeleteRaceInfo(
                117L,
                "race-category-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactly(
            actual -> assertThat(actual)
                .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
        );
    }

    @Test
    void should_delete_race_category_with_details() {

        Person patient = new Person(117L, "local-id-value");
        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "race-category-value",
                List.of("race-category-one", "race-category-two"),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddRace(
                117L,
                Instant.parse("2022-05-12T11:15:17Z"),
                "another-race-category-value",
                List.of(),
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.delete(
            new PatientCommand.DeleteRaceInfo(
                117L,
                "race-category-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getRaces()).satisfiesExactly(
            actual -> assertThat(actual)
                .returns("another-race-category-value", PersonRace::getRaceCategoryCd)
        );
    }

    @Test
    void should_add_name() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2023-05-15T10:00:00Z"),
                "prefix",
                "First",
                "Middle",
                "Second-Middle",
                "Last",
                "Second-Last",
                "JR",
                "Degree",
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30Z")
            )
        );

        assertThat(patient.getNames()).satisfiesExactly(
            actual -> assertThat(actual)
                .returns(Instant.parse("2023-05-15T10:00:00Z"), PersonName::getAsOfDate)
                .returns("prefix", PersonName::getNmPrefix)
                .returns("First", PersonName::getFirstNm)
                .returns("Second-Middle", PersonName::getMiddleNm2)
                .returns("Middle", PersonName::getMiddleNm)
                .returns("Last", PersonName::getLastNm)
                .returns("Second-Last", PersonName::getLastNm2)
                .returns(Suffix.JR, PersonName::getNmSuffix)
                .returns("Degree", PersonName::getNmDegree)
                .returns("L", PersonName::getNmUseCd)
                .satisfies(
                    name -> assertThat(name)
                        .describedAs("expected name identifier starts at sequence 1")
                        .extracting(PersonName::getId)
                        .returns(117L, PersonNameId::getPersonUid)
                        .returns((short) 1, PersonNameId::getPersonNameSeq)
                )
                .satisfies(
                    added -> assertThat(added.getAudit())
                        .describedAs("expected name audit state")
                        .satisfies(
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )


        );
    }

    @Test
    void should_add_another_name() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "First",
                "Middle",
                "Last",
                "JR",
                "L",
                171L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2023-05-15T10:00:00Z"),
                "Another-Prefix",
                "Another-First",
                "Another-Middle",
                "Another-Second-Middle",
                "Another-Last",
                "Another-Second-Last",
                "SR",
                "Another-Degree",
                "A",
                131L,
                Instant.parse("2021-02-03T04:05:06Z")
            )
        );

        assertThat(patient.getNames()).satisfiesExactly(
            actual -> assertThat(actual)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 1, PersonNameId::getPersonNameSeq),
            actual -> assertThat(actual)
                .returns(Instant.parse("2023-05-15T10:00:00Z"), PersonName::getAsOfDate)
                .returns("Another-Prefix", PersonName::getNmPrefix)
                .returns("Another-First", PersonName::getFirstNm)
                .returns("Another-Second-Middle", PersonName::getMiddleNm2)
                .returns("Another-Middle", PersonName::getMiddleNm)
                .returns("Another-Last", PersonName::getLastNm)
                .returns("Another-Second-Last", PersonName::getLastNm2)
                .returns(Suffix.SR, PersonName::getNmSuffix)
                .returns("Another-Degree", PersonName::getNmDegree)
                .returns("A", PersonName::getNmUseCd)
                .satisfies(
                    name -> assertThat(name)
                        .describedAs("expected name identifier starts at sequence 2")
                        .extracting(PersonName::getId)
                        .returns(117L, PersonNameId::getPersonUid)
                        .returns((short) 2, PersonNameId::getPersonNameSeq)
                )
                .satisfies(
                    added -> assertThat(added.getAudit())
                        .describedAs("expected name audit state")
                        .satisfies(
                            audit -> assertThat(audit.getAdded())
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2021-02-03T04:05:06Z"), Added::getAddTime)
                        )
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2021-02-03T04:05:06Z"), Changed::getLastChgTime)
                        )
                )


        );
    }

    @Test
    void should_update_existing_name() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "First",
                "Middle",
                "Last",
                "JR",
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateNameInfo(
                117L,
                (short) 1,
                Instant.parse("2023-05-15T10:00:00Z"),
                "prefix",
                "First",
                "Middle",
                "Second-Middle",
                "Last",
                "Second-Last",
                "JR",
                "Degree",
                "L",
                171L,
                Instant.parse("2021-04-05T06:07:08Z")
            )
        );

        assertThat(patient.getNames()).satisfiesExactly(
            actual -> assertThat(actual)
                .returns(Instant.parse("2023-05-15T10:00:00Z"), PersonName::getAsOfDate)
                .returns("prefix", PersonName::getNmPrefix)
                .returns("First", PersonName::getFirstNm)
                .returns("Second-Middle", PersonName::getMiddleNm2)
                .returns("Middle", PersonName::getMiddleNm)
                .returns("Last", PersonName::getLastNm)
                .returns("Second-Last", PersonName::getLastNm2)
                .returns(Suffix.JR, PersonName::getNmSuffix)
                .returns("Degree", PersonName::getNmDegree)
                .returns("L", PersonName::getNmUseCd)
                .satisfies(
                    name -> assertThat(name)
                        .describedAs("expected name identifier starts at sequence 1")
                        .extracting(PersonName::getId)
                        .returns(117L, PersonNameId::getPersonUid)
                        .returns((short) 1, PersonNameId::getPersonNameSeq)
                )
                .satisfies(
                    added -> assertThat(added.getAudit())
                        .describedAs("expected name audit state")
                        .satisfies(
                            audit -> assertThat(audit.getChanged())
                                .returns(171L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2021-04-05T06:07:08Z"), Changed::getLastChgTime)
                        )
                )
        );
    }

    @Test
    void should_remove_existing_name() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "First",
                "Middle",
                "Last",
                "JR",
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "Other-First",
                "Other-Middle",
                "Other-Last",
                null,
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.delete(
            new PatientCommand.DeleteNameInfo(
                117L,
                (short) 2,
                171L,
                Instant.parse("2021-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getNames()).satisfiesExactlyInAnyOrder(
            actual -> assertThat(actual)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 1, PersonNameId::getPersonNameSeq)

        );
    }

    @Test
    void should_add_minimal_name_at_sequence_one() {
        Person actual = new Person(117L, "local-id-value");

        actual.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "First",
                "Middle",
                "Last",
                "JR",
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(actual.getNames()).satisfiesExactly(
            actual_primary -> assertThat(actual_primary)
                .returns(Instant.parse("2021-05-15T10:00:00Z"), PersonName::getAsOfDate)
                .returns("First", PersonName::getFirstNm)
                .returns("Middle", PersonName::getMiddleNm)
                .returns("Last", PersonName::getLastNm)
                .returns(Suffix.JR, PersonName::getNmSuffix)
                .returns("L", PersonName::getNmUseCd)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 1, PersonNameId::getPersonNameSeq)
        );
    }

    @Test
    void should_add_secondary_name() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "First",
                "Middle",
                "Last",
                "JR",
                "L",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

        actual.add(
            new PatientCommand.AddName(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "Other",
                "OtherMiddle",
                "OtherLast",
                "SR",
                "AL",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")));

        assertThat(actual.getFirstNm()).isEqualTo("First");
        assertThat(actual.getMiddleNm()).isEqualTo("Middle");
        assertThat(actual.getLastNm()).isEqualTo("Last");
        assertThat(actual.getNmSuffix()).isEqualTo(Suffix.JR);

        assertThat(actual.getNames()).satisfiesExactly(
            actual_primary -> assertThat(actual_primary)
                .returns("First", PersonName::getFirstNm)
                .returns("Middle", PersonName::getMiddleNm)
                .returns("Last", PersonName::getLastNm)
                .returns(Suffix.JR, PersonName::getNmSuffix)
                .returns("L", PersonName::getNmUseCd)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 1, PersonNameId::getPersonNameSeq),
            actual_alias -> assertThat(actual_alias)
                .returns("Other", PersonName::getFirstNm)
                .returns("OtherMiddle", PersonName::getMiddleNm)
                .returns("OtherLast", PersonName::getLastNm)
                .returns(Suffix.SR, PersonName::getNmSuffix)
                .returns("AL", PersonName::getNmUseCd)
                .extracting(PersonName::getId)
                .returns(117L, PersonNameId::getPersonUid)
                .returns((short) 2, PersonNameId::getPersonNameSeq)
        );

    }

    @Test
    void should_add_minimal_postal_address() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
            new PatientCommand.AddAddress(
                117L,
                4861L,
                "SA1",
                "SA2",
                "city-description",
                "State",
                "Zip",
                "county-code",
                "country-code",
                "Census Tract",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_postal_locator -> assertThat(actual_postal_locator)
                    .isInstanceOf(PostalEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                    .returns(4861L, p -> p.getId().getLocatorUid())
                    .returns("H", EntityLocatorParticipation::getCd)
                    .extracting(PostalEntityLocatorParticipation::getLocator)
                    .returns(4861L, PostalLocator::getId)
                    .returns("SA1", PostalLocator::getStreetAddr1)
                    .returns("SA2", PostalLocator::getStreetAddr2)
                    .returns("city-description", PostalLocator::getCityDescTxt)
                    .returns("State", PostalLocator::getStateCd)
                    .returns("county-code", PostalLocator::getCntyCd)
                    .returns("Zip", PostalLocator::getZipCd)
                    .returns("country-code", PostalLocator::getCntryCd)

            );

    }

    @Test
    void should_add_postal_address() {

        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddAddress(
                117L,
                4861L,
                Instant.parse("2021-07-07T03:35:13Z"),
                "type-value",
                "use-value",
                "SA1",
                "SA2",
                "city-description",
                "State",
                "Zip",
                "county-code",
                "country-code",
                "Census Tract",
                "Comments",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .isInstanceOf(PostalEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                    .returns(4861L, p -> p.getId().getLocatorUid())
                    .returns("type-value", EntityLocatorParticipation::getCd)
                    .returns("use-value", EntityLocatorParticipation::getUseCd)
                    .returns(Instant.parse("2021-07-07T03:35:13Z"), EntityLocatorParticipation::getAsOfDate)
                    .returns(131L, EntityLocatorParticipation::getAddUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getAddTime)
                    .returns(131L, EntityLocatorParticipation::getLastChgUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getLastChgTime)
                    .returns("Comments", EntityLocatorParticipation::getLocatorDescTxt)
                    .extracting(PostalEntityLocatorParticipation::getLocator)
                    .returns(4861L, PostalLocator::getId)
                    .returns("SA1", PostalLocator::getStreetAddr1)
                    .returns("SA2", PostalLocator::getStreetAddr2)
                    .returns("city-description", PostalLocator::getCityDescTxt)
                    .returns("State", PostalLocator::getStateCd)
                    .returns("county-code", PostalLocator::getCntyCd)
                    .returns("Zip", PostalLocator::getZipCd)
                    .returns("country-code", PostalLocator::getCntryCd)
                    .returns("Census Tract", PostalLocator::getCensusTract)
            );

    }

    @Test
    void should_update_existing_postal_address() {

        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddAddress(
                117L,
                4861L,
                "SA1",
                "SA2",
                "city-description",
                "State",
                "Zip",
                "county-code",
                "country-code",
                "Census Tract",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateAddress(
                117L,
                4861L,
                Instant.parse("2021-07-07T03:35:13Z"),
                "type-value",
                "use-value",
                "SA1",
                "SA2",
                "city-description",
                "State",
                "Zip",
                "county-code",
                "country-code",
                "Census Tract",
                "Comments",
                171L,
                Instant.parse("2020-03-04T00:00:00Z")
            )
        );

        assertThat(patient.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .isInstanceOf(PostalEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
                    .returns(4861L, p -> p.getId().getLocatorUid())
                    .returns("type-value", EntityLocatorParticipation::getCd)
                    .returns("use-value", EntityLocatorParticipation::getUseCd)
                    .returns(Instant.parse("2021-07-07T03:35:13Z"), EntityLocatorParticipation::getAsOfDate)
                    .returns(131L, EntityLocatorParticipation::getAddUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getAddTime)
                    .returns(171L, EntityLocatorParticipation::getLastChgUserId)
                    .returns(Instant.parse("2020-03-04T00:00:00Z"), EntityLocatorParticipation::getLastChgTime)
                    .returns("Comments", EntityLocatorParticipation::getLocatorDescTxt)
                    .extracting(PostalEntityLocatorParticipation::getLocator)
                    .returns(4861L, PostalLocator::getId)
                    .returns("SA1", PostalLocator::getStreetAddr1)
                    .returns("SA2", PostalLocator::getStreetAddr2)
                    .returns("city-description", PostalLocator::getCityDescTxt)
                    .returns("State", PostalLocator::getStateCd)
                    .returns("county-code", PostalLocator::getCntyCd)
                    .returns("Zip", PostalLocator::getZipCd)
                    .returns("country-code", PostalLocator::getCntryCd)
                    .returns("Census Tract", PostalLocator::getCensusTract)
            );

    }

    @Test
    void should_delete_existing_postal_address() {

        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddAddress(
                117L,
                4861L,
                "SA1",
                "SA2",
                "city-description",
                "State",
                "Zip",
                "county-code",
                "country-code",
                "Census Tract",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddAddress(
                117L,
                5331L,
                "Other-SA1",
                "Other-SA2",
                "Other-city",
                "Other-State",
                "Other-Zip",
                "Other-county-code",
                "Other-country-code",
                null,
                171L,
                Instant.parse("2020-03-04T08:45:23Z")
            )
        );

        patient.delete(
            new PatientCommand.DeleteAddress(
                117L,
                5331L,
                191L,
                Instant.parse("2021-05-24T11:01:17Z")
            )
        );

        assertThat(patient.addresses())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .returns(4861L, p -> p.getId().getLocatorUid())
            );

    }


    @Test
    void should_add_minimal_email_address() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
            new PatientCommand.AddEmailAddress(
                117L,
                5333L,
                "AnEmail@email.com",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_email_locator -> assertThat(actual_email_locator)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5333L, p -> p.getId().getLocatorUid())
                    .returns("NET", EntityLocatorParticipation::getCd)
                    .extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(5333L, TeleLocator::getId)
                    .returns("AnEmail@email.com", TeleLocator::getEmailAddress)

            );

    }

    @Test
    void should_add_minimal_phone_number() {

        Person actual = new Person(117L, "local-id-value");

        actual.add(
            new PatientCommand.AddPhoneNumber(
                117L,
                5347L,
                "CP",
                "MC",
                "Phone Number",
                "Extension",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(actual.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual_phone_locator -> assertThat(actual_phone_locator)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5347L, p -> p.getId().getLocatorUid())
                    .returns("CP", EntityLocatorParticipation::getCd)
                    .extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(5347L, TeleLocator::getId)
                    .returns("Phone Number", TeleLocator::getPhoneNbrTxt)
                    .returns("Extension", TeleLocator::getExtensionTxt));

    }

    @Test
    void should_add_phone() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddPhone(
                117L,
                5347L,
                "type-value",
                "use-value",
                Instant.parse("2023-11-27T22:53:07Z"),
                "country-code",
                "number",
                "extension",
                "email",
                "url",
                "comment",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5347L, p -> p.getId().getLocatorUid())
                    .returns("type-value", EntityLocatorParticipation::getCd)
                    .returns("use-value", EntityLocatorParticipation::getUseCd)
                    .returns(Instant.parse("2023-11-27T22:53:07Z"), EntityLocatorParticipation::getAsOfDate)
                    .returns(131L, EntityLocatorParticipation::getAddUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getAddTime)
                    .returns(131L, EntityLocatorParticipation::getLastChgUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getLastChgTime)
                    .returns("comment", EntityLocatorParticipation::getLocatorDescTxt)
                    .extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(5347L, TeleLocator::getId)
                    .returns("country-code", TeleLocator::getCntryCd)
                    .returns("number", TeleLocator::getPhoneNbrTxt)
                    .returns("extension", TeleLocator::getExtensionTxt)
                    .returns("email", TeleLocator::getEmailAddress)
                    .returns("url", TeleLocator::getUrlAddress)
            );
    }

    @Test
    void should_update_existing_phone() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddPhone(
                117L,
                5347L,
                "type-value",
                "use-value",
                Instant.parse("2023-11-27T22:53:07Z"),
                "country-code",
                "number",
                "extension",
                "email",
                "url",
                "comment",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdatePhone(
                117L,
                5347L,
                "updated-type-value",
                "updated-use-value",
                Instant.parse("2023-11-27T22:53:07Z"),
                "updated-country-code",
                "updated-number",
                "updated-extension",
                "updated-email",
                "updated-url",
                "updated-comment",
                171L,
                Instant.parse("2023-07-01T13:17:00Z")
            )
        );

        assertThat(patient.getNbsEntity().getEntityLocatorParticipations())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5347L, p -> p.getId().getLocatorUid())
                    .returns("updated-type-value", EntityLocatorParticipation::getCd)
                    .returns("updated-use-value", EntityLocatorParticipation::getUseCd)
                    .returns(Instant.parse("2023-11-27T22:53:07Z"), EntityLocatorParticipation::getAsOfDate)
                    .returns(131L, EntityLocatorParticipation::getAddUserId)
                    .returns(Instant.parse("2020-03-03T10:15:30.00Z"), EntityLocatorParticipation::getAddTime)
                    .returns(171L, EntityLocatorParticipation::getLastChgUserId)
                    .returns(Instant.parse("2023-07-01T13:17:00Z"), EntityLocatorParticipation::getLastChgTime)
                    .returns("updated-comment", EntityLocatorParticipation::getLocatorDescTxt)
                    .extracting(TeleEntityLocatorParticipation::getLocator)
                    .returns(5347L, TeleLocator::getId)
                    .returns("updated-country-code", TeleLocator::getCntryCd)
                    .returns("updated-number", TeleLocator::getPhoneNbrTxt)
                    .returns("updated-extension", TeleLocator::getExtensionTxt)
                    .returns("updated-email", TeleLocator::getEmailAddress)
                    .returns("updated-url", TeleLocator::getUrlAddress)
            );
    }

    @Test
    void should_delete_existing_phone() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddPhone(
                117L,
                5347L,
                "type-value",
                "use-value",
                Instant.parse("2023-11-27T22:53:07Z"),
                "country-code",
                "number",
                "extension",
                "email",
                "url",
                "comment",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddPhone(
                117L,
                1567L,
                "type-value",
                "use-value",
                Instant.parse("2023-11-27T22:53:07Z"),
                "country-code",
                "number",
                "extension",
                "email",
                "url",
                "comment",
                171L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.delete(
            new PatientCommand.DeletePhone(
                117L,
                1567L,
                293L,
                Instant.parse("2023-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.phones())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .isInstanceOf(TeleEntityLocatorParticipation.class)
                    .asInstanceOf(InstanceOfAssertFactories.type(TeleEntityLocatorParticipation.class))
                    .returns(5347L, p -> p.getId().getLocatorUid())
            );
    }


    @Test
    void should_delete_patient_without_associations() {

        PatientAssociationCountFinder finder = mock(PatientAssociationCountFinder.class);

        when(finder.count(anyLong())).thenReturn(0L);

        Person actual = new Person(117L, "local-id-value");

        actual.delete(
            new PatientCommand.Delete(
                117L,
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            ),
            finder
        );

        assertThat(actual.getRecordStatusCd()).isEqualTo(RecordStatus.LOG_DEL);
        assertThat(actual.getRecordStatusTime()).isEqualTo("2020-03-03T10:15:30.00Z");
        assertThat(actual.getVersionCtrlNbr()).isEqualTo((short) 2);
        assertThat(actual.getLastChgUserId()).isEqualTo((short) 131L);
        assertThat(actual.getLastChgTime()).isEqualTo("2020-03-03T10:15:30.00Z");
    }

    @Test
    void should_not_allow_deletion_of_patient_with_associations() {
        PatientAssociationCountFinder finder = mock(PatientAssociationCountFinder.class);

        when(finder.count(anyLong())).thenReturn(1L);

        Person actual = new Person(117L, "local-id-value");

        Instant deletedOn = Instant.parse("2020-03-03T10:15:30.00Z");
        var deleteCommand = new PatientCommand.Delete(
            117L,
            131L,
            deletedOn
        );

        assertThatThrownBy(() ->
            actual.delete(
                deleteCommand,
                finder
            )
        ).isInstanceOf(PatientHasAssociatedEventsException.class)
            .asInstanceOf(InstanceOfAssertFactories.type(PatientHasAssociatedEventsException.class))
            .returns(117L, PatientHasAssociatedEventsException::patient);
    }

    @Test
    void should_set_general_info_fields() {
        Person actual = new Person(121L, "local-id-value");
        var command = new PatientCommand.UpdateGeneralInfo(
            121L,
            Instant.parse("2010-03-03T10:15:30.00Z"),
            "marital status",
            "mothers maiden name",
            1,
            2,
            "occupation code",
            "education level",
            "prim language",
            "speaks english",
            "eharsId",
            12L,
            Instant.parse("2019-03-03T10:15:30.00Z"));

        actual.update(command);

        assertThat(actual)
            .returns(Instant.parse("2010-03-03T10:15:30.00Z"), Person::getAsOfDateGeneral)
            .returns("marital status", Person::getMaritalStatusCd)
            .returns("mothers maiden name", Person::getMothersMaidenNm)
            .returns((short) 1, Person::getAdultsInHouseNbr)
            .returns((short) 2, Person::getChildrenInHouseNbr)
            .returns("occupation code", Person::getOccupationCd)
            .returns("education level", Person::getEducationLevelCd)
            .returns("prim language", Person::getPrimLangCd)
            .returns("speaks english", Person::getSpeaksEnglishCd)
            .returns("eharsId", Person::getEharsId)
            .returns(12L, Person::getLastChgUserId)
            .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
        ;
    }

    @Test
    void should_change_ethnicity() {

        Person patient = new Person(121L, "local-id-value");

        patient.update(
            new PatientCommand.UpdateEthnicityInfo(
                121L,
                Instant.parse("2012-03-03T10:15:30.00Z"),
                "ethnic-group-value",
                "unknown-reason-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient)
            .returns(131L, Person::getLastChgUserId)
            .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Person::getLastChgTime)
            .extracting(Person::getEthnicity)
            .returns(Instant.parse("2012-03-03T10:15:30.00Z"), PatientEthnicity::asOf)
            .returns("ethnic-group-value", PatientEthnicity::ethnicGroup)
            .returns("unknown-reason-value", PatientEthnicity::unknownReason);
    }

    @Test
    void should_add_detailed_ethnicity() {

        Person patient = new Person(121L, "local-id-value");

        PersonEthnicGroup actual = patient.add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(actual.getPersonUid()).isEqualTo(patient);
        assertThat(actual.getId())
            .returns(121L, PersonEthnicGroupId::getPersonUid)
            .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
        ;

        assertThat(patient.getEthnicity().ethnicities())
            .satisfiesExactlyInAnyOrder(
                actual_detail -> assertThat(actual_detail)
                    .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
            );

    }

    @Test
    void should_add_another_detailed_ethnicity() {

        Person patient = new Person(121L, "local-id-value");

        patient.add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getEthnicity().ethnicities())
            .satisfiesExactlyInAnyOrder(
                actual -> assertThat(actual)
                    .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd),
                actual -> assertThat(actual)
                    .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("next-ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
            );

    }

    @Test
    void should_remove_detailed_ethnicity() {

        Person patient = new Person(121L, "local-id-value");

        patient.add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.remove(
            new PatientCommand.RemoveDetailedEthnicity(
                121L,
                "next-ethnicity-value",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.getEthnicity().ethnicities())
            .satisfiesExactly(
                actual -> assertThat(actual)
                    .returns("ACTIVE", PersonEthnicGroup::getRecordStatusCd)
                    .extracting(PersonEthnicGroup::getId)
                    .returns(121L, PersonEthnicGroupId::getPersonUid)
                    .returns("ethnicity-value", PersonEthnicGroupId::getEthnicGroupCd)
            );

    }

    @Test
    void should_add_minimal_identity_with_sequence_one() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddIdentification(
                117L,
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.identifications()).satisfiesExactly(
            actual -> assertThat(actual)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getAudit)
                        .satisfies(
                            added -> assertThat(added)
                                .extracting(Audit::getAdded)
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            changed -> assertThat(changed)
                                .extracting(Audit::getChanged)
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
                .returns("identification-type", EntityId::getTypeCd)
                .returns("authority-value", EntityId::getAssigningAuthorityCd)
                .returns("identification-value", EntityId::getRootExtensionTxt)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getId)
                        .returns((short) 1, EntityIdId::getEntityIdSeq)
                )

        );
    }

    @Test
    void should_add_identity_with_sequence_one() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddIdentification(
                117L,
                Instant.parse("1999-09-09T11:59:13Z"),
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient.identifications()).satisfiesExactly(
            actual -> assertThat(actual)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getAudit)
                        .satisfies(
                            added -> assertThat(added)
                                .extracting(Audit::getAdded)
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            changed -> assertThat(changed)
                                .extracting(Audit::getChanged)
                                .returns(131L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Changed::getLastChgTime)
                        )
                )
                .returns("identification-type", EntityId::getTypeCd)
                .returns(Instant.parse("1999-09-09T11:59:13Z"), EntityId::getAsOfDate)
                .returns("authority-value", EntityId::getAssigningAuthorityCd)
                .returns("identification-value", EntityId::getRootExtensionTxt)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getId)
                        .returns((short) 1, EntityIdId::getEntityIdSeq)
                )

        );
    }

    @Test
    void should_update_existing_identity() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddIdentification(
                117L,
                Instant.parse("1999-09-09T11:59:13Z"),
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.update(
            new PatientCommand.UpdateIdentification(
                117L,
                1,
                Instant.parse("2001-05-19T11:59:00Z"),
                "updated-identification-value",
                "updated-authority-value",
                "updated-identification-type",
                171L,
                Instant.parse("2020-03-13T13:15:30Z")
            )
        );

        assertThat(patient.identifications()).satisfiesExactly(
            actual -> assertThat(actual)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getAudit)
                        .satisfies(
                            added -> assertThat(added)
                                .extracting(Audit::getAdded)
                                .returns(131L, Added::getAddUserId)
                                .returns(Instant.parse("2020-03-03T10:15:30.00Z"), Added::getAddTime)
                        )
                        .satisfies(
                            changed -> assertThat(changed)
                                .extracting(Audit::getChanged)
                                .returns(171L, Changed::getLastChgUserId)
                                .returns(Instant.parse("2020-03-13T13:15:30Z"), Changed::getLastChgTime)
                        )
                )
                .returns("updated-identification-type", EntityId::getTypeCd)
                .returns(Instant.parse("2001-05-19T11:59:00Z"), EntityId::getAsOfDate)
                .returns("updated-authority-value", EntityId::getAssigningAuthorityCd)
                .returns("updated-identification-value", EntityId::getRootExtensionTxt)

        );
    }

    @Test
    void should_delete_existing_identity() {
        Person patient = new Person(117L, "local-id-value");

        patient.add(
            new PatientCommand.AddIdentification(
                117L,
                Instant.parse("1999-09-09T11:59:13Z"),
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.add(
            new PatientCommand.AddIdentification(
                117L,
                Instant.parse("2001-05-19T11:59:00Z"),
                "other-identification-value",
                "other-authority-value",
                "other-identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        patient.delete(
            new PatientCommand.DeleteIdentification(
                117,
                1,
                171L,
                Instant.parse("2020-03-13T13:15:30Z")
            )
        );

        assertThat(patient.identifications()).satisfiesExactly(
            actual -> assertThat(actual)
                .satisfies(
                    identification -> assertThat(identification)
                        .extracting(EntityId::getId)
                        .returns((short) 2, EntityIdId::getEntityIdSeq)
                )

        );
    }

    @Test
    void should_update_patient_mortality_when_patient_is_deceased() {

        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                null,
                null,
                null,
                null,
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
            .returns(Deceased.Y, Person::getDeceasedIndCd)
            .returns(Instant.parse("1987-11-17T00:00:00Z"), Person::getDeceasedTime)
            .returns(131L, Person::getLastChgUserId)
            .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime);
    }

    @Test
    void should_update_patient_mortality_with_new_mortality_location_when_patient_is_deceased() {

        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
            .satisfies(
                actual -> assertThat(actual.addresses())
                    .satisfiesExactly(
                        address -> assertThat(address)
                            .returns("U", PostalEntityLocatorParticipation::getCd)
                            .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                            .extracting(PostalEntityLocatorParticipation::getLocator)
                            .returns("city", PostalLocator::getCityDescTxt)
                            .returns("state", PostalLocator::getStateCd)
                            .returns("county", PostalLocator::getCntyCd)
                            .returns("country", PostalLocator::getCntryCd)
                    )
            )
        ;
    }

    @Test
    void should_clear_patient_mortality_when_patient_is_not_deceased() {

        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "N",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
            .returns(Deceased.N, Person::getDeceasedIndCd)
            .returns(null, Person::getDeceasedTime)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
            .satisfies(
                actual -> assertThat(actual.addresses())
                    .satisfiesExactly(
                        address -> assertThat(address)
                            .returns("U", PostalEntityLocatorParticipation::getCd)
                            .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                            .extracting(PostalEntityLocatorParticipation::getLocator)
                            .returns(null, PostalLocator::getCityDescTxt)
                            .returns(null, PostalLocator::getStateCd)
                            .returns(null, PostalLocator::getCntyCd)
                            .returns(null, PostalLocator::getCntryCd)
                    )
            )
        ;
    }

    @Test
    void should_clear_patient_mortality_when_patient_is_not_known_to_be_deceased() {

        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "UNK",
                LocalDate.of(1987, Month.NOVEMBER, 17),
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateMorbidity)
            .returns(Deceased.UNK, Person::getDeceasedIndCd)
            .returns(null, Person::getDeceasedTime)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
            .satisfies(
                actual -> assertThat(actual.addresses())
                    .satisfiesExactly(
                        address -> assertThat(address)
                            .returns("U", PostalEntityLocatorParticipation::getCd)
                            .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                            .extracting(PostalEntityLocatorParticipation::getLocator)
                            .returns(null, PostalLocator::getCityDescTxt)
                            .returns(null, PostalLocator::getStateCd)
                            .returns(null, PostalLocator::getCntyCd)
                            .returns(null, PostalLocator::getCntryCd)
                    )
            )
        ;
    }

    @Test
    void should_update_patient_mortality_with_changed_mortality_location_when_patient_is_deceased() {

        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                "Y",
                null,
                "city",
                null,
                null,
                null,
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        patient.update(
            new PatientCommand.UpdateMortality(
                121L,
                Instant.parse("2023-06-21T03:21:00Z"),
                "Y",
                LocalDate.of(1986, Month.NOVEMBER, 16),
                "changed",
                null,
                null,
                null,
                171L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-21T03:21:00Z"), Person::getAsOfDateMorbidity)
            .returns(Instant.parse("1986-11-16T00:00:00Z"), Person::getDeceasedTime)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(171L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
            .satisfies(
                actual -> assertThat(actual.addresses())
                    .satisfiesExactly(
                        address -> assertThat(address)
                            .returns("U", PostalEntityLocatorParticipation::getCd)
                            .returns("DTH", PostalEntityLocatorParticipation::getUseCd)
                            .extracting(PostalEntityLocatorParticipation::getLocator)
                            .returns("changed", PostalLocator::getCityDescTxt)
                    )
            )
        ;
    }

    @Test
    void should_update_patient_gender() {

        Person patient = new Person(121L, "local-id-value");

        patient.update(
            new PatientCommand.UpdateGender(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                Gender.U.value(),
                "gender-unknown-reason",
                "gender-preferred",
                "gender-additional",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            )
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateSex)
            .returns(Gender.U, Person::getCurrSexCd)
            .returns("gender-unknown-reason", Person::getSexUnkReasonCd)
            .returns("gender-preferred", Person::getPreferredGenderCd)
            .returns("gender-additional", Person::getAdditionalGenderCd)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
        ;

    }

    @Test
    void should_update_patient_birth() {
        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateBirth(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                LocalDate.of(1949, 10, 15),
                Gender.U.value(),
                Indicator.NO.getId(),
                17,
                null,
                null,
                null,
                null,
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .returns(Instant.parse("2023-06-01T03:21:00Z"), Person::getAsOfDateSex)
            .returns(Instant.parse("1949-10-15T00:00:00Z"), Person::getBirthTime)
            .returns(Gender.U, Person::getBirthGenderCd)
            .returns((short) 17, Person::getBirthOrderNbr)
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
        ;
    }

    @Test
    void should_update_patient_birth_with_location() {
        Person patient = new Person(121L, "local-id-value");

        AddressIdentifierGenerator generator = () -> 1157L;

        patient.update(
            new PatientCommand.UpdateBirth(
                121L,
                Instant.parse("2023-06-01T03:21:00Z"),
                null,
                null,
                null,
                null,
                "city",
                "state",
                "county",
                "country",
                131L,
                Instant.parse("2019-03-03T10:15:30.00Z")
            ),
            generator
        );

        assertThat(patient)
            .satisfies(
                actual -> assertThat(actual.addresses())
                    .satisfiesExactly(
                        address -> assertThat(address)
                            .returns("F", PostalEntityLocatorParticipation::getCd)
                            .returns("BIR", PostalEntityLocatorParticipation::getUseCd)
                            .extracting(PostalEntityLocatorParticipation::getLocator)
                            .returns("city", PostalLocator::getCityDescTxt)
                            .returns("state", PostalLocator::getStateCd)
                            .returns("county", PostalLocator::getCntyCd)
                            .returns("country", PostalLocator::getCntryCd)
                    )
            )
            .satisfies(
                changed -> assertThat(changed)
                    .returns(131L, Person::getLastChgUserId)
                    .returns(Instant.parse("2019-03-03T10:15:30.00Z"), Person::getLastChgTime)
            )
        ;
    }
}
