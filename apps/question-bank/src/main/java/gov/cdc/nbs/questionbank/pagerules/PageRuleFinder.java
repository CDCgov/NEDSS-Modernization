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
        [question1].question_label          as [sourceQuestionLabel],
        [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
        [question1].wa_question_uid         as [sourceQuestionId],
        STRING_AGG([question2].question_label, ',') WITHIN GROUP 
         (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',')) as [targetQuestionLabels],
            0                                  as [TotalCount]
      from WA_rule_metadata [rule]
       left join WA_question [question1] on [rule].source_question_identifier = [question1].question_identifier
       left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
       left join WA_question [question2]
         on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
      where  [rule].wa_rule_metadata_uid =:ruleId
          
      group by
        [rule].wa_rule_metadata_uid,
        [rule].wa_template_uid,
        [rule].rule_cd,
        [rule].rule_desc_txt,
        [rule].source_question_identifier,
        [rule].rule_expression,
        [rule].source_values,
        [rule].logic,
        [rule].target_type,
        [rule].target_question_identifier,
        [question1].question_label,
        [CodeSet].code_set_nm,
        [question1].wa_question_uid,
        [rule].add_time
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
         [question1].question_label          as [sourceQuestionLabel],
         [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
         [question1].wa_question_uid         as [sourceQuestionId],
         STRING_AGG([question2].question_label, ', ') WITHIN GROUP 
         (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
          as [targetQuestionLabels],
            (SELECT COUNT(*)
                    FROM WA_rule_metadata [rule]
                        WHERE [rule].wa_template_uid = :pageId)
             as [TotalCount]
       from WA_rule_metadata [rule]
        left join WA_question [question1] on [rule].source_question_identifier = [question1].question_identifier
        left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
        left join WA_question [question2]
         on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
       where  [rule].wa_template_uid =:pageId
       group by
         [rule].wa_rule_metadata_uid,
         [rule].wa_template_uid,
         [rule].rule_cd,
         [rule].rule_desc_txt,
         [rule].source_question_identifier,
         [rule].rule_expression,
         [rule].source_values,
         [rule].logic,
         [rule].target_type,
         [rule].target_question_identifier,
         [question1].question_label,
         [CodeSet].code_set_nm,
         [question1].wa_question_uid,
         [rule].add_time
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
         [question1].question_label          as [sourceQuestionLabel],
         [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
         [question1].wa_question_uid         as [sourceQuestionId],
         STRING_AGG([question2].question_label, ', ') WITHIN GROUP 
        (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
         as [targetQuestionLabels],
         (SELECT COUNT(*)
      from WA_rule_metadata [rule]
        left join WA_question [question1] on [rule].source_question_identifier = [question1].question_identifier
        left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
        left join WA_question [question2]
            on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
      where [rule].wa_template_uid =:pageId
        and 
        (
         UPPER([rule].source_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
         OR UPPER([question1].question_label) LIKE CONCAT('%', UPPER(:searchValue), '%')
         OR UPPER([rule].target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
         OR CHARINDEX(',' + :searchValue + ',', ',' + [question2].question_label + ',') > 0
         OR [rule].wa_rule_metadata_uid  LIKE CONCAT('%', :searchValue, '%')
        )
         ) as [TotalCount]
      from WA_rule_metadata [rule]
        left join WA_question [question1] on [rule].source_question_identifier = [question1].question_identifier
        left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
        left join WA_question [question2]
            on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
      where [rule].wa_template_uid =:pageId
        and 
        (
           UPPER([rule].source_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
           OR UPPER([question1].question_label) LIKE CONCAT('%', UPPER(:searchValue), '%')
           OR UPPER([rule].target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
           OR CHARINDEX(',' + :searchValue + ',', ',' + [question2].question_label + ',') > 0
           OR [rule].wa_rule_metadata_uid  LIKE CONCAT('%', :searchValue, '%')
        )
      group by
         [rule].wa_rule_metadata_uid,
         [rule].wa_template_uid,
         [rule].rule_cd,
         [rule].rule_desc_txt,
         [rule].source_question_identifier,
         [rule].rule_expression,
         [rule].source_values,
         [rule].logic,
         [rule].target_type,
         [rule].target_question_identifier,
         [question1].question_label,
         [CodeSet].code_set_nm,
         [question1].wa_question_uid,
         [rule].add_time
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
            "pageSize", pageSize
        )
    );
    List<Rule> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = ((PageRuleMapper) mapper).getTotalRowsCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }
}
