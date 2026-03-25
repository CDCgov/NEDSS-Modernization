package gov.cdc.nbs.questionbank.page.service;

import gov.cdc.nbs.questionbank.page.model.PageHistory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PageHistoryFinder {

  private final JdbcTemplate jdbcTemplate;

  private String pageHistoryQuery =
      """
      with target as (
                       SELECT
                           template_nm as template_name
                       FROM WA_template
                       WHERE wa_template_uid = ?
                   )
      SELECT
        hist.publish_version_nbr AS publishVersionNbr,
        CONVERT(varchar, hist.last_chg_time, 101) AS lastUpdatedDate,
        CONCAT(userProfile.first_nm, ' ', userProfile.last_nm) AS lastUpdatedBy,
        hist.version_note AS notes
      FROM
        WA_template_hist hist
        LEFT OUTER JOIN user_profile userProfile ON hist.last_chg_user_id = userProfile.nedss_entry_id
      WHERE
        hist.template_nm = (select template_name from target)
      UNION
      SELECT
        tem.publish_version_nbr AS publishVersionNbr,
        CONVERT(varchar, tem.last_chg_time, 101) AS lastUpdatedDate,
        CONCAT(userProfile.first_nm, ' ', userProfile.last_nm) AS lastUpdatedBy,
        tem.version_note AS notes
      FROM
        WA_template tem
        LEFT OUTER JOIN user_profile userProfile ON tem.last_chg_user_id = userProfile.nedss_entry_id
      WHERE
        tem.template_nm = (select template_name from target)
        AND tem.template_type IN('Published With Draft', 'Published')
      ORDER BY publish_version_nbr ASC
      OFFSET ? rows
      FETCH NEXT ? ROWS ONLY
        """;

  private static final String ROWS_COUNT =
      """
      with target as (
          SELECT
              template_nm as template_name
          FROM WA_template
          WHERE wa_template_uid = ?
      ),
      template_hist_data as (
          SELECT
              hist.publish_version_nbr AS publishVersionNbr
          FROM
              WA_template_hist hist
              LEFT OUTER JOIN user_profile userProfile ON hist.last_chg_user_id = userProfile.nedss_entry_id
          WHERE
              hist.template_nm = (select template_name from target)
          UNION
          SELECT
              tem.publish_version_nbr AS publishVersionNbr
          FROM
              WA_template tem
              LEFT OUTER JOIN user_profile userProfile ON tem.last_chg_user_id = userProfile.nedss_entry_id
          WHERE
              tem.template_nm = (select template_name from target)
              AND tem.template_type IN ('Published With Draft', 'Published')
      )
      SELECT
         COUNT(*)
      FROM
          template_hist_data
        """;

  private static final int PAGE_ID = 1;
  private static final int OFFSET = 2;
  private static final int LIMIT = 3;
  RowMapper<Integer> integerRowMapper = new SingleColumnRowMapper<>(Integer.class);

  public Page<PageHistory> getPageHistory(Long pageId, Pageable pageable) {
    int limit = pageable.getPageSize();
    int offset = pageable.getPageNumber() * limit;
    Sort sort = pageable.getSort();
    String query = pageHistoryQuery;
    if (sort.isSorted()) {
      query =
          pageHistoryQuery.replace(
              "publish_version_nbr ASC",
              "publish_version_nbr ASC" + "," + sort.toString().replace(": ", " "));
    }
    List<PageHistory> result =
        jdbcTemplate.query(
            query,
            setter -> {
              setter.setLong(PAGE_ID, pageId);
              setter.setLong(OFFSET, offset);
              setter.setLong(LIMIT, limit);
            },
            (rs, rowNum) ->
                new PageHistory(
                    rs.getString("publishVersionNbr"),
                    rs.getString("lastUpdatedDate"),
                    rs.getString("lastUpdatedBy"),
                    rs.getString("notes")));
    return new PageImpl<>(result, pageable, getTotalRowsCount(pageId));
  }

  private int getTotalRowsCount(long pageId) {
    return this.jdbcTemplate
        .query(ROWS_COUNT, setter -> setter.setLong(PAGE_ID, pageId), integerRowMapper)
        .get(0);
  }
}
