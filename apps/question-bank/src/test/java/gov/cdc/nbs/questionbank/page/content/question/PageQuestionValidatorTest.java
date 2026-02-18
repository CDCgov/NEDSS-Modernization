package gov.cdc.nbs.questionbank.page.content.question;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QWaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.question.response.ValidationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageQuestionValidatorTest {
  private static final QWaUiMetadata table = QWaUiMetadata.waUiMetadata;

  @Mock private JPAQueryFactory factory;

  @InjectMocks private PageQuestionValidator validator;

  @Test
  void should_fail_no_datamart() {
    ValidationResponse response = validator.validateDataMart(1l, 2l, null);
    assertThat(response.isValid()).isFalse();
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_fail_already_exists() {
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(table.id.count())).thenReturn(query);
    when(query.from(table)).thenReturn(query);
    when(query.where(
            table
                .waTemplateUid
                .id
                .eq(1l)
                .and(table.id.ne(2l))
                .and(table.waRdbMetadatum.userDefinedColumnNm.eq("datamart".toUpperCase()))))
        .thenReturn(query);
    when(query.fetchOne()).thenReturn(1l);

    ValidationResponse response = validator.validateDataMart(1l, 2l, "datamart");
    assertThat(response.isValid()).isFalse();
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_succeed() {
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(table.id.count())).thenReturn(query);
    when(query.from(table)).thenReturn(query);
    when(query.where(
            table
                .waTemplateUid
                .id
                .eq(1l)
                .and(table.id.ne(2l))
                .and(table.waRdbMetadatum.userDefinedColumnNm.eq("datamart".toUpperCase()))))
        .thenReturn(query);
    when(query.fetchOne()).thenReturn(0l);

    ValidationResponse response = validator.validateDataMart(1l, 2l, "datamart");
    assertThat(response.isValid()).isTrue();
  }
}
