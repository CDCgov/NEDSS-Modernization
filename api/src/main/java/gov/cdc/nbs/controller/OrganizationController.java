package gov.cdc.nbs.controller;

import gov.cdc.nbs.entity.Organization;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.OrganizationFilter;
import gov.cdc.nbs.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @QueryMapping()
    public List<Organization> findOrganizationsByFilter(@Argument OrganizationFilter filter) {
        return organizationService.findOrganizationsByFilter(filter);
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
