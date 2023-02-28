package gov.cdc.nbs.patient.treatment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

class PatientTreatmentRowMapper {

    record Index(
            int treatment,
            int createdOn,
            int description,
            int event,
            int treatedOn,
            PatientTreatmentProviderRowMapper.Index provider,
            int investigationId,
            int investigationLocal,
            int investigationCondition
    ) {

        static Index resolve(final ResultSet resultSet, final Label label) throws SQLException {
            PatientTreatmentProviderRowMapper.Index provider = PatientTreatmentProviderRowMapper.Index.resolve(
                    resultSet,
                    label.provider()
            );
            return new Index(
                    resultSet.findColumn(label.treatment()),
                    resultSet.findColumn(label.createdOn()),
                    resultSet.findColumn(label.description()),
                    resultSet.findColumn(label.event()),
                    resultSet.findColumn(label.treatedOn()),
                    provider,
                    resultSet.findColumn(label.investigationId()),
                    resultSet.findColumn(label.investigationLocal()),
                    resultSet.findColumn(label.investigationCondition())
            );
        }

    }

    record Label(
            String treatment,
            String createdOn,
            String description,
            String event,
            String treatedOn,
            PatientTreatmentProviderRowMapper.Label provider,
            String investigationId,
            String investigationLocal,
            String investigationCondition
    ) {

    }

    private final PatientTreatmentProviderRowMapper providerMapper;

    private final Label label;
    private Index index;

    PatientTreatmentRowMapper(final Label label) {
        this.label = label;
        this.providerMapper = new PatientTreatmentProviderRowMapper(label.provider());
    }

    PatientTreatmentRowMapper(final Label label, final Index index) {
        this.label = Objects.requireNonNull(label, "A label is required");
        this.index = Objects.requireNonNull(index, "An index is required");
        this.providerMapper = new PatientTreatmentProviderRowMapper(label.provider(), index.provider());
    }

    /**
     * Creates a {@link PatientTreatmentRowMapper} by resolving the indices of columns within the {@code ResultSet}.
     *
     * @param resultSet The ResultSet to specialize the mapper to
     * @return A {@link PatientTreatmentRowMapper} specialized to a ResultSet.
     */
    PatientTreatmentRowMapper specialized(final ResultSet resultSet) throws SQLException {
        Index specialized = Index.resolve(resultSet, label);
        return new PatientTreatmentRowMapper(
                label,
                specialized
        );
    }

    /**
     * Creates a {@link PatientTreatment} from the current row of the {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} containing values that represent a {@link PatientTreatment}.
     * @return The {@link PatientTreatment} represented by a single row of the {@code ResultSet}.*
     */
    PatientTreatment map(final ResultSet resultSet) throws SQLException {
        Index columns = ensureIndex(resultSet);

        long treatment = resultSet.getLong(columns.treatment());
        Instant createdOn = mapInstant(resultSet, columns.createdOn());

        Instant treatedOn = mapInstant(resultSet, columns.treatedOn());

        String description = resultSet.getString(columns.description());
        String event = resultSet.getString(columns.event());

        String provider = mapProvider(resultSet);

        PatientTreatment.Investigation investigation = mapInvestigation(resultSet, columns);

        return new PatientTreatment(
                treatment,
                createdOn,
                provider,
                treatedOn,
                description,
                event,
                investigation
        );
    }

    private Instant mapInstant(final ResultSet resultSet, final int column) throws SQLException {
        Timestamp value = resultSet.getTimestamp(column);

        return value == null ? null : value.toInstant();
    }

    private String mapProvider(final ResultSet resultSet) throws SQLException {
        return this.providerMapper.map(resultSet);
    }

    private PatientTreatment.Investigation mapInvestigation(
            final ResultSet rs,
            final Index columns
    ) throws SQLException {
        long identifier = rs.getLong(columns.investigationId());
        String local = rs.getString(columns.investigationLocal());
        String condition = rs.getString(columns.investigationCondition());

        return new PatientTreatment.Investigation(
                identifier,
                local,
                condition
        );
    }

    private Index ensureIndex(final ResultSet resultSet) throws SQLException {
        if (this.index == null) {
            this.index = Index.resolve(resultSet, label);
        }

        return this.index;
    }
}
