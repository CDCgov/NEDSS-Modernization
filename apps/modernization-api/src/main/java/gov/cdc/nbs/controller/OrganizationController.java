package gov.cdc.nbs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.OrganizationFilter;
import gov.cdc.nbs.service.OrganizationService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class OrganizationController {

    @Autowired
    private final OrganizationService organizationService;

    @QueryMapping()
    public Page<Organization> findOrganizationsByFilter(@Argument OrganizationFilter filter,
            @Argument GraphQLPage page) {
        return organizationService.findOrganizationsByFilter(filter, page);
    }

    @QueryMapping()
    public Page<Organization> findAllOrganizations(@Argument GraphQLPage page) {
        return organizationService.findAllOrganizations(page);
    }

    @QueryMapping()
    public Optional<Organization> findOrganizationById(@Argument Long id) {
        return organizationService.findOrganizationById(id);
    }
}
