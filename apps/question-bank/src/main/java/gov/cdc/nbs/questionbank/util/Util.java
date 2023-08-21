package gov.cdc.nbs.questionbank.util;

import gov.cdc.nbs.questionbank.exception.NullObjectException;

public class Util {

    private Util() {

    }

    public static <T> T requireNonNull(T obj, String field) {
        if (obj == null) {
            throw new NullObjectException(field + " must not be null");
        }
        return obj;
    }

    public static String requireNotEmpty(String obj, String field) {
        if (obj == null || obj.isBlank()) {
            throw new NullObjectException(field + " must not be null or empty");
        }
        return obj;
    }


}
