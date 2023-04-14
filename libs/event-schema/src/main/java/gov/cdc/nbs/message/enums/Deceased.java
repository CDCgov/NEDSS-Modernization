package gov.cdc.nbs.message.enums;

public enum Deceased {
    Y("Yes"),
    N("No"),
    UNK("Unknown"),

    FALSE("false") // this value may be bad data in the db restore
    ;
    private final String display;

    Deceased(final String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
