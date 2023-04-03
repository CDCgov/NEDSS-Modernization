package gov.cdc.nbs.patient.morbidity;

import gov.cdc.nbs.entity.odse.QActRelationship;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObsValueNumeric;
import gov.cdc.nbs.entity.odse.QObsValueTxt;
import gov.cdc.nbs.entity.odse.QObservation;
import gov.cdc.nbs.entity.odse.QParticipation;
import gov.cdc.nbs.entity.odse.QPerson;
import gov.cdc.nbs.entity.odse.QPersonName;
import gov.cdc.nbs.entity.odse.QPublicHealthCase;
import gov.cdc.nbs.entity.odse.QTreatment;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QConditionCode;
import gov.cdc.nbs.entity.srte.QJurisdictionCode;
import gov.cdc.nbs.entity.srte.QLabResult;
import gov.cdc.nbs.entity.srte.QLabTest;

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

    private final QActRelationship morbidityLabReport;
    private final QObservation order;
    private final QActRelationship labReportComponents;
    private final QObservation labResult;
    private final LabOrderResultTables labOrderResults;

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

    public QActRelationship morbidityLabReport() {
        return morbidityLabReport;
    }

    public QObservation order() {
        return order;
    }

    public QActRelationship labReportComponents() {
        return labReportComponents;
    }

    public QObservation labResult() {
        return labResult;
    }

    public LabOrderResultTables labOrderResults() {
        return labOrderResults;
    }

    PatientMorbidityTables() {
        morbidity = new QObservation("morbidity");
        jurisdiction = new QJurisdictionCode("jurisdiction");
        subjectOfMorbidity = new QParticipation("subject_of_morbidity");
        subject = new QPerson("subject");
        condition = new QConditionCode("condition");
        morbidityProvider = new QParticipation("morbidity_provider");
        provider = new QPersonName("provider_name");

        //  associated with investigation
        caseOfMorbidity = new QActRelationship("case_of_morbidity");
        investigation = new QPublicHealthCase("investigation");
        investigationCondition = new QConditionCode("investigation_condition");

        //  treatment
        treatmentOfMorbidity = new QActRelationship("treatment_of_morbidity");
        treatment = new QTreatment("treatment");

        //  lab result
        morbidityLabReport = new QActRelationship("morbidity_lab_report");
        order = new QObservation("lab_order");
        labReportComponents = new QActRelationship("lab_result_components");
        labResult = new QObservation("lab_report");

        labOrderResults = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );


    }
}
