package gov.cdc.nbs.questionbank.pagerules;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import gov.cdc.nbs.questionbank.page.summary.download.exceptions.PdfCreationException;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class PdfCreator {
  private static final Font HELVETICA = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);
  private static final List<String> HEADERS =
      Arrays.asList("Function", "Source Field", "Logic", "Value(s)", "Target Field(s)", "ID");

  public byte[] create(List<Rule> rules) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document();
    document.setPageSize(PageSize.A4.rotate());
    try {
      PdfWriter.getInstance(document, outputStream);

      document.open();
      PdfPTable table = new PdfPTable(6);
      table.setWidthPercentage(95);

      addHeaders(table);

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
        addRow(table, row);
      }
      document.add(table);
      document.close();
      outputStream.close();
      return outputStream.toByteArray();
    } catch (DocumentException | IOException e) {
      throw new PdfCreationException("Failed to create pdf");
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

  private void addHeaders(PdfPTable table) {
    HEADERS.forEach(
        columnTitle -> {
          PdfPCell header = new PdfPCell();
          header.setBackgroundColor(new BaseColor(233, 233, 233));
          header.setBorderWidth(1);
          header.setPhrase(new Phrase(columnTitle, HELVETICA));
          table.addCell(header);
        });
  }

  private void addRow(PdfPTable table, List<String> row) {
    for (String data : row) {
      table.addCell(createStringCell(data, HELVETICA));
    }
  }

  private PdfPCell createStringCell(String content, Font font) {
    if (content == null) {
      return new PdfPCell();
    }
    return new PdfPCell(new Phrase(content, font));
  }
}
