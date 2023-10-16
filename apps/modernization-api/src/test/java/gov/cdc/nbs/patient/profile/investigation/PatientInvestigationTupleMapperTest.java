package gov.cdc.nbs.patient.profile.investigation;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientInvestigationTupleMapperTest {

    @Test
    void should_map_investigation_from_tuple() {
        PatientInvestigationTupleMapper.Tables tables = new PatientInvestigationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(1787L);
        when(tuple.get(tables.investigation().activityFromTime)).thenReturn(Instant.parse("2022-06-07T12:01:47Z"));

        when(tuple.get(tables.condition().conditionShortNm)).thenReturn("condition");

        when(tuple.get(tables.status().codeShortDescTxt)).thenReturn("status");

        when(tuple.get(tables.caseStatus().codeShortDescTxt)).thenReturn("case-status");

        when(tuple.get(tables.jurisdiction().codeShortDescTxt)).thenReturn("jurisdiction");

        when(tuple.get(tables.investigation().localId)).thenReturn("event");

        when(tuple.get(tables.investigation().coinfectionId)).thenReturn("co-infection");

        when(tuple.get(tables.notificationStatus().codeShortDescTxt)).thenReturn("notification-status");

        when(tuple.get(tables.investigator().firstNm)).thenReturn("investi");
        when(tuple.get(tables.investigator().lastNm)).thenReturn("gator");

        PatientInvestigationTupleMapper mapper = new PatientInvestigationTupleMapper(tables);

        PatientInvestigation actual = mapper.map(tuple);

        assertThat(actual.investigation()).isEqualTo(1787L);
        assertThat(actual.startedOn()).isEqualTo("2022-06-07");
        assertThat(actual.condition()).isEqualTo("condition");
        assertThat(actual.status()).isEqualTo("status");
        assertThat(actual.caseStatus()).isEqualTo("case-status");
        assertThat(actual.jurisdiction()).isEqualTo("jurisdiction");
        assertThat(actual.event()).isEqualTo("event");
        assertThat(actual.coInfection()).isEqualTo("co-infection");
        assertThat(actual.notification()).isEqualTo("notification-status");
        assertThat(actual.investigator()).isEqualTo("investi gator");
    }

    @Test
    void should_resolve_a_page_builder_investigation_as_comparable() {
        PatientInvestigationTupleMapper.Tables tables = new PatientInvestigationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(1787L);

        when(tuple.get(tables.condition().investigationFormCd)).thenReturn("pg_builder");

        PatientInvestigationTupleMapper mapper = new PatientInvestigationTupleMapper(tables);

        PatientInvestigation actual = mapper.map(tuple);

        assertThat(actual.comparable()).isTrue();
    }

    @Test
    void should_resolve_a_non_page_builder_investigation_as_not_comparable() {
        PatientInvestigationTupleMapper.Tables tables = new PatientInvestigationTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.investigation().id)).thenReturn(1787L);

        when(tuple.get(tables.condition().investigationFormCd)).thenReturn("inv_builder");

        PatientInvestigationTupleMapper mapper = new PatientInvestigationTupleMapper(tables);

        PatientInvestigation actual = mapper.map(tuple);

        assertThat(actual.comparable()).isFalse();
    }

    @Test
    void should_not_map_address_from_tuple_without_identifier() {
        PatientInvestigationTupleMapper.Tables tables = new PatientInvestigationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        PatientInvestigationTupleMapper mapper = new PatientInvestigationTupleMapper(tables);


        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }
}
