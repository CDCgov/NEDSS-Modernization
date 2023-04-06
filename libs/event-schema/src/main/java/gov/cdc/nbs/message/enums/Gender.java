package gov.cdc.nbs.message.enums;

public enum Gender {
    M("Male"),
    F("Female"),
    U("Unknown");

    private final String display;

    Gender(String display) {
        this.display = display;
    }

    public String display() {
        return this.display;
    }
}
