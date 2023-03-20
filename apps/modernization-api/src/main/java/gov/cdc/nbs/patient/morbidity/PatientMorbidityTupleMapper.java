package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.NameRenderer;

import java.time.Instant;
import java.util.Objects;

class PatientMorbidityTupleMapper {

    private final PatientMorbidityTables tables;

    public PatientMorbidityTupleMapper(final PatientMorbidityTables tables) {
        this.tables = tables;
    }

    PatientMorbidity map(final Tuple tuple) {
        Long morbidity = Objects.requireNonNull(tuple.get(tables.morbidity().id), "A morbidity is required");
        Instant receivedOn = tuple.get(tables.morbidity().addTime);

        String providerPrefix = tuple.get(tables.provider().nmPrefix);
        String providerFirstName = tuple.get(tables.provider().firstNm);
        String providerLastName = tuple.get(tables.provider().lastNm);
        Suffix providerSuffix = tuple.get(tables.provider().nmSuffix);

        String provider = NameRenderer.render(
            providerPrefix,
            providerFirstName,
            providerLastName,
            providerSuffix
        );

        Instant reportedOn = tuple.get(tables.morbidity().activityToTime);
        String condition = tuple.get(tables.condition().conditionShortNm);
        String jurisdiction = tuple.get(tables.jurisdiction().codeShortDescTxt);

        String event = tuple.get(tables.morbidity().localId);

        PatientMorbidity.Investigation investigation = maybeMapInvestigation(tuple);

        return new PatientMorbidity(
            morbidity,
            receivedOn,
            provider,
            reportedOn,
            condition,
            jurisdiction,
            event,
            investigation
        );
    }

    private PatientMorbidity.Investigation maybeMapInvestigation(final Tuple tuple) {
        Long identifier = tuple.get(tables.investigation().id);
        String local = tuple.get(tables.investigation().localId);
        String condition = tuple.get(tables.investigationCondition().conditionShortNm);

        return identifier == null
            ? null
            : new PatientMorbidity.Investigation(
            identifier,
            local,
            condition
        );
    }


}
