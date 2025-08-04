package gov.cdc.nbs.message.enums;

public enum Suffix {
    ESQ("ESQ"),
    II("II"),
    III("III"),
    IV("IV"),
    JR("JR"),
    SR("SR"),
    V("V");

    public static Suffix resolve(final String value) {
        return value == null ? null : Suffix.valueOf(value);
    }

    private final String value;

  Suffix(final String value) {
        this.value = value;
  }

    public String value() {
        return value;
    }
}
