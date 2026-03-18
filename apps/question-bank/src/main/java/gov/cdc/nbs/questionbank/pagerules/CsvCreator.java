package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.page.summary.download.exceptions.CsvCreationException;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Component;

@Component
public class CsvCreator {

  public byte[] create(List<Rule> rules) {
    final CSVFormat format =
        CSVFormat.Builder.create()
            .setQuoteMode(QuoteMode.MINIMAL)
            .setHeader("Function", "Source Field", "Logic", "Value(s)", "Target Field(s)", "ID")
            .build();

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {

      for (Rule r : rules) {
        List<String> row =
            Arrays.asList(
                r.ruleFunction().getValue(),
                "%s (%s)"
                    .formatted(r.sourceQuestion().label(), r.sourceQuestion().questionIdentifier()),
                r.comparator().getValue(),
                formatSourceValues(r.anySourceValue(), r.sourceValues()),
                formatTargets(r.targets()),
                String.valueOf(r.id()));
        csvPrinter.printRecord(row);
      }
      csvPrinter.flush();
      return out.toByteArray();
    } catch (IOException e) {
      throw new CsvCreationException("Failed to create csv from Page Library");
    }
  }

  private String formatSourceValues(boolean anySourceValue, List<String> sourceValues) {
    if (anySourceValue) {
      return "Any Source Value";
    } else if (sourceValues != null) {
      return String.join(",", sourceValues);
    }
    return "";
  }

  private String formatTargets(List<Target> targets) {
    return targets.stream()
        .map(t -> "%s (%s)".formatted(t.label(), t.targetIdentifier()))
        .collect(Collectors.joining(","));
  }
}
