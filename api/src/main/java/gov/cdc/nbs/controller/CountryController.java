package gov.cdc.nbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import gov.cdc.nbs.entity.srte.CountryCode;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.repository.CountryCodeRepository;

@Controller
public class CountryController {
    @Value("${nbs.max-page-size: 50}")
    private Integer MAX_PAGE_SIZE;

    @Autowired
    private CountryCodeRepository countryCodeRepository;

    @QueryMapping()
    public Page<CountryCode> findAllCountryCodes(@Argument GraphQLPage page) {
        return countryCodeRepository.findAll(GraphQLPage.toPageable(page, MAX_PAGE_SIZE));
    }
}
