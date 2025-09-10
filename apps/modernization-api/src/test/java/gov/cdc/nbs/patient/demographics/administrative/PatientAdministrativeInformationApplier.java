package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class PatientAdministrativeInformationApplier {

  private final JdbcClient client;

  PatientAdministrativeInformationApplier(final JdbcClient client) {
    this.client = client;
  }

  void withAsOf(final PatientIdentifier identifier, final LocalDate value) {
    withAdministrative(identifier, value, null);
  }

  void withComment(final PatientIdentifier identifier, final String value) {
    withAdministrative(identifier, null, value);
  }

  void withAdministrative(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String comment
  ) {
    client.sql(
            "update person set as_of_date_admin = coalesce(:asOf, as_of_date_admin, getDate()), [description] = coalesce(:comment, [description]) where person_uid = :patient")
        .param("asOf", asOf)
        .param("comment", comment)
        .param("patient", identifier.id())
        .update();
  }
}
