package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersonEntityIdsResolverTest {

    @Test
    void should_resolve_identifications_as_entityIds() {

        Person patient = new Person(117L, "local");

        patient.add(
            new PatientCommand.AddIdentification(
                patient.getId(),
            Instant.parse("2017-05-16T11:13:19Z"),
                "123456789",
                "OTH",
                "ssn",
                9999L,
                Instant.now()
            )
        );

        PersonEntityIdsResolver resolver = new PersonEntityIdsResolver();

        List<EntityId> actual = resolver.resolve(patient);

        assertThat(actual).satisfiesExactly(
            actual_identification -> assertThat(actual_identification)
                .returns("123456789", EntityId::getRootExtensionTxt)
                .returns("OTH", EntityId::getAssigningAuthorityCd)
                .returns("ssn", EntityId::getTypeCd)
        );
    }
}
