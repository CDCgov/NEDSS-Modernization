package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.itextpdf.text.DocumentException;

import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.graphql.searchFilter.LabReportFilter;
import gov.cdc.nbs.service.EventService;
import gov.cdc.nbs.service.ExportService;
import io.swagger.annotations.ApiImplicitParam;

@RestController
public class ExportController {

    @Autowired
    private ExportService exportService;

    @Autowired
    private EventService eventService;

    @PostMapping("/investigations/export/pdf")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<Resource> generateExportPdf(@RequestBody InvestigationFilter filter)
            throws DocumentException {
        var investigations = eventService.findInvestigationsByFilterForExport(filter);
        var pdf = exportService.generateInvestigationPdf(investigations);
        var pdfResource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("InvestigationSearchResults.pdf").build().toString())
                .body(pdfResource);
    }

    @PostMapping("/investigations/export/csv")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<String> generateExportCsv(@RequestBody InvestigationFilter filter) throws DocumentException {
        var investigations = eventService.findInvestigationsByFilterForExport(filter);
        var csv = exportService.generateInvestigationCsv(investigations);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("InvestigationSearchResults.csv").build().toString())
                .body(csv);
    }

    @PostMapping("/labreport/export/pdf")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<Resource> generateExport(@RequestBody LabReportFilter filter) throws DocumentException {
        var labReports = eventService.findLabReportsByFilterForExport(filter);
        var pdf = exportService.generateLabReportPdf(labReports);
        var pdfResource = new ByteArrayResource(pdf);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(pdfResource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("LabReportSearchResults.pdf").build().toString())
                .body(pdfResource);
    }

    @PostMapping("/labreport/export/csv")
    @ApiImplicitParam(name = "Authorization", required = true, allowEmptyValue = false, paramType = "header")
    public ResponseEntity<String> generateExportCsv(@RequestBody LabReportFilter filter) throws DocumentException {
        var labReports = eventService.findLabReportsByFilterForExport(filter);
        var csv = exportService.generateLabReportCsv(labReports);

        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename("LabReportSearchResults.csv").build().toString())
                .body(csv);
    }
}
