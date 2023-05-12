package gov.cdc.nbs.patient.contact;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.investigation.association.AssociatedWith;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientNamedByContactTupleMapperTest {

    @Test
    void should_map_contact_from_tuple() {

        PatientNamedByContactTupleMapper.Tables tables = new PatientNamedByContactTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.tracing().id)).thenReturn(2357L);
        when(tuple.get(tables.tracing().addTime)).thenReturn(Instant.parse("2023-01-17T22:54:43Z"));
        when(tuple.get(tables.namedOn())).thenReturn(Instant.parse("2023-01-17T22:54:43Z"));

        when(tuple.get(tables.subject().id)).thenReturn(433L);

        when(tuple.get(tables.associatedWith().investigation().id)).thenReturn(227L);
        when(tuple.get(tables.associatedWith().investigation().localId)).thenReturn("investigation-local");

        when(tuple.get(tables.associatedWith().condition().id)).thenReturn("condition-id");
        when(tuple.get(tables.associatedWith().condition().conditionShortNm)).thenReturn("condition-description");

        when(tuple.get(tables.tracing().localId)).thenReturn("event");

        PatientNamedByContactTupleMapper mapper = new PatientNamedByContactTupleMapper(tables);

        PatientContacts.NamedByContact actual = mapper.map(tuple);

        assertThat(actual.contactRecord()).isEqualTo(2357L);
        assertThat(actual.createdOn()).isEqualTo("2023-01-17T22:54:43Z");

        assertThat(actual.condition())
            .returns("condition-id", PatientContacts.Condition::id)
            .returns("condition-description", PatientContacts.Condition::description);

        assertThat(actual.contact())
            .returns(433L, PatientContacts.NamedContact::id);

        assertThat(actual.namedOn()).isEqualTo("2023-01-17T22:54:43Z");
        assertThat(actual.event()).isEqualTo("event");

        assertThat(actual.associatedWith())
            .returns(227L, AssociatedWith::id)
            .returns("investigation-local", AssociatedWith::local)
            .returns("condition-description", AssociatedWith::condition)
        ;

    }

    @Test
    void should_not_map_contact_from_tuple_without_identifier() {
        PatientNamedByContactTupleMapper.Tables tables = new PatientNamedByContactTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);


        PatientNamedByContactTupleMapper mapper = new PatientNamedByContactTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");
    }
}
