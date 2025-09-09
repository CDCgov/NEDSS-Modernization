package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NBSEntityTest {

  @Test
  void should_update_patient_birth_location() {

    NBSEntity entity = new NBSEntity(121L, "PSN");

    AddressIdentifierGenerator generator = () -> 1157L;

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .returns("city", PostalLocator::city)
                        .returns("state", PostalLocator::state)
                        .returns("county", PostalLocator::county)
                        .returns("country", PostalLocator::country)
                )
        );
  }

  @Test
  void should_update_patient_birth_location_when_only_city_present() {

    NBSEntity entity = new NBSEntity(121L, "PSN");

    AddressIdentifierGenerator generator = () -> 1157L;

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            "city-value",
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .returns("city-value", PostalLocator::city)
                        .returns(null, PostalLocator::state)
                        .returns(null, PostalLocator::county)
                        .returns(null, PostalLocator::country)
                )
        );
  }

  @Test
  void should_update_patient_birth_location_when_only_county_present() {

    NBSEntity entity = new NBSEntity(121L, "PSN");

    AddressIdentifierGenerator generator = () -> 1157L;

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            null,
            null,
            "county-value",
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .satisfies(
                            updated -> assertThat(updated)
                                .extracting(PostalEntityLocatorParticipation::status)
                                .satisfies(StatusAssertions.active("2019-03-03T10:15:30"))
                        )
                        .satisfies(
                            updated -> assertThat(updated)
                                .extracting(PostalEntityLocatorParticipation::recordStatus)
                                .satisfies(RecordStatusAssertions.active("2019-03-03T10:15:30"))
                        )
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .satisfies(
                            updated -> assertThat(updated)
                                .extracting(PostalLocator::recordStatus)
                                .satisfies(RecordStatusAssertions.active("2019-03-03T10:15:30"))
                        )
                        .returns(null, PostalLocator::city)
                        .returns(null, PostalLocator::state)
                        .returns("county-value", PostalLocator::county)
                        .returns(null, PostalLocator::country)
                )
        );
  }

  @Test
  void should_update_patient_birth_location_when_only_state_present() {

    NBSEntity entity = new NBSEntity(121L, "PSN");

    AddressIdentifierGenerator generator = () -> 1157L;

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            null,
            "state-value",
            null,
            null,
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .returns(null, PostalLocator::city)
                        .returns("state-value", PostalLocator::state)
                        .returns(null, PostalLocator::county)
                        .returns(null, PostalLocator::country)
                )
        );
  }

  @Test
  void should_update_patient_birth_location_when_only_country_present() {

    NBSEntity entity = new NBSEntity(121L, "PSN");

    AddressIdentifierGenerator generator = () -> 1157L;

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            "country-value",
            131L,
            LocalDateTime.parse("2019-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .returns(null, PostalLocator::city)
                        .returns(null, PostalLocator::state)
                        .returns(null, PostalLocator::county)
                        .returns("country-value", PostalLocator::country)
                )
        );
  }

  @Test
  void should_inactive_patient_birth_when_location_not_present() {

    AddressIdentifierGenerator generator = () -> 1157L;

    NBSEntity entity = new NBSEntity(121L, "PSN");

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-01"),
            null,
            null,
            null,
            null,
            "city",
            "state",
            "county",
            "country",
            131L,
            LocalDateTime.parse("2019-02-05T10:15:30")
        ),
        generator
    );

    entity.update(
        new PatientCommand.UpdateBirth(
            121L,
            LocalDate.parse("2023-06-17"),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            131L,
            LocalDateTime.parse("2023-03-03T10:15:30")
        ),
        generator
    );

    assertThat(entity)
        .satisfies(
            actual -> assertThat(actual.addresses())
                .satisfiesExactly(
                    address -> assertThat(address)
                        .returns("F", PostalEntityLocatorParticipation::type)
                        .returns("BIR", PostalEntityLocatorParticipation::use)
                        .satisfies(
                            updated -> assertThat(updated)
                                .as("Participation status made inactive")
                                .extracting(PostalEntityLocatorParticipation::status)
                                .satisfies(StatusAssertions.inactive("2023-03-03T10:15:30"))
                        )
                        .satisfies(
                            updated -> assertThat(updated)
                                .as("Participation record status made inactive")
                                .extracting(PostalEntityLocatorParticipation::recordStatus)
                                .satisfies(RecordStatusAssertions.inactive("2023-03-03T10:15:30"))
                        )
                        .extracting(PostalEntityLocatorParticipation::locator)
                        .satisfies(
                            updated -> assertThat(updated)
                                .as("Locator record record status remains unchanged")
                                .extracting(PostalLocator::recordStatus)
                                .satisfies(RecordStatusAssertions.active("2019-02-05T10:15:30"))
                        )
                )
        );
  }

}
