package gov.cdc.nbs.event.search.labreport;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.event.search.labreport.model.CodedResult;
import gov.cdc.nbs.repository.LabResultRepository;
import gov.cdc.nbs.repository.SnomedCodeRepository;

@Component
public class CodedResultFinder {
    private final Integer maxPageSize;
    private final LabResultRepository labResultRepository;
    private final SnomedCodeRepository snomedCodeRepository;

    public CodedResultFinder(
            final LabResultRepository labResultRepository,
            final SnomedCodeRepository snomedCodeRepository,
            @Value("${nbs.max-page-size: 50}") final Integer maxPageSize) {
        this.labResultRepository = labResultRepository;
        this.snomedCodeRepository = snomedCodeRepository;
        this.maxPageSize = maxPageSize;
    }

    public List<CodedResult> findDistinctCodedResults(String searchText, boolean snomed) {
        String searchString = "%" + searchText + "%";
        if (snomed) {
            Pageable pageable = PageRequest.of(0, maxPageSize, Direction.ASC, "snomedDescTxt");
            return snomedCodeRepository.findDistinctSnomedCodes(searchString, pageable)
                    .stream()
                    .map(CodedResult::new)
                    .toList();
        } else {
            Pageable pageable = PageRequest.of(0, maxPageSize, Direction.ASC, "labResultDescTxt");
            return labResultRepository.findDistinctLabResults(searchString, pageable)
                    .stream()
                    .map(CodedResult::new)
                    .toList();
        }
    }

}
