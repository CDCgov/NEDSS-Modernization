package gov.cdc.nbs.questionbank.question.request;

import org.springframework.data.domain.Sort.Direction;

public record FindQuestionRequest(String search, PageData pageData) {

    public record PageData(int page, int pageSize, Direction direction, String sort) {
    }

}
