package gov.cdc.nbs.questionbank.page.util;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PageSummarySearchHolder {

    private String search;
    private Page<PageSummary> results;
}
