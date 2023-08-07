package gov.cdc.nbs.patient.profile.investigation;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.entity.srte.QJurisdictionCode;
import gov.cdc.nbs.patient.NameRenderer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

class PatientInvestigationTupleMapper {

    record Tables(
        QPublicHealthCase investigation,
        QConditionCode condition,
        QCodeValueGeneral status,
        QCodeValueGeneral caseStatus,
        QJurisdictionCode jurisdiction,
        QCodeValueGeneral notificationStatus,
        QPerson investigator
    ) {

        Tables() {
            this(
                QPublicHealthCase.publicHealthCase,
                QConditionCode.conditionCode,
                new QCodeValueGeneral("status"),
                new QCodeValueGeneral("case_status"),
                QJurisdictionCode.jurisdictionCode,
                new QCodeValueGeneral("notification_status"),
                new QPerson("investigator")
            );
        }
    }


    private final Tables tables;

    PatientInvestigationTupleMapper(Tables tables) {
        this.tables = tables;
    }

    PatientInvestigation map(final Tuple tuple) {

        long identifier = Objects.requireNonNull(
            tuple.get(this.tables.investigation().id),
            "An investigation identifier is required."
        );

        LocalDate startedOn = resolveStartedOn(tuple);
        String condition = tuple.get(tables.condition().conditionShortNm);
        String status = tuple.get(tables.status().codeShortDescTxt);
        String caseStatus = tuple.get(tables.caseStatus().codeShortDescTxt);
        String jurisdiction = tuple.get(tables.jurisdiction().codeShortDescTxt);
        String event = tuple.get(tables.investigation().localId);
        String coInfection = tuple.get(tables.investigation().coinfectionId);
        String notification = tuple.get(tables.notificationStatus().codeShortDescTxt);

        String investigator = mapInvestigator(tuple);

        return new PatientInvestigation(
            identifier,
            startedOn,
            condition,
            status,
            caseStatus,
            jurisdiction,
            event,
            coInfection,
            notification,
            investigator
        );
    }

    private LocalDate resolveStartedOn(final Tuple tuple) {

        Instant value = tuple.get(tables.investigation().activityFromTime);

        return value == null
            ? null
            : LocalDate.ofInstant(value, ZoneId.of("UTC"));

    }

    private String mapInvestigator(final Tuple tuple) {
        String first = tuple.get(tables.investigator().firstNm);
        String last = tuple.get(tables.investigator().lastNm);

        return NameRenderer.render(first, last);
    }
}
