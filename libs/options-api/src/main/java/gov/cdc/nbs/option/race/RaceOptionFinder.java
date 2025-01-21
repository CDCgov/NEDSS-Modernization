package gov.cdc.nbs.option.race;

import gov.cdc.nbs.option.jdbc.SQLBasedOptionFinder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
class RaceOptionFinder extends SQLBasedOptionFinder {

  private static final String QUERY = """
      select
          code                                as [value],
          CONCAT(
            UPPER(SUBSTRING(code_short_desc_txt, 1, 1)),
            SUBSTRING(code_short_desc_txt, 2, LEN(code_short_desc_txt))
          )                                   as [name],
          row_number() over (order by [code]) as [order]
      from NBS_SRTE..Code_value_general
      where code_set_nm ='RACE_CALCULATED'
      order by
        code
      """;


  RaceOptionFinder(final JdbcTemplate template) {
    super(QUERY, template);
  }


}
