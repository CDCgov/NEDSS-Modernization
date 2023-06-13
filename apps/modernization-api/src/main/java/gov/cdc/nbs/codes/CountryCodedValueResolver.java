package gov.cdc.nbs.codes;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class CountryCodedValueResolver {

    private final CountryCodedValueFinder finder;

    public CountryCodedValueResolver(final CountryCodedValueFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<CodedValue> countries() {
        return finder.all();
    }

}
