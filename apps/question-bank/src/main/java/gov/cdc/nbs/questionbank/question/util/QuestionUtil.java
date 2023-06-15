package gov.cdc.nbs.questionbank.question.util;

import gov.cdc.nbs.questionbank.question.exception.NullObjectException;

public class QuestionUtil {

    private QuestionUtil() {

    }

    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new NullObjectException(message);
        }
        return obj;
    }


}
