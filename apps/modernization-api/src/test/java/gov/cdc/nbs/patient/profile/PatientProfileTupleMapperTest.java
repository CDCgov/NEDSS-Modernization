package gov.cdc.nbs.patient.profile;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.entity.odse.QPerson;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientProfileTupleMapperTest {

    @Test
    void should_map_patient_profile_from_tuple() {

        QPerson table = new QPerson("patient");

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(table.id)).thenReturn(3167L);
        when(tuple.get(table.localId)).thenReturn("local-id-value");
        when(tuple.get(table.versionCtrlNbr)).thenReturn((short) 89);

        PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

        Instant asOf = Instant.parse("2023-05-04T03:02:01Z");

        PatientProfile actual = mapper.map(asOf, tuple);

        assertThat(actual.id()).isEqualTo(3167L);
        assertThat(actual.local()).isEqualTo("local-id-value");
        assertThat(actual.version()).isEqualTo(89);
    }

    @Test
    void should_not_map_patient_profile_when_parent_id_not_present() {

        QPerson table = new QPerson("patient");

        Tuple tuple = mock(Tuple.class);


        PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

        Instant asOf = Instant.parse("2023-05-04T03:02:01Z");

        assertThatThrownBy(() -> mapper.map(asOf, tuple))
            .hasMessageContaining("id is required");

    }

    @Test
    void should_not_map_patient_profile_when_version_not_present() {

        QPerson table = new QPerson("patient");

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(table.id)).thenReturn(3167L);
        when(tuple.get(table.localId)).thenReturn("local-id-value");


        PatientProfileTupleMapper mapper = new PatientProfileTupleMapper(table);

        Instant asOf = Instant.parse("2023-05-04T03:02:01Z");

        assertThatThrownBy(() -> mapper.map(asOf, tuple))
            .hasMessageContaining("version is required");

    }
}
