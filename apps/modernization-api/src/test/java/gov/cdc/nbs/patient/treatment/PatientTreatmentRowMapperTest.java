package gov.cdc.nbs.patient.treatment;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class PatientTreatmentRowMapperTest {

    @Test
    void should_resolve_column_index_from_result_set_using_label() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn(2).when(resultSet).findColumn("treatment");
        doReturn(3).when(resultSet).findColumn("created_on");
        doReturn(5).when(resultSet).findColumn("description");
        doReturn(7).when(resultSet).findColumn("event");
        doReturn(11).when(resultSet).findColumn("treated_on");
        doReturn(13).when(resultSet).findColumn("provider_prefix");
        doReturn(17).when(resultSet).findColumn("provider_first_name");
        doReturn(19).when(resultSet).findColumn("provider_last_name");
        doReturn(23).when(resultSet).findColumn("provider_suffix");
        doReturn(29).when(resultSet).findColumn("investigation_id");
        doReturn(31).when(resultSet).findColumn("investigation_local");
        doReturn(37).when(resultSet).findColumn("investigation_condition");

        PatientTreatmentRowMapper.Label label = new PatientTreatmentRowMapper.Label(
                "treatment",
                "created_on",
                "description",
                "event",
                "treated_on",
                new PatientTreatmentProviderRowMapper.Label(
                        "provider_prefix",
                        "provider_first_name",
                        "provider_last_name",
                        "provider_suffix"
                ),
                "investigation_id",
                "investigation_local",
                "investigation_condition"
        );

        PatientTreatmentRowMapper.Index actual = PatientTreatmentRowMapper.Index.resolve(
                resultSet,
                label
        );

        assertThat(actual.treatment()).isEqualTo(2);
        assertThat(actual.createdOn()).isEqualTo(3);
        assertThat(actual.description()).isEqualTo(5);
        assertThat(actual.event()).isEqualTo(7);
        assertThat(actual.treatedOn()).isEqualTo(11);
        assertThat(actual.investigationId()).isEqualTo(29);
        assertThat(actual.investigationLocal()).isEqualTo(31);
        assertThat(actual.investigationCondition()).isEqualTo(37);

        assertThat(actual.provider().prefix()).isEqualTo(13);
        assertThat(actual.provider().first()).isEqualTo(17);
        assertThat(actual.provider().last()).isEqualTo(19);
        assertThat(actual.provider().suffix()).isEqualTo(23);
    }

    @Test
    void should_map_patient_treatment_from_result_set() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn(1091L).when(resultSet).getLong(1);
        doReturn(Timestamp.from(Instant.parse("2022-06-17T15:04:39Z"))).when(resultSet).getTimestamp(2);
        doReturn("description-value").when(resultSet).getString(3);
        doReturn("event-value").when(resultSet).getString(4);
        doReturn(Timestamp.from(Instant.parse("2022-06-18T01:24:48Z"))).when(resultSet).getTimestamp(5);

        doReturn("provider-prefix-value").when(resultSet).getString(6);
        doReturn("provider-first-name-value").when(resultSet).getString(7);
        doReturn("provider-last-name-value").when(resultSet).getString(8);
        doReturn("provider-suffix-value").when(resultSet).getString(9);

        doReturn(2161L).when(resultSet).getLong(10);
        doReturn("investigation-local-value").when(resultSet).getString(11);
        doReturn("investigation-condition-value").when(resultSet).getString(12);

        PatientTreatmentRowMapper.Index index = new PatientTreatmentRowMapper.Index(1, 2, 3, 4, 5, new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9), 10, 11, 12);

        PatientTreatmentRowMapper.Label label = new PatientTreatmentRowMapper.Label(
                "treatment",
                "created_on",
                "description",
                "event",
                "treated_on",
                new PatientTreatmentProviderRowMapper.Label(
                        "provider_prefix",
                        "provider_first_name",
                        "provider_last_name",
                        "provider_suffix"
                ),
                "investigation_id",
                "investigation_local",
                "investigation_condition"
        );

        PatientTreatmentRowMapper mapper = new PatientTreatmentRowMapper(
                label,
                index
        );


        PatientTreatment actual = mapper.map(resultSet);

        assertThat(actual.treatment()).isEqualTo(1091L);
        assertThat(actual.createdOn()).isEqualTo("2022-06-17T15:04:39Z");
        assertThat(actual.provider()).isEqualTo("provider-prefix-value provider-first-name-value provider-last-name-value provider-suffix-value");
        assertThat(actual.treatedOn()).isEqualTo("2022-06-18T01:24:48Z");
        assertThat(actual.description()).isEqualTo("description-value");
        assertThat(actual.event()).isEqualTo("event-value");

        assertThat(actual.associatedWith()).satisfies(
                investigation -> assertThat(investigation)
                        .returns(2161L, PatientTreatment.Investigation::id)
                        .returns("investigation-local-value", PatientTreatment.Investigation::local)
                        .returns("investigation-condition-value", PatientTreatment.Investigation::condition)
        );

    }

    @Test
    void should_map_patient_treatment_from_result_set_without_provider() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        PatientTreatmentRowMapper.Index index = new PatientTreatmentRowMapper.Index(1, 2, 3, 4, 5, new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9), 10, 11, 12);

        PatientTreatmentRowMapper.Label label = new PatientTreatmentRowMapper.Label(
                "treatment",
                "created_on",
                "description",
                "event",
                "treated_on",
                new PatientTreatmentProviderRowMapper.Label(
                        "provider_prefix",
                        "provider_first_name",
                        "provider_last_name",
                        "provider_suffix"
                ),
                "investigation_id",
                "investigation_local",
                "investigation_condition"
        );

        PatientTreatmentRowMapper mapper = new PatientTreatmentRowMapper(
                label,
                index
        );


        PatientTreatment actual = mapper.map(resultSet);

        assertThat(actual.provider()).isNull();

    }


}