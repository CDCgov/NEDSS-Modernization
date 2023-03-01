package gov.cdc.nbs.address;

public record County(String code, String description) {

    public County(String code) {
        this(code, null);
    }
}
