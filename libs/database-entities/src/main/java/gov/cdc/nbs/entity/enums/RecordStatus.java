package gov.cdc.nbs.entity.enums;

public enum RecordStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    LOG_DEL("INACTIVE"),
    SUPERCEDED("SUPERSEDED");

    private final String display;

    RecordStatus(final String display) {
        this.display = display;
    }

    public String display() {
        return display;
    }
}
