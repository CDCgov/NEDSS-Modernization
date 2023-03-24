package gov.cdc.nbs.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.net.HttpHeaders;
import com.itextpdf.text.DocumentException;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.investigation.InvestigationFilter;
import gov.cdc.nbs.investigation.InvestigationFinder;
import gov.cdc.nbs.labreport.LabReportFinder;
import gov.cdc.nbs.service.ExportService;
import io.swagger.annotations.ApiImplicitParam;

@RestController
public class ExportController {
    private static final int MAX_EXPORT_SIZE = 1000;

    @Autowired
    private ExportService exportService;

    @Autowired
    private LabReportFinder labReportFinder;

    @Autowired
    private InvestigationFinder investigationFinder;

    @PostMapping(value = "/investigation/export/pdf", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<byte[]> generateInvestigationPdf(@RequestBody InvestigationFilter filter)
            throws DocumentException, IOException {
        var investigations = investigationFinder.find(filter, Pageable.ofSize(MAX_EXPORT_SIZE));
        var pdf = exportService.generateInvestigationPdf(investigations);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("InvestigationSearchResults.pdf").build()
                                .toString())
                .body(pdf);
    }

    @PostMapping("/investigation/export/csv")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<String> generateInvestigationCsv(@RequestBody InvestigationFilter filter) {
        var investigations = investigationFinder.find(filter, Pageable.ofSize(MAX_EXPORT_SIZE));
        var csv = exportService.generateInvestigationCsv(investigations);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("InvestigationSearchResults.csv").build()
                                .toString())
                .body(csv);
    }

    @PostMapping(value = "/labreport/export/pdf", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<byte[]> generateLabReportPdf(@RequestBody LabReportFilter filter)
            throws DocumentException, IOException {
        var labReports = labReportFinder.find(filter, Pageable.ofSize(MAX_EXPORT_SIZE));
        var pdf = exportService.generateLabReportPdf(labReports);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("LabReportSearchResults.pdf")
                                .build().toString())
                .body(pdf);
    }

    @PostMapping("/labreport/export/csv")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<String> generateLabReportCsv(@RequestBody LabReportFilter filter) {
        var labReports = labReportFinder.find(filter, Pageable.ofSize(MAX_EXPORT_SIZE));
        var csv = exportService.generateLabReportCsv(labReports);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("LabReportSearchResults.csv")
                                .build().toString())
                .body(csv);
    }
}
