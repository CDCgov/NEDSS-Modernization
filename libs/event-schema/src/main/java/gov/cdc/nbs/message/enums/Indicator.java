package gov.cdc.nbs.message.enums;

public enum Indicator {
    Yes("Y", "Yes"),
    No("N", "No"),
    Unknown("UNK", "Unknown");
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
