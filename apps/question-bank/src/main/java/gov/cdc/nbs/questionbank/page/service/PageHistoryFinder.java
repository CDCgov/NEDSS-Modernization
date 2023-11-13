package gov.cdc.nbs.questionbank.page.service;

import gov.cdc.nbs.questionbank.page.model.PageHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PageHistoryFinder {
    private final JdbcTemplate jdbcTemplate;

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("MM/dd/yyyy")
            .withZone(ZoneId.of("UTC"));

    private static final String QUERY = "SELECT hist.publish_version_nbr AS publishVersionNbr," +
            "  hist.last_chg_time AS lastUpdatedDate," +
            " userProfile.first_nm || ' ' || userProfile.last_nm AS lastUpdatedBy, " +
            " hist.version_note AS notes " +
            " FROM WA_template_hist hist" +
            " LEFT OUTER JOIN user_profile userProfile " +
            " ON hist.last_chg_user_id = userProfile.nedss_entry_id " +
            " WHERE hist.wa_template_uid = ? " +
            " UNION " +
            " SELECT tem.publish_version_nbr AS publishVersionNbr," +
            " tem.last_chg_time AS lastUpdatedDate," +
            " userProfile.first_nm || ' ' || userProfile.last_nm AS lastUpdatedBy," +
            " tem.version_note AS notes " +
            " FROM WA_template tem " +
            " LEFT OUTER JOIN user_profile userProfile" +
            " ON tem.last_chg_user_id = userProfile.nedss_entry_id " +
            " WHERE tem.wa_template_uid = ? AND tem.template_type IN ('Published With Draft','Published')";

    public List<PageHistory> getPageHistory(Long pageId) {
        return jdbcTemplate.query(QUERY, setter -> {
            setter.setLong(1, pageId);
            setter.setLong(2, pageId);
        }, (rs, rowNum) -> new PageHistory(
                rs.getString("publishVersionNbr"),
                rs.getTimestamp("lastUpdatedDate").toLocalDateTime().format(dateFormatter),
                rs.getString("lastUpdatedBy"),
                rs.getString("notes")
        ));
    }

}

