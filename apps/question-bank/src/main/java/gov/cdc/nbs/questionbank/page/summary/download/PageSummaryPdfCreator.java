package gov.cdc.nbs.questionbank.page.summary.download;

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
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageSummaryPdfCreator {

  private static final Font helvetica = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);
  private static final List<String> PAGE_LIBRARY_HEADERS =
      Arrays.asList(
          "Event Type",
          "Page Name",
          "Page State",
          "Related Conditions(s)",
          "Last Updated",
          "Last Updated By");
  private static final DateTimeFormatter dateFormatter =
      DateTimeFormatter.ofPattern("MM/dd/yyyy").withZone(ZoneId.of("UTC"));

  public byte[] create(Page<PageSummary> summaries) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document();
    document.setPageSize(PageSize.A4.rotate());
    try {
      PdfWriter.getInstance(document, outputStream);

      document.open();
      PdfPTable table = new PdfPTable(6);
      table.setWidthPercentage(95);

      addPageLibraryTableHeader(table);

      for (PageSummary s : summaries) {
        List<String> row =
            Arrays.asList(
                s.eventType().name(),
                s.name(),
                s.status(),
                formatConditions(s.conditions()),
                s.lastUpdate() != null ? dateFormatter.format(s.lastUpdate()) : "",
                s.lastUpdateBy());
        addRow(table, row);
      }
      document.add(table);
      document.close();
      outputStream.close();
      return outputStream.toByteArray();
    } catch (DocumentException | IOException e) {
      throw new PdfCreationException("Failed to create pdf from Page Library");
    }
  }

  private String formatConditions(Collection<ConditionSummary> conditions) {
    return conditions.stream()
        .map(c -> "%s(%s)".formatted(c.name(), c.id()))
        .collect(Collectors.joining(","));
  }

  private void addPageLibraryTableHeader(PdfPTable table) {
    PAGE_LIBRARY_HEADERS.forEach(
        columnTitle -> {
          PdfPCell header = new PdfPCell();
          header.setBackgroundColor(new BaseColor(233, 233, 233));
          header.setBorderWidth(1);
          header.setPhrase(new Phrase(columnTitle, helvetica));
          table.addCell(header);
        });
  }

  private void addRow(PdfPTable table, List<String> row) {
    for (String data : row) {
      table.addCell(createStringCell(data, helvetica));
    }
  }

  private PdfPCell createStringCell(String content, Font font) {
    if (content == null) {
      return new PdfPCell();
    }
    return new PdfPCell(new Phrase(content, font));
  }
}
