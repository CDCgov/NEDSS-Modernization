package gov.cdc.nbs.patient.profile.race;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PatientRaceTupleMapperTest {

    @Test
    void should_map_race_from_tuple() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.personRace().asOfDate)).thenReturn(Instant.parse("2023-01-17T22:54:43Z"));

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        PatientRace actual = mapper.map(tuple);

        assertThat(actual.patient()).isEqualTo(2357L);
        assertThat(actual.id()).isEqualTo(433L);
        assertThat(actual.version()).isEqualTo((short) 227);
        assertThat(actual.asOf()).isEqualTo("2023-01-17T22:54:43Z");

        assertThat(actual.category()).isNull();
        assertThat(actual.detailed()).isEmpty();
    }

    @Test
    void should_map_race_from_tuple_with_category() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.category().id.code)).thenReturn("category-id");
        when(tuple.get(tables.category().codeShortDescTxt)).thenReturn("category-description");

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        PatientRace actual = mapper.map(tuple);

        assertThat(actual.category().id()).isEqualTo("category-id");
        assertThat(actual.category().description()).isEqualTo("category-description");
    }

    @Test
    void should_map_race_from_tuple_with_race() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);
        when(tuple.get(tables.category().id.code)).thenReturn("category-id");

        when(tuple.get(tables.race().id)).thenReturn("race-id");
        when(tuple.get(tables.race().codeShortDescTxt)).thenReturn("race-description");

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        PatientRace actual = mapper.map(tuple);

        assertThat(actual.detailed()).satisfiesExactly(
            actualRace -> assertThat(actualRace)
                .returns("race-id", PatientRace.Race::id)
                .returns("race-description", PatientRace.Race::description)
        );
    }

    @Test
    void should_map_race_from_tuple_with_race_when_category_not_present() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.race().id)).thenReturn("race-id");
        when(tuple.get(tables.race().codeShortDescTxt)).thenReturn("race-description");

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        PatientRace actual = mapper.map(tuple);

        assertThat(actual.detailed()).satisfiesExactly(
            actualRace -> assertThat(actualRace)
                .returns("race-id", PatientRace.Race::id)
                .returns("race-description", PatientRace.Race::description)
        );
    }

    @Test
    void should_map_race_from_tuple_without_race_when_same_as_category() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        when(tuple.get(tables.category().id.code)).thenReturn("same-id");
        when(tuple.get(tables.category().codeShortDescTxt)).thenReturn("same-description");

        when(tuple.get(tables.race().id)).thenReturn("same-id");
        when(tuple.get(tables.race().codeShortDescTxt)).thenReturn("same-description");

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        PatientRace actual = mapper.map(tuple);

        assertThat(actual.detailed()).isEmpty();
    }

    @Test
    void should_not_map_race_from_tuple_without_identifier() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("identifier is required");

    }

    @Test
    void should_not_map_race_from_tuple_without_patient() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.patient().id)).thenReturn(433L);
        when(tuple.get(tables.patient().versionCtrlNbr)).thenReturn((short) 227);

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("patient is required");

    }

    @Test
    void should_not_map_race_from_tuple_without_version() {
        PatientRaceTupleMapper.Tables tables = new PatientRaceTupleMapper.Tables();

        Tuple tuple = mock(Tuple.class);
        when(tuple.get(tables.patient().personParentUid.id)).thenReturn(2357L);
        when(tuple.get(tables.patient().id)).thenReturn(433L);

        PatientRaceTupleMapper mapper = new PatientRaceTupleMapper(tables);

        assertThatThrownBy(() -> mapper.map(tuple))
            .hasMessageContaining("version is required");

    }

}
