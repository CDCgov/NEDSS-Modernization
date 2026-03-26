package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import java.time.LocalDate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class PatientIdentificationDemographicApplier {

  private final JdbcClient client;

  public PatientIdentificationDemographicApplier(final JdbcClient client) {
    this.client = client;
  }

  public void withIdentification(final PatientIdentifier identifier) {
    withIdentification(
        identifier, RandomUtil.oneFrom("DL", "SS"), RandomUtil.getRandomNumericString(8));
  }

  public void withIdentification(
      final PatientIdentifier identifier, final String type, final String value) {

    withIdentification(
        identifier, type, RandomUtil.maybeOneFrom("OTH", "SSA"), value, RandomUtil.dateInPast());
  }

  public void withIdentification(
      final PatientIdentifier identifier,
      final String type,
      final String issuer,
      final String value,
      final LocalDate asOf) {
    this.client
        .sql(
            """
            insert into Entity_id (
                entity_uid,
                entity_id_seq,
                as_of_date,
                type_cd,
                assigning_authority_cd,
                root_extension_txt,
                add_time,
                record_status_time,
                record_status_cd
            ) values (
                :patient,
                (select count(*) from Entity_id where entity_uid = :patient),
                :asOf,
                :type,
                :issuer,
                :value,
                getDate(),
                getdate(),
                'ACTIVE'
            );
            """)
        .param("patient", identifier.id())
        .param("asOf", asOf)
        .param("type", type)
        .param("issuer", issuer)
        .param("value", value)
        .update();
  }
}
