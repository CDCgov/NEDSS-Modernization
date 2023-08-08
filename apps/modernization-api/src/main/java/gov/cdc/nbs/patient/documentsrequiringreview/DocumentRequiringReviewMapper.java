package gov.cdc.nbs.patient.documentsrequiringreview;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import gov.cdc.nbs.entity.odse.QNbsDocument;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QOrganization;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.Description;
import gov.cdc.nbs.patient.documentsrequiringreview.DocumentRequiringReview.FacilityProvider;

class DocumentRequiringReviewMapper {
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String DATE_RECEIVED = "dateReceived";
    private static final String EVENT_DATE = "eventDate";
    private static final String LOCAL_ID = "localId";
    private static final QNbsDocument DOCUMENT = QNbsDocument.nbsDocument;
    private static final QObservation OBSERVATION = QObservation.observation;
    private static final QConditionCode CONDITION = QConditionCode.conditionCode;
    private static final QObservation OBSERVATION_2 = new QObservation("obs2");
    private static final QObsValueCoded OBS_VALUE_CODED = QObsValueCoded.obsValueCoded;
    private static final QPersonName PERSON_NAME = QPersonName.personName;
    private static final QOrganization ORGANIZATION = QOrganization.organization;

    DocumentRequiringReview toDocumentRequiringReview(final Tuple row) {

        String type = row.get(Expressions.stringPath(TYPE));

        if (type.equals("Document")) {
            return new DocumentRequiringReview(
                    row.get(Expressions.path(Long.class, ID)),
                    row.get(Expressions.path(String.class, LOCAL_ID)),
                    type,
                    row.get(Expressions.path(Instant.class, EVENT_DATE)),
                    row.get(Expressions.path(Instant.class, DATE_RECEIVED)),
                    false,
                    row.get(DOCUMENT.externalVersionCtrlNbr) > 1,
                    Arrays.asList(new FacilityProvider("Sending Facility", row.get(DOCUMENT.sendingFacilityNm))),
                    Arrays.asList(new Description(row.get(CONDITION.conditionDescTxt), "")));
        } else if (type.equals("MorbReport") || type.equals("LabReport")) {
            List<Description> descriptions = type.equals("MorbReport")
                    ? Arrays.asList(new Description(row.get(CONDITION.conditionDescTxt), ""))
                    : new ArrayList<>();
            boolean isElectronic = Character.valueOf('Y').equals(row.get(OBSERVATION.electronicInd));
            return new DocumentRequiringReview(
                    row.get(Expressions.path(Long.class, ID)),
                    row.get(Expressions.path(String.class, LOCAL_ID)),
                    type,
                    row.get(Expressions.path(Instant.class, EVENT_DATE)),
                    row.get(Expressions.path(Instant.class, DATE_RECEIVED)),
                    isElectronic,
                    false,
                    new ArrayList<>(),
                    descriptions);
        } else {
            return null;
        }
    }

    FacilityProvider toOrderingProvider(Tuple row) {
        String name = Arrays.asList(
                row.get(PERSON_NAME.nmPrefix),
                row.get(PERSON_NAME.firstNm),
                row.get(PERSON_NAME.lastNm),
                row.get(PERSON_NAME.nmSuffix) != null ? row.get(PERSON_NAME.nmSuffix).display() : null)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
        return new FacilityProvider("Ordering provider", name);
    }

    FacilityProvider toReportingFacility(Tuple row) {
        return new FacilityProvider(
                "Reporting facility",
                row.get(ORGANIZATION.displayNm));
    }

    Description toDescription(Tuple row) {
        return new Description(row.get(OBSERVATION_2.cdDescTxt), row.get(OBS_VALUE_CODED.displayName));
    }
}
