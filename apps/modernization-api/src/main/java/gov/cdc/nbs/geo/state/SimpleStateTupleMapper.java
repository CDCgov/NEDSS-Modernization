package gov.cdc.nbs.geo.state;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QStateCode;

import java.util.Objects;
import java.util.Optional;

public class SimpleStateTupleMapper {

    public record Tables(QPostalLocator address, QStateCode state) {
    }


    private final Tables tables;

    public SimpleStateTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    public SimpleState map(final Tuple tuple) {
        String id = Objects.requireNonNull(
            tuple.get(this.tables.address().stateCd),
            "A state identifier is required"
        );
        String description = tuple.get(this.tables.state().stateNm);

        return resolve(id, description);
    }

    private SimpleState resolve(final String id, final String description) {
        return new SimpleState(
            id,
            description == null ? id : description
        );
    }

    public Optional<SimpleState> maybeMap(final Tuple tuple) {
        String id = tuple.get(this.tables.address().stateCd);
        String description = tuple.get(this.tables.state().stateNm);

        return id == null
            ? Optional.empty()
            : Optional.of(resolve(id, description))
            ;
    }
}
