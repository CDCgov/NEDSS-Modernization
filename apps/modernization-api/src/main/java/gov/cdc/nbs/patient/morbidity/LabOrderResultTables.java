package gov.cdc.nbs.patient.morbidity;

import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObsValueNumeric;
import gov.cdc.nbs.entity.odse.QObsValueTxt;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QLabResult;
import gov.cdc.nbs.entity.srte.QLabTest;

record LabOrderResultTables(
    QLabTest labTest,

    QCodeValueGeneral status,
    QObsValueCoded codedResult,
    QLabResult codedLabResult,
    QObsValueNumeric numericResult,
    QObsValueTxt textResult
) {

}
