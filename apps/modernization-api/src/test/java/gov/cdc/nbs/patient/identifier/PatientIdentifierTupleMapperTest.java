package gov.cdc.nbs.patient.identifier;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.util.OptionalLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentifierTupleMapperTest {

    @Test
    void should_map_patient_profile_from_tuple() {

        PatientIdentifierTupleMapper.Tables table = new PatientIdentifierTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(table.patient().id)).thenReturn(3167L);
        when(tuple.get(table.patient().localId)).thenReturn("local-id-value");

        PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);
        when(resolver.resolve(anyString())).thenReturn(OptionalLong.of(1000L));

        PatientIdentifierTupleMapper mapper = new PatientIdentifierTupleMapper(table, resolver);

        PatientIdentifier actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo(3167L);
        assertThat(actual.local()).isEqualTo("local-id-value");
        assertThat(actual.shortId()).isEqualTo(1000L);
    }

    @Test
    void should_not_map_patient_profile_when_parent_id_not_present() {

        PatientIdentifierTupleMapper.Tables table = new PatientIdentifierTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);

        PatientIdentifierTupleMapper mapper = new PatientIdentifierTupleMapper(table, resolver);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("id is required");

    }

    @Test
    void should_not_map_patient_profile_when_local_id_not_present() {

        PatientIdentifierTupleMapper.Tables table = new PatientIdentifierTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(table.patient().id)).thenReturn(3167L);

        PatientShortIdentifierResolver resolver = mock(PatientShortIdentifierResolver.class);

        PatientIdentifierTupleMapper mapper = new PatientIdentifierTupleMapper(table, resolver);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("local id is required");

    }

}
