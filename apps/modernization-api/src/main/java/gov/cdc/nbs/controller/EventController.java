package gov.cdc.nbs.controller;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.entity.odse.Observation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.service.EventService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EventController {
    private static final String HAS_AUTHORITY = "hasAuthority('";
    private static final String AND = " and ";
    private static final String FIND_PATIENT = HAS_AUTHORITY + Operations.FIND + "-" + BusinessObjects.PATIENT + "')";
    private static final String VIEWWORKUP_PATIENT = HAS_AUTHORITY + Operations.VIEWWORKUP + "-"
            + BusinessObjects.PATIENT + "')";

    private static final String VIEW_LAB_REPORT = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";
    private static final String VIEW_MORBIDITY_REPORT = HAS_AUTHORITY + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONMORBIDITYREPORT
            + "')";

    private static final String FIND_PATIENT_AND_VIEW_LAB_REPORT = FIND_PATIENT + AND + VIEW_LAB_REPORT;
    private static final String VIEW_PATIENT_FILE_AND_VIEW_LAB_REPORT = VIEWWORKUP_PATIENT + AND + VIEW_LAB_REPORT;


    private static final String FIND_PATIENT_AND_VIEW_MORBIDITY_REPORT = FIND_PATIENT + AND + VIEW_MORBIDITY_REPORT;

    private final EventService eventService;

    @QueryMapping
    @PreAuthorize(FIND_PATIENT_AND_VIEW_LAB_REPORT)
    public Page<LabReport> findLabReportsByFilter(@Argument LabReportFilter filter,
            @Argument GraphQLPage page) {
        return eventService.findLabReportsByFilter(filter, page);
    }

    @QueryMapping
    @PreAuthorize(VIEW_PATIENT_FILE_AND_VIEW_LAB_REPORT)
    public Page<LabReport> findDocumentsRequiringReviewForPatient(@Argument Long patientId,
            @Argument GraphQLPage page) {
        return eventService.findDocumentsRequiringReviewForPatient(patientId, page);
    }

    @QueryMapping()
    @PreAuthorize(FIND_PATIENT_AND_VIEW_MORBIDITY_REPORT)
    public Page<Observation> findMorbidtyReportForPatient(@Argument Long patientId, @Argument GraphQLPage page) {
        return eventService.findMorbidtyReportForPatient(patientId, page);
    }

}
