package gov.cdc.nbs.patient;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
class PatientHistoryCleaner {

  private static final String DELETE = """
      delete from [locator]
      from [Tele_locator_hist] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[tele_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      delete from [locator]
      from [Postal_locator_hist] [locator]
          join [Entity_locator_participation] [participation] on
                  [locator].[postal_locator_uid] = [participation].[locator_uid]
      where [participation].entity_uid in (:identifiers);
      
      delete from Entity_loc_participation_hist where entity_uid in (:identifiers);
       
      delete from Entity_id_hist where entity_uid in (:identifiers);
      
      delete from Person_race_hist where person_uid in (:identifiers);
      
      delete from Person_ethnic_group_hist where person_uid in (:identifiers);
      
      delete from Person_Name_hist where person_uid in (:identifiers);
      
      delete from person_hist where person_uid in (:identifiers);
       """;

  private final Available<PatientIdentifier> available;
  private final NamedParameterJdbcTemplate template;

  PatientHistoryCleaner(
      final Available<PatientIdentifier> available,
      final NamedParameterJdbcTemplate template
  ) {
    this.available = available;
    this.template = template;
  }

  void clean() {

    List<Long> identifiers = available.all().map(PatientIdentifier::id).toList();

    if (!identifiers.isEmpty()) {
      Map<String, List<Long>> parameters = Map.of("identifiers", identifiers);
      this.template.execute(DELETE, parameters, PreparedStatement::execute);
    }
  }
}
