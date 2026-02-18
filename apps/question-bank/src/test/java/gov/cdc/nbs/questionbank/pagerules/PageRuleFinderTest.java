package gov.cdc.nbs.questionbank.pagerules;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

class PageRuleFinderTest {

  @Mock NamedParameterJdbcTemplate template;

  @Mock PageRuleMapper mapper;

  @InjectMocks PageRuleFinder pageRuleFinder;

  public PageRuleFinderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getRule_validRuleId_returnRule() {
    Long ruleId = 123l;
    Rule expectedResponse = getRuleResponse();
    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Rule actualResponse = pageRuleFinder.findByRuleId(ruleId);
    assertNotNull(actualResponse);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getAllRules_withoutPageable() {
    Long pageId = 100L;
    Rule expectedResponse = getRuleResponse();

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    List<Rule> actualResponsPage = pageRuleFinder.getAllRules(pageId);
    assertNotNull(actualResponsPage);
  }

  @Test
  @SuppressWarnings("unchecked")
  void getAllRules_validPageId_returnAllRules() {
    Long pageId = 100l;
    Rule expectedResponse = getRuleResponse();
    Pageable pageable = PageRequest.of(0, 20);

    SearchPageRuleRequest request = new SearchPageRuleRequest("");

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));

    Page<Rule> actualResponsPage = pageRuleFinder.searchPageRule(pageId, request, pageable);
    assertNotNull(actualResponsPage);
  }

  @ParameterizedTest
  @SuppressWarnings("unchecked")
  @ValueSource(
      strings = {"sourcefields", "function", "values", "logic", "id", "random", "add_time"})
  void getSearchRules_validSearchRequest_returnSearchRules(String value) {
    Long pageId = 100l;
    Rule expectedResponse = getRuleResponse();
    Pageable pageable = PageRequest.of(0, 20).withSort(Sort.by(value));

    SearchPageRuleRequest request = new SearchPageRuleRequest("searchValue");

    when(template.query(any(String.class), any(MapSqlParameterSource.class), any(RowMapper.class)))
        .thenReturn(Collections.singletonList(expectedResponse));
    Page<Rule> actualResponsPage = pageRuleFinder.searchPageRule(pageId, request, pageable);
    assertNotNull(actualResponsPage);
  }

  Rule getRuleResponse() {
    return new Rule(
        100,
        200l,
        Rule.RuleFunction.ENABLE,
        "testDescription",
        null,
        true,
        new ArrayList<>(),
        Rule.Comparator.EQUAL_TO,
        Rule.TargetType.QUESTION,
        new ArrayList<>());
  }
}
