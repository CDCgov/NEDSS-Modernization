package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PostalEntityLocatorParticipationTest {

  @Test
  void should_inactive_existing_address() {

    Person patient = new Person(117L, "local-id-value");

    PostalEntityLocatorParticipation address = new PostalEntityLocatorParticipation(
        patient.getNbsEntity(),
        new EntityLocatorParticipationId(patient.getId(), 5331L),
        new PatientCommand.AddAddress(
            117L,
            5331L,
            Instant.parse("2021-07-07T03:06:09Z"),
            "SA1",
            "SA2",
            "city-description",
            "State",
            "Zip",
            "county-code",
            "country-code",
            "Census Tract",
            131L,
            LocalDateTime.parse("2020-03-03T10:15:30")
        )
    );

    address.delete(
        new PatientCommand.DeleteAddress(
            117L,
            5331L,
            191L,
            LocalDateTime.parse("2021-05-24T11:01:17")
        )
    );

    assertThat(address.audit())
        .describedAs("expected audit state")
        .satisfies(AuditAssertions.added(131L, "2020-03-03T10:15:30"))
        .satisfies(AuditAssertions.changed(191L, "2021-05-24T11:01:17"));


    assertThat(address)
        .isInstanceOf(PostalEntityLocatorParticipation.class)
        .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
        .returns(5331L, p -> p.getId().getLocatorUid())
        .extracting(EntityLocatorParticipation::recordStatus)
        .satisfies(RecordStatusAssertions.inactive("2021-05-24T11:01:17"))
    ;
  }
}
