package gov.cdc.nbs.support.provider;

import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
class ProviderMother {

  private static final String DELETE_IN = """
      delete from Participation where subject_class_cd = 'PSN' and subject_entity_uid in (:identifiers)
      delete from Person_name where person_uid in (:identifiers);
      delete from Person where person_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers) and class_cd = 'PSN';
      """;

  private static final String CREATE = """
      delete from Entity where entity_uid in (:identifier)
      insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
      insert into Person(person_uid, version_ctrl_nbr, cd) values (:identifier, 1, 'PRV');

      insert into Person_name(
        person_uid,
        person_name_seq,
        first_nm,
        last_nm,
        status_cd,
        status_time
      ) values (
        :identifier,
        1,
        :first,
        :last,
        'A',
        GETDATE()
      )
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final NamedParameterJdbcTemplate template;
  private final Available<ProviderIdentifier> available;
  private final Active<ProviderIdentifier> active;

  ProviderMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcTemplate template,
      final Available<ProviderIdentifier> available,
      final Active<ProviderIdentifier> active) {
    this.idGenerator = idGenerator;
    this.template = new NamedParameterJdbcTemplate(template);
    this.available = available;
    this.active = active;
  }

  void reset() {

    List<Long> created = this.available.all()
        .map(ProviderIdentifier::identifier)
        .toList();

    if (!created.isEmpty()) {

      Map<String, List<Long>> parameters = Map.of("identifiers", created);

      template.execute(
          DELETE_IN,
          new MapSqlParameterSource(parameters),
          PreparedStatement::executeUpdate);
      this.active.reset();
    }
  }

  void create(final String first, final String last) {

    long identifier = idGenerator.next();

    Map<String, ? extends Serializable> parameters = Map.of(
        "identifier", identifier,
        "first", first,
        "last", last);

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);


    ProviderIdentifier created = new ProviderIdentifier(identifier);

    this.available.available(created);
    this.active.active(created);

  }

}
