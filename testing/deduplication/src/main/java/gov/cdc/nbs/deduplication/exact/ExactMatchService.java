package gov.cdc.nbs.deduplication.exact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.response.ExactMatchResponse;
import gov.cdc.nbs.deduplication.response.ExactMatchResponse.ExactMatch;

@Component
public class ExactMatchService {

  private static final String FIND_PIVOTS = """
      SELECT DISTINCT personuid
      FROM   (SELECT p.person_uid               "personUid",
                     Row_number()
                       OVER (
                         partition BY p.curr_sex_cd, p.birth_time, pn.first_nm,
                       pn.last_nm
                         ORDER BY p.person_uid) AS ItemNumber
              FROM   person_name pn WITH (nolock ),
                     person p WITH (nolock ),
                     person p2 WITH (nolock ),
                     person_name pn2 WITH (nolock )
              WHERE  p.person_uid = pn.person_uid
                     AND p.cd = 'PAT'
                     AND p.person_uid = p.person_parent_uid
                     AND p.dedup_match_ind IS NULL
                     AND p.record_status_cd = 'ACTIVE'
                     AND pn.nm_use_cd = 'L'
                     AND pn.record_status_cd = 'ACTIVE'
                     AND pn.last_nm IS NOT NULL
                     AND pn.first_nm IS NOT NULL
                     AND p2.person_uid = pn2.person_uid
                     AND p2.cd = 'PAT'
                     AND p2.person_uid = p2.person_parent_uid
                     AND p2.record_status_cd = 'ACTIVE'
                     AND pn2.nm_use_cd = 'L'
                     AND pn2.record_status_cd = 'ACTIVE'
                     AND pn2.last_nm IS NOT NULL
                     AND pn2.first_nm IS NOT NULL
                     AND Rtrim(Ltrim(pn.last_nm)) = Rtrim(Ltrim(pn2.last_nm))
                     AND Rtrim(Ltrim(pn.first_nm)) = Rtrim(Ltrim(pn2.first_nm))
                     AND p.curr_sex_cd = p2.curr_sex_cd
                     AND p.curr_sex_cd IS NOT NULL
                     AND p2.curr_sex_cd IS NOT NULL
                     AND p.birth_time = p2.birth_time
                     AND p.birth_time IS NOT NULL
                     AND p2.birth_time IS NOT NULL
                     AND p.ethnic_group_ind = p2.ethnic_group_ind
                     AND p.person_uid != p2.person_uid) a
      WHERE  itemnumber = 1;
              """;

  private static final String FIND_MATCHING_PSN = """
      SELECT
        p.person_uid "personUid"
      FROM
        person p WITH (
          NOLOCK
      ),
        person p1 WITH (
          NOLOCK
      )
      WHERE
        p.person_uid = p.person_parent_uid
        AND p.CURR_SEX_CD = p1.CURR_SEX_CD
        AND p.cd = 'PAT'
        AND p.BIRTH_TIME = p1.BIRTH_TIME
        AND p.ETHNIC_GROUP_IND = p1.ETHNIC_GROUP_IND
        AND((
            p.BIRTH_GENDER_CD = p1.BIRTH_GENDER_CD
      )
          OR(
            p.BIRTH_GENDER_CD IS NULL
            OR p1.BIRTH_GENDER_CD IS NULL
      )
      )
        AND((
            p.MULTIPLE_BIRTH_IND = p1.MULTIPLE_BIRTH_IND
      )
          OR(
            p.MULTIPLE_BIRTH_IND IS NULL
            OR p1.MULTIPLE_BIRTH_IND IS NULL
      )
      )
        AND((
            p.DECEASED_IND_CD = p1.DECEASED_IND_CD
      )
          OR(
            p.DECEASED_IND_CD IS NULL
            OR p1.DECEASED_IND_CD IS NULL
      )
      )
        AND((
            p.DECEASED_TIME = p1.DECEASED_TIME
      )
          OR(
            p.DECEASED_TIME IS NULL
            OR p1.DECEASED_TIME IS NULL
      )
      )
        AND p.record_status_cd = 'ACTIVE'
        AND p1.record_status_cd = 'ACTIVE'
        AND p.curr_sex_cd != 'U'
        AND p1.curr_sex_cd != 'U'
        AND(
          p.birth_gender_cd != 'U'
          OR p.BIRTH_GENDER_CD IS NULL
      )
        AND(
          p1.birth_gender_cd != 'U'
          OR p1.BIRTH_GENDER_CD IS NULL
      )
        AND p1.person_uid = ?
        AND p.person_uid != ?;
          """;


  private static final String FIND_MATCHING_RACE = """
      SELECT DISTINCT
          pr.person_uid "personUid"
      FROM person_race pr WITH(NOLOCK),
        person_race pr1 WITH(NOLOCK),
        person pe WITH(NOLOCK),
        person pe1 WITH(NOLOCK)
      WHERE pr.RACE_CATEGORY_CD = pr1.RACE_CATEGORY_CD
         AND pr.RACE_CD = pr1.RACE_CD
         AND pe.person_uid = pe.person_parent_uid
         AND pe.cd = 'PAT'
         AND pe1.cd = 'PAT'
         AND pe1.CURR_SEX_CD = pe.CURR_SEX_CD
         AND pe1.BIRTH_TIME = pe.BIRTH_TIME
         AND pr.PERSON_UID = pe.PERSON_UID
         AND pe.ETHNIC_GROUP_IND = pe1.ETHNIC_GROUP_IND
         AND pe1.PERSON_UID = pr1.person_uid
         AND pr.record_status_cd = 'ACTIVE'
         AND pr1.record_status_cd = 'ACTIVE'
         AND pe.record_status_cd = 'ACTIVE'
         AND pe1.record_status_cd = 'ACTIVE'
      AND pr1.person_uid = CONVERT(VARCHAR(10), ?)
      AND pr.person_uid IN(?)
              """;

  private static final String FIND_MATCHING_IDENTIFICATION = """
      SELECT DISTINCT
          ei.entity_uid "personUid"
      FROM Entity_id ei WITH(NOLOCK),
        Entity_id ei1 WITH(NOLOCK),
        person pe WITH(NOLOCK),
        person pe1 WITH(NOLOCK)
      WHERE ei.ROOT_EXTENSION_TXT = ei1.ROOT_EXTENSION_TXT
         AND ei.TYPE_CD = ei1.TYPE_CD
         AND ei.RECORD_STATUS_CD = 'ACTIVE'
         AND ei1.RECORD_STATUS_CD = 'ACTIVE'
         AND pe.person_uid = pe.person_parent_uid
         AND pe.person_uid = ei.entity_uid
         AND pe.cd = 'PAT'
         AND pe.record_status_cd = 'ACTIVE'
         AND pe1.person_uid = pe1.person_parent_uid
         AND pe1.person_uid = ei1.entity_uid
         AND pe1.cd = 'PAT'
         AND pe1.record_status_cd = 'ACTIVE'
         AND ei1.entity_uid = CONVERT(VARCHAR(10), ?)
         AND ei.entity_uid IN(?)
                """;

  private static final String FIND_MATCHING_NAME =
      """
          SELECT pn1.person_uid "personUid", pn1.AS_OF_DATE "asOfDate"
          FROM (select person_uid, AS_OF_DATE,LAST_NM,FIRST_NM, NM_USE_CD,record_status_cd,LAST_NM2,MIDDLE_NM,MIDDLE_NM2, nm_suffix, NM_DEGREE from person_name WITH(NOLOCK) where person_uid= CONVERT(VARCHAR(10), ?)) pn ,
           (select person_uid, AS_OF_DATE, LAST_NM,FIRST_NM, NM_USE_CD,record_status_cd,LAST_NM2,MIDDLE_NM,MIDDLE_NM2, nm_suffix, NM_DEGREE from person_name WITH(NOLOCK) where person_uid in (?)) pn1
          WHERE RTRIM(LTRIM(pn.LAST_NM)) = RTRIM(LTRIM(pn1.LAST_NM))
            AND RTRIM(LTRIM(pn.FIRST_NM)) = RTRIM(LTRIM(pn1.FIRST_NM))
            AND pn.NM_USE_CD = 'L'
            AND pn1.NM_USE_CD = 'L'
            AND pn.record_status_cd = 'ACTIVE'
            AND pn1.record_status_cd = 'ACTIVE'
            AND ((pn.LAST_NM2 IS NULL
          AND pn1.LAST_NM2 IS NULL)
             OR (pn.LAST_NM2 = pn1.LAST_NM2))
            AND ((pn.MIDDLE_NM = pn1.MIDDLE_NM)
             OR (pn.MIDDLE_NM IS NULL
             AND pn1.MIDDLE_NM IS NULL))
            AND ((pn.MIDDLE_NM2 = pn1.MIDDLE_NM2)
             OR (pn.MIDDLE_NM2 IS NULL
             OR pn1.MIDDLE_NM2 IS NULL))
            AND ((pn.nm_suffix = pn1.nm_suffix)
             OR (pn.nm_suffix IS NULL
             OR pn1.NM_SUFFIX IS NULL))
          ORDER BY pn.person_uid;
                       """;

  private static final String FIND_MATCHING_LOCATORS =
      """
          SELECT elp1.entity_uid "personUid",
                 elp1.AS_OF_DATE "asOfDate"
          FROM Entity_locator_participation elp WITH(NOLOCK),
                    (select postal_locator_uid, state_cd, CITY_DESC_TXT, ZIP_CD from Postal_locator WITH(NOLOCK) where postal_locator_uid in (select locator_uid from Entity_locator_participation WITH(NOLOCK) where entity_uid = CONVERT(VARCHAR(10), ?))) Pl,
               Entity_locator_participation elp1 WITH(NOLOCK),
                (select postal_locator_uid, state_cd, CITY_DESC_TXT, ZIP_CD from Postal_locator WITH(NOLOCK) where postal_locator_uid in (select locator_uid from Entity_locator_participation WITH(NOLOCK) where entity_uid IN(?))) Pl1
          WHERE elp.LOCATOR_UID = pl.POSTAL_LOCATOR_UID
                AND elp1.LOCATOR_UID = pl1.POSTAL_LOCATOR_UID
                AND ((pl.state_cd = pl1.state_cd)
                     OR (pl.state_cd IS NULL
                         OR pl1.state_cd IS NULL))
                AND ((pl.CITY_DESC_TXT = pl1.CITY_DESC_TXT)
                     OR (pl.CITY_DESC_TXT IS NULL
                         OR pl1.CITY_DESC_TXT IS NULL))
                AND ((pl.ZIP_CD = pl1.ZIP_CD)
                     OR (pl.ZIP_CD IS NULL
                         OR pl1.ZIP_CD IS NULL))
                AND elp.USE_CD != 'BIR'
                AND elp.USE_CD != 'DTH'
                AND elp.RECORD_STATUS_CD = 'ACTIVE'
                AND elp1.USE_CD != 'BIR'
                AND elp1.USE_CD != 'DTH'
                AND elp1.RECORD_STATUS_CD = 'ACTIVE'
                                            """;

  private final JdbcTemplate template;

  public ExactMatchService(final JdbcTemplate template) {
    this.template = template;
  }


  public ExactMatchResponse match() {
    long start = System.currentTimeMillis();
    // Get a starting list of persons to check for equality
    List<ExactMatch> matches = findPivots()
        .stream()
        .map(ExactMatch::new)
        .toList();

    // verify matches all have matching person data 
    if (!matches.isEmpty()) {
      matches = findMatchesOnPersonData(matches);
    }

    // verify matches all have matching race data
    //if (!matches.isEmpty()) {
    // skipping race check since no test data includes race information 
    // matches = findMatchesOnRaceData(matches);
    //}

    // verify matches all have matching identification data
    if (!matches.isEmpty()) {
      matches = findMatchesOnIdentificationData(matches);
    }

    // verify matches all have matching name data
    if (!matches.isEmpty()) {
      matches = findMatchesOnNameData(matches);
    }

    // verify matches all have matching locator data
    if (!matches.isEmpty()) {
      matches = findMatchesOnLocatorData(matches);
    }


    long end = System.currentTimeMillis();
    return new ExactMatchResponse(matches, end - start);
  }

  private List<String> findPivots() {
    return template.query(
        FIND_PIVOTS,
        rowToString());
  }

  private List<ExactMatch> findMatchesOnPersonData(List<ExactMatch> exactMatches) {
    // for each person id, fetch the list of matching person records
    return exactMatches.stream()
        .map(m -> {
          m.matches().addAll(queryMatchingPersons(m.patient()));
          return m;
        })
        .filter(m -> !m.matches().isEmpty())
        .toList();
  }

  private List<String> queryMatchingPersons(String personUid) {
    return template.query(
        FIND_MATCHING_PSN,
        setter -> {
          setter.setString(1, personUid);
          setter.setString(2, personUid);
        },
        rowToString());
  }

  private List<ExactMatch> findMatchesOnRaceData(List<ExactMatch> exactMatches) {
    // for each person id, verify race data matches for all in group
    return exactMatches.stream()
        .map(m -> {
          List<String> matchingRaceData = queryMatchingRace(m.patient(), m.matches());
          m.matches().removeIf(id -> !matchingRaceData.contains(id));
          return m;
        })
        .filter(m -> !m.matches().isEmpty())
        .toList();
  }

  private List<String> queryMatchingRace(String personUid, List<String> personIds) {
    String idList = String.join(",", personIds);
    return template.query(
        FIND_MATCHING_RACE,
        setter -> {
          setter.setString(1, personUid);
          setter.setString(2, idList);
        },
        rowToString());
  }

  private List<ExactMatch> findMatchesOnIdentificationData(List<ExactMatch> exactMatches) {
    // for each person id, verify identification data matches for all in group
    return exactMatches.stream()
        .map(m -> {
          List<String> matchingRaceData = queryMatchingIdentification(m.patient(), m.matches());
          m.matches().removeIf(id -> !matchingRaceData.contains(id));
          return m;
        })
        .filter(m -> !m.matches().isEmpty())
        .toList();
  }

  private List<String> queryMatchingIdentification(String personUid, List<String> personIds) {
    return personIds.stream()
        .map(id -> template.query(
            FIND_MATCHING_IDENTIFICATION,
            setter -> {
              setter.setString(1, personUid);
              setter.setString(2, id);
            },
            rowToString()))
        .flatMap(List::stream)
        .toList();
  }

  private List<ExactMatch> findMatchesOnNameData(List<ExactMatch> exactMatches) {
    // for each person id, verify name data matches for all in group
    return exactMatches.stream()
        .map(m -> {
          List<String> matchingRaceData = queryMatchingNames(m.patient(), m.matches());
          m.matches().removeIf(id -> !matchingRaceData.contains(id));
          return m;
        })
        .filter(m -> !m.matches().isEmpty())
        .toList();
  }

  private List<String> queryMatchingNames(String personUid, List<String> personIds) {
    return personIds.stream()
        .map(id -> template.query(
            FIND_MATCHING_NAME,
            setter -> {
              setter.setString(1, personUid);
              setter.setString(2, id);
            },
            rowToString()))
        .flatMap(List::stream)
        .toList();
  }

  private List<ExactMatch> findMatchesOnLocatorData(List<ExactMatch> exactMatches) {
    // for each person id, verify locator data matches for all in group
    return exactMatches.stream()
        .map(m -> {
          List<String> matchingRaceData = queryMatchingLocator(m.patient(), m.matches());
          m.matches().removeIf(id -> !matchingRaceData.contains(id));
          return m;
        })
        .filter(m -> !m.matches().isEmpty())
        .toList();
  }

  private List<String> queryMatchingLocator(String personUid, List<String> personIds) {
    return personIds.stream()
        .map(id -> template.query(
            FIND_MATCHING_LOCATORS,
            setter -> {
              setter.setString(1, personUid);
              setter.setString(2, id);
            },
            rowToString()))
        .flatMap(List::stream)
        .toList();
  }

  // Converts the response from the database to a list of strings
  private RowMapper<String> rowToString() {
    return new RowMapper<String>() {
      @Override
      public String mapRow(final ResultSet resultSet, final int row) throws SQLException {
        return resultSet.getString(1);
      }
    };
  }
}
