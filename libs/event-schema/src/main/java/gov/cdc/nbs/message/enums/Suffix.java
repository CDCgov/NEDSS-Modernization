package gov.cdc.nbs.message.enums;

public enum Suffix {
    ESQ("Esquire"),
    II("II / The Second"),
    III("III / The Third"),
    IV("IV / The Fourth"),
    JR("Jr."),
    SR("Sr."),
    V("V / The Fifth");

    private final String display;

    Suffix(final String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
