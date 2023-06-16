package gov.cdc.nbs.codes;

import java.util.function.UnaryOperator;

class CodedValues {

    static UnaryOperator<CodedValue> withStandardizedName() {
        return existing -> new CodedValue(existing.value(), StandardNameFormatter.formatted(existing.name()));
    }

    private CodedValues() {

    }
}
