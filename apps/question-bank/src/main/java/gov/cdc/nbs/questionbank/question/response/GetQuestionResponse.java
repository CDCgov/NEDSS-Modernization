package gov.cdc.nbs.questionbank.question.response;

import gov.cdc.nbs.questionbank.question.model.Question;

public record GetQuestionResponse(Question question, boolean isInUse) {}
