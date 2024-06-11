package gov.cdc.nbs.option.resultedtest.autocomplete;

import gov.cdc.nbs.option.jdbc.autocomplete.SQLBasedOptionResolver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ResultedTestOptionResolver extends SQLBasedOptionResolver {
  private static final String QUERY =
      """
          with [resultedtest]([value], [name]) as (
              SELECT DISTINCT
                lab_test_cd, lab_test_desc_txt
              FROM
                NBS_SRTE.dbo.Lab_test
              WHERE
                test_type_cd = 'R'
              UNION
              SELECT DISTINCT
                loinc_cd, component_name
              FROM
                NBS_SRTE.dbo.LOINC_code
              WHERE
                related_class_cd in('ABXBACT','BC','CELLMARK','CHAL','CHALSKIN','CHEM','COAG','CYTO','DRUG','DRUG/TOX','HEM','HEM/BC','MICRO','MISC','PANEL.ABXBACT','PANEL.BC','PANEL.CHEM','PANEL.MICRO','PANEL.OBS','PANEL.SERO','PANEL.TOX','PANEL.UA','SERO','SPEC','TOX','UA','VACCIN')
          )
          select
              [value],
              [name],
              row_number() over( order by [name])
          from [resultedtest]
          where [name] like :criteria or [name] like :prefixCriteria or[value] like :criteria

          order by
              [name]

          offset 0 rows
          fetch next :limit rows only
          """;

  public ResultedTestOptionResolver(final NamedParameterJdbcTemplate template) {
    super(QUERY, template);
  }
}
