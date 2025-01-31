package gov.cdc.nbs.patient.profile;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientProfileTupleMapperTest {

  @Test
  void should_map_patient_profile_from_tuple() {

    PatientProfileTupleMapper.Tables table = new PatientProfileTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(table.patient().personParentUid.id)).thenReturn(3167L);
    when(tuple.get(table.patient().personParentUid.localId)).thenReturn("local-id-value");
    when(tuple.get(table.patient().personParentUid.versionCtrlNbr)).thenReturn((short) 89);
    when(tuple.get(table.patient().personParentUid.recordStatus.status)).thenReturn("ACTIVE");

    PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

    PatientProfile actual = mapper.map(tuple);

    assertThat(actual.id()).isEqualTo(3167L);
    assertThat(actual.local()).isEqualTo("local-id-value");
    assertThat(actual.version()).isEqualTo((short) 89);
    assertThat(actual.status()).isEqualTo("ACTIVE");
  }

  @ParameterizedTest
  @MethodSource("statuses")
  void should_map_patient_profile_statuses(final String status, final String expected) {

    PatientProfileTupleMapper.Tables table = new PatientProfileTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    when(tuple.get(table.patient().personParentUid.id)).thenReturn(3167L);
    when(tuple.get(table.patient().personParentUid.localId)).thenReturn("local-id-value");
    when(tuple.get(table.patient().personParentUid.versionCtrlNbr)).thenReturn((short) 89);

    when(tuple.get(table.patient().personParentUid.recordStatus.status)).thenReturn(status);

    PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

    PatientProfile actual = mapper.map(tuple);

    assertThat(actual.status()).isEqualTo(expected);
  }

  static Stream<Arguments> statuses() {
    return Stream.of(
        Arguments.arguments("ACTIVE", "ACTIVE"),
        Arguments.arguments("INACTIVE", "INACTIVE"),
        Arguments.arguments("LOG_DEL", "INACTIVE"),
        Arguments.arguments("SUPERCEDED", "SUPERSEDED")
    );
  }

  @Test
  void should_not_map_patient_profile_when_parent_id_not_present() {

    PatientProfileTupleMapper.Tables table = new PatientProfileTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);

    PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("id is required");

  }

  @Test
  void should_not_map_patient_profile_when_version_not_present() {

    PatientProfileTupleMapper.Tables table = new PatientProfileTupleMapper.Tables();

    Tuple tuple = mock(Tuple.class);
    when(tuple.get(table.patient().personParentUid.id)).thenReturn(3167L);
    when(tuple.get(table.patient().personParentUid.localId)).thenReturn("local-id-value");

    PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

    assertThatThrownBy(() -> mapper.map(tuple))
        .hasMessageContaining("version is required");

  }
}
