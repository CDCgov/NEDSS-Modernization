package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.data.LimitString;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class PatientEmailDemographicApplier {

  private final JdbcClient client;
  private final PhoneIdentifierGenerator phoneIdentifierGenerator;
  private final Faker faker;

  public PatientEmailDemographicApplier(final JdbcClient client, final PhoneIdentifierGenerator phoneIdentifierGenerator) {
    this.client = client;
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
    this.faker = new Faker(Locale.of("en-us"));
  }

  public void withEmail(final PatientIdentifier identifier) {
    withEmail(identifier, LimitString.toMaxLength(faker.internet().emailAddress(), 100));
  }

  public void withEmail(final PatientIdentifier identifier, final String email) {
    withEmail(
        identifier,
        "NET",
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        email,
        RandomUtil.dateInPast()
    );
  }

  public void withEmail(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String email,
      final LocalDate asOf
  ) {
    long locator = this.phoneIdentifierGenerator.generate();

    client.sql(
            """
                --- Entity Participation
                insert into Entity_locator_participation (
                    version_ctrl_nbr,
                    entity_uid,
                    locator_uid,
                    add_time,
                    last_chg_time,
                    record_status_cd,
                    record_status_time,
                    status_cd,
                    status_time,
                    as_of_date,
                    use_cd,
                    cd,
                    class_cd
                ) values (
                    1,
                    :patient,
                    :locator,
                    getDate(),
                    getDate(),
                    'ACTIVE',
                    getDate(),
                    'A',
                    getDate(),
                    :asOf,
                    :use,
                    :type,
                    'TELE'
                );
                
                insert into Tele_locator (
                      tele_locator_uid,
                      email_address,
                      add_time,
                      last_chg_time,
                      record_status_cd,
                      record_status_time
                ) values (
                      :locator,
                      :email,
                      getDate(),
                      getDate(),
                      'ACTIVE',
                      getDate()
                );
                """
        ).param("asOf", asOf)
        .param("type", type)
        .param("use", use)
        .param("email", email)
        .param("locator", locator)
        .param("patient", identifier.id())
        .update();
  }
}
