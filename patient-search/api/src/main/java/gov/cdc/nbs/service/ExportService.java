package gov.cdc.nbs.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;

@Service
public class ExportService {
    private static final List<String> LAB_REPORT_HEADERS = Arrays.asList(
            "Document Type",
            "Date Received",
            "Facility/Provider",
            "Patient",
            "Description",
            "Jurisdiction",
            "Associated With",
            "Local ID");
    private static final List<String> INVESTIGATION_HEADERS = Arrays.asList(
            "Start Date",
            "Investigator",
            "Jurisdiction",
            "Patient",
            "Condition",
            "Case Status",
            "Notification",
            "Investigation ID");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.of("UTC"));
    private static final Font helvetica = new Font(FontFamily.HELVETICA, 7, Font.NORMAL);

    public String generateInvestigationCsv(Page<Investigation> investigations) {
        StringBuilder sb = new StringBuilder();
        // Header row
        sb.append(INVESTIGATION_HEADERS.stream().collect(Collectors.joining(",")));
        sb.append("\n");
        investigations.forEach(i -> {
            // Start Date
            if (i.getAddTime() != null) {
                sb.append(dateFormatter.format(i.getAddTime()));
            }
            sb.append(",");
            // Investigator
            sb.append("\"" + getInvestigatorString(i) + "\"");
            sb.append(",");
            // Jurisdiction
            if (i.getJurisdictionCodeDescTxt() != null) {
                sb.append(i.getJurisdictionCodeDescTxt());
            }
            sb.append(",");
            // Patient
            sb.append("\"" + getPatientString(i) + "\"");
            sb.append(",");
            // Condition
            if (i.getCdDescTxt() != null) {
                sb.append("\"" + i.getCdDescTxt() + "\"");
            }
            sb.append(",");
            // Case Status
            sb.append(getCaseStatusString(i.getCaseClassCd()));
            sb.append(",");
            // Notification
            if (i.getNotificationLocalId() != null) {
                sb.append(i.getNotificationLocalId());
            }
            sb.append(",");
            // Investigation ID
            if (i.getLocalId() != null) {
                sb.append(i.getLocalId());
            }
            sb.append("\n");
        });
        return sb.toString();
    }

    public String generateLabReportCsv(Page<LabReport> labReports) {
        StringBuilder sb = new StringBuilder();
        // Header row
        sb.append(LAB_REPORT_HEADERS.stream().collect(Collectors.joining(",")));
        sb.append("\n");
        labReports.forEach(r -> {
            // Document Type
            sb.append("Lab Report");
            sb.append(",");
            // Date Received
            if (r.getAddTime() != null) {
                sb.append(dateFormatter.format(r.getAddTime()));
            }
            sb.append(",");
            // Facility/Provider
            sb.append("\"" + getFacilityProviderString(r) + "\"");
            sb.append(",");
            // Patient
            sb.append("\"" + getPatientString(r) + "\"");
            sb.append(",");
            // Description
            sb.append("\"" + getDescriptionString(r) + "\"");
            sb.append(",");
            // Jurisdiction
            if (r.getJurisdictionCodeDescTxt() != null) {
                sb.append(r.getJurisdictionCodeDescTxt());
            }
            sb.append(",");
            // Associated With
            sb.append("\"" + getAssociatedWithString(r) + "\"");
            sb.append(",");
            // Local ID
            sb.append(r.getLocalId());

            sb.append("\n");
        });
        return sb.toString();
    }

    public byte[] generateInvestigationPdf(Page<Investigation> investigations) throws DocumentException, IOException {
        var outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(95);

        addInvestigationTableHeader(table);
        investigations.getContent().forEach(i -> addRows(table, i));
        document.add(table);
        document.close();
        outputStream.close();

        return outputStream.toByteArray();
    }

    public byte[] generateLabReportPdf(Page<LabReport> labReports) throws DocumentException, IOException {
        var outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());
        PdfWriter.getInstance(document, outputStream);

        document.open();
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(95);

        addLabReportTableHeader(table);
        labReports.getContent().forEach(i -> addRows(table, i));
        document.add(table);
        document.close();
        outputStream.close();

        return outputStream.toByteArray();
    }

    private void addInvestigationTableHeader(PdfPTable table) {
        INVESTIGATION_HEADERS
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(new BaseColor(233, 233, 233));
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, helvetica));
                    table.addCell(header);
                });
    }

    private void addLabReportTableHeader(PdfPTable table) {
        LAB_REPORT_HEADERS
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(new BaseColor(233, 233, 233));
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, helvetica));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Investigation investigation) {
        // Start Date
        table.addCell(createDateCell(investigation.getAddTime(), helvetica));
        // Investigator
        table.addCell(createStringCell(getInvestigatorString(investigation), helvetica));
        // Jurisdiction
        table.addCell(createStringCell(investigation.getJurisdictionCodeDescTxt(), helvetica));
        // Patient
        table.addCell(createStringCell(getPatientString(investigation), helvetica));
        // Condition
        table.addCell(createStringCell(investigation.getCdDescTxt(), helvetica));
        // Case Status
        table.addCell(createStringCell(getCaseStatusString(investigation.getCaseClassCd()), helvetica));
        // Notification
        table.addCell(createStringCell(investigation.getNotificationLocalId(), helvetica));
        // Investigation ID
        table.addCell(createStringCell(investigation.getLocalId(), helvetica));
    }

    private void addRows(PdfPTable table, LabReport labReport) {
        // Document Type
        table.addCell(createStringCell("Lab Report", helvetica));
        // Date Received
        table.addCell(createDateCell(labReport.getAddTime(), helvetica));
        // Facility/Provider
        table.addCell(createStringCell(getFacilityProviderString(labReport), helvetica));
        // Patient
        table.addCell(createStringCell(getPatientString(labReport), helvetica));
        // Description
        table.addCell(createStringCell(getDescriptionString(labReport), helvetica));
        // Jurisdiction
        table.addCell(createStringCell(labReport.getJurisdictionCodeDescTxt(), helvetica));
        // Associated With
        table.addCell(createStringCell(getAssociatedWithString(labReport), helvetica));
        // Local ID
        table.addCell(createStringCell(labReport.getLocalId(), helvetica));
    }

    private PdfPCell createDateCell(Instant content, Font font) {
        if (content == null) {
            return new PdfPCell();
        }
        return new PdfPCell(new Phrase(dateFormatter.format(content), font));
    }

    private PdfPCell createStringCell(String content, Font font) {
        if (content == null) {
            return new PdfPCell();
        }
        return new PdfPCell(new Phrase(content, font));
    }

    private String getCaseStatusString(String caseClassCd) {
        if (caseClassCd != null) {
            return caseClassCd.equals("C") ? "Confirmed" : caseClassCd;
        }
        return "";
    }

    private String getInvestigatorString(Investigation investigation) {
        return investigation.getPersonParticipations()
                .stream()
                .filter(p -> p.getTypeCd() != null && p.getTypeCd().equals("InvestgrOfPHC"))
                .findFirst()
                .map(i -> i.getLastName() + ", " + i.getFirstName())
                .orElse("");
    }

    private String getFacilityProviderString(LabReport labReport) {
        return labReport.getOrganizationParticipations()
                .stream()
                .filter(o -> o.getTypeCd() != null && o.getTypeCd().equals("AUT"))
                .findFirst()
                .map(f -> "Reporting Facility:\n" + f.getName())
                .orElse("");
    }

    private String getDescriptionString(LabReport labReport) {
        return labReport.getObservations()
                .stream()
                .filter(o -> o.getDomainCd() != null && o.getDomainCd().equals("Result"))
                .findFirst()
                .map(o -> o.getCdDescTxt() + "\n" + o.getDisplayName())
                .orElse("");
    }

    private String getAssociatedWithString(LabReport labReport) {
        if (labReport.getAssociatedInvestigations() == null || labReport.getAssociatedInvestigations().isEmpty()) {
            return "";
        }
        return labReport.getAssociatedInvestigations()
                .stream()
                .map(i -> i.getLocalId() + "\n" + i.getCdDescTxt())
                .collect(Collectors.joining("\n"));
    }

    private String getPatientString(Investigation investigation) {
        return investigation.getPersonParticipations()
                .stream()
                .filter(p -> p.getTypeCd() != null && p.getTypeCd().equals("SubjOfPHC"))
                .findFirst()
                .map(i -> formatPatientInfo(i))
                .orElse("");
    }

    private String getPatientString(LabReport labReport) {
        return labReport.getPersonParticipations()
                .stream()
                .filter(p -> p.getTypeDescTxt() != null && p.getTypeDescTxt().equals("Patient subject"))
                .findFirst()
                .map(i -> formatPatientInfo(i))
                .orElse("");
    }

    private String formatPatientInfo(ElasticsearchPersonParticipation p) {
        if (p == null) {
            return "";
        }
        String birthString = "";
        if (p.getBirthTime() != null) {
            var years = ChronoUnit.YEARS.between(p.getBirthTime().atZone(ZoneId.systemDefault()),
                    Instant.now().atZone(ZoneId.systemDefault()));
            if (years > 0) {
                birthString = dateFormatter.format(p.getBirthTime()) + " (" + years + " Years)";
            } else {
                birthString = dateFormatter.format(p.getBirthTime());
            }
        }
        String currentSexString = "";
        if (p.getCurrSexCd() != null) {
            switch (p.getCurrSexCd()) {
                case "F":
                    currentSexString = "Female";
                    break;
                case "M":
                    currentSexString = "Male";
                    break;
                case "UNK":
                    currentSexString = "Unknown";
                    break;
            }
        }
        return p.getLastName() + ", " + p.getFirstName() + "\n" + "Patient ID: " + p.getEntityId() + "\n"
                + currentSexString + "\n" + birthString;
    }
}
