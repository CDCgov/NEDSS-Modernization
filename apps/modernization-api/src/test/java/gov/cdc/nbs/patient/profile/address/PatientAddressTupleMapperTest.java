package gov.cdc.nbs.patient.profile.address;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientAddressTupleMapperTest {

    @Test
    void should_map_address_from_tuple() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);
        when(tuple.get(tables.locators().asOfDate)).thenReturn(LocalDate.parse("1993-11-09"));
        when(tuple.get(tables.locators().cd)).thenReturn("type-id");
        when(tuple.get(tables.type().codeShortDescTxt)).thenReturn("type-description");
        when(tuple.get(tables.locators().useCd)).thenReturn("use-id");
        when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-description");

        when(tuple.get(tables.address().streetAddr1)).thenReturn("address1");
        when(tuple.get(tables.address().streetAddr2)).thenReturn("address2");
        when(tuple.get(tables.address().cityDescTxt)).thenReturn("city");
        when(tuple.get(tables.address().zipCd)).thenReturn("zipcode");

        when(tuple.get(tables.address().censusTract)).thenReturn("census-tract");
        when(tuple.get(tables.locators().locatorDescTxt)).thenReturn("comment");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(59L);
        assertThat(actual.id()).isEqualTo(157L);
        assertThat(actual.version()).isEqualTo((short) 269);

        assertThat(actual.asOf()).isEqualTo("1993-11-09");

        assertThat(actual.type().id()).isEqualTo("type-id");
        assertThat(actual.type().description()).isEqualTo("type-description");

        assertThat(actual.use().id()).isEqualTo("use-id");
        assertThat(actual.use().description()).isEqualTo("use-description");

        assertThat(actual.address1()).isEqualTo("address1");
        assertThat(actual.address2()).isEqualTo("address2");
        assertThat(actual.city()).isEqualTo("city");
        assertThat(actual.zipcode()).isEqualTo("zipcode");

        assertThat(actual.county()).isNull();
        assertThat(actual.state()).isNull();
        assertThat(actual.country()).isNull();

        assertThat(actual.censusTract()).isEqualTo("census-tract");
        assertThat(actual.comment()).isEqualTo("comment");
    }

    @Test
    void should_not_map_address_from_tuple_without_identifier() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_address_from_tuple_without_patient() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_address_from_tuple_without_version() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }

    @Test
    void should_map_address_from_tuple_with_known_county() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");
        when(tuple.get(tables.county().codeDescTxt)).thenReturn("county-description");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.county().id()).isEqualTo("county-id");
        assertThat(actual.county().description()).isEqualTo("county-description");
    }

    @Test
    void should_map_address_from_tuple_unknown_county() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.county().id()).isEqualTo("county-id");
        assertThat(actual.county().description()).isEqualTo("county-id");
    }

    @Test
    void should_map_address_from_tuple_with_known_state() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");
        when(tuple.get(tables.state().stateNm)).thenReturn("state-description");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.state().id()).isEqualTo("state-id");
        assertThat(actual.state().description()).isEqualTo("state-description");
    }

    @Test
    void should_map_address_from_tuple_with_unknown_state() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.state().id()).isEqualTo("state-id");
        assertThat(actual.state().description()).isEqualTo("state-id");
    }

    @Test
    void should_map_address_from_tuple_with_known_country() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");
        when(tuple.get(tables.country().codeDescTxt)).thenReturn("country-description");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.country().id()).isEqualTo("country-id");
        assertThat(actual.country().description()).isEqualTo("country-description");
    }

    @Test
    void should_map_address_from_tuple_with_unknown_country() {
        PatientAddressTupleMapper.Tables tables = new PatientAddressTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(59L);
        when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
        when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");

        PatientAddressTupleMapper mapper = new PatientAddressTupleMapper(tables);

        PatientAddress actual = mapper.map(tuple);

        assertThat(actual.country().id()).isEqualTo("country-id");
        assertThat(actual.country().description()).isEqualTo("country-id");
    }
}

