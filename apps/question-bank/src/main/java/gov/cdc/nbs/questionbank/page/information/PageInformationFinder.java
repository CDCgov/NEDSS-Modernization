package gov.cdc.nbs.questionbank.page.information;

import gov.cdc.nbs.accumulation.Accumulator;
import gov.cdc.nbs.questionbank.page.ConditionRowMapper;
import gov.cdc.nbs.questionbank.page.EventTypeRowMapper;
import gov.cdc.nbs.questionbank.page.MessageMappingGuideRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class PageInformationFinder {

  private static final String QUERY = """
      select
          [page].wa_template_uid                      as [page],
          [object].code                               as [event_type_value],
          [object].code_short_desc_txt                as [event_type_name],
          [message_mapping_guide].code                as [message_mapping_guide_value],
          [message_mapping_guide].code_short_desc_txt as [message_mapping_guide_name],
          [page].template_nm                          as [name],
          [page].datamart_nm                          as [datamart],
          [page].desc_txt                             as [description],
          [condition].[condition_cd]                  as [condition_value],
          [condition].condition_short_nm              as [condition_name]
      from WA_template [page]
          join [NBS_SRTE]..Code_value_general [object] on
                  [object].[code_set_nm] = 'BUS_OBJ_TYPE'
              and [object].code = [page].bus_obj_type
            
          join [NBS_SRTE]..Code_value_general [message_mapping_guide] on
                  [message_mapping_guide].[code_set_nm] = 'NBS_MSG_PROFILE'
              and [message_mapping_guide].code = [page].nnd_entity_identifier
            
          left join Page_cond_mapping [mapping] on
                  [mapping].wa_template_uid = [page].wa_template_uid
            
          left join NBS_SRTE..Condition_code [condition] on
                  [condition].condition_cd = [mapping].condition_cd
            
      where [page].[wa_template_uid] = ?
      """;

  private final JdbcTemplate template;
  private final PageInformationRowMapper mapper;
  private final PageInformationMerger merger;

  PageInformationFinder(final JdbcTemplate template) {
    this.template = template;
    this.mapper = new PageInformationRowMapper(
        new PageInformationRowMapper.Columns(
            1,
            new EventTypeRowMapper.Columns(2, 3),
            new MessageMappingGuideRowMapper.Columns(4, 5),
            6,
            7,
            8,
            new ConditionRowMapper.Columns(9, 10)
        )
    );
    this.merger = new PageInformationMerger();
  }

  Optional<PageInformation> find(final long page) {
    return this.template.query(
            QUERY,
            statement -> statement.setLong(1, page),
            this.mapper
        ).stream()
        .collect(Accumulator.accumulating(PageInformation::page, this.merger::merge))
        ;
  }
}
