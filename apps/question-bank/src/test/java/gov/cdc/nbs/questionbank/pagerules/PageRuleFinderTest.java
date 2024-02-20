package gov.cdc.nbs.questionbank.pagerules;

import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PageRuleFinderTest {

  @Mock
  NamedParameterJdbcTemplate template;

  @Mock
  PageRuleMapper mapper;

  @InjectMocks
  PageRuleFinder pageRuleFinder;


  public PageRuleFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getRule_validRuleId_returnRule() {
    Long ruleId = 123l;
    Rule expectedResponse = getRuleResponse();
    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Rule actualResponse = pageRuleFinder.findByRuleId(ruleId);
    assertNotNull(actualResponse);
  }

  @Test
  void getAllRules_validPageId_returnAllRules() {
    Long pageId = 100l;
    Rule expectedResponse = getRuleResponse();
    Pageable pageable = mock(Pageable.class);
    Sort sort = mock(Sort.class);
    when(sort.isSorted()).thenReturn(true);
    when(sort.toString()).thenReturn("searchField");
    when(pageable.getSort()).thenReturn(sort);

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Page<Rule> actualResponsPage = pageRuleFinder.findByPageId(pageId, pageable);
    assertNotNull(actualResponsPage);
  }

  @Test
  void getSearchRules_validSearchRequest_returnSearchRules() {
    Long pageId = 100l;
    Rule expectedResponse = getRuleResponse();
    Pageable pageable = mock(Pageable.class);
    Sort sort = mock(Sort.class);

    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");

    when(sort.isSorted()).thenReturn(true);
    when(sort.toString()).thenReturn("searchField");
    when(pageable.getSort()).thenReturn(sort);

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));
    Page<Rule> actualResponsPage = pageRuleFinder.searchPageRule(pageId, request, pageable);
    assertNotNull(actualResponsPage);
  }


  Rule getRuleResponse() {
    return new Rule(100,
        200l,
        Rule.RuleFunction.ENABLE,
        "testDescription",
        null,
        true,
        new ArrayList<>(),
        Rule.Comparator.EQUAL_TO,
        Rule.TargetType.QUESTION,
        new ArrayList<>()
    );
  }



}
