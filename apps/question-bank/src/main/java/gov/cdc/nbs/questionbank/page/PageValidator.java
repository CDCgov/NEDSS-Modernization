package gov.cdc.nbs.questionbank.page;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageCreateException;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.request.PageValidationRequest;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import org.springframework.stereotype.Component;

@Component
public class PageValidator {
  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;
  private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;

  private final JPAQueryFactory factory;

  public PageValidator(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  boolean validate(PageValidationRequest request) {
    return factory
            .select(pageTable.id.count())
            .from(pageTable)
            .where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase()))
            .fetchFirst()
        == 0;
  }

  void validate(PageCreateRequest request) {
    if (request.name() == null || request.name().isEmpty()) {
      throw new PageCreateException(PageConstants.ADD_PAGE_NAME_EMPTY);
    }

    if (request.eventType() == null || request.eventType().isEmpty()) {
      throw new PageCreateException(PageConstants.ADD_PAGE_EVENTTYPE_EMPTY);
    }

    if (request.templateId() == null || request.templateId().intValue() < 1) {
      throw new PageCreateException(PageConstants.ADD_PAGE_TEMPLATE_EMPTY);
    }

    if (request.messageMappingGuide() == null || request.messageMappingGuide().isEmpty()) {
      throw new PageCreateException(PageConstants.ADD_PAGE_MMG_EMPTY);
    }

    if (!validate(new PageValidationRequest(request.name()))) {
      String finalMessage = PageConstants.ADD_PAGE_TEMPLATENAME_EXISTS.formatted(request.name());
      throw new PageCreateException(finalMessage);
    }

    validateMmg(request.messageMappingGuide());
  }

  void validateMmg(String code) {
    boolean exists =
        factory
                .select(codeValueGeneral.id.code.count())
                .from(codeValueGeneral)
                .where(codeValueGeneral.id.code.equalsIgnoreCase(code))
                .fetchFirst()
            == 1;
    if (!exists) {
      throw new PageCreateException(PageConstants.ADD_PAGE_MMG_INVALID);
    }
  }
}
