package gov.cdc.nbs.patient.profile.identification;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientIdentificationTupleMapperTest {

    @Test
    void should_map_identification_from_tuple() {
        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(2357L);
        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.identification().id.entityIdSeq)).thenReturn((short) 557);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.identification().typeCd)).thenReturn("type-id");
        when(tuple.get(tables.type().codeShortDescTxt)).thenReturn("type-description");

        when(tuple.get(tables.identification().rootExtensionTxt)).thenReturn("value");

        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        PatientIdentification actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.sequence()).isEqualTo((short) 557);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");

        assertThat(actual.type().id()).isEqualTo("type-id");
        assertThat(actual.type().description()).isEqualTo("type-description");

        assertThat(actual.authority()).isNull();

        assertThat(actual.value()).isEqualTo("value");

    }

    @Test
    void should_map_identification_from_tuple_with_authority() {
        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.identification().id.entityIdSeq)).thenReturn((short) 557);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.identification().assigningAuthorityCd)).thenReturn("authority-id");
        when(tuple.get(tables.authority().codeShortDescTxt)).thenReturn("authority-description");

        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        PatientIdentification actual = mapper.map(tuple);

        assertThat(actual.authority().id()).isEqualTo("authority-id");
        assertThat(actual.authority().description()).isEqualTo("authority-description");
    }

    @Test
    void should_map_identification_from_tuple_with_authority_lacking_description() {
        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.identification().id.entityIdSeq)).thenReturn((short) 557);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.identification().assigningAuthorityCd)).thenReturn("authority-id");

        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        PatientIdentification actual = mapper.map(tuple);

        assertThat(actual.authority().id()).isEqualTo("authority-id");
        assertThat(actual.authority().description()).isEqualTo("authority-id");
    }

    @Test
    void should_not_map_identification_from_tuple_without_patient() {
        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.identification().id.entityIdSeq)).thenReturn((short) 557);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_identification_from_tuple_without_version() {
        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.identification().id.entityIdSeq)).thenReturn((short) 557);

        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }

    @Test
    void should_not_map_identification_from_tuple_without_sequence() {

        PatientIdentificationTupleMapper.Tables tables = new PatientIdentificationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.identification().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);


        PatientIdentificationTupleMapper mapper = new PatientIdentificationTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("sequence is required");

    }
}
