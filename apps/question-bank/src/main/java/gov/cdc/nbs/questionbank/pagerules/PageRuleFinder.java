package gov.cdc.nbs.questionbank.pagerules;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

@Component
class PageRuleFinder {
  private static final String FIND_BY_RULE_ID =
      """
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
            [question1].question_label         as [sourceQuestionLabel],
            [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
            STRING_AGG(CONVERT(NVARCHAR(max),[question2].question_label), '##') WITHIN GROUP
             (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))as [targetQuestionLabels],
            STRING_AGG(CONVERT(NVARCHAR(max),[question2].nbs_ui_component_uid), ',') as [targetsNbsComponentIds],
                1                              as [TotalCount]
          from WA_rule_metadata [rule]
            left join WA_UI_Metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
            left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
            left join WA_UI_Metadata [question2]
                on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
                where [rule].wa_template_uid = [question1].wa_template_uid
                and  [question1].wa_template_uid = [question2].wa_template_uid
                and [rule].wa_rule_metadata_uid = :ruleId
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
             [rule].add_time
            """;

  private String findByPageId =
      """
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
             [question1].question_label         as [sourceQuestionLabel],
             [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
             STRING_AGG(CONVERT(NVARCHAR(max),[question2].question_label), '##') WITHIN GROUP
            (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
                                                as [targetQuestionLabels],
             STRING_AGG(CONVERT(NVARCHAR(max),[question2].nbs_ui_component_uid), ',') as [targetsNbsComponentIds],
             (SELECT COUNT(DISTINCT [rule].wa_rule_metadata_uid)
              from WA_rule_metadata [rule]
                left join WA_UI_Metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
                left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
                left join WA_UI_Metadata [question2]
                    on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
                    where [rule].wa_template_uid = [question1].wa_template_uid
                    and  [question1].wa_template_uid = [question2].wa_template_uid
                    and [rule].wa_template_uid=:pageId
             ) as [totalCount]
          from WA_rule_metadata [rule]
            left join WA_UI_Metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
            left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
            left join WA_UI_Metadata [question2]
                on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
                where [rule].wa_template_uid = [question1].wa_template_uid
                and  [question1].wa_template_uid = [question2].wa_template_uid
                and [rule].wa_template_uid=:pageId
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
             [rule].add_time
               """;

  private String findBySearchValue =
      """
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
             [question1].question_label         as [sourceQuestionLabel],
             [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
             STRING_AGG(CONVERT(NVARCHAR(max),[question2].question_label), '##') WITHIN GROUP
            (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
                                                as [targetQuestionLabels],
               STRING_AGG(CONVERT(NVARCHAR(max),[question2].nbs_ui_component_uid), ',') as [targetsNbsComponentIds],
             (SELECT COUNT(DISTINCT [rule].wa_rule_metadata_uid)
              from WA_rule_metadata [rule]
                left join WA_UI_metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
                left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
                left join WA_UI_metadata [question2]
                    on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
              where [rule].wa_template_uid =:pageId and [question1].wa_template_uid = :pageId and [question2].wa_template_uid = :pageId
                and
                (
                UPPER([rule].source_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
                OR UPPER([question1].question_label) LIKE CONCAT('%', UPPER(:searchValue), '%')
                OR UPPER([rule].target_question_identifier) LIKE CONCAT('%', UPPER(:searchValue), '%')
                OR CHARINDEX(',' + :searchValue + ',', ',' + [question2].question_label + ',') > 0
                OR [rule].wa_rule_metadata_uid  LIKE CONCAT('%', :searchValue, '%')
                )
             ) as [totalCount]
          from WA_rule_metadata [rule]
            left join WA_UI_metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
            left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
            left join WA_UI_metadata [question2]
                on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
          where [rule].wa_template_uid =:pageId and [question1].wa_template_uid = :pageId and [question2].wa_template_uid = :pageId
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
             [rule].add_time
             order by sortReplace
             offset :offset rows
             fetch next :pageSize rows only
             """;

  private final NamedParameterJdbcTemplate template;
  private final RowMapper<Rule> mapper;
  private static final String DEFAULT_SORT_COLUMN = "add_time";
  private static final String REPLACE_STRING = "sortReplace";

  PageRuleFinder(final NamedParameterJdbcTemplate template, PageRuleMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  Rule findByRuleId(final long ruleId) {
    MapSqlParameterSource parameters = new MapSqlParameterSource("ruleId", ruleId);
    List<Rule> result = this.template.query(FIND_BY_RULE_ID, parameters, mapper);
    return !result.isEmpty() ? result.get(0) : null;
  }

  private String resolveSort(String sort) {
    switch (sort) {
      case "sourcefields":
        return "[question1].question_label";
      case "function":
        return "[rule].rule_cd";
      case "values":
        return "[rule].source_values";
      case "logic":
        return "[rule].logic";
      case "id":
        return "[rule].wa_rule_metadata_uid";
      default:
        return DEFAULT_SORT_COLUMN;
    }
  }

  Page<Rule> searchPageRule(long pageId, SearchPageRuleRequest request, final Pageable pageable) {
    String searchValue = request.searchValue();
    int pageSize = pageable.getPageSize();
    int offset = pageable.getPageNumber() * pageSize;
    String query = findBySearchValue;

    if (pageable.getSort().isSorted()) {
      String sort = pageable.getSort().toList().get(0).getProperty().toLowerCase();
      Direction direction =
          pageable.getSort().toList().get(0).getDirection().isAscending()
              ? Direction.DESC
              : Direction.ASC;
      if (!DEFAULT_SORT_COLUMN.equals(sort)) {
        query =
            findBySearchValue.replace(
                REPLACE_STRING, resolveSort(sort).replace(": ", " ") + " " + direction);
      } else {
        query =
            findBySearchValue.replace(
                REPLACE_STRING, "[rule]." + DEFAULT_SORT_COLUMN + " " + "desc");
      }
    }

    SqlParameterSource parameters =
        new MapSqlParameterSource(
            Map.of(
                "pageId", pageId,
                "searchValue", (searchValue == null ? "" : searchValue),
                "offset", offset,
                "pageSize", pageSize));
    List<Rule> result = this.template.query(query, parameters, mapper);
    long totalRowsCount = ((PageRuleMapper) mapper).getTotalRowsCount();
    return new PageImpl<>(result, pageable, totalRowsCount);
  }

  List<Rule> getAllRules(long pageId) {
    String query = findByPageId;

    SqlParameterSource parameters = new MapSqlParameterSource(Map.of("pageId", pageId));
    return this.template.query(query, parameters, mapper);
  }
}
