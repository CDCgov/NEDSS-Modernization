package gov.cdc.nbs.controller;

import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.config.security.SecurityUtil.BusinessObjects;
import gov.cdc.nbs.config.security.SecurityUtil.Operations;
import gov.cdc.nbs.entity.elasticsearch.Investigation;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.InvestigationFilter;
import gov.cdc.nbs.service.EventService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EventController {
    private final String FIND_PATIENT = "hasAuthority('" + Operations.FIND + "-" + BusinessObjects.PATIENT + "')";
    private final String VIEW_INVESTIGATION = "hasAuthority('" + Operations.VIEW + "-" + BusinessObjects.INVESTIGATION
            + "')";
    private final String FIND_PATIENT_AND_VIEW_INVESTIGATION = FIND_PATIENT + " and " + VIEW_INVESTIGATION;

    private final EventService eventService;

    @QueryMapping
    @PreAuthorize(FIND_PATIENT_AND_VIEW_INVESTIGATION)
    public Page<Investigation> findInvestigationsByFilter(@Argument InvestigationFilter filter,
            @Argument GraphQLPage page) {
        return eventService.findInvestigationsByFilter(filter, page);
    }
}
