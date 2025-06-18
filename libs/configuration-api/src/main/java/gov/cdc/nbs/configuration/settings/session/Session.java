package gov.cdc.nbs.configuration.settings.session;

public record Session(long warning, long expiration, String keepAlivePath) {
}
