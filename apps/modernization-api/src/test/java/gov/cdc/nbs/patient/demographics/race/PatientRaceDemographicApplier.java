package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.identity.MotherSettings;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static gov.cdc.nbs.support.util.RandomUtil.oneFrom;

@Component
public class PatientRaceDemographicApplier {

  private static final String[] RACES = {"2106-3", "2054-5", "2028-9", "U"};

  private final JdbcClient client;
  private final MotherSettings settings;

  PatientRaceDemographicApplier(final JdbcClient client, final MotherSettings settings) {
    this.client = client;
    this.settings = settings;
  }

  public void withRace(final PatientIdentifier patient) {
    withRace(patient, oneFrom(RACES));
  }

  public void withRace(final PatientIdentifier patient, final String race) {
    withRace(patient, LocalDate.now(), race);
  }

  void withRace(
      final PatientIdentifier patient,
      final LocalDate asOf,
      final String race
  ) {
    this.client.sql(
            """
                insert into Person_race (
                    person_uid,
                    as_of_date,
                    race_cd,
                    race_category_cd,
                    add_user_id,
                    add_time,
                    last_chg_user_id,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                ) values (
                    :patient,
                    :asOf,
                    :race,
                    :race,
                    :addedBy,
                    :addedOn,
                    :addedBy,
                    :addedOn,
                    'ACTIVE',
                    :addedOn
                );
                """
        )
        .param("patient", patient.id())
        .param("asOf", asOf)
        .param("race", race)
        .param("addedBy", settings.createdBy())
        .param("addedOn", settings.createdOn())
        .update();
  }

  void withRace(
      final PatientIdentifier patient,
      final String category,
      final String race
  ) {
    this.client.sql(
            """
                insert into Person_race (
                    person_uid,
                    as_of_date,
                    race_cd,
                    race_category_cd,
                    add_user_id,
                    add_time,
                    last_chg_user_id,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                )
                select distinct
                    person_uid,
                    as_of_date,
                    :race,
                    race_category_cd,
                    add_user_id,
                    add_time,
                    last_chg_user_id,
                    last_chg_time,
                    record_status_cd,
                    record_status_time
                from Person_race [race]
                where   [race].person_uid = :patient
                    and [race].race_category_cd = :category
                
                """
        )
        .param("patient", patient.id())
        .param("category", category)
        .param("race", race)
        .update();
  }
}
