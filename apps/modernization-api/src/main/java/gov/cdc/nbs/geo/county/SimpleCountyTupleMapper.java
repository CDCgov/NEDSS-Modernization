package gov.cdc.nbs.geo.county;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;

import java.util.Objects;
import java.util.Optional;

public class SimpleCountyTupleMapper {

    public record Tables(QPostalLocator address, QCodeValueGeneral county) {
    }


    private final Tables tables;

    public SimpleCountyTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    public SimpleCounty map(final Tuple tuple) {
        String id = Objects.requireNonNull(
            tuple.get(this.tables.address().cntyCd),
            "A county identifier is required"
        );
        String description = tuple.get(this.tables.county().codeShortDescTxt);

        return resolve(id, description);
    }

    private SimpleCounty resolve(final String id, final String description) {
        return new SimpleCounty(
            id,
            description == null ? id : description
        );
    }

    public Optional<SimpleCounty> maybeMap(final Tuple tuple) {
        String id = tuple.get(this.tables.address().cntyCd);
        String description = tuple.get(this.tables.county().codeShortDescTxt);

        return id == null
            ? Optional.empty()
            : Optional.of(resolve(id, description))
            ;
    }
}
