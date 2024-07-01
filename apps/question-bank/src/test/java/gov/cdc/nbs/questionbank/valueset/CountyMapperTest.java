package gov.cdc.nbs.questionbank.valueset;


import gov.cdc.nbs.questionbank.valueset.response.County;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountyMapperTest {

  private static final int CODE = 1;
  private static final int SHORT_DESCRIPTION = 2;
  private static final int DESCRIPTION = 3;
  private static final int CODE_SET_NAME = 4;

  CountyMapper mapper = new CountyMapper();

  @Test
  void findByStateCodeTest() throws SQLException {
    ResultSet rs = mock(ResultSet.class);
    when(rs.getString(CODE)).thenReturn("55077");
    when(rs.getString(SHORT_DESCRIPTION)).thenReturn("Wood,WV");
    when(rs.getString(DESCRIPTION)).thenReturn("Wood County");
    when(rs.getString(CODE_SET_NAME)).thenReturn("COUNTY_CCD");

    County response = mapper.mapRow(rs, 1);
    assertEquals("55077", response.code());
    assertEquals("Wood,WV", response.shortName());
    assertEquals("Wood County", response.longName());
    assertEquals("COUNTY_CCD", response.codeSetName());
  }

}
