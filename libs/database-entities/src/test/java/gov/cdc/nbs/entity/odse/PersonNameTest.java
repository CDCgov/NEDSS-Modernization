package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Changed;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PersonNameTest {

    @Test
    void should_inactivate_existing_name() {

        Person patient = new Person(117L, "local-id-value");

        PersonNameId identifier = new PersonNameId(117L, (short)2);

        PersonName name = new PersonName(
            identifier,
            patient,
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

        name.delete(
            new PatientCommand.DeleteNameInfo(
                117L,
                (short) 2,
                171L,
                Instant.parse("2021-03-03T10:15:30.00Z")
            )
        );

        assertThat(name)
            .satisfies(
                removed -> assertThat(removed.getAudit())
                    .describedAs("expected name audit state")
                    .satisfies(
                        audit -> assertThat(audit.changed())
                            .returns(171L, Changed::changedBy)
                            .returns(Instant.parse("2021-03-03T10:15:30.00Z"), Changed::changedOn)
                    )
            )
            .satisfies(
                removed -> assertThat(removed)
                    .describedAs("expected name is inactive")
                    .returns("INACTIVE", PersonName::getRecordStatusCd)
                    .returns(Instant.parse("2021-03-03T10:15:30.00Z"), PersonName::getRecordStatusTime)
            )
            .extracting(PersonName::getId)
            .returns(117L, PersonNameId::getPersonUid)
            .returns((short) 2, PersonNameId::getPersonNameSeq)
            ;
    }
}
