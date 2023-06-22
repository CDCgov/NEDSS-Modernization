package gov.cdc.nbs.message.enums;

public enum Deceased {
    Y("Y", "Yes"),
    N("N", "No"),
    UNK("UNK", "Unknown"),

    FALSE("false", "false") // this value may be bad data in the db restore
    ;

    public static  Deceased resolve(final String value) {
        return value == null ? null : Deceased.valueOf(value);
    }

    private final String value;
    private final String display;

    Deceased(String value, final String display) {
        this.value = value;
        this.display = display;
    }

    public String value() {
        return value;
    }

    public String display() {
        return display;
    }
}
