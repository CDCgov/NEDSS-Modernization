package gov.cdc.nbs.investigation.association;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QConditionCode;

import java.util.Optional;

public class AssociatedWithTupleMapper {

    public record Tables(
        QPublicHealthCase investigation,
        QConditionCode condition
    ) {
    }


    private final Tables tables;

    public AssociatedWithTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    public Optional<AssociatedWith> maybeMap(final Tuple tuple) {
        Long identifier = tuple.get(tables.investigation().id);
        String local = tuple.get(tables.investigation().localId);
        String condition = tuple.get(tables.condition().conditionShortNm);

        return identifier == null
            ? Optional.empty()
            : Optional.of(
            new AssociatedWith(
                identifier,
                local,
                condition
            )
        );
    }
}
