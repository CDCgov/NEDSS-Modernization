package gov.cdc.nbs.deduplication;

import java.util.List;
import java.time.Instant;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.model.PatientData;
import gov.cdc.nbs.deduplication.model.PatientData.Bundle;
import gov.cdc.nbs.deduplication.model.PatientData.Bundle.Entry.Resource.Address;
import org.apache.commons.codec.language.Soundex;


@Component
public class DataManager {
  private static final String RESET_DEDUP_IND = """
      UPDATE person
      SET dedup_match_ind = null,
      group_nbr = null,
      group_time = null
      """;

  private static final String DELETE_ENTITIES = """
      DELETE FROM Entity WHERE entity_uid < 10000000;
        """;

  private static final String DELETE_PERSONS = """
      DELETE FROM Person WHERE person_uid < 10000000
        """;

  private static final String DELETE_PERSON_NAMES = """
      DELETE FROM Person_name WHERE person_uid< 10000000;
        """;

  private static final String DELETE_ADDRESSES = """
      DELETE FROM Postal_locator WHERE postal_locator_uid< 10000000;
      DELETE FROM Entity_locator_participation WHERE entity_uid < 10000000;
        """;

  private static final String DELETE_IDENTIFIERS = """
      DELETE FROM Entity_id WHERE entity_uid < 10000000
      """;

  private static final String INSERT_ENTITY = """
      INSERT INTO Entity(entity_uid, class_cd) VALUES (?, 'PSN');
        """;

  private static final String INSERT_PERSON =
      """
          INSERT INTO Person(
            person_uid,
            person_parent_uid,
            birth_time,
            version_ctrl_nbr,
            record_status_cd,
            cd,
            curr_sex_cd)
          VALUES (
            ?,
            ?,
            ?,
            1,
            'ACTIVE',
            'PAT',
            '');
            """;

  private static final String INSERT_NAME =
      """
          INSERT INTO Person_name (
            person_uid,
            person_name_seq,
            status_cd,
            status_time,
            first_nm,
            first_nm_sndx,
            last_nm,
            last_nm_sndx,
            nm_use_cd,
            record_status_cd,
            as_of_date)
          VALUES(
            ?,
            1,
            'A',
            '2024-05-07 15:00:00.000',
            ?,
            ?,
            ?,
            ?,
            'L',
            'ACTIVE',
            '2024-05-07 15:00:00.000')
          """;

  private static final String INSERT_ADDRESS = """
      INSERT INTO Postal_locator(postal_locator_uid, street_addr1, city_desc_txt, state_cd, zip_cd, cntry_cd)
      VALUES (?, ?, ?, ?, ?, '840')
          """;

  private static final String INSERT_ENTITY_LOCATOR_PARTICIPATION = """
        INSERT INTO Entity_locator_participation (entity_uid, locator_uid, version_ctrl_nbr)
      VALUES (?, ?, 1)
            """;

  private static final String INSERT_SSN = """
      INSERT INTO Entity_id (entity_uid, entity_id_seq, root_extension_txt, type_cd)
      VALUES (?, 1, ?, 'SS')
        """;

  private final JdbcTemplate template;
  private final Soundex soundex;

  public DataManager(final JdbcTemplate template) {
    this.template = template;
    this.soundex = new Soundex();
  }

  public int reset() {
    template.update(DELETE_IDENTIFIERS);
    template.update(DELETE_ADDRESSES);
    template.update(DELETE_PERSON_NAMES);
    template.update(DELETE_PERSONS);
    template.update(DELETE_ENTITIES);
    return template.update(RESET_DEDUP_IND);
  }

  // Inserts provided patient data into the NBS 6 database
  public List<PatientData> load(List<PatientData> data) {
    data.forEach(d -> {
      String id = d.external_person_id();
      insertPersonEntity(id, d.bundle().entry().get(0).resource().birthDate());
      insertNames(id, d.bundle());
      insertAddresses(id, d.bundle());
      insertIdentifiers(id, d.bundle());
    });

    return data;
  }

  // Inserts a new 'Entity' as well as a blank 'Person' record into the database 
  private void insertPersonEntity(String id, String birthDate) {
    template.update(INSERT_ENTITY, id);
    template.update(INSERT_PERSON, id, id, birthDate + " 00:00:00.000");
  }

  // Inserts a 'person_name' entry for each name provided
  private void insertNames(String id, Bundle bundle) {
    bundle.entry()
        .stream()
        .flatMap(e -> e.resource().name().stream())
        .forEach(n -> n.given().forEach(firstName -> template.update(
            INSERT_NAME,
            id,
            n.family(),
            soundex.encode(n.family()),
            firstName,
            soundex.encode(firstName))));
  }

  // Inserts a 'postal_locator' and 'entity_locator_participation' entry for each address provided
  private void insertAddresses(String id, Bundle bundle) {
    bundle.entry()
        .stream()
        .flatMap(e -> e.resource().address().stream())
        .forEach(a -> insertAddress(id, a));
  }

  private void insertAddress(String id, Address address) {
    template.update(
        INSERT_ADDRESS,
        id,
        address.line().get(0),
        address.city(),
        address.state(),
        address.postalCode());

    template.update(INSERT_ENTITY_LOCATOR_PARTICIPATION, id, id);
  }

  // Inserts an 'entity_id' entry for each identifier provided (currently expects only SSN)
  private void insertIdentifiers(String id, Bundle bundle) {
    bundle.entry()
        .stream()
        .flatMap(e -> e.resource().identifier().stream())
        .forEach(i -> template.update(INSERT_SSN, id, i.value()));
  }
}
