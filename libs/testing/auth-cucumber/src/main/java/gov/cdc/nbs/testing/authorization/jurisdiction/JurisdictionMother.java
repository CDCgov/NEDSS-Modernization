package gov.cdc.nbs.testing.authorization.jurisdiction;

import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ScenarioScope
class JurisdictionMother {

  private static final String DELETE_IN = """
      delete
      from NBS_SRTE.[dbo].Jurisdiction_code
      where nbs_uid in (:identifiers);
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
  private final JdbcClient client;
  private final Available<JurisdictionIdentifier> available;
  private final Active<JurisdictionIdentifier> active;
  private final List<Long> identifiers;

  JurisdictionMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<JurisdictionIdentifier> available,
      final Active<JurisdictionIdentifier> active
  ) {
    this.idGenerator = idGenerator;
    this.client = client;
    this.identifiers = new ArrayList<>();
    this.available = available;
    this.active = active;
  }

  @PreDestroy
  void reset() {

    if (!identifiers.isEmpty()) {

      client.sql(DELETE_IN)
          .param("identifiers", identifiers)
          .update();

      this.identifiers.clear();
    }
  }

  void create(final String name) {

    long identifier = idGenerator.next();
    String code = String.valueOf(identifier);

    client.sql(CREATE)
        .param("identifier", identifier)
        .param("code", code)
        .param("name", name)
        .update();

    JurisdictionIdentifier created = new JurisdictionIdentifier(identifier, code, name);

    this.identifiers.add(identifier);

    this.available.available(created);
    this.active.active(created);
  }

}
