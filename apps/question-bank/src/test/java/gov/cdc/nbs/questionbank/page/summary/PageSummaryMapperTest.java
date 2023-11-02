package gov.cdc.nbs.questionbank.page.summary;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.questionbank.page.summary.PageSummaryMapper;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryTables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class PageSummaryMapperTest {

  private final PageSummaryTables tables = new PageSummaryTables();
  private final PageSummaryMapper mapper = new PageSummaryMapper(tables);

  @ParameterizedTest
  @CsvSource({
      "Draft,0,Published with Draft",
      "Draft,null,Initial Draft",
      "Published,0,Published",
  })
  void should_set_status(String templateType, String versionNbr, String expected) {
    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(this.tables.page().id)).thenReturn(1L);
    when(tuple.get(this.tables.page().templateType)).thenReturn(templateType);

    Integer version = "null".equals(versionNbr) ? null : Integer.parseInt(versionNbr);
    when(tuple.get(this.tables.page().publishVersionNbr)).thenReturn(version);

    PageSummary summary = mapper.map(tuple);

    assertThat(summary.status()).isEqualTo(expected);
  }

  @Test
  void should_set_messageMappingGuide() {

    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(this.tables.page().id)).thenReturn(17L);
    when(tuple.get(this.tables.page().templateNm)).thenReturn("template name");
    when(tuple.get(this.tables.page().descTxt)).thenReturn("template description");
    when(tuple.get(this.tables.page().templateType)).thenReturn("Draft");
    when(tuple.get(this.tables.page().publishVersionNbr)).thenReturn(null);
    when(tuple.get(this.tables.eventType().id.code)).thenReturn("INV");
    when(tuple.get(this.tables.eventType().codeShortDescTxt)).thenReturn("Investigation");
    when(tuple.get(this.tables.mappingGuide().id.code)).thenReturn("mmgId");
    when(tuple.get(this.tables.mappingGuide().codeShortDescTxt)).thenReturn("mmg description");
    when(tuple.get(this.tables.page().lastChgTime)).thenReturn(Instant.parse("2023-10-17T15:27:13Z"));
    when(tuple.get(this.tables.page().lastChgUserId)).thenReturn(2L);
    when(tuple.get(this.tables.lastUpdatedBy())).thenReturn("first last");

    PageSummary actual = mapper.map(tuple);

    assertThat(actual.id()).isEqualTo(17L);
    assertThat(actual.name()).isEqualTo("template name");
    assertThat(actual.description()).isEqualTo("template description");
    assertThat(actual.status()).isEqualTo("Initial Draft");

    assertThat(actual.eventType().value()).isEqualTo("INV");
    assertThat(actual.eventType().name()).isEqualTo("Investigation");

    assertThat(actual.messageMappingGuide().value()).isEqualTo("mmgId");
    assertThat(actual.messageMappingGuide().name()).isEqualTo("mmg description");

    assertThat(actual.lastUpdateBy()).isEqualTo("first last");
    assertThat(actual.lastUpdate()).isEqualTo("2023-10-17T15:27:13Z");

  }

}
