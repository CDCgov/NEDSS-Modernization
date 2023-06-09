package gov.cdc.nbs.codes;

import gov.cdc.nbs.patient.KeyValuePair;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class PrimaryOccupationResolver {

    private final PrimaryOccupationValueSetFinder finder;

    public PrimaryOccupationResolver(final PrimaryOccupationValueSetFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public Collection<CodedValue> primaryOccupations() {
        return finder.all();
    }

}
