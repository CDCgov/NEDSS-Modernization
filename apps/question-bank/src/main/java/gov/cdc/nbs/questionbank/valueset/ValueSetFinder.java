package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.response.ValueSetSearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ValueSetFinder {

  private String findValueSetQuery = """
      SELECT 
          NBS_SRTE.dbo.Codeset.value_set_type_cd,
          NBS_SRTE.dbo.Codeset.value_set_code,
          NBS_SRTE.dbo.Codeset.value_set_nm,
          NBS_SRTE.dbo.Codeset.code_set_desc_txt,
          NBS_SRTE.dbo.Codeset.value_set_status_cd
      FROM NBS_SRTE.dbo.Codeset 
      WHERE (NBS_SRTE.dbo.Codeset.value_set_nm LIKE  ?
          OR NBS_SRTE.dbo.Codeset.code_set_desc_txt LIKE ?
          OR NBS_SRTE.dbo.Codeset.value_set_code LIKE ?)
          AND NBS_SRTE.dbo.Codeset.class_cd = 'code_value_general'
          ORDER BY value_set_nm
          OFFSET ? rows
          FETCH NEXT ? ROWS ONLY
          """;

  private final JdbcTemplate jdbcTemplate;
  private final RowMapper<ValueSetSearchResponse> mapper;
  private static final int VALUE_SET_NAME = 1;
  private static final int CODE_SET_DESCRIPTION = 2;
  private static final int VALUE_SET_CODE = 3;
  private static final int OFFSET = 4;
  private static final int LIMIT = 5;



  ValueSetFinder(final JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
    this.mapper = new ValueSetSearchResponseMapper();
  }

  public List<ValueSetSearchResponse> findValueSet(final ValueSetSearchRequest request, Pageable pageable) {
    int limit = pageable.getPageSize();
    int offset = pageable.getPageNumber() * limit;
    Sort sort = pageable.getSort();

    if (sort.isSorted()) {
      findValueSetQuery = findValueSetQuery.replace("ORDER BY value_set_nm",
          " ORDER BY " + sort.toString().replace(": ", " "));
    }

    return this.jdbcTemplate.query(
        findValueSetQuery,
        setter -> {
          setter.setString(VALUE_SET_NAME, "%" + request.getValueSetNm() + "%");
          setter.setString(CODE_SET_DESCRIPTION, "%" + request.getValueSetDescription() + "%");
          setter.setString(VALUE_SET_CODE, "%" + request.getValueSetCode() + "%");
          setter.setInt(OFFSET, offset);
          setter.setInt(LIMIT, limit);
        }, mapper
    );
  }
}
