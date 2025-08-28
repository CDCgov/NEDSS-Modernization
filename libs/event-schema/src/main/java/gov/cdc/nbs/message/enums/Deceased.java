package gov.cdc.nbs.message.enums;

public enum Deceased {
    Y("Y"),
    N("N"),
    UNK("UNK"),

    FALSE("false") // this value may be bad data in the db restore
    ;

    public static  Deceased resolve(final String value) {
        return value == null ? null : Deceased.valueOf(value);
    }

    private final String value;

  Deceased(String value) {
        this.value = value;
  }

    public String value() {
        return value;
    }

}
