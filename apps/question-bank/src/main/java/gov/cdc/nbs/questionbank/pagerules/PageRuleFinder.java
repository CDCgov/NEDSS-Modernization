package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.pagerules.response.RuleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
class PageRuleFinder {
  private static final String FIND_BY_RULE_ID = """   
      select
           [rule].wa_rule_metadata_uid        as [ruleId],
           [rule].wa_template_uid             as [template],
           [rule].rule_cd                     as [function],
           [rule].rule_desc_txt               as [description],
           [rule].source_question_identifier  as [sourceQuestion],
           [rule].rule_expression             as [ruleExpression],
           [rule].source_values               as [sourceValues],
           [rule].logic                       as [comparator],
           [rule].target_type                 as [targetType],
           [rule].target_question_identifier  as [targetQuestions],
           0                                  as [TotalCount]
       from WA_rule_metadata [rule]
       where   [rule].wa_rule_metadata_uid =:ruleId
       """;

  private  String findByPageId = """   
      select
           [rule].wa_rule_metadata_uid        as [ruleId],
           [rule].wa_template_uid             as [template],
           [rule].rule_cd                     as [function],
           [rule].rule_desc_txt               as [description],
           [rule].source_question_identifier  as [sourceQuestion],
           [rule].rule_expression             as [ruleExpression],
           [rule].source_values               as [sourceValues],
           [rule].logic                       as [comparator],
           [rule].target_type                 as [targetType],
           [rule].target_question_identifier  as [targetQuestions],
           (SELECT COUNT(*) 
                  FROM WA_rule_metadata [rule] 
                      WHERE [rule].wa_rule_metadata_uid = :pageId)
                                              as [TotalCount]
       from WA_rule_metadata [rule]
       where   [rule].wa_template_uid =:pageId
       order by add_time
       offset :offset rows
       fetch next :pageSize rows only
       """;

  private  String findBySearchValue = """   
      select
           [rule].wa_rule_metadata_uid        as [ruleId],
           [rule].wa_template_uid             as [template],
           [rule].rule_cd                     as [function],
           [rule].rule_desc_txt               as [description],
           [rule].source_question_identifier  as [sourceQuestion],
           [rule].rule_expression             as [ruleExpression],
           [rule].source_values               as [sourceValues],
           [rule].logic                       as [comparator],
           [rule].target_type                 as [targetType],
           [rule].target_question_identifier  as [targetQuestions],
           (SELECT COUNT(*) 
                  FROM WA_rule_metadata [rule]  
                     where    UPPER(source_values) LIKE CONCAT('%', UPPER(:searchValue), '%') OR 
                     UPPER(target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
           ) as [TotalCount] 
                                                
       from WA_rule_metadata [rule]
       where    UPPER(source_values) LIKE CONCAT('%', UPPER(:searchValue), '%') OR 
                UPPER(target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
       order by add_time
       offset :offset rows
       fetch next :pageSize rows only
       """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<RuleResponse> mapper;
  private static final String DEFAULT_SORT_COLUMN = "add_time";

  PageRuleFinder(final NamedParameterJdbcTemplate template) {
    this.template = template;
    mapper = new PageRuleMapper();
  }

  RuleResponse findByRuleId(final long ruleId) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("ruleId", ruleId);
    List<RuleResponse> result = this.template.query(FIND_BY_RULE_ID, parameters, mapper);
    return !result.isEmpty() ? result.get(0) : null;
  }

  Page<RuleResponse> findByPageId(final long pageId, final Pageable pageable) {
    int pageSize = pageable.getPageSize();
    int offset = pageable.getPageNumber() * pageSize;
    Sort sort = pageable.getSort();
    String query=findByPageId;
    if (sort.isSorted()) {
      query = findByPageId.replace(DEFAULT_SORT_COLUMN,
          DEFAULT_SORT_COLUMN + "," + sort.toString().replace(": ", " "));
    }
    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of("pageId", pageId,
            "offset", offset,
            "pageSize", pageSize));
    List<RuleResponse> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = result.get(0).totalCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }


  Page<RuleResponse> searchPageRule(String searchValue, final Pageable pageable) {
    int pageSize = pageable.getPageSize();
    int offset = pageable.getPageNumber() * pageSize;
    Sort sort = pageable.getSort();
    String query=findBySearchValue;
    if (sort.isSorted()) {
      query = findBySearchValue.replace(DEFAULT_SORT_COLUMN,
          DEFAULT_SORT_COLUMN + "," + sort.toString().replace(": ", " "));
    }
    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "searchValue", searchValue,
            "offset", offset,
            "pageSize", pageSize
        )
    );
    List<RuleResponse> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = result.get(0).totalCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }
}
