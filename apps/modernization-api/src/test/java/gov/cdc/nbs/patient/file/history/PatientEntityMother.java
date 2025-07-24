package gov.cdc.nbs.patient.file.history;

import gov.cdc.nbs.testing.support.Available;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ScenarioScope
class PatientEntityMother {


  private static final String DELETE_IN = """
        delete from Person_name where person_uid in (:identifiers);
        delete from Person_hist where person_uid in (:identifiers);
        delete from Person where person_uid in (:identifiers);
        delete from Entity where entity_uid in (:identifiers);
        """;

  private static final String CREATE = """
        insert into Entity(entity_uid, class_cd) values (:identifier, 'PSN');
        insert into Person(person_uid, version_ctrl_nbr, cd) values (:identifier, 1, 'PAT');
        insert into Person_name(
            person_uid,
            person_name_seq,
            nm_use_cd,
            first_nm,
            last_nm,
            status_cd,
            status_time
        ) values (
            :identifier,
            1,
            'L',
            :first,
            :last,
            'A',
            GETDATE()
        );
        insert into Person_hist(
            person_uid,
            version_ctrl_nbr,
            cd
        ) values (
            :identifier,
            1,
            'PAT'
        );
        """;

  private final JdbcClient client;
  private final Available<Long> available;

  PatientEntityMother(final JdbcClient client) {
    this.client = client;
    this.available = new Available<>();
  }

  void create(final long identifier,  final String first, final String last) {

    client.sql(CREATE)
        .param("identifier", identifier)
        .param("first", first)
        .param("last", last)
        .update();

    available.available(identifier);
  }

  void remove(long identifier) {
    client.sql(DELETE_IN)
        .param("identifiers", List.of(identifier))
        .update();
  }

  @PreDestroy
  void reset() {
    var identifiers = available.all().toList();
    if (!identifiers.isEmpty()) {
      client.sql(DELETE_IN)
          .param("identifiers", identifiers)
          .update();
      available.reset();
    }
  }
}
