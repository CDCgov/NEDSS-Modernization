package gov.cdc.nbs.event.search.labreport;

import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.event.search.labreport.model.CodedResult;

@Controller
public class LabResultController {

    private final CodedResultFinder finder;

    public LabResultController(final CodedResultFinder finder) {
        this.finder = finder;
    }

    @QueryMapping
    public List<CodedResult> findDistinctCodedResults(@Argument String searchText) {
        return finder.findDistinctCodedResults(searchText);
    }

}
