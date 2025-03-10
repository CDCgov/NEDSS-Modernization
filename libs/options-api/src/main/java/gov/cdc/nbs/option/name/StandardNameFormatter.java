package gov.cdc.nbs.option.name;

public class StandardNameFormatter {


    public static String formatted(final String value) {
        return value == null ? null : format(value);
    }

    private static String format(final String value) {
        return value.length() > 1
            ? value.substring(0,1).toUpperCase() + value.substring(1).toLowerCase()
            : value.toUpperCase();
    }

    private StandardNameFormatter() {
    }

}
