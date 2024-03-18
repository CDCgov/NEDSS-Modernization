package gov.cdc.nbs.questionbank.pagerules;

import java.util.Arrays;
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
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeset;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.entity.pagerule.QWaRuleMetadata;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceQuestion;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.exceptions.RuleNotFoundException;


@Component
class PageRuleFinder {
  private static final QWaRuleMetadata ruleTable = QWaRuleMetadata.waRuleMetadata;
  private static final QWaUiMetadata metadataTable = QWaUiMetadata.waUiMetadata;
  private static final QCodeset codesetTable = QCodeset.codeset;

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
             [question1].question_label          as [sourceQuestionLabel],
             [CodeSet].code_set_nm              as [sourceQuestionCodeSet],
             STRING_AGG(CONVERT(NVARCHAR(max),[question2].question_label), ', ') WITHIN GROUP
            (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
             as [targetQuestionLabels],
             (SELECT COUNT(DISTINCT [rule].wa_rule_metadata_uid)
              from WA_rule_metadata [rule]
                left join WA_UI_Metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
                left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
                left join WA_UI_Metadata [question2]
                    on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
                    where [rule].wa_template_uid =:pageId and [question1].wa_template_uid = :pageId and [question2].wa_template_uid = :pageId
             ) as [totalCount]
          from WA_rule_metadata [rule]
            left join WA_UI_Metadata [question1] on [rule].source_question_identifier = [question1].question_identifier
            left join [NBS_SRTE]..Codeset [CodeSet] on  [question1].code_set_group_id = [CodeSet].code_set_group_id
            left join WA_UI_Metadata [question2]
                on CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ',') > 0
                where [rule].wa_template_uid =:pageId and [question1].wa_template_uid = :pageId and [question2].wa_template_uid = :pageId
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
             STRING_AGG(CONVERT(NVARCHAR(max),[question2].question_label), ', ') WITHIN GROUP
            (ORDER BY CHARINDEX(',' + [question2].question_identifier + ',', ',' + [rule].target_question_identifier + ','))
             as [targetQuestionLabels],
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

  private final JPAQueryFactory factory;

  PageRuleFinder(
      final NamedParameterJdbcTemplate template,
      final PageRuleMapper mapper,
      final JPAQueryFactory factory) {
    this.template = template;
    this.mapper = mapper;
    this.factory = factory;
  }

  public Rule findById(final long rule) {
    Tuple row = factory.select(
        ruleTable.id, // id
        ruleTable.waTemplateUid, // page id
        ruleTable.ruleCd, // rule function ('Enable', 'Disable', etc)
        ruleTable.ruleDescText, // description
        ruleTable.sourceQuestionIdentifier, // source question
        metadataTable.questionLabel, // source label
        ruleTable.ruleExpression, // expression
        ruleTable.sourceValues, // comma separated source values or 'Any Source Value'
        ruleTable.logic, // =, >, <, <>, >=, <=
        ruleTable.targetType, // QUESTION or SUBSECTION
        ruleTable.targetQuestionIdentifier, // comma separated list of target identifiers
        codesetTable.id.codeSetNm) // Valueset associated with source question)
        .from(ruleTable)
        .leftJoin(metadataTable)
        .on(ruleTable.sourceQuestionIdentifier.eq(metadataTable.questionIdentifier)
            .and(metadataTable.waTemplateUid.id.eq(ruleTable.waTemplateUid)))
        .leftJoin(codesetTable).on(metadataTable.codeSetGroupId.eq(codesetTable.codeSetGroup.id))
        .where(ruleTable.id.eq(rule))
        .fetchFirst();
    if (row == null) {
      throw new RuleNotFoundException(rule);
    }

    return toRule(rule, row);
  }

  private Rule toRule(final long rule, final Tuple row) {
    String[] targetIdentifiers = row.get(ruleTable.targetQuestionIdentifier).split(",");
    List<Target> targets = fetchTargets(targetIdentifiers, row.get(ruleTable.waTemplateUid));
    String sourceValue = row.get(ruleTable.sourceValues);
    boolean anySourceValue = row.get(ruleTable.ruleExpression).contains("(  )") ||
        "Any Source Value".equals(sourceValue);

    List<String> sourceValues;
    if (sourceValue != null) {
      sourceValues = Arrays.asList(sourceValue.split(","));
    } else {
      sourceValues = Arrays.asList();
    }

    return new Rule(
        rule,
        row.get(ruleTable.waTemplateUid),
        RuleFunction.valueFrom(row.get(ruleTable.ruleCd)),
        row.get(ruleTable.ruleDescText),
        new SourceQuestion(
            row.get(ruleTable.sourceQuestionIdentifier),
            row.get(metadataTable.questionIdentifier),
            row.get(codesetTable.id.codeSetNm)),
        anySourceValue,
        sourceValues,
        Comparator.valueFrom(row.get(ruleTable.logic)),
        TargetType.valueOf(row.get(ruleTable.targetType)),
        targets);
  }

  private List<Target> fetchTargets(final String[] identifiers, final long page) {
    return factory.select(
        metadataTable.questionIdentifier,
        metadataTable.questionLabel)
        .from(metadataTable)
        .where(metadataTable.questionIdentifier.in(identifiers).and(metadataTable.waTemplateUid.id.eq(page)))
        .fetch()
        .stream()
        .map(r -> new Target(r.get(metadataTable.questionIdentifier), r.get(metadataTable.questionLabel)))
        .toList();
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
          pageable.getSort().toList().get(0).getDirection().isAscending() ? Direction.DESC : Direction.ASC;
      if (!DEFAULT_SORT_COLUMN.equals(sort)) {
        query = findBySearchValue.replace(REPLACE_STRING,
            resolveSort(sort).replace(": ", " ") + " " + direction);
      } else {
        query = findBySearchValue.replace(REPLACE_STRING, "[rule]." + DEFAULT_SORT_COLUMN + " " + "desc");
      }
    }


    SqlParameterSource parameters = new MapSqlParameterSource(
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

    SqlParameterSource parameters = new MapSqlParameterSource(
        Map.of(
            "pageId", pageId));
    return this.template.query(query, parameters, mapper);
  }
}
