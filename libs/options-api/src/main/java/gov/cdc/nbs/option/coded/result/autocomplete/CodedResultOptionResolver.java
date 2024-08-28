package gov.cdc.nbs.option.coded.result.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class CodedResultOptionResolver extends SQLBasedOptionResolver {
  private static final String QUERY =
      """
          with [coded_test]([value], [name]) as (
                select
                    lab_result_cd,
                    lab_result_desc_txt
                from
                    [NBS_SRTE].[dbo].[Lab_result]
                where organism_name_ind = 'N'
                and (
                            lab_result_desc_txt like :criteria
                        or  lab_result_desc_txt like :prefixCriteria
                        or  lab_result_cd like :criteria
                    )
                union
                select
                    snomed_cd,
                    snomed_desc_txt
                from
                    [NBS_SRTE].[dbo].[Snomed_code]
                where   snomed_desc_txt like :criteria
                    or snomed_desc_txt like :prefixCriteria
                    or  snomed_cd like :criteria
          )
          select
              [value],
              [name],
              row_number() over( order by [name])
          from [coded_test]

          order by
              [name]

          offset 0 rows
          fetch next :limit rows only
          """;

  CodedResultOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
