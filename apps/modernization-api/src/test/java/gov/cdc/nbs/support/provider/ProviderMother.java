package gov.cdc.nbs.support.provider;

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
class ProviderMother {

  private static final String DELETE_IN = """
      delete from Participation where subject_class_cd = 'PSN' and subject_entity_uid in (:identifiers)
      delete from Person_name where person_uid in (:identifiers);
      delete from Person where person_uid in (:identifiers);
      delete from Entity where entity_uid in (:identifiers);
      """;

  private static final String CREATE = """
      insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
      insert into Person(person_uid, version_ctrl_nbr, cd) values (:identifier, 1, 'PRV');
      
      insert into Person_name(
        person_uid,
        person_name_seq,
        nm_use_cd,
        nm_prefix,
        first_nm,
        last_nm,
        status_cd,
        status_time
      ) values (
        :identifier,
        1,
        'L',
        :prefix,
        :first,
        :last,
        'A',
        GETDATE()
      )
      """;

  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final Available<ProviderIdentifier> available;
  private final Active<ProviderIdentifier> active;
  private final TestingDataCleaner<Long> cleaner;

  ProviderMother(
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client,
      final Available<ProviderIdentifier> available,
      final Active<ProviderIdentifier> active
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

  void create(final String prefix, final String first, final String last) {

    long identifier = idGenerator.next();

    client.sql(CREATE)
        .param("identifier", identifier)
        .param("prefix", prefix)
        .param("first", first)
        .param("last", last)
        .update();

    ProviderIdentifier.Name name = new ProviderIdentifier.Name(prefix, first, last);

    ProviderIdentifier created = new ProviderIdentifier(identifier, name);
    this.cleaner.include(identifier);

    this.available.available(created);
    this.active.active(created);

  }

}
