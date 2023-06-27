package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.time.Instant;

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

        address.delete(
            new PatientCommand.DeleteAddress(
                117L,
                5331L,
                191L,
                Instant.parse("2021-05-24T11:01:17Z")
            )
        );

        assertThat(address)
            .isInstanceOf(PostalEntityLocatorParticipation.class)
            .asInstanceOf(InstanceOfAssertFactories.type(PostalEntityLocatorParticipation.class))
            .returns(5331L, p -> p.getId().getLocatorUid())
            .returns("INACTIVE", EntityLocatorParticipation::getRecordStatusCd)
            .returns(Instant.parse("2021-05-24T11:01:17Z"), EntityLocatorParticipation::getRecordStatusTime)
            .returns(191L, EntityLocatorParticipation::getLastChgUserId)
            .returns(Instant.parse("2021-05-24T11:01:17Z"), EntityLocatorParticipation::getLastChgTime)
            ;
    }
}
