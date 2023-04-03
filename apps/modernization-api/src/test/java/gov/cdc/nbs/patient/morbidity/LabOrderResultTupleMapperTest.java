package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QObsValueCoded;
import gov.cdc.nbs.entity.odse.QObsValueNumeric;
import gov.cdc.nbs.entity.odse.QObsValueTxt;
import gov.cdc.nbs.entity.srte.QCodeValueGeneral;
import gov.cdc.nbs.entity.srte.QLabResult;
import gov.cdc.nbs.entity.srte.QLabTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LabOrderResultTupleMapperTest {

    @Test
    void should_map_lab_order_result_from_tuple() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.labTest().labTestDescTxt)).thenReturn("lab-test-value");
        when(tuple.get(tables.status().codeShortDescTxt)).thenReturn("status-value");

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.labTest()).isEqualTo("lab-test-value");
        assertThat(actual.status()).isEqualTo("status-value");
        assertThat(actual.codedResult()).isNull();
        assertThat(actual.numericResult()).isNull();
        assertThat(actual.textResult()).isNull();
    }

    @Test
    void should_map_lab_order_result_with_coded_value_from_tuple() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.codedLabResult().labResultDescTxt)).thenReturn("coded-result-value");

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.codedResult()).isEqualTo("coded-result-value");
    }

    @Test
    void should_map_lab_order_result_with_numeric_result_from_tuple() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.numericResult().comparatorCd1)).thenReturn("=");
        when(tuple.get(tables.numericResult().numericValue1)).thenReturn(new BigDecimal("2351"));

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.numericResult()).isEqualTo("=2351");
    }

    @Test
    void should_not_map_lab_order_result_with_numeric_result_without_comparator() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.numericResult().numericValue1)).thenReturn(new BigDecimal("2351"));

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.numericResult()).isNull();
    }

    @Test
    void should_not_map_lab_order_result_with_numeric_result_without_numeric_value() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.numericResult().comparatorCd1)).thenReturn("=");

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.numericResult()).isNull();
    }

    @Test
    void should_map_lab_order_result_with_text_result_from_tuple() {

        LabOrderResultTables tables = new LabOrderResultTables(
            new QLabTest("lab_test"),
            new QCodeValueGeneral("lab_result_status"),
            new QObsValueCoded("coded_result"),
            new QLabResult("lab_result"),
            new QObsValueNumeric("numeric_result"),
            new QObsValueTxt("text_result")
        );

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.textResult().valueTxt)).thenReturn("text-result-value");

        LabOrderResultTupleMapper mapper = new LabOrderResultTupleMapper(tables);

        PatientMorbidity.LabOrderResult actual = mapper.map(tuple);

        assertThat(actual.textResult()).isEqualTo("text-result-value");
    }
}
