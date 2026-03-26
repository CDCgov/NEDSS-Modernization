package gov.cdc.nbs.patient.profile.redirect.incoming;

import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class IncomingPatientFromActionFinder {

  private static final int LOCAL_ID_PARAMETER = 1;

  private static final String QUERY =
      """
      select
        [parent].[local_id]
      from person [patient]
        join participation [participation] on
              [participation].record_status_cd = 'ACTIVE'
          and [participation].[subject_class_cd] = 'PSN'
          and [participation].[type_cd] in ('SubjOfPHC', 'SubjOfDoc', 'PATSBJ','SubjOfMorbReport','SubjOfTrmt','SubOfVacc', 'VaccGiven')
          and [participation].[subject_entity_uid] = [patient].person_uid

        join Person [parent] on
          [parent].
        person_uid = [patient].[person_parent_uid]
        where  [participation].[act_uid] = ?
        """;

  private final JdbcTemplate template;
  private final IncomingPatientRowMapper mapper;

  IncomingPatientFromActionFinder(
      final JdbcTemplate template, final IncomingPatientRowMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  /**
   * Resolves the {@link IncomingPatient} based on an {@code identifier} of an Action
   * (Investigation, Document, Lab report, Morbidity Report, Treatment, or Vaccination).
   *
   * @param identifier The unique identifier of the Action.
   * @return an {@link Optional} that if present contains the {@code IncomingPatient}.
   */
  public Optional<IncomingPatient> find(final long identifier) {
    return this.template
        .query(QUERY, setter -> setter.setLong(LOCAL_ID_PARAMETER, identifier), this.mapper)
        .stream()
        .flatMap(Optional::stream)
        .findFirst();
  }
}
