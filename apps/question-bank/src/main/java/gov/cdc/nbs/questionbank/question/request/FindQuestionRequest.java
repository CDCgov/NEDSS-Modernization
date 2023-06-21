package gov.cdc.nbs.questionbank.question.request;

import org.springframework.data.domain.Pageable;

public record FindQuestionRequest(String search, Pageable pageable) {

}
