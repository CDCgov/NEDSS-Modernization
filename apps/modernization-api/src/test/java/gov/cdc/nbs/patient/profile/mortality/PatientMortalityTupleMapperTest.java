package gov.cdc.nbs.patient.profile.mortality;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Deceased;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientMortalityTupleMapperTest {

    @Test
    void should_map_mortality_from_tuple() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().asOfDateMorbidity)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().deceasedTime)).thenReturn(LocalDate.parse("1999-09-09"));

        when(tuple.get(tables.address().cityDescTxt)).thenReturn("city");

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        PatientMortality actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");

        assertThat(actual.deceasedOn()).isEqualTo("1999-09-09");

        assertThat(actual.deceased()).isNull();

        assertThat(actual.city()).isEqualTo("city");

        assertThat(actual.state()).isNull();
        assertThat(actual.county()).isNull();
        assertThat(actual.country()).isNull();
    }

    @Test
    void should_map_mortality_from_tuple_with_deceased() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().deceasedIndCd)).thenReturn(Deceased.N);

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        PatientMortality actual = mapper.map(tuple);

        assertThat(actual.deceased().id()).isEqualTo("N");
        assertThat(actual.deceased().description()).isEqualTo("No");

    }

    @Test
    void should_map_mortality_from_tuple_with_mortality_state() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");
        when(tuple.get(tables.state().stateNm)).thenReturn("state-description");

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        PatientMortality actual = mapper.map(tuple);

        assertThat(actual.state().id()).isEqualTo("state-id");
        assertThat(actual.state().description()).isEqualTo("state-description");

    }

    @Test
    void should_map_mortality_from_tuple_with_mortality_county() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");
        when(tuple.get(tables.county().codeDescTxt)).thenReturn("county-description");

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        PatientMortality actual = mapper.map(tuple);

        assertThat(actual.county().id()).isEqualTo("county-id");
        assertThat(actual.county().description()).isEqualTo("county-description");

    }

    @Test
    void should_map_mortality_from_tuple_with_mortality_country() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");
        when(tuple.get(tables.country().codeDescTxt)).thenReturn("country-description");

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        PatientMortality actual = mapper.map(tuple);

        assertThat(actual.country().id()).isEqualTo("country-id");
        assertThat(actual.country().description()).isEqualTo("country-description");

    }

    @Test
    void should_not_map_mortality_from_tuple_without_identifier() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_mortality_from_tuple_without_patient() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_mortality_from_tuple_without_version() {
        PatientMortalityTupleMapper.Tables tables = new PatientMortalityTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

        PatientMortalityTupleMapper mapper = new PatientMortalityTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }
}
