package gov.cdc.nbs.questionbank.question.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String message) {
        super(message);
    }

    public QuestionNotFoundException(Long id) {
        super("Unable to find question with id: " + id);
    }
}
