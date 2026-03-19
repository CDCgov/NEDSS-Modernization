package gov.cdc.nbs.questionbank.valueset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.valueset.response.County;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@ExtendWith(MockitoExtension.class)
class CountyFinderTest {

  @InjectMocks CountyFinder countyfinder;

  @Mock NamedParameterJdbcTemplate template;

  @Test
  @SuppressWarnings("unchecked")
  void findByStateCodeTest() {
    String stateCode = "05";
    List<County> expectedResult = getCountyResponse();
    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(getCountyResponse());
    List<County> actualResult = countyfinder.findByStateCode(stateCode);
    assertNotNull(actualResult);
    assertFalse(actualResult.isEmpty());
    assertEquals(expectedResult, actualResult);
  }

  List<County> getCountyResponse() {
    return Arrays.asList(
        new County("55077", "Wood,WV", "Wood County", "COUNTY_CCD"),
        new County("55078", "Bennett,SD", "Bennett County", "COUNTY_CCD"));
  }
}
