package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static gov.cdc.nbs.support.util.RandomUtil.oneFrom;

@Component
public class PatientEthnicityDemographicApplier {

  private static final String[] ETHNICITIES = {
      "2135-2", // Hispanic
      "2186-5", // Non-Hispanic
      "UNK"     // Unknown
  };

  private final JdbcClient client;

  public PatientEthnicityDemographicApplier(final JdbcClient client) {
    this.client = client;
  }

  public void withEthnicity(final PatientIdentifier identifier) {
    withEthnicity(identifier, oneFrom(ETHNICITIES), LocalDate.now());
  }

  void withEthnicity(
      final PatientIdentifier identifier,
      final String ethnicity,
      final LocalDate asOf
  ) {
    client.sql(
            """
                update person set
                    ethnic_group_ind = ?,
                    as_of_date_ethnicity = ?,
                    ethnic_unk_reason_cd = null
                where person_uid = ?
                """
        )
        .param(ethnicity)
        .param(asOf)
        .param(identifier.id())
        .update();
  }

  void withSpecificEthnicity(
      final PatientIdentifier identifier,
      final String detail
  ) {
    client.sql(
            """
                insert into Person_ethnic_group(
                    person_uid,
                    ethnic_group_cd,
                    add_time,
                    record_status_cd
                ) values (
                    :patient,
                    :detail,
                    getDate(),
                    'ACTIVE'
                )
                """
        )
        .param("patient", identifier.id())
        .param("detail", detail)
        .update();
  }

  void withUnknownEthnicity(
      final PatientIdentifier identifier,
      final String reason,
      final LocalDate asOf
  ) {
    client.sql(
            """
                update person set
                    ethnic_group_ind = 'UNK',
                    ethnic_unk_reason_cd = ?,
                    as_of_date_ethnicity = ?
                where person_uid = ?
                """
        )
        .param(reason)
        .param(asOf)
        .param(identifier.id())
        .update();
  }

}
