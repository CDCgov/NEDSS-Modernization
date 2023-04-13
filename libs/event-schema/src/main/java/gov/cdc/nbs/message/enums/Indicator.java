package gov.cdc.nbs.message.enums;

public enum Indicator {
    YES("Y", "Yes"),
    NO("N", "No"),
    UNKNOWN("UNK", "Unknown");
    private final String code;
    private final String display;

    Indicator(String code, final String display) {
        this.code = code;
        this.display = display;
    }

    public String code() {
        return code;
    }

    public String display() {
        return display;
    }
}
