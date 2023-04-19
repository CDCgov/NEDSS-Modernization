package gov.cdc.nbs.investigation.association;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QConditionCode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssociatedWithTupleMapperTest {

    @Test
    void should_map_associated_with_when_identifier_is_present() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
            QPublicHealthCase.publicHealthCase,
            QConditionCode.conditionCode
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(4127L);
        when(tuple.get(tables.investigation().localId)).thenReturn("investigation-local");
        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("investigation-condition");

        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        Optional<AssociatedWith> actual = mapper.maybeMap(tuple);

        assertThat(actual).hasValueSatisfying(
            actual_associated -> assertThat(actual_associated)
                .returns(4127L, AssociatedWith::id)
                .returns("investigation-local", AssociatedWith::local)
                .returns("investigation-condition", AssociatedWith::condition)
        );
    }


    @Test
    void should_map_empty_when_identifier_is_not_present() {

        AssociatedWithTupleMapper.Tables tables = new AssociatedWithTupleMapper.Tables(
            QPublicHealthCase.publicHealthCase,
            QConditionCode.conditionCode
        );

        Tuple tuple = mock(Tuple.class);

        AssociatedWithTupleMapper mapper = new AssociatedWithTupleMapper(tables);

        Optional<AssociatedWith> actual = mapper.maybeMap(tuple);

        assertThat(actual).isNotPresent();
    }
}
