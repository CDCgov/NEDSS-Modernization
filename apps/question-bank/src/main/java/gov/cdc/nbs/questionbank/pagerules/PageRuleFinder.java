package gov.cdc.nbs.questionbank.pagerules;

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
           [question].question_label          as [sourceQuestionLabel],
           [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
           [question].wa_question_uid         as [sourceQuestionId],
           0                                  as [TotalCount]
       from WA_rule_metadata [rule]
       left join WA_question [question] on [rule].source_question_identifier = [question].question_identifier
       left join [NBS_SRTE]..Codeset [CodeSet] on  [question].code_set_group_id = [CodeSet].code_set_group_id
       where  [rule].wa_rule_metadata_uid =:ruleId
       """;

  private String findByPageId = """
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
           [question].question_label          as [sourceQuestionLabel],
           [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
           [question].wa_question_uid         as [sourceQuestionId],
           (SELECT COUNT(*)
                  FROM WA_rule_metadata [rule]
                      WHERE [rule].wa_template_uid = :pageId)
                                              as [TotalCount]
       from WA_rule_metadata [rule]
       left join WA_question [question] on [rule].source_question_identifier = [question].question_identifier
       left join [NBS_SRTE]..Codeset [CodeSet] on  [question].code_set_group_id = [CodeSet].code_set_group_id
       where   [rule].wa_template_uid =:pageId
       order by [rule].add_time
       offset :offset rows
       fetch next :pageSize rows only
       """;

  private String findBySearchValue = """
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
           [question].question_label          as [sourceQuestionLabel],
           [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
           [question].wa_question_uid         as [sourceQuestionId],
           (SELECT COUNT(*)
                  FROM WA_rule_metadata [rule]
                     where  [rule].wa_template_uid =:pageId
                     and  ( UPPER(source_values) LIKE CONCAT('%', UPPER(:searchValue), '%')
                           OR UPPER(target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
                          )
           ) as [TotalCount]
       from WA_rule_metadata [rule]
       left join WA_question [question] on [rule].source_question_identifier = [question].question_identifier
       left join [NBS_SRTE]..Codeset [CodeSet] on  [question].code_set_group_id = [CodeSet].code_set_group_id
       where  [rule].wa_template_uid =:pageId
       and  ( UPPER(source_values) LIKE CONCAT('%', UPPER(:searchValue), '%')
             OR UPPER(target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
            )
       order by [rule].add_time
       offset :offset rows
       fetch next :pageSize rows only
       """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<Rule> mapper;
  private static final String DEFAULT_SORT_COLUMN = "add_time";



  PageRuleFinder(final NamedParameterJdbcTemplate template, PageRuleMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  Rule findByRuleId(final long ruleId) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("ruleId", ruleId);
    List<Rule> result = this.template.query(FIND_BY_RULE_ID, parameters, mapper);
    return !result.isEmpty() ? result.get(0) : null;
  }

  Page<Rule> findByPageId(final long pageId, final Pageable pageable) {
    int pageSize = pageable.getPageSize();
    int offset = pageable.getPageNumber() * pageSize;
    Sort sort = pageable.getSort();
    String query = findByPageId;
    if (sort.isSorted()) {
      query = findByPageId.replace(DEFAULT_SORT_COLUMN,
          DEFAULT_SORT_COLUMN + "," + sort.toString().replace(": ", " "));
    }
    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of("pageId", pageId,
            "offset", offset,
            "pageSize", pageSize));
    List<Rule> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = ((PageRuleMapper) mapper).getTotalRowsCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }


  Page<Rule> searchPageRule(long pageId, SearchPageRuleRequest request, final Pageable pageable) {
    String searchValue = request.searchValue();
    int pageSize = pageable.getPageSize();
    int offset = pageable.getPageNumber() * pageSize;
    Sort sort = pageable.getSort();
    String query = findBySearchValue;
    if (sort.isSorted()) {
      query = findBySearchValue.replace(DEFAULT_SORT_COLUMN,
          DEFAULT_SORT_COLUMN + "," + sort.toString().replace(": ", " "));
    }
    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "pageId", pageId,
            "searchValue", searchValue,
            "offset", offset,
            "pageSize", pageSize));
    List<Rule> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = ((PageRuleMapper) mapper).getTotalRowsCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }
}
