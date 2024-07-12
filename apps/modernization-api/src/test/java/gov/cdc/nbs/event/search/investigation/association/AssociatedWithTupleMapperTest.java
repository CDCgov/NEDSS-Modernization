package gov.cdc.nbs.event.search.investigation.association;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.patient.profile.association.AssociatedWith;
import gov.cdc.nbs.patient.profile.association.AssociatedWithTupleMapper;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssociatedWithTupleMapperTest {

    @Test
    void should_map_associated_with_when_identifier_is_present() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
                QPublicHealthCase.publicHealthCase,
                QConditionCode.conditionCode);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(4127L);
        when(tuple.get(tables.investigation().localId)).thenReturn("investigation-local");
        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("investigation-condition");

        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        Optional<AssociatedWith> actual = mapper.maybeMap(tuple);

        assertThat(actual).hasValueSatisfying(
                actualAssociated -> assertThat(actualAssociated)
                        .returns(4127L, AssociatedWith::id)
                        .returns("investigation-local", AssociatedWith::local)
                        .returns("investigation-condition", AssociatedWith::condition));
    }


    @Test
    void should_map_empty_when_identifier_is_not_present() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
                QPublicHealthCase.publicHealthCase,
                QConditionCode.conditionCode);

        Tuple tuple = mock(Tuple.class);

        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        Optional<AssociatedWith> actual = mapper.maybeMap(tuple);

        assertThat(actual).isNotPresent();
    }

    @Test
    void should_map_associated_with_from_tuple() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
                QPublicHealthCase.publicHealthCase,
                QConditionCode.conditionCode);

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(4127L);
        when(tuple.get(tables.investigation().localId)).thenReturn("investigation-local");
        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("investigation-condition");

        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        AssociatedWith actual = mapper.map(tuple);

        assertThat(actual)
                .returns(4127L, AssociatedWith::id)
                .returns("investigation-local", AssociatedWith::local)
                .returns("investigation-condition", AssociatedWith::condition);
    }

    @Test
    void should_not_map_associated_with_from_tuple_without_identifier() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
                QPublicHealthCase.publicHealthCase,
                QConditionCode.conditionCode);

        Tuple tuple = mock(Tuple.class);


        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
                .hasMessageContaining("identifier is required");

    }
}
