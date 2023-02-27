package gov.cdc.nbs.patient.treatment;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A {@link ResultSetExtractor} that extracts zero or more {@link PatientTreatment} instances from a {@code ResultSet}.
 */
class PatientTreatmentResultSetExtractor implements ResultSetExtractor<List<PatientTreatment>> {

    private final PatientTreatmentRowMapper mapper;

    PatientTreatmentResultSetExtractor(final PatientTreatmentRowMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<PatientTreatment> extractData(final ResultSet resultSet) throws SQLException, DataAccessException {

        PatientTreatmentRowMapper specialized = this.mapper.specialized(resultSet);

        List<PatientTreatment> results = new ArrayList<>();

        while (resultSet.next()) {
            PatientTreatment row = specialized.map(resultSet);
            results.add(row);
        }

        return results;
    }
}
