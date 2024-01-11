package gov.cdc.nbs.questionbank.valueset.read;

import gov.cdc.nbs.questionbank.valueset.RaceConceptFinder;
import gov.cdc.nbs.questionbank.valueset.RaceConceptMapper;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.util.ValueSetConstants;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RaceConceptFinderTest {

  @Mock
  JdbcTemplate jdbcTemplate;

  @InjectMocks
  RaceConceptFinder raceConceptFinder;

  @Captor
  private ArgumentCaptor<PreparedStatementSetter> setterCaptor;

  public RaceConceptFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findRaceConceptCodes_validCodeSetName_returnListOfRaceConceptCodes() {
    List<Concept> expectedResponse = getListOfRaceConceptCodes();
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(),
        any(RaceConceptMapper.class))).thenReturn(expectedResponse);
    List<Concept> actualResponse = raceConceptFinder.findRaceConceptCodes(ValueSetConstants.RACE_CONCEPT_CODE_SET);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void findRaceConceptCodes_inValidCodeSetName_returnEmptyList() {
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(),
        any(RaceConceptMapper.class))).thenReturn(Collections.EMPTY_LIST);
    List<Concept> actualResponse = raceConceptFinder.findRaceConceptCodes("invalidCodeSetName");
    assertTrue(actualResponse.isEmpty());
  }

  @Test
  void testRaceConceptCodesParametersSet() {
    List<Concept> expectedResponse = getListOfRaceConceptCodes();
    when(jdbcTemplate.query(anyString(), setterCaptor.capture(), any(RowMapper.class)))
        .thenAnswer(invocation -> {
          PreparedStatementSetter setter = setterCaptor.getValue();
          PreparedStatement preparedStatement = mock(PreparedStatement.class);
          setter.setValues(preparedStatement);
          return expectedResponse;
        });
    List<Concept> actualResponse = raceConceptFinder.findRaceConceptCodes(ValueSetConstants.RACE_CONCEPT_CODE_SET);
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void testMapRow() throws SQLException {
    RaceConceptMapper mapper = new RaceConceptMapper();
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(1)).thenReturn("code");
    when(resultSet.getString(2)).thenReturn("P_RACE_CAT");
    when(resultSet.getString(3)).thenReturn("display");
    when(resultSet.getString(4)).thenReturn("longName");
    when(resultSet.getString(5)).thenReturn("codeSystem");
    when(resultSet.getString(6)).thenReturn("A");
    when(resultSet.getString(7)).thenReturn("2024-01-09T02:03:04Z");
    when(resultSet.getString(8)).thenReturn("2024-01-09T02:03:04Z");

    Concept response = mapper.mapRow(resultSet, 1);
    assertEquals("code", response.localCode());
    assertEquals("P_RACE_CAT", response.codeSetName());
    assertEquals("display", response.display());
    assertEquals("longName", response.longName());
    assertEquals("codeSystem", response.codeSystem());
    assertEquals("Active", response.status());

  }


  private List<Concept> getListOfRaceConceptCodes() {
    List<Concept> conceptList = new ArrayList<>();
    conceptList.add(new Concept("code", "P_RACE_CAT", "display",
        "longName", null, null,
        "codeSystem", "Active", Instant.now(), Instant.now()));
    return conceptList;
  }
}
