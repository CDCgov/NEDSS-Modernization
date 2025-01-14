package gov.cdc.nbs.codes;

import java.util.function.UnaryOperator;

class CodedValues {

    static UnaryOperator<CodedValue> withStandardizedName() {
        return existing -> new CodedValue(existing.value(), StandardNameFormatter.formatted(existing.name()));
    }

    static UnaryOperator<CodedValue> withEducationName() {
        return existing -> new CodedValue(existing.value(), StandardNameFormatter.formatted(existing.name()).replaceFirst("(?i)ged", "GED"));
    }

    private CodedValues() {

    }
}
