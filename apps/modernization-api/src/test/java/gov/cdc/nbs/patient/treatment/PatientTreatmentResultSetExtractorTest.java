package gov.cdc.nbs.patient.treatment;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientTreatmentResultSetExtractorTest {

    @Test
    void should_specialize_the_mapper_with_the_provided_result_set() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);

        PatientTreatmentRowMapper mapper = mock(PatientTreatmentRowMapper.class);

        PatientTreatmentResultSetExtractor extractor = new PatientTreatmentResultSetExtractor(mapper);

        List<PatientTreatment> actual = extractor.extractData(resultSet);

        assertThat(actual).isEmpty();

        verify(mapper).specialized(resultSet);

    }

    @Test
    void should_collect_results_into_list() throws SQLException {

        PatientTreatmentRowMapper mapper = mock(PatientTreatmentRowMapper.class);

        when(mapper.specialized(any())).thenReturn(mapper);

        when(mapper.map(any())).thenReturn(
                new PatientTreatment(
                        2119L,
                        Instant.parse("2022-05-13T10:34:00Z"),
                        "provider-value",
                        Instant.parse("2022-05-10T15:04:39Z"),
                        "description-value",
                        "event-value",
                        new PatientTreatment.Investigation(
                                2251L,
                                "local-value",
                                "condition-value"
                        )

                )
        );



        PatientTreatmentResultSetExtractor extractor = new PatientTreatmentResultSetExtractor(mapper);

        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.next()).thenReturn(true, true, true, false);

        List<PatientTreatment> actual = extractor.extractData(resultSet);

        assertThat(actual).hasSize(3);
    }
}