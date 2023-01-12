package gov.cdc.nbs.controller;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.entity.elasticsearch.LabReport;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.InvestigationFilter;
import gov.cdc.nbs.graphql.filter.LabReportFilter;
import gov.cdc.nbs.service.EventService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EventController {
    private static final String hasAuthority = "hasAuthority('";
    private static final String and = " and ";
    private static final String findPatient = hasAuthority + Operations.FIND + "-" + BusinessObjects.PATIENT + "')";
    private static final String viewInvestigation = hasAuthority + Operations.VIEW + "-" + BusinessObjects.INVESTIGATION
            + "')";
    private static final String findPatientAndViewInvestigation = findPatient + and + viewInvestigation;
    private static final String viewLabReport = hasAuthority + Operations.VIEW + "-"
            + BusinessObjects.OBSERVATIONLABREPORT
            + "')";
    private static final String findPatientAndViewLabReport = findPatient + and + viewLabReport;

    private final EventService eventService;

    @QueryMapping
    @PreAuthorize(findPatientAndViewInvestigation)
    public Page<Investigation> findInvestigationsByFilter(@Argument InvestigationFilter filter,
            @Argument GraphQLPage page) {
        return eventService.findInvestigationsByFilter(filter, page);
    }

    @QueryMapping
    @PreAuthorize(findPatientAndViewLabReport)
    public Page<LabReport> findLabReportsByFilter(@Argument LabReportFilter filter,
            @Argument GraphQLPage page) {
        return eventService.findLabReportsByFilter(filter, page);
    }
}
