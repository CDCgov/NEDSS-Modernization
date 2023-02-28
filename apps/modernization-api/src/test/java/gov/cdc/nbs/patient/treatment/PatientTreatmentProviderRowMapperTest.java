package gov.cdc.nbs.patient.treatment;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class PatientTreatmentProviderRowMapperTest {

    @Test
    void should_resolve_column_index_from_result_set_using_label() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn(13).when(resultSet).findColumn("prefix");
        doReturn(17).when(resultSet).findColumn("first_name");
        doReturn(19).when(resultSet).findColumn("last_name");
        doReturn(23).when(resultSet).findColumn("suffix");

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper.Index actual = PatientTreatmentProviderRowMapper.Index.resolve(
                resultSet,
                label
        );

        assertThat(actual.prefix()).isEqualTo(13);
        assertThat(actual.first()).isEqualTo(17);
        assertThat(actual.last()).isEqualTo(19);
        assertThat(actual.suffix()).isEqualTo(23);
    }

    @Test
    void should_map_provider_from_result_set() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn("prefix-value").when(resultSet).getString(6);
        doReturn("first-name-value").when(resultSet).getString(7);
        doReturn("last-name-value").when(resultSet).getString(8);
        doReturn("suffix-value").when(resultSet).getString(9);

        PatientTreatmentProviderRowMapper.Index index = new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9);

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper mapper = new PatientTreatmentProviderRowMapper(label, index);


        String actual = mapper.map(resultSet);

        assertThat(actual).isEqualTo("prefix-value first-name-value last-name-value suffix-value");

    }

    @Test
    void should_map_provider_from_result_set_without_prefix() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn("first-name-value").when(resultSet).getString(7);
        doReturn("last-name-value").when(resultSet).getString(8);
        doReturn("suffix-value").when(resultSet).getString(9);

        PatientTreatmentProviderRowMapper.Index index = new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9);

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper mapper = new PatientTreatmentProviderRowMapper(label, index);


        String actual = mapper.map(resultSet);

        assertThat(actual).isEqualTo("first-name-value last-name-value suffix-value");

    }

    @Test
    void should_map_provider_from_result_set_without_suffix() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn("prefix-value").when(resultSet).getString(6);
        doReturn("first-name-value").when(resultSet).getString(7);
        doReturn("last-name-value").when(resultSet).getString(8);

        PatientTreatmentProviderRowMapper.Index index = new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9);

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper mapper = new PatientTreatmentProviderRowMapper(label, index);


        String actual = mapper.map(resultSet);

        assertThat(actual).isEqualTo("prefix-value first-name-value last-name-value");

    }

    @Test
    void should_map_provider_from_result_set_without_prefix_or_suffix() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        doReturn("first-name-value").when(resultSet).getString(7);
        doReturn("last-name-value").when(resultSet).getString(8);

        PatientTreatmentProviderRowMapper.Index index = new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9);

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper mapper = new PatientTreatmentProviderRowMapper(label, index);


        String actual = mapper.map(resultSet);

        assertThat(actual).isEqualTo("first-name-value last-name-value");

    }

    @Test
    void should_not_map_provider_from_result_set_when_provider_name_fields_are_not_present() throws SQLException {

        ResultSet resultSet = mock(ResultSet.class);

        PatientTreatmentProviderRowMapper.Index index = new PatientTreatmentProviderRowMapper.Index(6, 7, 8, 9);

        PatientTreatmentProviderRowMapper.Label label = new PatientTreatmentProviderRowMapper.Label(
                "prefix",
                "first_name",
                "last_name",
                "suffix"
        );

        PatientTreatmentProviderRowMapper mapper = new PatientTreatmentProviderRowMapper(label, index);


        String actual = mapper.map(resultSet);

        assertThat(actual).isNull();

    }
}