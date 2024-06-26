package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class TeleEntityLocatorParticipationTest {

    @Test
    void should_inactivate_existing_phone() {

        Person patient = new Person(117L, "local-id-value");

        TeleEntityLocatorParticipation participation = new TeleEntityLocatorParticipation(
            patient.getNbsEntity(),
            new EntityLocatorParticipationId(patient.getId(), 5347L),
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

        participation.delete(
            new PatientCommand.DeletePhone(
                117L,
                5347L,
                293L,
                Instant.parse("2023-03-03T10:15:30.00Z")
            )
        );

        assertThat(participation)
            .returns(5347L, p -> p.getId().getLocatorUid())
            .returns("INACTIVE", EntityLocatorParticipation::getRecordStatusCd)
            .returns(Instant.parse("2023-03-03T10:15:30.00Z"), EntityLocatorParticipation::getRecordStatusTime)
            .returns(293L, EntityLocatorParticipation::getLastChgUserId)
            .returns(Instant.parse("2023-03-03T10:15:30.00Z"), EntityLocatorParticipation::getLastChgTime)
        ;
    }
}
