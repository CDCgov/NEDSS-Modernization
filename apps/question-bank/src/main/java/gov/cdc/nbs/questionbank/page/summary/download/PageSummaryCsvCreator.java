package gov.cdc.nbs.questionbank.page.summary.download;

import gov.cdc.nbs.questionbank.page.summary.download.exceptions.CsvCreationException;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageSummaryCsvCreator {
  private static final DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(ZoneId.of("UTC"));

  public ByteArrayInputStream toCsv(Page<PageSummary> summaries) {

    final CSVFormat format =
        CSVFormat.Builder.create()
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader(
                "Event Type",
                "Page Name",
                "Page State",
                "Related Conditions(s)",
                "Last Updated",
                "Last Updated By")
            .build();

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

      for (PageSummary s : summaries) {
        List<String> row =
            Arrays.asList(
                s.eventType().name(),
                s.name(),
                s.status(),
                formatConditions(s.conditions()),
                s.lastUpdate() != null ? dateFormatter.format(s.lastUpdate()) : "",
                s.lastUpdateBy());
        csvPrinter.printRecord(row);
      }
      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new CsvCreationException("Failed to create csv from Page Library");
    }
  }

  private String formatConditions(Collection<ConditionSummary> conditions) {
    return conditions.stream()
        .map(c -> "%s(%s)".formatted(c.name(), c.id()))
        .collect(Collectors.joining(","));
  }
}
