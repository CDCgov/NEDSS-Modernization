package gov.cdc.nbs.patient.profile.general;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Indicator;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientGeneralTupleMapperTest {

    @Test
    void should_map_general_from_tuple() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.patient().asOfDateGeneral)).thenReturn(Instant.parse("2023-01-17T22:54:43Z"));

        when(tuple.get(tables.patient().mothersMaidenNm)).thenReturn("maternal-maiden-name");

        when(tuple.get(tables.patient().adultsInHouseNbr)).thenReturn((short) 73);
        when(tuple.get(tables.patient().childrenInHouseNbr)).thenReturn((short) 129);

        when(tuple.get(tables.patient().eharsId)).thenReturn("state-HIV-case");

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        PatientGeneral actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17T22:54:43Z");

        assertThat(actual.maritalStatus()).isNull();

        assertThat(actual.maternalMaidenName()).isEqualTo("maternal-maiden-name");
        assertThat(actual.adultsInHouse()).isEqualTo((short) 73);
        assertThat(actual.childrenInHouse()).isEqualTo((short) 129);

        assertThat(actual.occupation()).isNull();
        assertThat(actual.educationLevel()).isNull();
        assertThat(actual.primaryLanguage()).isNull();
        assertThat(actual.speaksEnglish()).isNull();

        assertThat(actual.stateHIVCase()).isEqualTo("state-HIV-case");

    }

    @Test
    void should_map_general_from_tuple_with_marital_status() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.maritalStatus().id.code)).thenReturn("marital-status-id");
        when(tuple.get(tables.maritalStatus().codeShortDescTxt)).thenReturn("marital-status-description");

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        PatientGeneral actual = mapper.map(tuple);

        assertThat(actual.maritalStatus().id()).isEqualTo("marital-status-id");
        assertThat(actual.maritalStatus().description()).isEqualTo("marital-status-description");

    }

    @Test
    void should_map_general_from_tuple_with_occupation() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.occupation().id)).thenReturn("occupation-id");
        when(tuple.get(tables.occupation().codeShortDescTxt)).thenReturn("occupation-description");

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        PatientGeneral actual = mapper.map(tuple);

        assertThat(actual.occupation().id()).isEqualTo("occupation-id");
        assertThat(actual.occupation().description()).isEqualTo("occupation-description");

    }

    @Test
    void should_map_general_from_tuple_with_education_level() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.education().id.code)).thenReturn("education-level-id");
        when(tuple.get(tables.education().codeShortDescTxt)).thenReturn("education-level-description");

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        PatientGeneral actual = mapper.map(tuple);

        assertThat(actual.educationLevel().id()).isEqualTo("education-level-id");
        assertThat(actual.educationLevel().description()).isEqualTo("education-level-description");

    }

    @Test
    void should_map_general_from_tuple_with_speaks_english() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.patient().speaksEnglishCd)).thenReturn("Y");

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        PatientGeneral actual = mapper.map(tuple);

        assertThat(actual.speaksEnglish()).isEqualTo(Indicator.YES);

    }

    @Test
    void should_not_map_general_from_tuple_without_identifier() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_general_from_tuple_without_patient() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_general_from_tuple_without_version() {
        PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

        PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }
}
