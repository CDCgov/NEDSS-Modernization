package gov.cdc.nbs.address;

public record City(String code, String description) {

    public City(String code) {
        this(code, null);
    }
}
