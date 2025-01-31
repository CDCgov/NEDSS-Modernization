package gov.cdc.nbs.patient.profile.administrative;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientAdministrativeTupleMapperTest {

    @Test
    void should_map_administrative_from_tuple() {
        PatientAdministrativeTupleMapper.Tables tables = new PatientAdministrativeTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().asOfDateAdmin)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.patient().description)).thenReturn("description-value");

        PatientAdministrativeTupleMapper mapper = new PatientAdministrativeTupleMapper(tables);

        PatientAdministrative actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.comment()).isEqualTo("description-value");

    }

    @Test
    void should_not_map_administrative_from_tuple_without_identifier() {
        PatientAdministrativeTupleMapper.Tables tables = new PatientAdministrativeTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientAdministrativeTupleMapper mapper = new PatientAdministrativeTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_administrative_from_tuple_without_patient() {
        PatientAdministrativeTupleMapper.Tables tables = new PatientAdministrativeTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientAdministrativeTupleMapper mapper = new PatientAdministrativeTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_administrative_from_tuple_without_version() {
        PatientAdministrativeTupleMapper.Tables tables = new PatientAdministrativeTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

        PatientAdministrativeTupleMapper mapper = new PatientAdministrativeTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }
}
