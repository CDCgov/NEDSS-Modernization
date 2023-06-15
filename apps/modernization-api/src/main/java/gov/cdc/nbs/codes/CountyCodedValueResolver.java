package gov.cdc.nbs.codes;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class CountyCodedValueResolver {

    private final CountyCodedValueFinder finder;

    public CountyCodedValueResolver(final CountyCodedValueFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<GroupedCodedValue> counties(@Argument final String state) {
        return state == null ? finder.all() : finder.all(state);
    }

}
