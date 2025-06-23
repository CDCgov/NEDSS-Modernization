package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class PatientGeneralInformationDemographicApplier {

  private final JdbcClient client;

  PatientGeneralInformationDemographicApplier(final JdbcClient client) {
    this.client = client;
  }


  void withMaritalStatus(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    marital_status_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withMaidenName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    mothers_maiden_nm = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withAdultsInResidence(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final int value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    adults_in_house_nbr = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withChildrenInResidence(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final int value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    children_in_house_nbr = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withPrimaryOccupation(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    occupation_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withEducationLevel(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    education_level_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withPrimaryLanguage(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    prim_lang_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withSpeaksEnglish(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String value
  ) {
    client.sql(
            """
                update person set
                    as_of_date_general = coalesce(?, as_of_date_general, getDate()),
                    speaks_english_cd = ?
                where person_uid = ?
                """
        )
        .param(asOf)
        .param(value)
        .param(identifier.id())
        .update();
  }

  void withStateHIVCase(final PatientIdentifier identifier, final String value) {
    client.sql("""
            update person set
                as_of_date_general = coalesce(as_of_date_general, getDate()),
                ehars_id = ?
            where person_uid = ?
            """)
        .params(value, identifier.id())
        .update();

  }
}
