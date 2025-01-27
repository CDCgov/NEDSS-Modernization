package gov.cdc.nbs.patient.profile.birth;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientBirthTupleMapperTest {

    @Test
    void should_map_birth_from_tuple() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().asOfDateSex)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().birthTime)).thenReturn(LocalDateTime.parse("1999-09-09T09:09:09"));

        when(tuple.get(tables.address().cityDescTxt)).thenReturn("birth-city");

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");

        assertThat(actual.bornOn()).isEqualTo("1999-09-09");

        assertThat(actual.multipleBirth()).isNull();
        assertThat(actual.birthOrder()).isNull();

        assertThat(actual.city()).isEqualTo("birth-city");

        assertThat(actual.state()).isNull();
        assertThat(actual.county()).isNull();
        assertThat(actual.country()).isNull();
    }

    @Test
    void should_map_patient_summary_with_age_calculated_from_birthday() {

        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.patient().birthTime)).thenReturn(LocalDateTime.parse("2001-07-07T00:00:00"));

        Clock clock = Clock.fixed(
            Instant.parse("2023-06-07T10:15:20Z"),
            ZoneId.of("UTC")
        );

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(clock, tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.bornOn()).isEqualTo("2001-07-07");

        assertThat(actual.age()).isEqualTo(21);
    }

    @Test
    void should_map_birth_from_tuple_with_multiple_birth() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().multipleBirthInd)).thenReturn("multiple-birth-id");
        when(tuple.get(tables.multipleBirth().codeShortDescTxt)).thenReturn("multiple-birth-description");

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.multipleBirth().id()).isEqualTo("multiple-birth-id");
        assertThat(actual.multipleBirth().description()).isEqualTo("multiple-birth-description");

    }

    @Test
    void should_map_birth_from_tuple_with_birth_order() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().birthOrderNbr)).thenReturn((short) 5);

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.birthOrder()).isEqualTo((short) 5);


    }

    @Test
    void should_map_birth_from_tuple_with_birth_state() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().stateCd)).thenReturn("state-id");
        when(tuple.get(tables.state().stateNm)).thenReturn("state-description");

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.state().id()).isEqualTo("state-id");
        assertThat(actual.state().description()).isEqualTo("state-description");

    }

    @Test
    void should_map_birth_from_tuple_with_birth_county() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().cntyCd)).thenReturn("county-id");
        when(tuple.get(tables.county().codeDescTxt)).thenReturn("county-description");

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.county().id()).isEqualTo("county-id");
        assertThat(actual.county().description()).isEqualTo("county-description");

    }

    @Test
    void should_map_birth_from_tuple_with_birth_country() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.address().cntryCd)).thenReturn("country-id");
        when(tuple.get(tables.country().codeDescTxt)).thenReturn("country-description");

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        PatientBirth actual = mapper.map(tuple);

        assertThat(actual.country().id()).isEqualTo("country-id");
        assertThat(actual.country().description()).isEqualTo("country-description");

    }

    @Test
    void should_not_map_birth_from_tuple_without_identifier() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_birth_from_tuple_without_patient() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_birth_from_tuple_without_version() {
        PatientBirthTupleMapper.Tables tables = new PatientBirthTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

        PatientBirthTupleMapper mapper = new PatientBirthTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }

}
