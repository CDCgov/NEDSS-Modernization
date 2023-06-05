package gov.cdc.nbs.questionbank.question;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record QuestionPage(
        Integer pageSize,
        Integer pageNumber) {

    public static Pageable toPageable(QuestionPage page) {
        if (page == null) {
            return null;
        }
        return PageRequest.of(page.pageNumber(), page.pageSize());
    }
}
