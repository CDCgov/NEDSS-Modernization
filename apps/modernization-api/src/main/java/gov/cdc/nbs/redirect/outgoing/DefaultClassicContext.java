package gov.cdc.nbs.redirect.outgoing;

public record DefaultClassicContext(String host, String user, String session) implements ClassicContext {
}
