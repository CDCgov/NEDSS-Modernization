package gov.cdc.nbs.patient.profile.vaccination;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.profile.association.AssociatedWith;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PatientVaccinationTupleMapperTest {

    @Test
    void should_map_vaccination_from_tuple() {

        PatientVaccinationTupleMapper.Tables tables = new PatientVaccinationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.vaccination().id)).thenReturn(5669L);
        when(tuple.get(tables.vaccination().addTime)).thenReturn(Instant.parse("2022-03-17T00:45:07Z"));

        when(tuple.get(tables.vaccination().activityFromTime)).thenReturn(Instant.parse("2021-04-07T05:49:00Z"));

        when(tuple.get(tables.vaccine().codeShortDescTxt)).thenReturn("administered");

        when(tuple.get(tables.vaccination().localId)).thenReturn("event");

        PatientVaccinationTupleMapper mapper = new PatientVaccinationTupleMapper(tables);

        PatientVaccination actual = mapper.map(tuple);

        assertThat(actual.vaccination()).isEqualTo(5669L);
        assertThat(actual.createdOn()).isEqualTo("2022-03-17T00:45:07Z");

        assertThat(actual.administeredOn()).isEqualTo("2021-04-07T05:49:00Z");
        assertThat(actual.administered()).isEqualTo("administered");
        assertThat(actual.event()).isEqualTo("event");

        assertThat(actual.associatedWith()).isNull();
    }

    @Test
    void should_map_vaccination_from_tuple_with_associated_investigation() {

        PatientVaccinationTupleMapper.Tables tables = new PatientVaccinationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.vaccination().id)).thenReturn(5669L);

        when(tuple.get(tables.associatedWith().investigation().id)).thenReturn(4127L);
        when(tuple.get(tables.associatedWith().investigation().localId)).thenReturn("investigation-local");
        when(tuple.get(tables.associatedWith().condition().conditionShortNm)).thenReturn("investigation-condition");

        PatientVaccinationTupleMapper mapper = new PatientVaccinationTupleMapper(tables);

        PatientVaccination actual = mapper.map(tuple);

        assertThat(actual).extracting(PatientVaccination::associatedWith)
                .returns(4127L, AssociatedWith::id)
                .returns("investigation-local", AssociatedWith::local)
                .returns("investigation-condition", AssociatedWith::condition);
    }

    @Test
    void should_map_vaccination_from_tuple_with_associated_provider() {

        PatientVaccinationTupleMapper.Tables tables = new PatientVaccinationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        when(tuple.get(tables.vaccination().id)).thenReturn(5669L);

        when(tuple.get(tables.provider().name().nmPrefix)).thenReturn("prefix");
        when(tuple.get(tables.provider().name().firstNm)).thenReturn("first");
        when(tuple.get(tables.provider().name().lastNm)).thenReturn("last");
        when(tuple.get(tables.provider().name().nmSuffix)).thenReturn(Suffix.IV);

        PatientVaccinationTupleMapper mapper = new PatientVaccinationTupleMapper(tables);

        PatientVaccination actual = mapper.map(tuple);

        assertThat(actual.provider()).isEqualTo("prefix first last IV");
    }

    @Test
    void should_not_map_address_from_tuple_without_identifier() {
        PatientVaccinationTupleMapper.Tables tables = new PatientVaccinationTupleMapper.Tables();


        Tuple tuple = mock(Tuple.class);

        PatientVaccinationTupleMapper mapper = new PatientVaccinationTupleMapper(tables);


        assertThatThrownBy(() -> mapper.map(tuple))
                .hasMessageContaining("identifier is required");

    }
}
