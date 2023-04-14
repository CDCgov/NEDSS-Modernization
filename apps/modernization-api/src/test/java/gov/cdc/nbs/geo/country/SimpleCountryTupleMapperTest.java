package gov.cdc.nbs.geo.country;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCountryCode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleCountryTupleMapperTest {

    @Test
    void should_map_known_country_from_tuple() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");
        when(tuple.get(tables.country().codeDescTxt)).thenReturn("country-description");

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        SimpleCountry actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("country-id");
        assertThat(actual.description()).isEqualTo("country-description");
    }

    @Test
    void should_map_unknown_country_from_tuple() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        SimpleCountry actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("country-id");
        assertThat(actual.description()).isEqualTo("country-id");
    }

    @Test
    void should_not_map_country_without_identifier() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");
    }

    @Test
    void should_map_known_country_if_present() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");
        when(tuple.get(tables.country().codeDescTxt)).thenReturn("country-description");

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        Optional<SimpleCountry> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleCountry("country-id", "country-description"));

    }

    @Test
    void should_map_unknown_country_if_present() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        Optional<SimpleCountry> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleCountry("country-id", "country-id"));

    }

    @Test
    void should_not_map_country_when_not_present() {
        SimpleCountryTupleMapper.Tables tables = new SimpleCountryTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QCountryCode.countryCode
        );

        Tuple tuple = mock(Tuple.class);

        SimpleCountryTupleMapper mapper = new SimpleCountryTupleMapper(tables);

        Optional<SimpleCountry> actual = mapper.maybeMap(tuple);

        assertThat(actual).isEmpty();

    }

}
