package gov.cdc.nbs.questionbank.page.service;

import gov.cdc.nbs.questionbank.page.model.PageHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.Types;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageHistoryFinder {
    private final JdbcTemplate jdbcTemplate;
    private static final String QUERY = "SELECT hist.publish_version_nbr AS publishVersionNbr," +
            " CONVERT(varchar, hist.last_chg_time, 101) AS lastUpdatedDate," +
            " userProfile.first_nm || ' ' || userProfile.last_nm AS lastUpdatedBy, " +
            " hist.version_note AS notes " +
            " FROM WA_template_hist hist LEFT OUTER JOIN user_profile userProfile ON hist.last_chg_user_id = userProfile.nedss_entry_id " +
            " WHERE hist.template_nm = ? " +
            " UNION " +
            " SELECT tem.publish_version_nbr AS publishVersionNbr," +
            " CONVERT(varchar, tem.last_chg_time, 101) AS lastUpdatedDate," +
            " userProfile.first_nm || ' ' || userProfile.last_nm AS lastUpdatedBy," +
            " tem.version_note AS notes " +
            " FROM WA_template tem LEFT OUTER JOIN user_profile userProfile ON tem.last_chg_user_id = userProfile.nedss_entry_id " +
            " WHERE tem.template_nm = ? AND tem.template_type IN ('Published With Draft','Published')";

    public List<PageHistory> getPageHistory(String waTemplateName) {
        Object[] args = new Object[]{waTemplateName, waTemplateName};
        int[] argTypes = new int[]{Types.VARCHAR, Types.VARCHAR};
        return jdbcTemplate.query(QUERY, args, argTypes, (rs, rowNum) -> new PageHistory(
                rs.getString("publishVersionNbr"),
                rs.getString("lastUpdatedDate"),
                rs.getString("lastUpdatedBy"),
                rs.getString("notes")
        ));
    }
}

