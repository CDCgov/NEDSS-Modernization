package gov.cdc.nbs.provider;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.NameRenderer;

public class ProviderNameTupleMapper {

    public record Tables(QPersonName name) {
    }
    private final Tables tables;

    public ProviderNameTupleMapper(final Tables tables) {
        this.tables = tables;
    }

    public String map(final Tuple tuple) {
        String providerPrefix = tuple.get(tables.name().nmPrefix);
        String providerFirstName = tuple.get(tables.name().firstNm);
        String providerLastName = tuple.get(tables.name().lastNm);
        Suffix providerSuffix = tuple.get(tables.name().nmSuffix);

        return NameRenderer.render(
            providerPrefix,
            providerFirstName,
            providerLastName,
            providerSuffix
        );
    }
}
