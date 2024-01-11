package gov.cdc.nbs.questionbank.valueset.read;

import gov.cdc.nbs.questionbank.valueset.RaceConceptFinder;
import gov.cdc.nbs.questionbank.valueset.RaceConceptMapper;
import gov.cdc.nbs.questionbank.valueset.response.RaceConcept;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
    List<RaceConcept> expectedResponse = getListOfRaceConceptCodes();
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(),
        any(RaceConceptMapper.class))).thenReturn(expectedResponse);
    List<RaceConcept> actualResponse = raceConceptFinder.findRaceConceptCodes("codeSetName");
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void findRaceConceptCodes_inValidCodeSetName_returnEmptyList() {
    when(jdbcTemplate.query(anyString(), (PreparedStatementSetter) any(),
        any(RaceConceptMapper.class))).thenReturn(Collections.EMPTY_LIST);
    List<RaceConcept> actualResponse = raceConceptFinder.findRaceConceptCodes("invalidCodeSetName");
    assertTrue(actualResponse.isEmpty());
  }

  @Test
  void testRaceConceptCodesParametersSet() {
    List<RaceConcept> expectedResponse = getListOfRaceConceptCodes();
    when(jdbcTemplate.query(anyString(), setterCaptor.capture(), any(RowMapper.class)))
        .thenAnswer(invocation -> {
          PreparedStatementSetter setter = setterCaptor.getValue();
          PreparedStatement preparedStatement = mock(PreparedStatement.class);
          setter.setValues(preparedStatement);
          return expectedResponse;
        });
    List<RaceConcept> actualResponse = raceConceptFinder.findRaceConceptCodes("codeSetName");
    assertEquals(expectedResponse, actualResponse);
  }

  @Test
  void testMapRow() throws SQLException {
    RaceConceptMapper mapper = new RaceConceptMapper();
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getString(1)).thenReturn("code");
    when(resultSet.getString(2)).thenReturn("codeSetName");
    when(resultSet.getString(3)).thenReturn("display");
    when(resultSet.getString(4)).thenReturn("longName");
    when(resultSet.getString(5)).thenReturn("codeSystem");
    when(resultSet.getString(6)).thenReturn("A");
    when(resultSet.getString(7)).thenReturn("2002-03-15 00:00:00.000");
    when(resultSet.getString(8)).thenReturn("2002-03-20 00:00:00.000");

    RaceConcept response = mapper.mapRow(resultSet, 1);
    assertEquals("code", response.localCode());
    assertEquals("codeSetName", response.codeSetName());
    assertEquals("display", response.display());
    assertEquals("longName", response.longName());
    assertEquals("codeSystem", response.codeSystem());
    assertEquals("Active", response.status());
    assertEquals("2002-03-15 00:00:00.000", response.effectiveFromTime());
    assertEquals("2002-03-20 00:00:00.000", response.effectiveToTime());
  }


  private List<RaceConcept> getListOfRaceConceptCodes() {
    List<RaceConcept> response = new ArrayList<>();
    response.add(new RaceConcept("code", "codeSetName", "display", "longName",
        "codeSystem", "Active", "2002-03-15 00:00:00.0", "2002-03-20 00:00:00.0"));
    return response;
  }
}
