package gov.cdc.nbs.patient.profile.ethnicity;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientEthnicityTupleMapperTest {

    @Test
    void should_map_ethnicity_from_tuple() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.patient().ethnicity.asOfDateEthnicity)).thenReturn(LocalDate.parse("2023-01-17"));

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        PatientEthnicity actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");

        assertThat(actual.ethnicGroup()).isNull();
        assertThat(actual.unknownReason()).isNull();
        assertThat(actual.detailed()).isEmpty();

    }

    @Test
    void should_map_ethnicity_from_tuple_with_ethnic_group() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.ethnicGroup().id.code)).thenReturn("ethnic-group-id");
        when(tuple.get(tables.ethnicGroup().codeShortDescTxt)).thenReturn("ethnic-group-description");

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        PatientEthnicity actual = mapper.map(tuple);

        assertThat(actual.ethnicGroup().id()).isEqualTo("ethnic-group-id");
        assertThat(actual.ethnicGroup().description()).isEqualTo("ethnic-group-description");

    }

    @Test
    void should_map_ethnicity_from_tuple_with_unknown_reason() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.unknownReason().id.code)).thenReturn("unknown-reason-id");
        when(tuple.get(tables.unknownReason().codeShortDescTxt)).thenReturn("unknown-reason-description");

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        PatientEthnicity actual = mapper.map(tuple);

        assertThat(actual.unknownReason().id()).isEqualTo("unknown-reason-id");
        assertThat(actual.unknownReason().description()).isEqualTo("unknown-reason-description");

    }

    @Test
    void should_map_ethnicity_from_tuple_with_ethnicity() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.ethnicity().id.code)).thenReturn("ethnicity-id");
        when(tuple.get(tables.ethnicity().codeShortDescTxt)).thenReturn("ethnicity-description");

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        PatientEthnicity actual = mapper.map(tuple);

        assertThat(actual.detailed()).satisfiesExactly(
            actualEthnicity -> assertAll(
                () -> assertThat(actualEthnicity.id()).isEqualTo("ethnicity-id"),
                () -> assertThat(actualEthnicity.description()).isEqualTo("ethnicity-description")
            )
        );

    }

    @Test
    void should_not_map_ethnicity_from_tuple_without_identifier() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_ethnicity_from_tuple_without_patient() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_ethnicity_from_tuple_without_version() {
        PatientEthnicityTupleMapper.Tables tables = new PatientEthnicityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);

        PatientEthnicityTupleMapper mapper = new PatientEthnicityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }
}
