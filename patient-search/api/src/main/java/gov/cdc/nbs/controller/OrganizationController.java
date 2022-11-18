package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.odse.Organization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.searchFilter.OrganizationFilter;
import gov.cdc.nbs.service.OrganizationService;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrganizationController {

    @Autowired
    private final OrganizationService organizationService;

    @QueryMapping()
    public List<Organization> findOrganizationsByFilter(@Argument OrganizationFilter filter,
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
