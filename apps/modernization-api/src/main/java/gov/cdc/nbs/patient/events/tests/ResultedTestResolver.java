package gov.cdc.nbs.patient.events.tests;

import com.google.common.collect.Multimap;
import gov.cdc.nbs.sql.MultiMapResultSetExtractor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class ResultedTestResolver {

  private static final String QUERY = """
      select
          [lab_result_components].target_act_uid          as [observation],
          coalesce(
                  [lab_test].lab_test_desc_txt,
                  [lab_result].cd_desc_txt
          )                                               as [name],
          [lab_result_status].[code_short_desc_txt]       as [status],
          coalesce(
                  [coded_result].lab_result_desc_txt,
                  [coded].display_name
          )                                               as [coded],
          [numeric].comparator_cd_1                       as [comparator],
          [numeric].numeric_value_1                       as [numeric],
          [numeric].numeric_scale_1                       as [scale],
          [numeric].high_range                            as [high_range],
          [numeric].low_range                             as [low_range],
          [numeric].numeric_unit_cd                       as [unit],
          [text].value_txt                                as [text]
      from  Act_relationship [lab_result_components]
              join observation [lab_result] with (nolock) on
                          [lab_result].observation_uid = [lab_result_components].[source_act_uid]
                      and [lab_result].obs_domain_cd_st_1 = 'Result'
      
          left join NBS_SRTE..Code_value_general [lab_result_status] with (nolock) on
                  [lab_result_status].[code_set_nm] = 'ACT_OBJ_ST'
              and [lab_result_status].code =  [lab_result].[status_cd]
      
          left join NBS_SRTE..Lab_test [lab_test] with (nolock) on
                  [lab_test].lab_test_cd = [lab_result].cd
      
          left join [Obs_value_coded] [coded] with (nolock) on
                  [coded].[observation_uid] = [lab_result].[observation_uid]
      
          left join NBS_SRTE..Lab_result [coded_result] with (nolock) on
                  [coded_result].[lab_result_cd] = [coded].code
      
          left join [Obs_value_numeric] [numeric] with (nolock) on
                  [numeric].[observation_uid] = [lab_result].[observation_uid]
              and [numeric].numeric_value_1 is not null
      
          left join [Obs_value_txt] [text] with (nolock) on
                  [text].observation_uid = [lab_result].[observation_uid]
              and [text].value_txt <> ''
              and [text].txt_type_cd <> 'N'
      
      where [lab_result_components].target_act_uid in (:identifiers)
              and [lab_result_components].type_cd = 'COMP'
              and [lab_result_components].source_class_cd = 'OBS'
              and [lab_result_components].target_class_cd = 'OBS'
      """;

  private final JdbcClient client;
  private final ResultSetExtractor<Multimap<Long, ResultedTest>> extractor;

  public ResultedTestResolver(final JdbcClient client) {
    this.client = client;

    RowMapper<ResultedTest> valueMapper = new ResultedTestRowMapper(
        new ResultedTestRowMapper.Column(2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    );

    this.extractor = new MultiMapResultSetExtractor<>(
        (rs, rowNum) -> rs.getLong(1),
        valueMapper
    );

  }

  public Map<Long, Collection<ResultedTest>> resolve(final Collection<Long> identifiers) {
    return identifiers.isEmpty()
        ? Collections.emptyMap()
        : execute(identifiers);
  }

  private Map<Long, Collection<ResultedTest>> execute(final Collection<Long> identifiers) {
    return this.client.sql(QUERY)
        .param("identifiers", identifiers)
        .query(extractor)
        .asMap();
  }
}
