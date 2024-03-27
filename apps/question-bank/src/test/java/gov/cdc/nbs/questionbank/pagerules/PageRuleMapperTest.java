package gov.cdc.nbs.questionbank.pagerules;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageRuleMapperTest {

  @InjectMocks
  private PageRuleMapper pageRuleMapper;


  @Test
  void testMapRow() throws SQLException {
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(1)).thenReturn(100l);
    when(resultSet.getLong(2)).thenReturn(123l);
    when(resultSet.getString(3)).thenReturn("Enable");
    when(resultSet.getString(4)).thenReturn("description");
    when(resultSet.getString(5)).thenReturn("test456");
    when(resultSet.getString(6)).thenReturn("test(  )test");
    when(resultSet.getString(7)).thenReturn("test1,test2,test3");
    when(resultSet.getString(8)).thenReturn("=");
    when(resultSet.getString(9)).thenReturn("QUESTION");
    when(resultSet.getString(10)).thenReturn("test456,test789,test123");
    when(resultSet.getString(11)).thenReturn("label123");
    when(resultSet.getString(12)).thenReturn("codeSetName");
    when(resultSet.getString(13)).thenReturn("test456_label##test789_label");
    when(resultSet.getLong(14)).thenReturn(10l);
    Rule actualResponse = pageRuleMapper.mapRow(resultSet, 1);
    validate(actualResponse);
    long rowsCount = pageRuleMapper.getTotalRowsCount();
    assertEquals(10l, rowsCount);
  }

  private void validate(Rule response) {
    assertEquals(100l, response.id());
    assertEquals(123l, response.template());
    assertEquals("ENABLE", response.ruleFunction().toString());
    assertEquals("description", response.description());
    assertEquals("test456", response.sourceQuestion().questionIdentifier());
    assertEquals("label123", response.sourceQuestion().label());
    assertEquals("codeSetName", response.sourceQuestion().codeSetName());
    assertEquals(true, response.anySourceValue());
    assertEquals(3, response.sourceValues().size());
    assertEquals("EQUAL_TO", response.comparator().toString());
    assertEquals("QUESTION", response.targetType().toString());
    assertEquals("test456", response.targets().get(0).targetIdentifier());
    assertEquals("test456_label", response.targets().get(0).label());
  }


  @Test
  void testMapRow_null_values() throws SQLException {
    ResultSet resultSet = mock(ResultSet.class);
    when(resultSet.getLong(1)).thenReturn(100l);
    when(resultSet.getLong(2)).thenReturn(123l);
    when(resultSet.getString(3)).thenReturn("xx");
    when(resultSet.getString(4)).thenReturn("description");
    when(resultSet.getString(5)).thenReturn("test456");
    when(resultSet.getString(6)).thenReturn("test( )test");
    when(resultSet.getString(7)).thenReturn(null);
    when(resultSet.getString(8)).thenReturn("xx");
    when(resultSet.getString(9)).thenReturn("xx");
    when(resultSet.getString(10)).thenReturn(null);
    when(resultSet.getString(11)).thenReturn("label123");
    when(resultSet.getString(12)).thenReturn("codeSetName");
    when(resultSet.getString(13)).thenReturn(null);
    when(resultSet.getLong(14)).thenReturn(10l);
    Rule actualResponse = pageRuleMapper.mapRow(resultSet, 1);

    assertNull(actualResponse.sourceValues());
    assertTrue(actualResponse.targets().isEmpty());
    assertNull(actualResponse.ruleFunction());
    assertNull(actualResponse.comparator());
    assertNull(actualResponse.targetType());
  }

}
