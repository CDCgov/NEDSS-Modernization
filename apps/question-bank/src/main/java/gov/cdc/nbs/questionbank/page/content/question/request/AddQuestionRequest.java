package gov.cdc.nbs.questionbank.page.content.question.request;

import java.util.Collection;

public record AddQuestionRequest(Collection<Long> questionIds) {}
