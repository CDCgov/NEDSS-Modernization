package gov.cdc.nbs.geo.state;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QStateCode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleStateTupleMapperTest {

    @Test
    void should_map_known_state_from_tuple() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");
        when(tuple.get(tables.state().codeDescTxt)).thenReturn("state-description");

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        SimpleState actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("state-id");
        assertThat(actual.description()).isEqualTo("state-description");
    }

    @Test
    void should_map_unknown_state_from_tuple() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        SimpleState actual = mapper.map(tuple);

        assertThat(actual.id()).isEqualTo("state-id");
        assertThat(actual.description()).isEqualTo("state-id");
    }

    @Test
    void should_not_map_state_without_identifier() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");
    }

    @Test
    void should_map_known_state_if_present() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");
        when(tuple.get(tables.state().codeDescTxt)).thenReturn("state-description");

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        Optional<SimpleState> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleState("state-id", "state-description"));

    }

    @Test
    void should_map_unknown_state_if_present() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        Optional<SimpleState> actual = mapper.maybeMap(tuple);

        assertThat(actual).isPresent()
            .contains(new SimpleState("state-id", "state-id"));

    }

    @Test
    void should_not_map_state_when_not_present() {
        SimpleStateTupleMapper.Tables tables = new SimpleStateTupleMapper.Tables(
            QPostalLocator.postalLocator,
            QStateCode.stateCode
        );

        Tuple tuple = mock(Tuple.class);

        SimpleStateTupleMapper mapper = new SimpleStateTupleMapper(tables);

        Optional<SimpleState> actual = mapper.maybeMap(tuple);

        assertThat(actual).isEmpty();

    }
}
