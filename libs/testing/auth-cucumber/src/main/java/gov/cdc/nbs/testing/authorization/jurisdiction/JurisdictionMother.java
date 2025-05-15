package gov.cdc.nbs.testing.authorization.jurisdiction;

import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
class JurisdictionMother {

  private static final String DELETE_IN = """
      delete
      from NBS_SRTE.[dbo].Jurisdiction_code
      where code in (:identifiers);
      """;

  private static final String CREATE = """
      insert into NBS_SRTE.[dbo].Jurisdiction_code(
        nbs_uid,
        code,
        type_cd,
        code_desc_txt,
        code_short_desc_txt
      )
      values (:identifier, :code, 'ALL', :name, :name)
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final NamedParameterJdbcTemplate template;
  private final Available<JurisdictionIdentifier> available;
  private final Active<JurisdictionIdentifier> active;

  JurisdictionMother(
      final SequentialIdentityGenerator idGenerator,
      final NamedParameterJdbcTemplate template,
      final Available<JurisdictionIdentifier> available,
      final Active<JurisdictionIdentifier> active) {
    this.idGenerator = idGenerator;
    this.template = template;
    this.available = available;
    this.active = active;
  }

  @PostConstruct
  void reset() {

    List<String> created = this.available.all()
        .map(JurisdictionIdentifier::code)
        .toList();

    if (!created.isEmpty()) {

      Map<String, List<String>> parameters = Map.of("identifiers", created);

      template.execute(
          DELETE_IN,
          new MapSqlParameterSource(parameters),
          PreparedStatement::executeUpdate
      );
      this.active.reset();
    }
  }

  void create(final String name) {

    long identifier = idGenerator.next();
    String code = String.valueOf(identifier);

    Map<String, ? extends Serializable> parameters = Map.of(
        "identifier", identifier,
        "code", code,
        "name", name
    );

    template.execute(
        CREATE,
        new MapSqlParameterSource(parameters),
        PreparedStatement::executeUpdate
    );

    JurisdictionIdentifier created = new JurisdictionIdentifier(identifier, code);

    this.available.available(created);
    this.active.active(created);
  }

}
