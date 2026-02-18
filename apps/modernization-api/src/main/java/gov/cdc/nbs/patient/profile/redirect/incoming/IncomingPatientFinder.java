package gov.cdc.nbs.patient.profile.redirect.incoming;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IncomingPatientFinder {

  private static final String QUERY =
      """
      select
          [patient].local_id
      from person [patient]
      where [patient].[person_parent_uid] = ?
      """;
  private static final int LOCAL_ID_PARAMETER = 1;

  private final JdbcTemplate template;
  private final IncomingPatientRowMapper mapper;

  IncomingPatientFinder(final JdbcTemplate template, final IncomingPatientRowMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  public Optional<IncomingPatient> find(final long identifier) {
    return this.template
        .query(QUERY, setter -> setter.setLong(LOCAL_ID_PARAMETER, identifier), this.mapper)
        .stream()
        .flatMap(Optional::stream)
        .findFirst();
  }
}
