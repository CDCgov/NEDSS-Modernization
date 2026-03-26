package gov.cdc.nbs.questionbank.page.summary.download;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary.EventType;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class PageSummaryCsvCreatorTest {

  PageSummaryCsvCreator creator = new PageSummaryCsvCreator();

  @Test
  void should_create_csv() {
    LocalDate date = LocalDate.of(2023, 12, 9);
    // Given page summaries
    Page<PageSummary> summaries =
        new PageImpl<>(
            Collections.singletonList(
                new PageSummary(
                    1,
                    new EventType("ET", "ET-Display"),
                    "pageName",
                    "Initial Draft",
                    Collections.singletonList(new ConditionSummary("2", "condition")),
                    date,
                    "test user")));

    // When a csv is created
    ByteArrayInputStream is = creator.toCsv(summaries);

    // Then the csv contains the proper info
    String csv = new String(is.readAllBytes());
    String expectedHeaders =
        "Event Type,Page Name,Page State,Related Conditions(s),Last Updated,Last Updated By";
    assertThat(csv.lines().toList().get(0)).isEqualTo(expectedHeaders);

    String expectedRow = "ET-Display,pageName,Initial Draft,condition(2),12/09/2023,test user";
    assertThat(csv.lines().toList().get(1)).isEqualTo(expectedRow);
  }
}
