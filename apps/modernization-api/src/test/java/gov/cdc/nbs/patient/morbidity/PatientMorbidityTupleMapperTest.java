package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PatientMorbidityTupleMapperTest {

    @Test
    void should_map_patient_morbidity_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.morbidity().addTime)).thenReturn(Instant.parse("2023-01-15T17:19:21Z"));
        when(tuple.get(tables.morbidity().activityToTime)).thenReturn(Instant.parse("2023-02-07T02:03:29Z"));
        when(tuple.get(tables.morbidity().localId)).thenReturn("local-id-value");

        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("condition-value");
        when(tuple.get(tables.jurisdiction().codeShortDescTxt)).thenReturn("jurisdiction-value");

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity actual = mapper.map(tuple);

        assertThat(actual.morbidity()).isEqualTo(79L);
        assertThat(actual.receivedOn()).isEqualTo("2023-01-15T17:19:21Z");

        assertThat(actual.provider()).isEmpty();

        assertThat(actual.reportedOn()).isEqualTo("2023-02-07T02:03:29Z");

        assertThat(actual.condition()).isEqualTo("condition-value");

        assertThat(actual.jurisdiction()).isEqualTo("jurisdiction-value");

        assertThat(actual.event()).isEqualTo("local-id-value");

        assertThat(actual.associatedWith()).isNull();

        assertThat(actual.treatments()).isEmpty();
    }

    @Test
    void should_map_patient_morbidity_with_provider_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.morbidity().addTime)).thenReturn(Instant.parse("2023-01-15T17:19:21Z"));

        when(tuple.get(tables.provider().nmPrefix)).thenReturn("provider-prefix");
        when(tuple.get(tables.provider().firstNm)).thenReturn("provider-first-name");
        when(tuple.get(tables.provider().lastNm)).thenReturn("provider-last-name");
        when(tuple.get(tables.provider().nmSuffix)).thenReturn(Suffix.ESQ);

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity actual = mapper.map(tuple);

        assertThat(actual.provider()).isEqualTo(
            "provider-prefix provider-first-name provider-last-name ESQ");

    }

    @Test
    void should_map_patient_morbidity_with_associatedWith_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.investigation().id)).thenReturn(1019L);
        when(tuple.get(tables.investigation().localId)).thenReturn("investigation-local-id-value");

        when(tuple.get(tables.investigationCondition().conditionShortNm)).thenReturn("investigation-condition");

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity.Investigation actual = mapper.map(tuple).associatedWith();

        assertThat(actual.id()).isEqualTo(1019L);

        assertThat(actual.local()).isEqualTo("investigation-local-id-value");

    }

    @Test
    void should_map_patient_morbidity_with_treatment_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.treatment().cdDescTxt)).thenReturn("treatment-value");

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity actual = mapper.map(tuple);

        assertThat(actual.treatments()).containsExactly("treatment-value");
    }

    @Test
    void should_map_patient_morbidity_with_lab_order_result_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.labOrderResults().labTest().labTestDescTxt)).thenReturn("lab-test-value");
        when(tuple.get(tables.labOrderResults().codedLabResult().labResultDescTxt)).thenReturn("coded-result-value");
        when(tuple.get(tables.labOrderResults().numericResult().comparatorCd1)).thenReturn("=");
        when(tuple.get(tables.labOrderResults().numericResult().numericValue1)).thenReturn(new BigDecimal("2351"));
        when(tuple.get(tables.labOrderResults().textResult().valueTxt)).thenReturn("text-result-value");

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity morbidity = mapper.map(tuple);

        assertThat(morbidity.labResults()).satisfiesExactly(
            actual -> assertAll(
                () -> assertThat(actual.labTest()).isEqualTo("lab-test-value"),
                () -> assertThat(actual.codedResult()).isEqualTo("coded-result-value"),
                () -> assertThat(actual.numericResult()).isEqualTo("=2351"),
                () -> assertThat(actual.textResult()).isEqualTo("text-result-value")
            )
        );
    }
}
