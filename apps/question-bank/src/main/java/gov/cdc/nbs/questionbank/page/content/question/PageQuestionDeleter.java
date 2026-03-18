package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.question.exception.DeletePageQuestionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import jakarta.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageQuestionDeleter {

  private static final String SELECT_ALL_IDENTIFIERS_FOR_PAGE =
      """
        SELECT
        concat(source_question_identifier, ',', target_question_identifier)
      FROM
        WA_rule_metadata
      WHERE
        wa_template_uid = ?;
              """;

  private final EntityManager entityManager;
  private final JdbcTemplate template;

  public PageQuestionDeleter(final EntityManager entityManager, final JdbcTemplate template) {
    this.entityManager = entityManager;
    this.template = template;
  }

  public void deleteQuestion(Long pageId, Long questionId, Long user) {
    WaUiMetadata metadata = entityManager.find(WaUiMetadata.class, questionId);
    if (metadata == null) {
      throw new DeletePageQuestionException("Failed to find question");
    }
    if (isAssociatedWithRule(metadata.getQuestionIdentifier(), pageId)) {
      throw new DeletePageQuestionException(
          "Unable to delete question associated with a business rule");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }
    page.deleteQuestion(new PageContentCommand.DeleteQuestion(questionId, user, Instant.now()));
  }

  private boolean isAssociatedWithRule(String questionIdentifier, long pageId) {
    String[] identifiers =
        template
            .query(SELECT_ALL_IDENTIFIERS_FOR_PAGE, setter -> setter.setLong(1, pageId), mapper())
            .stream()
            .collect(Collectors.joining(","))
            .split(",");

    return Arrays.asList(identifiers).contains(questionIdentifier);
  }

  private RowMapper<String> mapper() {
    return new RowMapper<String>() {
      @Override
      public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(1);
      }
    };
  }
}
