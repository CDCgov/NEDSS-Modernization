package gov.cdc.nbs.questionbank.page;

import org.springframework.stereotype.Component;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.request.PageValidationRequest;

@Component
public class PageValidator {
  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;

  private final JPAQueryFactory factory;

  public PageValidator(final JPAQueryFactory factory) {
    this.factory = factory;
  }

  public boolean validate(PageValidationRequest request) {
    return factory.select(pageTable.id.count())
        .from(pageTable)
        .where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase()))
        .fetchFirst() == 0;
  }

}
