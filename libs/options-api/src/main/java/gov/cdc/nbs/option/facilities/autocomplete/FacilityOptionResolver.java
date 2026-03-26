package gov.cdc.nbs.option.facilities.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FacilityOptionResolver extends SQLBasedOptionResolver {

  private static final String QUERY =
      """
          with [facility]([value], [name], [quickCode]) as (
              select
                  [organization].organization_uid,
                  [organization].display_nm + IIF(
                        [quick_code].root_extension_txt is null
                    or  [quick_code].root_extension_txt='',
                    '',
                    ' [' + [quick_code].root_extension_txt + ']'),
                  root_extension_txt
              from Organization [organization]
                left join Entity_id [quick_code] on
                  [quick_code].entity_uid = [organization].organization_uid
                  and [quick_code].type_cd ='QEC'
              where [organization].electronic_ind is null
                or [organization].electronic_ind <> 'Y'

          )
          select
              [value],
              [name],
              row_number() over( order by [name])
          from [facility]
          where [quickCode]=:quickCode or [name] like :criteria or [name] like :prefixCriteria

          order by
              [name]

          offset 0 rows
          fetch next :limit rows only
          """;

  public FacilityOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
