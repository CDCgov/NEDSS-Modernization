package gov.cdc.nbs.questionbank.page.component.tree;

import java.util.Collection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class FlattenedComponentFinder {

  private static final String QUERY =
      """
      select
          [component].wa_ui_metadata_uid          as [identifier],
          [component].nbs_ui_component_uid        as [type],
          [component].question_label              as [name],
          [component].display_ind                 as [visible],
          [component].order_nbr                   as [order],
          [component].question_group_seq_nbr      as [questionGroupSeq],
          [component].standard_nnd_ind_cd         as [is_standard_nnd],
          [component].standard_question_ind_cd    as [is_standard],
          [component].question_type               as [standard],
          [component].question_identifier         as [question],
          [component].data_type                   as [data_type],
          [component].sub_group_nm                as [sub_group],
          [component].[desc_txt]                  as [description],
          [component].[enable_ind]                as [enabled],
          [component].required_ind                as [required],
          [component].future_date_ind_cd          as [allow_future_dates],
          [component].coinfection_ind_cd          as [co-infection],
          [component].mask                        as [mask],
          [component].question_tool_tip           as [tool_tip],
          [component].default_value               as [default_value],
          [set].[code_set_nm]                     as [value_set],
          [component].admin_comment               as [adminComments],
          [component].field_size                  as [Field_length],
          [WaRdbMetadatum].rdb_table_nm           as [default_rdb_table_nm],
          [WaRdbMetadatum].rdb_column_nm          as [rdb_column_nm],
          [WaRdbMetadatum].rpt_admin_column_nm    as [Default_label_in_report],
          [WaRdbMetadatum].user_defined_column_nm as [data-mart-column-name],
          [component].block_nm                    as [blockName],
          [WaRdbMetadatum].block_pivot_nbr        as  [dataMartRepeatNumber],
          [component].data_location               as [dataLocation],
          [component].publish_ind_cd              as [is_published],
          [component].batch_table_appear_ind_cd   as  [appearsInBatch],
          [component].batch_table_header          as  [batchLabel],
          [component].batch_table_column_width    as  [batchWidth],
          [ui].component_behavior                 as  [componentBehavior],
          [ui].type_cd_desc                       as  [componentName],
          [set].class_cd                          as  [classCode]
      from WA_UI_metadata [component]
          left join WA_RDB_metadata [WaRdbMetadatum] on
              [WaRdbMetadatum].wa_ui_metadata_uid = [component].[wa_ui_metadata_uid]

          left join [NBS_SRTE]..Codeset [set] on
                  [set].code_set_group_id = [component].[code_set_group_id]

          left join NBS_ui_component [ui] on
                [ui].nbs_ui_component_uid = [component].nbs_ui_component_uid

      where   [component].wa_template_uid = ?
          and [component].order_nbr > 0
      order by
          [component].order_nbr asc
      """;
  private static final int PAGE_PARAMETER = 1;

  private final JdbcTemplate template;
  private final FlattenedComponentMapper mapper;

  FlattenedComponentFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new FlattenedComponentMapper(new FlattenedComponentMapper.Column());
  }

  Collection<FlattenedComponent> find(final long page) {
    return this.template.query(
        QUERY, statement -> statement.setLong(PAGE_PARAMETER, page), this.mapper);
  }
}
