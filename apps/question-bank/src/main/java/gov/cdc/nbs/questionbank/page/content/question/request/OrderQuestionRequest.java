package gov.cdc.nbs.questionbank.page.content.question.request;

public record OrderQuestionRequest(Long questionId, Integer tabPosition,
                                   Integer sectionPosition,
                                   Integer subSectionPosition,
                                   Integer currentPosition,
                                   Integer desiredPosition) {

}
