package gov.cdc.nbs.address;

public record Country(String code, String description) {

    public Country(String code) {
        this(code, null);
    }
}
