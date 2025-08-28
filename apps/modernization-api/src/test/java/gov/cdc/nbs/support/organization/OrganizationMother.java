package gov.cdc.nbs.support.organization;

import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class OrganizationMother {

  private static final String DELETE_IN = """
      delete
      from Organization
      where organization_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers) and class_cd = 'ORG';
      """;

  private static final String CREATE = """
      insert into Entity(entity_uid, class_cd) values (:identifier, 'ORG');
      insert into Organization(organization_uid, display_nm, local_id, version_ctrl_nbr)
      values (:identifier, :name, :local, 1);
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final Available<OrganizationIdentifier> available;
  private final Active<OrganizationIdentifier> active;
  private final TestingDataCleaner<Long> cleaner;

  OrganizationMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<OrganizationIdentifier> available,
      final Active<OrganizationIdentifier> active
  ) {
    this.idGenerator = idGenerator;
    this.client = client;

    this.available = available;
    this.active = active;
    this.cleaner = new TestingDataCleaner<>(client, DELETE_IN, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  void create(final String name) {

    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("ORG");

    client.sql(CREATE)
        .param("identifier", identifier)
        .param("name", name)
        .param("local", local)
        .update();

    this.cleaner.include(identifier);

    OrganizationIdentifier created = new OrganizationIdentifier(identifier);

    this.available.available(created);
    this.active.active(created);
  }

}
