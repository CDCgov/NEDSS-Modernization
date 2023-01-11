package gov.cdc.nbs.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import gov.cdc.nbs.entity.elasticsearch.AssociatedInvestigation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchObservation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchOrganizationParticipation;
import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;

public class ExportServiceTest {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter
            .ofPattern("dd/MM/yyyy")
            .withZone(ZoneId.of("UTC"));
    ExportService exportService = new ExportService();
    Instant now = Instant.now();
    Investigation investigation = Investigation.builder()
            .addTime(now)
            .personParticipations(
                    Arrays.asList(ElasticsearchPersonParticipation.builder()
                            .firstName("Imthe")
                            .lastName("Investigator")
                            .typeCd("InvestgrOfPHC")
                            .build(),
                            ElasticsearchPersonParticipation.builder()
                                    .firstName("AndIm")
                                    .lastName("ThePatient")
                                    .birthTime(now)
                                    .entityId(123L)
                                    .typeCd("SubjOfPHC")
                                    .build()))
            .cdDescTxt("condition")
            .jurisdictionCodeDescTxt("Random Jurisdiction")
            .caseClassCd("C")
            .notificationLocalId("notificationId")
            .build();

    LabReport labReport = LabReport.builder()
            .addTime(now)
            .personParticipations(
                    Arrays.asList(ElasticsearchPersonParticipation.builder()
                            .firstName("AndIm")
                            .lastName("ThePatient")
                            .birthTime(now)
                            .entityId(123L)
                            .typeDescTxt("Patient subject")
                            .build()))
            .organizationParticipations(Arrays.asList(ElasticsearchOrganizationParticipation.builder()
                    .typeCd("AUT")
                    .name("Some facility")
                    .build()))
            .observations(Arrays.asList(ElasticsearchObservation.builder()
                    .domainCd("Result")
                    .cdDescTxt("Some Test")
                    .displayName("positive")
                    .build()))
            .associatedInvestigations(Arrays.asList(AssociatedInvestigation.builder()
                    .localId("associated local Id")
                    .cdDescTxt("name of investigation")
                    .build()))
            .cdDescTxt("condition")
            .localId("lab report local Id")
            .jurisdictionCodeDescTxt("Random Jurisdiction")
            .build();

    @Test
    void testGenerateInvestigationCsv() {
        var csv = exportService.generateInvestigationCsv(new PageImpl<>(Arrays.asList(investigation)));
        assertNotNull(csv);
        assertTrue(csv.contains("Investigator, Imthe"));
        assertTrue(csv.contains("ThePatient, AndIm"));
        assertTrue(csv.contains("123"));
        assertTrue(csv.contains("condition"));
        assertTrue(csv.contains("Random Jurisdiction"));
        assertTrue(csv.contains("notificationId"));
        assertTrue(!csv.contains("null"));
        assertTrue(csv.contains(dateFormatter.format(now)));
    }

    @Test
    void testGenerateInvestigationPdf() throws DocumentException, IOException {
        var pdf = exportService.generateInvestigationPdf(new PageImpl<>(Arrays.asList(investigation)));
        var reader = new PdfReader(new ByteArrayInputStream(pdf));
        var pdfText = PdfTextExtractor.getTextFromPage(reader, 1);
        assertNotNull(pdfText);
        assertTrue(pdfText.contains("Investigator, Imthe"));
        assertTrue(pdfText.contains("ThePatient, AndIm"));
        assertTrue(pdfText.contains("123"));
        assertTrue(pdfText.contains("condition"));
        assertTrue(pdfText.contains("Random Jurisdiction"));
        assertTrue(pdfText.contains("notificationId"));
        assertTrue(!pdfText.contains("null"));
        assertTrue(pdfText.contains(dateFormatter.format(now)));
    }

    @Test
    void testGenerateLabReportCsv() {
        var csv = exportService.generateLabReportCsv(new PageImpl<>(Arrays.asList(labReport)));
        assertTrue(csv.contains("Lab Report"));
        assertTrue(csv.contains(dateFormatter.format(now)));
        assertTrue(csv.contains("Reporting Facility:\nSome facility"));
        assertTrue(csv.contains("ThePatient, AndIm"));
        assertTrue(csv.contains("Some Test\npositive"));
        assertTrue(csv.contains("Random Jurisdiction"));
        assertTrue(csv.contains("associated local Id\nname of investigation"));
        assertTrue(csv.contains("lab report local Id"));
    }

    @Test
    void testGenerateLabReportPdf() throws IOException, DocumentException {
        var pdf = exportService.generateLabReportPdf(new PageImpl<>(Arrays.asList(labReport)));
        var reader = new PdfReader(new ByteArrayInputStream(pdf));
        var pdfText = PdfTextExtractor.getTextFromPage(reader, 1);
        assertTrue(pdfText.contains("Lab Report"));
        assertTrue(pdfText.contains(dateFormatter.format(now)));
        assertTrue(pdfText.contains("Reporting Facility:"));
        assertTrue(pdfText.contains("Some facility"));
        assertTrue(pdfText.contains("ThePatient, AndIm"));
        assertTrue(pdfText.contains("Some Test"));
        assertTrue(pdfText.contains("positive"));
        assertTrue(pdfText.contains("Random Jurisdiction"));
        assertTrue(pdfText.contains("associated local Id"));
        assertTrue(pdfText.contains("name of investigation"));
        assertTrue(pdfText.contains("lab report local Id"));
    }
}