package gov.cdc.nbs.event.search.labreport;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.event.search.labreport.model.ResultedTest;
import gov.cdc.nbs.repository.LabTestRepository;
import gov.cdc.nbs.repository.LoincCodeRepository;

@Component
public class ResultedTestFinder {
    // used in the query to retrieve LOINC ResultedTests - NBS: ObservationProcessor.java #697
    private static List<String> relatedClassCodes = Arrays.asList(
            "ABXBACT",
            "BC",
            "CELLMARK",
            "CHAL",
            "CHALSKIN",
            "CHEM",
            "COAG",
            "CYTO",
            "DRUG",
            "DRUG/TOX",
            "HEM",
            "HEM/BC",
            "MICRO",
            "MISC",
            "PANEL.ABXBACT",
            "PANEL.BC",
            "PANEL.CHEM",
            "PANEL.MICRO",
            "PANEL.OBS",
            "PANEL.SERO",
            "PANEL.TOX",
            "PANEL.UA",
            "SERO",
            "SPEC",
            "TOX",
            "UA",
            "VACCIN");


    private final Integer maxPageSize;
    private final LabTestRepository labTestRepository;
    private final LoincCodeRepository loincCodeRepository;

    public ResultedTestFinder(
            final LabTestRepository labTestRepository,
            final LoincCodeRepository loincCodeRepository,
            @Value("${nbs.max-page-size: 50}") final Integer maxPageSize) {
        this.labTestRepository = labTestRepository;
        this.loincCodeRepository = loincCodeRepository;
        this.maxPageSize = maxPageSize;
    }

    public List<ResultedTest> findDistinctResultedTest(String searchText, boolean loinc) {
        if (loinc) {
            Pageable pageable = PageRequest.of(0, maxPageSize, Direction.ASC, "componentName");
            return loincCodeRepository.findDistinctTestNames(searchText, relatedClassCodes, pageable)
                    .stream()
                    .map(ResultedTest::new)
                    .toList();
        } else {
            Pageable pageable = PageRequest.of(0, maxPageSize, Direction.ASC, "labTestDescTxt");
            return labTestRepository
                    .findDistinctTestNames(searchText, pageable)
                    .stream()
                    .map(ResultedTest::new)
                    .toList();
        }
    }

}
