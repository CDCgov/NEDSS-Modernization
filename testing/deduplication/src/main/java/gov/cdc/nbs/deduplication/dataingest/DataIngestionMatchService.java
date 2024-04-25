package gov.cdc.nbs.deduplication.dataingest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.deduplication.request.MatchRequest;
import gov.cdc.nbs.deduplication.request.MatchRequest.Identification;
import gov.cdc.nbs.deduplication.request.MatchRequest.Name;
import gov.cdc.nbs.deduplication.response.DataIngestionMatchResponse;

@Component
public class DataIngestionMatchService {

  private static final String CHECK_MATCH = """
      SELECT patient_uid
      FROM   edx_patient_match
      WHERE  type_cd = 'PAT'
             AND match_string = ?;
              """;


  private final JdbcTemplate template;

  public DataIngestionMatchService(final JdbcTemplate template) {
    this.template = template;
  }

  public DataIngestionMatchResponse match(final MatchRequest request) {
    long start = System.currentTimeMillis();
    List<String> matches = matchLocalId(request.localId());

    if (matches.isEmpty()) {
      matches = matchEntityIds(request.name(), request.identifications());
    }

    if (matches.isEmpty()) {
      matches = matchNameDobSex(request.name(), request.dateOfBirth(), request.currentSex());
    }

    long end = System.currentTimeMillis();

    return new DataIngestionMatchResponse(matches, end - start);
  }

  // Attempt to find a patient match using the local id
  private List<String> matchLocalId(String localId) {
    if (localId == null) {
      return new ArrayList<>();
    }
    return findMatches(localId.toUpperCase());
  }

  // Attempt to find a patient match using supplied identifications
  private List<String> matchEntityIds(Name name, List<Identification> identifications) {
    if (identifications == null || identifications.isEmpty()) {
      return new ArrayList<>();
    }
    List<String> identificationStrings = identifications.stream()
        .map(i -> formatIdentiferString(name, i))
        .filter(i -> i != null)
        .toList();
    List<String> matches = new ArrayList<>();

    identificationStrings.forEach(s -> matches.addAll(findMatches(s)));
    return matches;
  }

  // Attempt to find a patient match using a combination of name, date of birth and current sex
  private List<String> matchNameDobSex(Name name, String dateOfBirth, String sex) {
    String nameDobSexString = formatNameDateOfBirthSexString(name, dateOfBirth, sex);
    if (nameDobSexString == null) {
      return new ArrayList<>();
    }

    return findMatches(nameDobSexString);
  }

  // Submits a search query to the database for the provided matchString. Returns a list of person_uid if any are found
  private List<String> findMatches(String matchString) {
    return template.query(
        CHECK_MATCH,
        ps -> ps.setString(1, matchString),
        rowToString());
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

  private String formatIdentiferString(Name name, Identification identification) {
    if (identification.value() == null || "".equals(identification.value().trim()) ||
        identification.authority() == null || "".equals(identification.authority().trim()) ||
        identification.authorityDesc() == null || "".equals(identification.authorityDesc().trim()) ||
        identification.authorityIdType() == null || "".equals(identification.authorityIdType().trim())) {
      return null;
    }

    String identificationString = "%s^%s^%s^%s^%s".formatted(
        identification.value(),
        identification.type(),
        identification.authority(),
        identification.authorityDesc(),
        identification.authorityIdType())
        .toUpperCase();

    String nameString = formatNameString(name);
    if (nameString != null) {
      return "%s^%s".formatted(identificationString, nameString);
    }
    return identificationString;
  }

  private String formatNameString(Name name) {
    if (name == null ||
        name.first() == null ||
        "".equals(name.first().trim()) ||
        name.last() == null ||
        "".equals(name.last().trim())) {
      return null;
    }

    return "%s^%s".formatted(
        name.last(),
        name.first())
        .toUpperCase();
  }

  private String formatNameDateOfBirthSexString(Name name, String dateOfBirth, String sex) {
    if (name == null ||
        name.first() == null ||
        "".equals(name.first().trim()) ||
        name.last() == null ||
        "".equals(name.last().trim()) ||
        dateOfBirth == null ||
        "".equals(dateOfBirth.trim()) ||
        sex == null ||
        "".equals(sex.trim())) {
      return null;
    }

    return "%s^%s^%s^%s".formatted(
        name.last(),
        name.first(),
        dateOfBirth,
        sex)
        .toUpperCase();
  }

}
