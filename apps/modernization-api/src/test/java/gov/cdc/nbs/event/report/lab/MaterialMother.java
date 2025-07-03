package gov.cdc.nbs.event.report.lab;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.testing.data.TestingDataCleaner;
import gov.cdc.nbs.testing.identity.SequentialIdentityGenerator;
import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PreDestroy;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
class MaterialMother {

  private static final String CREATE = """
      insert into Entity (entity_uid, class_cd) values (:identifier, 'MAT');
      
      insert into Material(
          material_uid,
          local_id,
          cd,
          version_ctrl_nbr,
          record_status_cd,
          record_status_time,
          add_time,
          add_user_id
      ) values (
          :identifier,
          :local,
          :source,
          1,
          'ACTIVE',
          :addedOn,
          :addedOn,
          :addedBy
      );
      
      insert into Participation(
          act_uid,
          act_class_cd,
          type_cd,
          record_status_cd,
          record_status_time,
          add_user_id,
          add_time,
          subject_class_cd,
          subject_entity_uid
      ) values (
          :report,
          'OBS',
          'SPC',
          'ACTIVE',
          :addedOn,
          :addedBy,
          :addedOn,
          'MAT',
          :identifier
      );
      """;

  private static final String DELETE = """
      delete from Participation where subject_class_cd = 'MAT' and subject_entity_uid in (:identifiers);
      
      delete from Material where material_uid in (:identifiers);
      
      delete from Entity where class_cd = 'MAT' and entity_uid in (:identifiers);
      """;

  private final MotherSettings settings;
  private final SequentialIdentityGenerator idGenerator;
  private final JdbcClient client;
  private final TestingDataCleaner<Long> cleaner;

  MaterialMother(
      final MotherSettings settings,
      final SequentialIdentityGenerator idGenerator,
      final JdbcClient client
  ) {
    this.settings = settings;
    this.idGenerator = idGenerator;
    this.client = client;
    this.cleaner = new TestingDataCleaner<>(client, DELETE, "identifiers");
  }

  @PreDestroy
  void reset() {
    this.cleaner.clean();
  }

  void create(
      final LabReportIdentifier report,
      final String source
  ) {
    long identifier = idGenerator.next();
    String local = idGenerator.nextLocal("MAT");

    client.sql(CREATE)
        .param("identifier", identifier)
        .param("source", source)
        .param("local", local)
        .param("report", report.identifier())
        .param("addedOn", this.settings.createdOn())
        .param("addedBy", this.settings.createdBy())
        .update();

    this.cleaner.include(identifier);

  }
}
