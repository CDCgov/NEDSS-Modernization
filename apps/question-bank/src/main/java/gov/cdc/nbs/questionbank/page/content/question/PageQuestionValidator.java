package gov.cdc.nbs.questionbank.page.content.question;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.question.response.ValidationResponse;
import org.springframework.stereotype.Component;

@Component
public class PageQuestionValidator {
  private static final QWaUiMetadata metadataTable = QWaUiMetadata.waUiMetadata;

  private final JPAQueryFactory factory;

  public PageQuestionValidator(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  // Validate the provided datamart name is not already in use for the provided page, excluding the
  // specified question
  public ValidationResponse validateDataMart(Long page, Long questionId, String datamart) {
    if (datamart == null) {
      return new ValidationResponse(false);
    }
    long count =
        factory
            .select(metadataTable.id.count())
            .from(metadataTable)
            .where(
                metadataTable
                    .waTemplateUid
                    .id
                    .eq(page)
                    .and(metadataTable.id.ne(questionId))
                    .and(
                        metadataTable.waRdbMetadatum.userDefinedColumnNm.eq(
                            datamart.toUpperCase())))
            .fetchOne();

    return new ValidationResponse(count == 0);
  }
}
