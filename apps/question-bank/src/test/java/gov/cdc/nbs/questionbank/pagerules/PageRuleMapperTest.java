package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageRuleMapperTest {

  @InjectMocks private PageRuleMapper pageRuleMapper;

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
    when(resultSet.getString(14)).thenReturn("1008,1007,1012");
    when(resultSet.getLong(15)).thenReturn(10l);
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
    when(resultSet.getString(14)).thenReturn(null);
    when(resultSet.getLong(15)).thenReturn(10l);
    Rule actualResponse = pageRuleMapper.mapRow(resultSet, 1);

    assertNull(actualResponse.sourceValues());
    assertTrue(actualResponse.targets().isEmpty());
    assertNull(actualResponse.ruleFunction());
    assertNull(actualResponse.comparator());
    assertNull(actualResponse.targetType());
  }

  @Test
  void testGetComponentType() throws SQLException {
    String targetIdentifiers =
        "test1,test1,test3,test4,test5,test6,test7,test8,test9,test10,test11,test12,"
            + "test13,test14,test15,test16,test17,test18,test19,test20,test21,test22,test23,test24,test25,test26,test27";
    String targetTypes =
        "1003,1011,1012,1022,1023,1007,1013,1001,1006,1024,1025,1027,1028,1008,1026,1029,1014,1000,1009"
            + ",1017,1019,1030,1032,1033,1034,1035,1036";

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
    when(resultSet.getString(11)).thenReturn("label123");
    when(resultSet.getString(12)).thenReturn("codeSetName");
    when(resultSet.getString(13)).thenReturn("");
    when(resultSet.getLong(15)).thenReturn(10l);
    when(resultSet.getString(10)).thenReturn(targetIdentifiers);
    when(resultSet.getString(14)).thenReturn(targetTypes);
    when(resultSet.getLong(15)).thenReturn(10l);
    Rule actualResponse = pageRuleMapper.mapRow(resultSet, 1);
    for (Rule.Target target : actualResponse.targets()) {
      assertNotNull(target.label());
    }
  }
}
