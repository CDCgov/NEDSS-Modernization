package gov.cdc.nbs.patient.morbidity;

import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.entity.srte.QJurisdictionCode;

class PatientMorbidityTables {
    private final QObservation morbidity;
    private final QJurisdictionCode jurisdiction;
    private final QParticipation subjectOfMorbidity;
    private final QConditionCode condition;
    private final QPerson subject;
    private final QActRelationship caseOfMorbidity;
    private final QPublicHealthCase investigation;
    private final QParticipation morbidityProvider;
    private final QPersonName provider;
    private final QConditionCode investigationCondition;
    private final QActRelationship treatmentOfMorbidity;
    private final QTreatment treatment;

    QObservation morbidity() {
        return morbidity;
    }

    QJurisdictionCode jurisdiction() {
        return jurisdiction;
    }

    QParticipation subjectOfMorbidity() {
        return subjectOfMorbidity;
    }

    QConditionCode condition() {
        return condition;
    }

    QPerson subject() {
        return subject;
    }

    QActRelationship caseOfMorbidity() {
        return caseOfMorbidity;
    }

    QPublicHealthCase investigation() {
        return investigation;
    }

    QParticipation morbidityProvider() {
        return morbidityProvider;
    }

    QPersonName provider() {
        return provider;
    }

    QConditionCode investigationCondition() {
        return investigationCondition;
    }

    QActRelationship treatmentOfMorbidity() {
        return treatmentOfMorbidity;
    }

    QTreatment treatment() {
        return treatment;
    }

    PatientMorbidityTables() {
        morbidity = new QObservation("morbidity");
        jurisdiction = new QJurisdictionCode("jurisdiction");
        subjectOfMorbidity = new QParticipation("subject_of_morbidity");
        condition = new QConditionCode("condition");
        subject = new QPerson("subject");
        caseOfMorbidity = new QActRelationship("case_of_morbidity");
        investigation = new QPublicHealthCase("investigation");
        morbidityProvider = new QParticipation("morbidity_provider");
        provider = new QPersonName("provider_name");
        investigationCondition = new QConditionCode("investigation_condition");
        treatmentOfMorbidity = new QActRelationship("treatment_of_morbidity");
        treatment = new QTreatment("treatment");
    }
}
