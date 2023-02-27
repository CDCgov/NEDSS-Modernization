package gov.cdc.nbs.patient.treatment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PatientTreatmentRowMapper {

    private static final String NAME_SEPARATOR = " ";

    record Index(
            int treatment,
            int createdOn,
            int description,
            int event,
            int treatedOn,
            int providerPrefix,
            int providerFirstName,
            int providerLastName,
            int providerSuffix,
            int investigationId,
            int investigationLocal,
            int investigationCondition
    ) {

        static Index resolve(final ResultSet resultSet, final Label label) throws SQLException {
            return new Index(
                    resultSet.findColumn(label.treatment()),
                    resultSet.findColumn(label.createdOn()),
                    resultSet.findColumn(label.description()),
                    resultSet.findColumn(label.event()),
                    resultSet.findColumn(label.treatedOn()),
                    resultSet.findColumn(label.providerPrefix()),
                    resultSet.findColumn(label.providerFirstName()),
                    resultSet.findColumn(label.providerLastName()),
                    resultSet.findColumn(label.providerSuffix()),
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
            String providerPrefix,
            String providerFirstName,
            String providerLastName,
            String providerSuffix,
            String investigationId,
            String investigationLocal,
            String investigationCondition
    ) {

    }

    private final Label label;
    private Index index;

    PatientTreatmentRowMapper(final Label label) {
        this.label = label;
    }

    PatientTreatmentRowMapper(final Label label, final Index index) {
        this.label = label;
        this.index = index;
    }

    /**
     * Creates a {@link PatientTreatmentRowMapper} by resolving the indices of columns within the {@code ResultSet}.
     *
     * @param resultSet The ResultSet to specialize the mapper to
     * @return A {@link PatientTreatmentRowMapper} specialized to a ResultSet.
     */
    PatientTreatmentRowMapper specialized(final ResultSet resultSet) throws SQLException {
        Index index = Index.resolve(resultSet, label);
        return new PatientTreatmentRowMapper(label, index);
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

        String provider = mapProvider(resultSet, columns);

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

    private String mapProvider(final ResultSet resultSet, final Index columns) throws SQLException {
        String prefix = resultSet.getString(columns.providerPrefix());
        String first = resultSet.getString(columns.providerFirstName());
        String last = resultSet.getString(columns.providerLastName());
        String suffix = resultSet.getString(columns.providerSuffix());

        return Stream.of(
                        prefix,
                        first,
                        last,
                        suffix
                ).filter(Objects::nonNull)
                .collect(Collectors.joining(NAME_SEPARATOR));
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
