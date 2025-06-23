package gov.cdc.nbs.patient.profile.general;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.data.sensitive.SensitiveValue;
import gov.cdc.nbs.data.sensitive.SensitiveValueResolver;
import gov.cdc.nbs.message.enums.Indicator;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientGeneralTupleMapperTest {

  @Test
  void should_map_general_from_tuple() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.patient().generalInformation.asOf)).thenReturn(LocalDate.parse("2023-01-17"));

    when(tuple.get(tables.patient().generalInformation.mothersMaidenName)).thenReturn("maternal-maiden-name");

    when(tuple.get(tables.patient().generalInformation.adultsInHouse)).thenReturn(73);
    when(tuple.get(tables.patient().generalInformation.childrenInHouse)).thenReturn(129);

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    PatientGeneral actual = mapper.map(tuple);

    assertThat(actual.patient()).isEqualTo(2357L);
    assertThat(actual.id()).isEqualTo(433L);
    assertThat(actual.version()).isEqualTo((short) 227);
    assertThat(actual.asOf()).isEqualTo("2023-01-17");

    assertThat(actual.maritalStatus()).isNull();

    assertThat(actual.maternalMaidenName()).isEqualTo("maternal-maiden-name");
    assertThat(actual.adultsInHouse()).isEqualTo(73);
    assertThat(actual.childrenInHouse()).isEqualTo(129);

    assertThat(actual.occupation()).isNull();
    assertThat(actual.educationLevel()).isNull();
    assertThat(actual.primaryLanguage()).isNull();
    assertThat(actual.speaksEnglish()).isNull();

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

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

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

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

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

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    PatientGeneral actual = mapper.map(tuple);

    assertThat(actual.educationLevel().id()).isEqualTo("education-level-id");
    assertThat(actual.educationLevel().description()).isEqualTo("education-level-description");

  }

  @Test
  void should_map_general_from_tuple_with_primary_language() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

    when(tuple.get(tables.language().id)).thenReturn("language-id");
    when(tuple.get(tables.language().codeShortDescTxt)).thenReturn("language-description");

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    PatientGeneral actual = mapper.map(tuple);

    assertThat(actual.primaryLanguage().id()).isEqualTo("language-id");
    assertThat(actual.primaryLanguage().description()).isEqualTo("language-description");

  }

  @Test
  void should_map_general_from_tuple_with_speaks_english() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

    when(tuple.get(tables.patient().generalInformation.speaksEnglish)).thenReturn("Y");

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    PatientGeneral actual = mapper.map(tuple);

    assertThat(actual.speaksEnglish()).isEqualTo(Indicator.YES);

  }

  @Test
  void should_not_map_general_from_tuple_without_identifier() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);
    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("identifier is required");

  }

  @Test
  void should_not_map_general_from_tuple_without_patient() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("patient is required");

  }

  @Test
  void should_not_map_general_from_tuple_without_version() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);

    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    PatientGeneralTupleMapper mapper = new PatientGeneralTupleMapper(tables, resolver);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("version is required");

  }

  @Test
  void should_map_general_from_tuple_with_state_HIV_case_protected_by_HIV_access() {
    PatientGeneralTupleMapper.Tables tables = new PatientGeneralTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
    when(tuple.get(tables.patient().id)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

    when(tuple.get(tables.patient().generalInformation.stateHIVCase)).thenReturn("state-HIV-case");



    SensitiveValueResolver resolver = mock(SensitiveValueResolver.class);

    SensitiveValue.Allowed<Object> allowed = new SensitiveValue.Allowed<>("allowed-value");
    when(resolver.resolve(any(), any())).thenReturn(allowed);


    SensitiveValue actual = new PatientGeneralTupleMapper(tables, resolver)
        .map(tuple)
        .stateHIVCase();

    assertThat(actual).isSameAs(allowed);

    ArgumentCaptor<Permission> captor = ArgumentCaptor.forClass(Permission.class);

    verify(resolver).resolve(captor.capture(), eq("state-HIV-case"));

    Permission permission = captor.getValue();

    assertThat(permission)
        .returns("HIVQuestions", Permission::operation)
        .returns("Global", Permission::object);

  }
}
