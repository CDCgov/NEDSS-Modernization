package gov.cdc.nbs.patient.demographics.name;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.support.util.RandomUtil;
import java.time.LocalDate;
import java.util.Locale;
import net.datafaker.Faker;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class PatientNameDemographicApplier {

  private final JdbcClient client;
  private final Faker faker;

  public PatientNameDemographicApplier(final JdbcClient client) {
    this.client = client;
    this.faker = new Faker(Locale.of("en-us"));
  }

  public void withName(final PatientIdentifier identifier) {
    withName(
        identifier,
        RandomUtil.dateInPast(),
        "L",
        faker.name().firstName(),
        faker.name().firstName(),
        faker.name().lastName(),
        null);
  }

  public void withName(
      final PatientIdentifier identifier,
      final String type,
      final String first,
      final String last) {
    withName(identifier, RandomUtil.dateInPast(), type, first, last);
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String last) {
    withName(identifier, asOf, type, first, null, last, null);
  }

  public void withName(
      final PatientIdentifier identifier,
      final LocalDate asOf,
      final String type,
      final String first,
      final String middle,
      final String last,
      final String suffix) {

    this.client
        .sql(
            """
            insert into Person_name (
                person_uid,
                person_name_seq,
                as_of_date,
                nm_use_cd,
                first_nm,
                first_nm_sndx,
                middle_nm,
                last_nm,
                last_nm_sndx,
                nm_suffix,
                status_cd,
                status_time,
                add_time,
                last_chg_time,
                record_status_time,
                record_status_cd
            ) values (
                :patient,
                (select count(*) from Person_name where person_uid = :patient),
                :asOf,
                :type,
                :first,
                soundex(:first),
                :middle,
                :last,
                soundex(:last),
                :suffix,
                'A',
                getDate(),
                getDate(),
                getDate(),
                getDate(),
                'ACTIVE'
            );
            """)
        .param("patient", identifier.id())
        .param("asOf", asOf)
        .param("type", type)
        .param("first", first)
        .param("middle", middle)
        .param("last", last)
        .param("suffix", suffix)
        .update();
  }
}
