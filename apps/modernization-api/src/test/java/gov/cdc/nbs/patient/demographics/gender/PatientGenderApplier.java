package gov.cdc.nbs.patient.demographics.gender;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class PatientGenderApplier {

  private final JdbcClient client;

  PatientGenderApplier(final JdbcClient client) {
    this.client = client;
  }

  void withGender(
      final PatientIdentifier patient,
      final String gender
  ) {
    withGender(patient, RandomUtil.dateInPast(), gender);
  }

  void withGender(
      final PatientIdentifier patient,
      final LocalDate asOf,
      final String gender

  ) {
    client.sql(
            """
                update person set
                    as_of_date_sex = ?,
                    curr_sex_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(gender)
        .param(patient.id())
        .update();
  }

  void withAdditionalGender(final PatientIdentifier patient, final String gender) {
    client.sql(
            """
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate()),
                    additional_gender_cd = ?
                where person_uid = ?
                """
        )
        .param(gender)
        .param(patient.id())
        .update();
  }

  void withUnknown(final PatientIdentifier patient, final String reason, final LocalDate asOf) {
    client.sql(
            """
                update person set
                    as_of_date_sex =:asOf,
                    curr_sex_cd = 'U',
                    sex_unk_reason_cd = :reason
                where person_uid = :patient
                """
        )
        .param("patient", patient.id())
        .param("asOf", asOf)
        .param("reason", reason)
        .update();
  }

  void withPreferredGender(final PatientIdentifier patient, final String gender) {
    client.sql(
            """
                update person set
                    as_of_date_sex = coalesce(as_of_date_sex, getDate()),
                    preferred_gender_cd = ?
                where person_uid = ?
                """
        )
        .param(gender)
        .param(patient.id())
        .update();
  }
}
