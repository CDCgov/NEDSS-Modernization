package gov.cdc.nbs.questionbank.page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.exception.PageCreateException;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.request.PageValidationRequest;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageValidatorTest {
  private static final QWaTemplate pageTable = QWaTemplate.waTemplate;
  private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;

  @Mock private JPAQueryFactory factory;

  @InjectMocks private PageValidator validator;

  @Test
  @SuppressWarnings("unchecked")
  void should_be_valid() {
    // Given a page name that is not being used
    PageValidationRequest request = new PageValidationRequest("some name");
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(pageTable.id.count())).thenReturn(query);
    when(query.from(pageTable)).thenReturn(query);
    when(query.where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase())))
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
    when(factory.select(pageTable.id.count())).thenReturn(query);
    when(query.from(pageTable)).thenReturn(query);
    when(query.where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(query);
    when(query.fetchFirst()).thenReturn(1l);

    // When the page name is validated
    boolean isValid = validator.validate(request);

    // Then the name is valid
    assertThat(isValid).isFalse();
  }

  @Test
  void should_be_valid_mmg() {
    // Given a mmg code that is valid
    String code = "mmgCode";
    validMmg(code);
    validator.validateMmg(code);
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_be_invalid_mmg() {
    // Given a mmg code that is valid
    String code = "mmgCode";
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(codeValueGeneral.id.code.count())).thenReturn(query);
    when(query.from(codeValueGeneral)).thenReturn(query);
    when(query.where(codeValueGeneral.id.code.equalsIgnoreCase(code))).thenReturn(query);
    when(query.fetchFirst()).thenReturn(0l);
    assertThrows(PageCreateException.class, () -> validator.validateMmg(code));
  }

  @Test
  void page_name_is_blank() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV", Arrays.asList("1023"), "", 10l, "HEP_Case_Map_V1.0", "unit test", "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void page_name_is_null() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV", Arrays.asList("1023"), null, 10l, "HEP_Case_Map_V1.0", "unit test", "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void event_type_is_blank() {
    PageCreateRequest request =
        new PageCreateRequest(
            "",
            Arrays.asList("1023"),
            "page name",
            10l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void event_type_is_null() {
    PageCreateRequest request =
        new PageCreateRequest(
            null,
            Arrays.asList("1023"),
            "page name",
            10l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void template_id_is_0() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV",
            Arrays.asList("1023"),
            "page name",
            0l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void template_id_is_null() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV",
            Arrays.asList("1023"),
            "page name",
            null,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void mmg_is_blank() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV", Arrays.asList("1023"), "page name", 10l, "", "unit test", "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  void mmg_is_null() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV", Arrays.asList("1023"), "page name", 10l, null, "unit test", "dataMart");
    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  @SuppressWarnings("unchecked")
  void mmg_is_invalid() {
    // Given an invalid MMG
    String code = "mmgCode";
    PageCreateRequest request =
        new PageCreateRequest(
            "INV", Arrays.asList("1023"), "page name", 10l, code, "unit test", "dataMart");
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(codeValueGeneral.id.code.count())).thenReturn(query);
    when(query.from(codeValueGeneral)).thenReturn(query);
    when(query.where(codeValueGeneral.id.code.equalsIgnoreCase(code))).thenReturn(query);
    when(query.fetchFirst()).thenReturn(0l);

    // and a valid name
    JPAQuery<Long> nameQuery = Mockito.mock(JPAQuery.class);
    when(factory.select(pageTable.id.count())).thenReturn(nameQuery);
    when(nameQuery.from(pageTable)).thenReturn(nameQuery);
    when(nameQuery.where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(nameQuery);
    when(nameQuery.fetchFirst()).thenReturn(0l);

    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  @SuppressWarnings("unchecked")
  void name_is_invalid() {
    PageCreateRequest request =
        new PageCreateRequest(
            "INV",
            Arrays.asList("1023"),
            "page name",
            10l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");

    // and an ivalid name
    JPAQuery<Long> nameQuery = Mockito.mock(JPAQuery.class);
    when(factory.select(pageTable.id.count())).thenReturn(nameQuery);
    when(nameQuery.from(pageTable)).thenReturn(nameQuery);
    when(nameQuery.where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(nameQuery);
    when(nameQuery.fetchFirst()).thenReturn(1l);

    assertThrows(PageCreateException.class, () -> validator.validate(request));
  }

  @Test
  @SuppressWarnings("unchecked")
  void should_validate() {
    // given a valid mmg
    validMmg("HEP_Case_Map_V1.0");
    PageCreateRequest request =
        new PageCreateRequest(
            "INV",
            Arrays.asList("1023"),
            "page name",
            10l,
            "HEP_Case_Map_V1.0",
            "unit test",
            "dataMart");
    // and a valid name
    JPAQuery<Long> nameQuery = Mockito.mock(JPAQuery.class);
    when(factory.select(pageTable.id.count())).thenReturn(nameQuery);
    when(nameQuery.from(pageTable)).thenReturn(nameQuery);
    when(nameQuery.where(pageTable.templateNm.toLowerCase().eq(request.name().toLowerCase())))
        .thenReturn(nameQuery);
    when(nameQuery.fetchFirst()).thenReturn(0l);
    validator.validate(request);
  }

  @SuppressWarnings("unchecked")
  private void validMmg(String code) {
    JPAQuery<Long> query = Mockito.mock(JPAQuery.class);
    when(factory.select(codeValueGeneral.id.code.count())).thenReturn(query);
    when(query.from(codeValueGeneral)).thenReturn(query);
    when(query.where(codeValueGeneral.id.code.equalsIgnoreCase(code))).thenReturn(query);
    when(query.fetchFirst()).thenReturn(1l);
  }
}
