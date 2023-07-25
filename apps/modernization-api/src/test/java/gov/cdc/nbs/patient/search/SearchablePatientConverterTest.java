package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import gov.cdc.nbs.entity.elasticsearch.NestedEntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.PatientCommand;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SearchablePatientConverterTest {

    @Test
    void should_convert_patient() {
        Person patient = new Person(1157L, "local");

        ElasticsearchPerson searchable = SearchablePatientConverter.toSearchable(patient);

        assertThat(searchable)
            .returns("1157", ElasticsearchPerson::getId)
            .returns(1157L, ElasticsearchPerson::getPersonUid)
            .returns("local", ElasticsearchPerson::getLocalId)
            .satisfies(
                actual -> assertThat(actual.getEntityId()).isEmpty()
            )
        ;
    }

    @Test
    void should_convert_patient_with_identifications() {

        Person patient = new Person(1157L, "local");

        patient.add(
            new PatientCommand.AddIdentification(
                1157L,
                Instant.parse("1999-09-09T11:59:13Z"),
                "identification-value",
                "authority-value",
                "identification-type",
                131L,
                Instant.parse("2020-03-03T10:15:30.00Z")
            )
        );

        ElasticsearchPerson searchable = SearchablePatientConverter.toSearchable(patient);

        assertThat(searchable.getEntityId()).satisfiesExactlyInAnyOrder(
            actual -> assertThat(actual)
                .returns("identification-value", NestedEntityId::getRootExtensionTxt)
                .returns("identification-type", NestedEntityId::getTypeCd)
                .returns("ACTIVE", NestedEntityId::getRecordStatusCd)
        );
    }


}
