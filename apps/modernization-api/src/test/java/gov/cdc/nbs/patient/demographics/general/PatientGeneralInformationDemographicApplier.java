package gov.cdc.nbs.patient.demographics.general;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDate;

class PatientGeneralInformationDemographicApplier {

  private final JdbcClient client;

  PatientGeneralInformationDemographicApplier(final JdbcClient client) {
    this.client = client;
  }


  void withAsOf(final PatientIdentifier identifier, final LocalDate value) {
    client.sql("update person set as_of_date_general = ? where person_uid = ?")
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
