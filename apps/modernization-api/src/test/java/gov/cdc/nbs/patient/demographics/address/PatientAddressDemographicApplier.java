package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Locale;

@Component
public class PatientAddressDemographicApplier {

  private final JdbcClient client;
  private final AddressIdentifierGenerator addressIdentifierGenerator;
  private final Faker faker;

  public PatientAddressDemographicApplier(
      final JdbcClient client,
      final AddressIdentifierGenerator addressIdentifierGenerator
  ) {
    this.client = client;
    this.addressIdentifierGenerator = addressIdentifierGenerator;
    this.faker = new Faker(Locale.of("en-us"));
  }

  public void withAddress(final PatientIdentifier identifier) {
    withAddress(
        identifier,
        "H",
        "H",
        faker.address().streetAddress(),
        faker.address().city(),
        null,
        randomState(),
        faker.address().zipCode(),
        LocalDate.now()
    );
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    withAddress(identifier, "H", use, address, city, county, state, zip);
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip
  ) {
    withAddress(identifier, type, use, address, city, county, state, zip, LocalDate.now());
  }

  public void withAddress(
      final PatientIdentifier identifier,
      final String type,
      final String use,
      final String address,
      final String city,
      final String county,
      final String state,
      final String zip,
      final LocalDate asOf
  ) {

    long locator = this.addressIdentifierGenerator.generate();

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
                    'PST'
                );
                
                insert into dbo.Postal_locator (
                    postal_locator_uid,
                    street_addr1,
                    city_desc_txt,
                    cnty_cd,
                    state_cd,
                    zip_cd,
                    add_time,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                ) values (
                    :locator,
                    :address,
                    :city,
                    :county,
                    :state,
                    :zip,
                    getDate(),
                    getDate(),
                    'ACTIVE',
                    getDate()
                );
                """
        ).param("asOf", asOf)
        .param("type", type)
        .param("use", use)
        .param("address", address)
        .param("city", city)
        .param("county", county)
        .param("zip", zip)
        .param("state", state)
        .param("locator", locator)
        .param("patient", identifier.id())
        .update();
  }

  private static final String[] STATES =
      {"01", "02", "04", "05", "06", "08", "09", "10", "11", "12", "13", "15", "16", "17", "18", "19", "20", "21", "22",
          "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
          "41", "42", "44", "45", "46", "47", "48", "49", "50", "51", "53", "54", "55", "56", "60", "64", "66", "68",
          "69", "70", "72", "78"};

  private static String randomState() {
    return RandomUtil.oneFrom(STATES);
  }
}
