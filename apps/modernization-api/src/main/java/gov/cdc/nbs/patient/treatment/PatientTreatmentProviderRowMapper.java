package gov.cdc.nbs.patient.treatment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PatientTreatmentProviderRowMapper {

    private static final String NAME_SEPARATOR = " ";

    record Index(
            int prefix,
            int first,
            int last,
            int suffix
    ) {

        static PatientTreatmentProviderRowMapper.Index resolve(
                final ResultSet resultSet,
                final Label label
        ) throws SQLException {
            return new Index(
                    resultSet.findColumn(label.prefix()),
                    resultSet.findColumn(label.first()),
                    resultSet.findColumn(label.last()),
                    resultSet.findColumn(label.suffix())
            );
        }

    }

    record Label(
            String prefix,
            String first,
            String last,
            String suffix
    ) {

    }

    private final Label label;
    private Index index;

    PatientTreatmentProviderRowMapper(final Label label) {
        this.label = label;
    }

    PatientTreatmentProviderRowMapper(final Label label, final Index index) {
        this.label = label;
        this.index = index;
    }

    private Index ensureIndex(final ResultSet resultSet) throws SQLException {
        if (this.index == null) {
            this.index = Index.resolve(resultSet, label);
        }

        return this.index;
    }

    /**
     * Creates a {@link PatientTreatmentProviderRowMapper} by resolving the indices of columns within the {@code ResultSet}.
     *
     * @param resultSet The ResultSet to specialize the mapper to
     * @return A {@link PatientTreatmentProviderRowMapper} specialized to a ResultSet.
     */
    PatientTreatmentProviderRowMapper specialized(final ResultSet resultSet) throws SQLException {
        Index specialized = Index.resolve(resultSet, label);
        return new PatientTreatmentProviderRowMapper(label, specialized);
    }

    String map(final ResultSet resultSet) throws SQLException {
        Index columns = ensureIndex(resultSet);

        String prefix = resultSet.getString(columns.prefix());
        String first = resultSet.getString(columns.first());
        String last = resultSet.getString(columns.last());
        String suffix = resultSet.getString(columns.suffix());

        return first == null && last == null ?
                null : combineName(prefix, first, last,suffix);

    }

    private String combineName(
            String prefix,
            String first,
            String last,
            String suffix
    ) {
        return Stream.of(
                        prefix,
                        first,
                        last,
                        suffix
                ).filter(Objects::nonNull)
                .collect(Collectors.joining(NAME_SEPARATOR));
    }

}
