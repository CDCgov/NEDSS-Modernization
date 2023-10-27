package gov.cdc.nbs.questionbank.page;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.authentication.entity.QAuthUser;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class PageSummaryMapperTest {

  private final PageSummaryMapper mapper = new PageSummaryMapper();

  @ParameterizedTest
  @CsvSource({
      "INV,Investigation",
      "CON,Contact",
      "VAC,Vaccination",
      "IXS,Interview",
      "SUS,Lab Susceptibility",
      "LAB,Lab Report",
      "ISO,Lab Isolate Tracking"
  })
  void should_resolve_summary_with_event_type_for_known_types(final String type, final String expected) {
    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(QWaTemplate.waTemplate.id)).thenReturn(1L);
    when(tuple.get(QWaTemplate.waTemplate.busObjType)).thenReturn(type);

    PageSummary summary = mapper.toPageSummary(tuple);

    assertThat(summary.eventType())
        .returns(type, PageSummary.EventType::value)
        .returns(expected, PageSummary.EventType::name);
  }

  @Test
  void should_set_event_display_when_unknown_type_found() {

    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(QWaTemplate.waTemplate.id)).thenReturn(1L);
    when(tuple.get(QWaTemplate.waTemplate.busObjType)).thenReturn("something different");

    PageSummary summary = mapper.toPageSummary(tuple);

    assertThat(summary.eventType())
        .returns("something different", PageSummary.EventType::value)
        .returns("something different", PageSummary.EventType::name);
  }

  @ParameterizedTest
  @CsvSource({
      "Draft,0,Published with Draft",
      "Draft,null,Initial Draft",
      "Published,0,Published",
  })
  void should_set_status(String templateType, String versionNbr, String expected) {
    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(QWaTemplate.waTemplate.id)).thenReturn(1L);
    when(tuple.get(QWaTemplate.waTemplate.templateType)).thenReturn(templateType);
    when(tuple.get(QWaTemplate.waTemplate.busObjType)).thenReturn("INV");

    Integer version = "null".equals(versionNbr) ? null : Integer.parseInt(versionNbr);
    when(tuple.get(QWaTemplate.waTemplate.publishVersionNbr)).thenReturn(version);

    PageSummary summary = mapper.toPageSummary(tuple);

    assertThat(summary.status()).isEqualTo(expected);
  }

  @Test
  void should_set_messageMappingGuide() {
    final QWaTemplate template = QWaTemplate.waTemplate;
    final QCodeValueGeneral cvg = QCodeValueGeneral.codeValueGeneral;
    final QAuthUser user = QAuthUser.authUser;

    Instant now = Instant.now();

    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(template.id)).thenReturn(1L);
    when(tuple.get(template.busObjType)).thenReturn("INV");
    when(tuple.get(template.templateNm)).thenReturn("template name");
    when(tuple.get(template.descTxt)).thenReturn("template description");
    when(tuple.get(template.templateType)).thenReturn("Draft");
    when(tuple.get(template.publishVersionNbr)).thenReturn(null);

    when(tuple.get(template.nndEntityIdentifier)).thenReturn("mmgId");
    when(tuple.get(cvg.codeShortDescTxt)).thenReturn("mmg description");
    when(tuple.get(template.lastChgTime)).thenReturn(now);
    when(tuple.get(template.lastChgUserId)).thenReturn(2L);
    when(tuple.get(user.userFirstNm)).thenReturn("first");
    when(tuple.get(user.userLastNm)).thenReturn("last");

    PageSummary summary = mapper.toPageSummary(tuple);
    assertEquals("Investigation", summary.eventType().name());
    assertEquals("INV", summary.eventType().value());
    assertEquals("template name", summary.name());
    assertEquals("template description", summary.description());
    assertEquals("Initial Draft", summary.status());
    assertEquals("mmgId", summary.messageMappingGuide().value());
    assertEquals("mmg description", summary.messageMappingGuide().name());
    assertEquals(now, summary.lastUpdate());
    assertEquals("first last", summary.lastUpdateBy());
  }

}
