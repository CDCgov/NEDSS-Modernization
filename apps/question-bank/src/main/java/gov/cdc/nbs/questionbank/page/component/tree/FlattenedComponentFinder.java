package gov.cdc.nbs.questionbank.page.component.tree;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
class FlattenedComponentFinder {

  private static final String QUERY = """
      select
          [component].wa_ui_metadata_uid        as [identifier],
          [component].nbs_ui_component_uid      as [type],
          [component].question_label            as [name],
          [component].display_ind               as [visible],
          [component].order_nbr                 as [order],
          [component].standard_question_ind_cd  as [is_standard],
          [component].question_type             as [standard],
          [component].question_identifier       as [question],
          [component].data_type                 as [data_type],
          [component].sub_group_nm              as [sub_group],
          [component].[desc_txt]                as [description],
          [component].[enable_ind]              as [enabled],
          [component].required_ind              as [required],
          [component].future_date_ind_cd        as [allow_future_dates],
          [component].coinfection_ind_cd        as [co-infection],
          [component].mask                      as [mask],
          [component].question_tool_tip         as [tool_tip],
          [component].default_value             as [default_value],
          [set].[code_set_nm]                   as [value_set]
      from WA_UI_metadata [component]

          left join [NBS_SRTE]..Codeset [set] on
                  [set].code_set_group_id = [component].[code_set_group_id]

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
        QUERY,
        statement -> statement.setLong(PAGE_PARAMETER, page),
        this.mapper

    );
  }
}
