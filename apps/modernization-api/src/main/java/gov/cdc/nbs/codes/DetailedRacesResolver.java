package gov.cdc.nbs.codes;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class DetailedRacesResolver {

    private final DetailedRaceFinder finder;

    public DetailedRacesResolver(final DetailedRaceFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<GroupedCodedValue> detailedRaces(@Argument final String category) {
        return category == null ? finder.all() : finder.all(category);
    }

}
