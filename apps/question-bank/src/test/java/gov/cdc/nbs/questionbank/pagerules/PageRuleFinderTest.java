package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageRuleFinderTest {

  @Mock
  NamedParameterJdbcTemplate template;

  @InjectMocks
  PageRuleFinder pageRuleFinder;


  public PageRuleFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getRule_validRuleId_returnRule() {
    Long ruleId = 100l;
    RuleResponse expectedResponse = getRuleResponse();
    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    RuleResponse actualResponse = pageRuleFinder.findByRuleId(ruleId);
    assertNotNull(actualResponse);
  }

  @Test
  void getAllRules_validPageId_returnAllRules() {
    Long pageId = 100l;
    RuleResponse expectedResponse = getRuleResponse();
    Pageable pageable = mock(Pageable.class);
    Sort sort = mock(Sort.class);

    when(sort.isSorted()).thenReturn(true);
    when(sort.toString()).thenReturn("searchField");
    when(pageable.getSort()).thenReturn(sort);

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Page<RuleResponse> actualResponsPage = pageRuleFinder.findByPageId(pageId,pageable);
    assertNotNull(actualResponsPage);
    assertEquals(1,actualResponsPage.getTotalElements());
  }


  @Test
  void getSearchRules_validSearchRequest_returnSearchRules() {
    RuleResponse expectedResponse = getRuleResponse();
    Pageable pageable = mock(Pageable.class);
    Sort sort = mock(Sort.class);

    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");

    when(sort.isSorted()).thenReturn(true);
    when(sort.toString()).thenReturn("searchField");
    when(pageable.getSort()).thenReturn(sort);

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Page<RuleResponse> actualResponsPage = pageRuleFinder.searchPageRule(request.searchValue(),pageable);
    assertNotNull(actualResponsPage);
    assertEquals(1,actualResponsPage.getTotalElements());
  }


    @Test
    void testMapRow() throws SQLException {
      PageRuleMapper mapper = new PageRuleMapper();
      ResultSet resultSet = mock(ResultSet.class);
      when(resultSet.getLong(1)).thenReturn(100l);
      when(resultSet.getLong(2)).thenReturn(123l);
      when(resultSet.getString(3)).thenReturn("function");
      when(resultSet.getString(4)).thenReturn("description");
      when(resultSet.getString(5)).thenReturn("sourceQuestion");
      when(resultSet.getString(6)).thenReturn("ruleExpression");
      when(resultSet.getString(7)).thenReturn("sourceValues");
      when(resultSet.getString(8)).thenReturn("comparator");
      when(resultSet.getString(9)).thenReturn("targetType");
      when(resultSet.getString(10)).thenReturn("targetQuestions");
      when(resultSet.getLong(11)).thenReturn(10l);

      RuleResponse response = mapper.mapRow(resultSet, 1);
      assertEquals(100l, response.ruleId());
      assertEquals(123l, response.templateId());
      assertEquals("function", response.function());
      assertEquals("description", response.description());
      assertEquals("sourceQuestion", response.sourceQuestion());
      assertEquals("ruleExpression", response.ruleExpression());
      assertEquals("sourceValues", response.sourceValues());
      assertEquals("comparator", response.comparator());
      assertEquals("targetType", response.targetType());
      assertEquals("targetQuestions", response.targetQuestions());
      assertEquals(10l, response.totalCount());
    }



  RuleResponse getRuleResponse() {
    return new RuleResponse(100,
        200l,
        "testFunction",
        "testDescription",
        "test456",
        "test( )",
        "test1,test2",
        "=",
        "Question",
        "test456,test456",
        1
    );
  }
}
