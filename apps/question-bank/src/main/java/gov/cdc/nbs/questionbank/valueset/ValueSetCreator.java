package gov.cdc.nbs.questionbank.valueset;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.command.ValueSetCommand;
import gov.cdc.nbs.questionbank.valueset.exception.CreateValuesetException;
import gov.cdc.nbs.questionbank.valueset.model.Valueset;
import gov.cdc.nbs.questionbank.valueset.request.CreateValuesetRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class ValueSetCreator {
  private static final Pattern PATTERN = Pattern.compile("^\\w+$");
  private static final List<String> validTypes = Arrays.asList("PHIN", "LOCAL");
  private final JdbcTemplate template;
  private final EntityManager entityManager;

  public ValueSetCreator(final EntityManager entityManager, final JdbcTemplate template) {
    this.entityManager = entityManager;
    this.template = template;
  }

  private static final String GET_ID_QUERY =
      """
      SELECT
      ISNULL(MAX(code_set_group_id + 10), 9910)
      FROM NBS_SRTE.dbo.Codeset_Group_Metadata
      """;

  private static final String CODE_EXISTS_QUERY =
      """
      SELECT
        count(*)
      FROM
        NBS_SRTE.dbo.codeset
      WHERE
        codeset.code_set_nm = ?
        AND codeSet.class_cd = 'code_value_general'
      """;

  private static final String NAME_EXISTS_QUERY =
      """
      SELECT
        count(*)
      FROM
        NBS_SRTE.dbo.codeset
      WHERE
        codeset.value_set_nm = ?
        AND codeSet.class_cd = 'code_value_general'
      """;

  public Valueset create(CreateValuesetRequest request, long userId) {
    String upperCaseCode = request.code().toUpperCase();

    if (!PATTERN.matcher(request.code()).matches()) {
      throw new CreateValuesetException("Invalid Value set code. Only [a-zA-Z0-9_] are supported");
    }
    if (!PATTERN.matcher(request.name()).matches()) {
      throw new CreateValuesetException("Invalid Value set name. Only [a-zA-Z0-9_] are supported");
    }
    if (!validTypes.contains(request.type().toUpperCase())) {
      throw new CreateValuesetException("Invalid Type specified");
    }
    if (nameAlreadyExists(request.name())) {
      throw new CreateValuesetException("Value set name already exists");
    }
    if (codeAlreadyExists(upperCaseCode)) {
      throw new CreateValuesetException("Value set code already exists");
    }

    Codeset valueset = new Codeset(asAdd(request, userId));
    try {
      entityManager.persist(valueset);
      return new Valueset(
          valueset.getCodeSetGroup().getId(),
          valueset.getValueSetTypeCd(),
          valueset.getId().getCodeSetNm(),
          valueset.getValueSetNm(),
          valueset.getCodeSetDescTxt());
    } catch (Exception e) {
      throw new CreateValuesetException("Failed to create valueset");
    }
  }

  public boolean nameAlreadyExists(String name) {
    Long count = template.queryForObject(NAME_EXISTS_QUERY, Long.class, name);
    return count != null && count > 0;
  }

  public boolean codeAlreadyExists(String code) {
    Long count = template.queryForObject(CODE_EXISTS_QUERY, Long.class, code);
    return count != null && count > 0;
  }

  public long getNextCodesetId() {
    Long id = template.queryForObject(GET_ID_QUERY, Long.class);
    return id != null ? id : 9910l;
  }

  public ValueSetCommand.Add asAdd(final CreateValuesetRequest request, long userId) {
    return new ValueSetCommand.Add(
        request.type().toUpperCase(),
        request.name(),
        request.code(),
        request.description(),
        getNextCodesetId(),
        Instant.now(),
        userId);
  }
}
