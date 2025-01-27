package gov.cdc.nbs.patient.profile.names;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientNameTupleMapperTest {

  @Test
  void should_map_person_name_from_tuple() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    when(tuple.get(tables.name().firstNm)).thenReturn("first-name-value");
    when(tuple.get(tables.name().middleNm)).thenReturn("middle-name-value");
    when(tuple.get(tables.name().middleNm2)).thenReturn("second-middle-name-value");
    when(tuple.get(tables.name().lastNm)).thenReturn("last-name-value");
    when(tuple.get(tables.name().lastNm2)).thenReturn("second-last-name-value");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    PatientName actual = mapper.map(tuple);

    assertThat(actual.patient()).isEqualTo(433L);
    assertThat(actual.version()).isEqualTo((short) 227);
    assertThat(actual.asOf()).isEqualTo("2023-01-17");
    assertThat(actual.sequence()).isEqualTo(5);

    assertThat(actual.use().id()).isEqualTo("use-code-patient");
    assertThat(actual.use().description()).isEqualTo("use-code-description");

    assertThat(actual.first()).isEqualTo("first-name-value");
    assertThat(actual.middle()).isEqualTo("middle-name-value");
    assertThat(actual.secondMiddle()).isEqualTo("second-middle-name-value");
    assertThat(actual.last()).isEqualTo("last-name-value");
    assertThat(actual.secondLast()).isEqualTo("second-last-name-value");

    assertThat(actual.prefix()).isNull();
    assertThat(actual.suffix()).isNull();
    assertThat(actual.degree()).isNull();
  }

  @Test
  void should_map_person_name_from_tuple_with_prefix() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    when(tuple.get(tables.name().nmPrefix)).thenReturn("prefix-patient");
    when(tuple.get(tables.prefix().codeShortDescTxt)).thenReturn("prefix-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    PatientName.Prefix actual = mapper.map(tuple).prefix();

    assertThat(actual.id()).isEqualTo("prefix-patient");
    assertThat(actual.description()).isEqualTo("prefix-description");

  }

  @Test
  void should_map_person_name_from_tuple_with_suffix() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.JR);

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    PatientName.Suffix actual = mapper.map(tuple).suffix();

    assertThat(actual.id()).isEqualTo("JR");
    assertThat(actual.description()).isEqualTo("Jr.");

  }

  @Test
  void should_map_person_name_from_tuple_with_degree() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    when(tuple.get(tables.name().nmDegree)).thenReturn("degree-patient");
    when(tuple.get(tables.degree().codeDescTxt)).thenReturn("degree-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    PatientName.Degree actual = mapper.map(tuple).degree();

    assertThat(actual.id()).isEqualTo("degree-patient");
    assertThat(actual.description()).isEqualTo("degree-description");

  }

  @Test
  void should_not_map_person_name_from_tuple_without_identifier() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("identifier is required");

  }

  @Test
  void should_not_map_person_name_from_tuple_without_version() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("version is required");

  }

  @Test
  void should_not_map_person_name_from_tuple_without_as_of() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);
    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("as of is required");

  }

  @Test
  void should_not_map_person_name_from_tuple_without_sequence() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));

    when(tuple.get(tables.name().nmUseCd)).thenReturn("use-code-patient");
    when(tuple.get(tables.use().codeShortDescTxt)).thenReturn("use-code-description");

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("sequence is required");

  }

  @Test
  void should_not_map_person_name_from_tuple_without_use() {

    PatientNameTupleMapper.Tables tables = new PatientNameTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.name().id.personUid)).thenReturn(433L);
    when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
    when(tuple.get(tables.name().asOfDate)).thenReturn(LocalDate.parse("2023-01-17"));
    when(tuple.get(tables.name().id.personNameSeq)).thenReturn((short) 5);

    PatientNameTupleMapper mapper = new PatientNameTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(tuple)).hasMessageContaining("use is required");

  }
}
