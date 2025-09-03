package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class PatientPhoneDemographicApplier {

  private final JdbcClient client;
  private final PhoneIdentifierGenerator phoneIdentifierGenerator;
  private final Faker faker;

  public PatientPhoneDemographicApplier(
      final JdbcClient client,
      final PhoneIdentifierGenerator phoneIdentifierGenerator
  ) {
    this.client = client;
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
    this.faker = new Faker(Locale.of("en-us"));
  }

  public void withPhone(final PatientIdentifier identifier) {
    withPhone(
        identifier,
        RandomUtil.oneFrom("AN", "BP", "CP", "FAX", "PH"),
        RandomUtil.oneFrom("SB", "EC", "H", "MC", "WP", "TMP"),
        RandomUtil.getRandomString(15),
        faker.phoneNumber().cellPhone(),
        faker.phoneNumber().extension(),
        RandomUtil.dateInPast()
    );
  }

  public void withPhone(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String countryCode,
      final String number,
      final String extension,
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
                    cntry_cd,
                    phone_nbr_txt,
                    extension_txt,
                    add_time,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                ) values (
                    :locator,
                    :countryCode,
                    :number,
                    :extension,
                    getDate(),
                    getDate(),
                    'ACTIVE',
                    getDate()
                );
                """
        ).param("asOf", asOf)
        .param("type", type)
        .param("use", use)
        .param("countryCode", countryCode)
        .param("number", number)
        .param("extension", extension)
        .param("locator", locator)
        .param("patient", identifier.id())
        .update();

  }
}
