package gov.cdc.nbs.geo.country;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPostalLocator;
import gov.cdc.nbs.entity.srte.QCountryCode;

import java.util.Objects;
import java.util.Optional;

public class SimpleCountryTupleMapper {

    public record Tables(QPostalLocator address, QCountryCode country) {
    }


    private final Tables tables;

    public SimpleCountryTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    public SimpleCountry map(final Tuple tuple) {
        String id = Objects.requireNonNull(
            tuple.get(this.tables.address().cntryCd),
            "A country identifier is required"
        );
        String description = tuple.get(this.tables.country().codeDescTxt);

        return resolve(id, description);
    }

    private SimpleCountry resolve(final String id, final String description) {
        return new SimpleCountry(
            id,
            description == null ? id : description
        );
    }

    public Optional<SimpleCountry> maybeMap(final Tuple tuple) {
        String id = tuple.get(this.tables.address().cntryCd);
        String description = tuple.get(this.tables.country().codeDescTxt);

        return id == null
            ? Optional.empty()
            : Optional.of(resolve(id, description))
            ;
    }
}
