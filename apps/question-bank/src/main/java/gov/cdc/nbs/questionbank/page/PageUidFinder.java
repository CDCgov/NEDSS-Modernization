package gov.cdc.nbs.questionbank.page;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class PageUidFinder {
  private static final String FIND_BY_FORM_CD_AND_TYPE_QUERY =
      """
      select [template].wa_template_uid
      from Wa_template [template]
      where [template].template_type = ?
          and [template].form_cd = ?
      """;

  private final JdbcTemplate template;

  private static final int LOCAL_TEMPLATE_TYPE = 1;
  private static final int LOCAL_FORM_CD = 2;
  private static final int COLUMN_INDEX = 1;

  public PageUidFinder(final JdbcTemplate template) {
    this.template = template;
  }

  public Long findTemplateByType(String formCode, String pageType) {
    List<Long> result =
        this.template.query(
            FIND_BY_FORM_CD_AND_TYPE_QUERY,
            setter -> {
              setter.setString(LOCAL_TEMPLATE_TYPE, pageType);
              setter.setString(LOCAL_FORM_CD, formCode);
            },
            (rs, row) -> rs.getLong(COLUMN_INDEX));

    if (!result.isEmpty()) {
      return result.get(0);
    } else {
      return null;
    }
  }
}
