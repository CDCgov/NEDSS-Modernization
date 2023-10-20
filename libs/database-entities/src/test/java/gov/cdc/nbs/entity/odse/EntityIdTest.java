package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class EntityIdTest {

    @Test
    void should_inactive_an_identification_when_deleted() {
        Person patient = new Person(117L, "local-id-value");

        EntityIdId identifier = new EntityIdId(patient.getId(), (short)3);

        EntityId identification = new EntityId(
            patient.getNbsEntity(),
            identifier,
            new PatientCommand.AddIdentification(
                117L,
                Instant.parse("2021-05-15T10:00:00Z"),
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        identification.delete(
            new PatientCommand.DeleteIdentification(
                117,
                1,
                191L,
                Instant.parse("2020-03-13T13:15:30Z")
            )
        );

        assertThat(identification)
            .returns("INACTIVE", EntityId::getRecordStatusCd)
            .returns(Instant.parse("2020-03-13T13:15:30Z"), EntityId::getRecordStatusTime)
            .extracting(EntityId::getAudit)
            .extracting(Audit::changed)
            .returns(191L, Changed::changedBy)
            .returns(Instant.parse("2020-03-13T13:15:30Z"), Changed::changedOn)
;
    }
}
