package gov.cdc.nbs.message.enums;

public enum Suffix {
    ESQ("ESQ", "Esquire"),
    II("II", "II / The Second"),
    III("III", "III / The Third"),
    IV("IV", "IV / The Fourth"),
    JR("JR", "Jr."),
    SR("SR", "Sr."),
    V("V", "V / The Fifth");

    private final String value;
    private final String display;

    Suffix(final String value, final String display) {
        this.value = value;
        this.display = display;
    }

    public String display() {
        return display;
    }

    public String value() {
        return value;
    }
}
