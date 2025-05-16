package gov.cdc.nbs.testing.authorization.programarea;

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
public class ProgramAreaMother {

  private static final String DELETE_IN = """
      delete
      from NBS_SRTE.[dbo].Program_area_code
      where nbs_uid in (:identifiers)
      """;

  private static final String CREATE = """
      insert into NBS_SRTE.[dbo].Program_area_code(
        nbs_uid,
        prog_area_cd,
        prog_area_desc_txt
      )
      values (:identifier, :code, :name)
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final Available<ProgramAreaIdentifier> available;
  private final Active<ProgramAreaIdentifier> active;
  private final List<Long> identifiers;

  ProgramAreaMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<ProgramAreaIdentifier> available,
      final Active<ProgramAreaIdentifier> active
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

    ProgramAreaIdentifier created = new ProgramAreaIdentifier(identifier, code);

    this.identifiers.add(identifier);

    this.available.available(created);
    this.active.active(created);
  }

}
