package gov.cdc.nbs.codes.location.state;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class StateCodedValueResolver {

    private final StateCodedValueFinder finder;

    public StateCodedValueResolver(final StateCodedValueFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<StateCodedValue> states() {
        return finder.all();
    }

}
