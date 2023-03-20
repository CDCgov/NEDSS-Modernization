package gov.cdc.nbs.patient.morbidity;

import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
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

    public QObservation morbidity() {
        return morbidity;
    }

    public QJurisdictionCode jurisdiction() {
        return jurisdiction;
    }

    public QParticipation subjectOfMorbidity() {
        return subjectOfMorbidity;
    }

    public QConditionCode condition() {
        return condition;
    }

    public QPerson subject() {
        return subject;
    }

    public QActRelationship caseOfMorbidity() {
        return caseOfMorbidity;
    }

    public QPublicHealthCase investigation() {
        return investigation;
    }

    public QParticipation morbidityProvider() {
        return morbidityProvider;
    }

    public QPersonName provider() {
        return provider;
    }

    public QConditionCode investigationCondition() {
        return investigationCondition;
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
    }
}
