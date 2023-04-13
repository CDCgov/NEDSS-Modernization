package gov.cdc.nbs.patient;

import gov.cdc.nbs.message.enums.Indicator;

public class IndicatorStringConverter {

    public static Indicator fromString(final String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Y" -> Indicator.Yes;
            case "UNK" -> Indicator.Unknown;
            case "N" -> Indicator.No;
            default -> null;
        };
    }

    public static String toString(final Indicator value) {
        if (value == null) {
            return null;
        }
        return value.code();
    }

    private IndicatorStringConverter() {

    }
}
