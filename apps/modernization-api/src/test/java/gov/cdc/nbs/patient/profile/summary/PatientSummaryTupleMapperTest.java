package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientSummaryTupleMapperTest {

    @Test
    void should_map_patient_summary_from_tuple() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().birthTime)).thenReturn(Instant.parse("2001-07-07T00:00:00Z"));

        when(tuple.get(tables.ethnicity().codeShortDescTxt)).thenReturn("ethnicity-value");

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.gender()).isNull();

        assertThat(actual.birthday()).isEqualTo("2001-07-07");

        assertThat(actual.ethnicity()).isEqualTo("ethnicity-value");
        assertThat(actual.race()).isNull();
    }

    @Test
    void should_map_patient_summary_from_tuple_with_name() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.prefix().codeShortDescTxt)).thenReturn("prefix-name-value");
        when(tuple.get(tables.name().firstNm)).thenReturn("first-name-value");
        when(tuple.get(tables.name().middleNm)).thenReturn("middle-name-value");
        when(tuple.get(tables.name().lastNm)).thenReturn("last-name-value");

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

        PatientSummary.Name actual = mapper.map(tuple).legalName();

        assertThat(actual.prefix()).isEqualTo("prefix-name-value");
        assertThat(actual.first()).isEqualTo("first-name-value");
        assertThat(actual.middle()).isEqualTo("middle-name-value");
        assertThat(actual.last()).isEqualTo("last-name-value");
        assertThat(actual.suffix()).isNull();
    }

    @Test
    void should_map_patient_summary_from_tuple_with_suffix() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.legalName().suffix()).isEqualTo("Esquire");
    }

    @Test
    void should_map_patient_summary_from_tuple_with_gender() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().currSexCd)).thenReturn(Gender.M);

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.gender()).isEqualTo("Male");
    }

    @Test
    void should_map_patient_summary_from_tuple_with_race() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.race().codeShortDescTxt)).thenReturn("race-value");

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.race()).isEqualTo("race-value");
    }

    @Test
    void should_map_patient_summary_with_age_calculated_from_birthday() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);


        when(tuple.get(tables.patient().birthTime)).thenReturn(Instant.parse("2001-07-07T00:00:00Z"));

        Clock clock = Clock.fixed(
            Instant.parse("2023-06-07T10:15:20Z"),
            ZoneId.of("UTC")
        );

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(clock, tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.birthday()).isEqualTo("2001-07-07");

        assertThat(actual.age()).isEqualTo(21);
    }

    @Test
    void should_map_patient_summary_with_age_when_birthday_not_present() {

        PatientSummaryTables tables = new PatientSummaryTables();

        Tuple tuple = mock(Tuple.class);

        Clock clock = Clock.fixed(
            Instant.parse("2023-06-07T10:15:20Z"),
            ZoneId.of("UTC")
        );

        PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(clock, tables);

        PatientSummary actual = mapper.map(tuple);

        assertThat(actual.age()).isNull();
    }
}
