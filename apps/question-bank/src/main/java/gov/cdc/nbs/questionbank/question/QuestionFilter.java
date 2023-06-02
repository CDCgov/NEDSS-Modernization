package gov.cdc.nbs.questionbank.question;

import org.springframework.data.domain.Pageable;

public record QuestionFilter(
        String searchText,
        Pageable pageable) {

}
