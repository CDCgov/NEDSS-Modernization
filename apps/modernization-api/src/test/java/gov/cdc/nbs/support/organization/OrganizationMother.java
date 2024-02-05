package gov.cdc.nbs.support.organization;

import gov.cdc.nbs.option.Option;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
class OrganizationMother {

  private static final String DELETE_IN = """
      delete
      from Organization
      where organization_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers) and class_cd = 'ORG';
      """;

  private static final String CREATE = """
      delete from Entity where entity_uid in (:identifier)
      insert into Entity(entity_uid, class_cd) values (:identifier, 'ORG');
      insert into Organization(organization_uid, display_nm, version_ctrl_nbr)
      values (:identifier, :name, 1);
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final NamedParameterJdbcTemplate template;
  private final Available<OrganizationIdentifier> available;
  private final Active<OrganizationIdentifier> active;

  OrganizationMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcTemplate template,
      final Available<OrganizationIdentifier> available,
      final Active<OrganizationIdentifier> active) {
    this.idGenerator = idGenerator;
    this.template = new NamedParameterJdbcTemplate(template);

    this.available = available;
    this.active = active;
  }

  void reset() {

    List<Long> created = this.available.all()
        .map(OrganizationIdentifier::identifier)
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

  void create(final String name) {

    long identifier = idGenerator.next();
    String localId = idGenerator.nextLocal("ORG");

    Map<String, ? extends Serializable> parameters = Map.of(
        "identifier", identifier,
        "name", name,
        "local", localId);

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate);

    OrganizationIdentifier created = new OrganizationIdentifier(identifier);

    this.available.available(created);
    this.active.active(created);
  }

}
