package gov.cdc.nbs.patient.profile.gender;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Gender;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientGenderTupleMapperTest {

    @Test
    void should_map_general_information_from_tuple() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().asOfDateSex)).thenReturn(LocalDate.parse("2023-01-17"));
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().additionalGenderCd)).thenReturn("additional-gender");

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        PatientGender actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17");


        assertThat(actual.current()).isNull();
        assertThat(actual.birth()).isNull();
        assertThat(actual.unknownReason()).isNull();
        assertThat(actual.preferred()).isNull();

        assertThat(actual.additional()).isEqualTo("additional-gender");
    }

    @Test
    void should_map_general_information_from_tuple_with_birth_gender() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().birthGenderCd)).thenReturn(Gender.F);

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        PatientGender actual = mapper.map(tuple);


        assertThat(actual.birth().id()).isEqualTo("F");
        assertThat(actual.birth().description()).isEqualTo("Female");

    }

    @Test
    void should_map_general_information_from_tuple_with_gender_unknown() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().sexUnkReasonCd)).thenReturn("unknown-reason-id");
        when(tuple.get(tables.unknownGenderReason().codeShortDescTxt)).thenReturn("unknown-reason-description");

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        PatientGender actual = mapper.map(tuple);

        assertThat(actual.unknownReason().id()).isEqualTo("unknown-reason-id");
        assertThat(actual.unknownReason().description()).isEqualTo("unknown-reason-description");

    }

    @Test
    void should_map_general_information_from_tuple_with_current_gender() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().currSexCd)).thenReturn(Gender.U);

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        PatientGender actual = mapper.map(tuple);


        assertThat(actual.current().id()).isEqualTo("U");
        assertThat(actual.current().description()).isEqualTo("Unknown");

    }

    @Test
    void should_map_general_information_from_tuple_with_preferred_gender() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().preferredGenderCd)).thenReturn("preferred-gender-id");
        when(tuple.get(tables.preferred().codeShortDescTxt)).thenReturn("preferred-gender-description");

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        PatientGender actual = mapper.map(tuple);


        assertThat(actual.preferred().id()).isEqualTo("preferred-gender-id");
        assertThat(actual.preferred().description()).isEqualTo("preferred-gender-description");

    }


    @Test
    void should_not_map_gender_from_tuple_without_identifier() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_gender_from_tuple_without_patient() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_gender_from_tuple_without_version() {
        PatientGenderTupleMapper.Tables tables = new PatientGenderTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

        PatientGenderTupleMapper mapper = new PatientGenderTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }

}
