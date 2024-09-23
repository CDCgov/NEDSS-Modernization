package gov.cdc.nbs.patient.profile.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.message.enums.Suffix;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientSummaryTupleMapperTest {

  @Test
  void should_map_patient_summary_from_tuple() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.patient().birthTime)).thenReturn(LocalDateTime.parse("2001-07-07T00:00:00"));

    when(tuple.get(tables.ethnicity().codeShortDescTxt)).thenReturn("summary-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    PatientSummary actual = mapper.map(Instant.now(), tuple);

    assertThat(actual.patient()).isEqualTo(113L);

    assertThat(actual.legalName()).isNull();
    assertThat(actual.gender()).isNull();

    assertThat(actual.birthday()).isEqualTo("2001-07-07");

    assertThat(actual.ethnicity()).isEqualTo("summary-value");

    assertThat(actual.phone()).isEmpty();
    assertThat(actual.email()).isEmpty();
  }

  @Test
  void should_map_patient_summary_from_tuple_with_name() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.prefix().codeShortDescTxt)).thenReturn("prefix-name-value");
    when(tuple.get(tables.name().firstNm)).thenReturn("first-name-value");
    when(tuple.get(tables.name().middleNm)).thenReturn("middle-name-value");
    when(tuple.get(tables.name().lastNm)).thenReturn("last-name-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary.Name actual = mapper.map(Instant.now(), tuple).legalName();

    assertThat(actual.prefix()).isEqualTo("prefix-name-value");
    assertThat(actual.first()).isEqualTo("first-name-value");
    assertThat(actual.middle()).isEqualTo("middle-name-value");
    assertThat(actual.last()).isEqualTo("last-name-value");
    assertThat(actual.suffix()).isNull();
  }

  @Test
  void should_map_patient_summary_name_from_tuple_with_at_least_prefix() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.prefix().codeShortDescTxt)).thenReturn("prefix-name-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary.Name actual = mapper.map(Instant.now(), tuple).legalName();

    assertThat(actual.prefix()).isEqualTo("prefix-name-value");
  }

  @Test
  void should_map_patient_summary_name_from_tuple_with_at_least_first() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.name().firstNm)).thenReturn("first-name-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary.Name actual = mapper.map(Instant.now(), tuple).legalName();

    assertThat(actual.first()).isEqualTo("first-name-value");
  }

  @Test
  void should_map_patient_summary_name_from_tuple_with_at_least_middle() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.name().middleNm)).thenReturn("middle-name-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary.Name actual = mapper.map(Instant.now(), tuple).legalName();

    assertThat(actual.middle()).isEqualTo("middle-name-value");
  }

  @Test
  void should_map_patient_summary_name_from_tuple_with_at_least_last() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.name().lastNm)).thenReturn("last-name-value");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary.Name actual = mapper.map(Instant.now(), tuple).legalName();

    assertThat(actual.last()).isEqualTo("last-name-value");
  }

  @Test
  void should_map_patient_summary_name_from_tuple_with_at_least_suffix() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);
    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.name().nmSuffix)).thenReturn(Suffix.ESQ);

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary actual = mapper.map(Instant.now(), tuple);

    assertThat(actual.legalName().suffix()).isEqualTo("Esquire");
  }

  @Test
  void should_map_patient_summary_from_tuple_with_gender() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.patient().currSexCd)).thenReturn(Gender.M);

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);



    PatientSummary actual = mapper.map(Instant.now(), tuple);

    assertThat(actual.gender()).isEqualTo("Male");
  }

  @Test
  void should_map_patient_summary_with_age_calculated_from_birthday() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.patient().birthTime)).thenReturn(LocalDateTime.parse("2001-07-07T00:00:00"));

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    PatientSummary actual = mapper.map(Instant.parse("2023-06-07T10:15:20Z"), tuple);

    assertThat(actual.birthday()).isEqualTo("2001-07-07");

    assertThat(actual.age()).isEqualTo(21);
  }

  @Test
  void should_map_patient_summary_with_age_when_birthday_not_present() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    PatientSummary actual = mapper.map(Instant.parse("2023-06-07T10:15:20Z"), tuple);

    assertThat(actual.age()).isNull();
  }

  @Test
  void should_map_patient_summary_from_tuple_with_phone() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.phoneUse().codeShortDescTxt)).thenReturn("phone-use");
    when(tuple.get(tables.phoneNumber().phoneNbrTxt)).thenReturn("phone-number");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    PatientSummary summary = mapper.map(Instant.now(), tuple);

    assertThat(summary.phone()).satisfiesExactly(
        actual -> assertAll(
            () -> assertThat(actual.use()).isEqualTo("phone-use"),
            () -> assertThat(actual.number()).isEqualTo("phone-number")
        )
    );
  }

  @Test
  void should_map_patient_summary_from_tuple_with_email() {

    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(tables.patient().personParentUid.id)).thenReturn(113L);

    when(tuple.get(tables.emailUse().codeShortDescTxt)).thenReturn("email-use");
    when(tuple.get(tables.email().emailAddress)).thenReturn("email-address");

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    PatientSummary summary = mapper.map(Instant.now(), tuple);

    assertThat(summary.email()).satisfiesExactly(
        actual -> assertAll(
            () -> assertThat(actual.use()).isEqualTo("email-use"),
            () -> assertThat(actual.address()).isEqualTo("email-address")
        )
    );


  }

  @Test
  void should_not_map_summary_from_tuple_without_patient() {
    PatientSummaryTupleMapper.Tables tables = new PatientSummaryTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    PatientSummaryTupleMapper mapper = new PatientSummaryTupleMapper(tables);

    assertThatThrownBy(() -> mapper.map(Instant.now(), tuple))
        .hasMessageContaining("patient is required");

  }
}
