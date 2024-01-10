package gov.cdc.nbs.questionbank.page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.request.PageValidationRequest;

@ExtendWith(MockitoExtension.class)
class PageValidatorTest {

  @Mock
  private JPAQueryFactory factory;

  @InjectMocks
  private PageValidator validator;


  @Test
  @SuppressWarnings("unchecked")
  void should_be_valid() {
    // Given a page name that is not being used
    PageValidationRequest request = new PageValidationRequest("some name");
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(QWaTemplate.waTemplate.id.count())).thenReturn(query);
    when(query.from(QWaTemplate.waTemplate)).thenReturn(query);
    when(query.where(QWaTemplate.waTemplate.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(query);
    when(query.fetchFirst()).thenReturn(0l);

    // When the page name is validated
    boolean isValid = validator.validate(request);

    // Then the name is valid
    assertThat(isValid).isTrue();
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_be_invalid() {
    // Given a page name that is used
    PageValidationRequest request = new PageValidationRequest("some name");
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(QWaTemplate.waTemplate.id.count())).thenReturn(query);
    when(query.from(QWaTemplate.waTemplate)).thenReturn(query);
    when(query.where(QWaTemplate.waTemplate.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(query);
    when(query.fetchFirst()).thenReturn(1l);

    // When the page name is validated
    boolean isValid = validator.validate(request);

    // Then the name is valid
    assertThat(isValid).isFalse();
  }
}
