package gov.cdc.nbs.geo.county;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCountyTupleMapperTest {

    @Test
    void should_map_known_county_from_tuple() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");
        when(tuple.get(tables.county().codeDescTxt)).thenReturn("county-description");

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        SimpleCounty actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("county-id");
        assertThat(actual.description()).isEqualTo("county-description");
    }

    @Test
    void should_map_unknown_county_from_tuple() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        SimpleCounty actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("county-id");
        assertThat(actual.description()).isEqualTo("county-id");
    }

    @Test
    void should_not_map_county_without_identifier() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");
    }

    @Test
    void should_map_known_county_if_present() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");
        when(tuple.get(tables.county().codeDescTxt)).thenReturn("county-description");

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        Optional<SimpleCounty> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleCounty("county-id", "county-description"));

    }

    @Test
    void should_map_unknown_county_if_present() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        Optional<SimpleCounty> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleCounty("county-id", "county-id"));

    }

    @Test
    void should_not_map_county_when_not_present() {
        SimpleCountyTupleMapper.Tables tables = new SimpleCountyTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCodeValueGeneral.codeValueGeneral
        );

        Tuple tuple = mock(Tuple.class);

        SimpleCountyTupleMapper mapper = new SimpleCountyTupleMapper(tables);

        Optional<SimpleCounty> actual = mapper.maybeMap(tuple);

        assertThat(actual).isEmpty();

    }

}
