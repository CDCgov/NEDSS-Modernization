package gov.cdc.nbs.patient.profile.phone;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientPhoneTupleMapperTest {

  @Test
  void should_map_phone_from_tuple() {
    PatientPhoneTupleMapper.Tables tables = new PatientPhoneTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().id)).thenReturn(59L);
    when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
    when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);
    when(tuple.get(tables.locators().asOfDate)).thenReturn(LocalDate.parse("1993-11-09"));
    when(tuple.get(tables.locators().cd)).thenReturn("type-id");
    when(tuple.get(tables.type().codeShortDescTxt)).thenReturn("type-description");
    when(tuple.get(tables.locators().useCd)).thenReturn("use-id");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-description");

    when(tuple.get(tables.phoneNumber().cntryCd)).thenReturn("country-code");
    when(tuple.get(tables.phoneNumber().phoneNbrTxt)).thenReturn("number");
    when(tuple.get(tables.phoneNumber().extensionTxt)).thenReturn("extension");

    when(tuple.get(tables.locators().locatorDescTxt)).thenReturn("comment");

    PatientPhoneTupleMapper mapper = new PatientPhoneTupleMapper(tables);

    PatientPhone actual = mapper.map(tuple);

    assertThat(actual.patient()).isEqualTo(59L);
    assertThat(actual.id()).isEqualTo(157L);
    assertThat(actual.version()).isEqualTo((short) 269);

    assertThat(actual.asOf()).isEqualTo("1993-11-09");

    assertThat(actual.type().id()).isEqualTo("type-id");
    assertThat(actual.type().description()).isEqualTo("type-description");

    assertThat(actual.use().id()).isEqualTo("use-id");
    assertThat(actual.use().description()).isEqualTo("use-description");

    assertThat(actual.countryCode()).isEqualTo("country-code");
    assertThat(actual.number()).isEqualTo("number");
    assertThat(actual.extension()).isEqualTo("extension");

    assertThat(actual.comment()).isEqualTo("comment");

    assertThat(actual.email()).isNull();
    assertThat(actual.url()).isNull();
  }

  @Test
  void should_map_email_from_tuple() {
    PatientPhoneTupleMapper.Tables tables = new PatientPhoneTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().id)).thenReturn(59L);
    when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
    when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);
    when(tuple.get(tables.locators().asOfDate)).thenReturn(LocalDate.parse("1993-11-09"));
    when(tuple.get(tables.locators().cd)).thenReturn("type-id");
    when(tuple.get(tables.type().codeShortDescTxt)).thenReturn("type-description");
    when(tuple.get(tables.locators().useCd)).thenReturn("use-id");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-description");

    when(tuple.get(tables.phoneNumber().emailAddress)).thenReturn("email");
    when(tuple.get(tables.phoneNumber().urlAddress)).thenReturn("url");

    PatientPhoneTupleMapper mapper = new PatientPhoneTupleMapper(tables);

    PatientPhone actual = mapper.map(tuple);

    assertThat(actual.email()).isEqualTo("email");
    assertThat(actual.url()).isEqualTo("url");

    assertThat(actual.countryCode()).isNull();
    assertThat(actual.number()).isNull();
    assertThat(actual.extension()).isNull();

    assertThat(actual.comment()).isNull();

  }


  @Test
  void should_not_map_phone_from_tuple_without_identifier() {
    PatientPhoneTupleMapper.Tables tables = new PatientPhoneTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().id)).thenReturn(59L);
    when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

    PatientPhoneTupleMapper mapper = new PatientPhoneTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("identifier is required");

  }

  @Test
  void should_not_map_phone_from_tuple_without_patient() {
    PatientPhoneTupleMapper.Tables tables = new PatientPhoneTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);
    when(tuple.get(tables.locators().versionCtrlNbr)).thenReturn((short) 269);

    PatientPhoneTupleMapper mapper = new PatientPhoneTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("patient is required");

  }

  @Test
  void should_not_map_phone_from_tuple_without_version() {
    PatientPhoneTupleMapper.Tables tables = new PatientPhoneTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().id)).thenReturn(59L);
    when(tuple.get(tables.locators().id.locatorUid)).thenReturn(157L);

    PatientPhoneTupleMapper mapper = new PatientPhoneTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("version is required");

  }
}
