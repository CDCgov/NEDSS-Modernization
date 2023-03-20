package gov.cdc.nbs.patient.morbidity;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
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

        when(tuple.get(tables.provider().nmPrefix)).thenReturn("provider-prefix");
        when(tuple.get(tables.provider().firstNm)).thenReturn("provider-first-name");
        when(tuple.get(tables.provider().lastNm)).thenReturn("provider-last-name");
        when(tuple.get(tables.provider().nmSuffix)).thenReturn(Suffix.ESQ);

        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("condition-value");
        when(tuple.get(tables.jurisdiction().codeShortDescTxt)).thenReturn("jurisdiction-value");

        when(tuple.get(tables.investigation().id)).thenReturn(1019L);
        when(tuple.get(tables.investigation().localId)).thenReturn("investigation-local-id-value");

        when(tuple.get(tables.investigationCondition().conditionShortNm)).thenReturn("investigation-condition");

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity actual = mapper.map(tuple);

        assertThat(actual.morbidity()).isEqualTo(79L);
        assertThat(actual.receivedOn()).isEqualTo("2023-01-15T17:19:21Z");

        assertThat(actual.provider()).isEqualTo(
            "provider-prefix provider-first-name provider-last-name ESQ");

        assertThat(actual.reportedOn()).isEqualTo("2023-02-07T02:03:29Z");

        assertThat(actual.condition()).isEqualTo("condition-value");

        assertThat(actual.jurisdiction()).isEqualTo("jurisdiction-value");

        assertThat(actual.event()).isEqualTo("local-id-value");

        PatientMorbidity.Investigation actual_associated_with = actual.associatedWith();

        assertThat(actual_associated_with.id()).isEqualTo(1019L);

        assertThat(actual_associated_with.local()).isEqualTo("investigation-local-id-value");

    }

    @Test
    void should_map_patient_morbidity_without_provider_from_tuple() {

        PatientMorbidityTables tables = new PatientMorbidityTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.morbidity().id)).thenReturn(79L);

        when(tuple.get(tables.morbidity().addTime)).thenReturn(Instant.parse("2023-01-15T17:19:21Z"));

        PatientMorbidityTupleMapper mapper = new PatientMorbidityTupleMapper(tables);

        PatientMorbidity actual = mapper.map(tuple);

        assertThat(actual.provider()).isEmpty();

    }

    @Test
    void should_map_patient_morbidity_without_associatedWith_from_tuple() {

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

        assertThat(actual.associatedWith()).isNull();

    }
}
