package gov.cdc.nbs.questionbank.page;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.util.PageConstants;

@Component
public class PageDraftFinder {
    private static final String FIND_BY_FORM_CD_AND_TYPE_QUERY = """
            select [template].wa_template_uid
            from Wa_template [template]
            where [template].template_type = ?
                and [template].form_cd = ?
            """;

    private final JdbcTemplate template;

    private static final int LOCAL_TEMPLATE_TYPE = 1;
    private static final int LOCAL_FORM_CD = 2;
    private static final int COLUMN_INDEX = 1;

    public PageDraftFinder(final JdbcTemplate template) {
        this.template = template;
    }

    public Long findDraftTemplate(final WaTemplate page) {
        return this.template.query(
                FIND_BY_FORM_CD_AND_TYPE_QUERY,
                setter -> {
                    setter.setString(LOCAL_TEMPLATE_TYPE, PageConstants.DRAFT);
                    setter.setString(LOCAL_FORM_CD, page.getFormCd());
                },
                (rs, row) -> rs.getLong(COLUMN_INDEX)).get(0);
    }
}
