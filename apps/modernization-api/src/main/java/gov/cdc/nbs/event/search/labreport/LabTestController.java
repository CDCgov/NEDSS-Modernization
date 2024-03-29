package gov.cdc.nbs.event.search.labreport;

import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.event.search.labreport.model.ResultedTest;

@Controller
public class LabTestController {

    private final ResultedTestFinder resultedTestFinder;

    public LabTestController(
            final ResultedTestFinder resultedTestFinder) {
        this.resultedTestFinder = resultedTestFinder;
    }

    @QueryMapping
    public List<ResultedTest> findDistinctResultedTest(@Argument String searchText) {
        return resultedTestFinder.findDistinctResultedTests(searchText);
    }

}
