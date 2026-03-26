package gov.cdc.nbs.data.sensitive;

public sealed interface SensitiveValue {

  record Allowed<V>(V value) implements SensitiveValue {}

  record Restricted(String reason) implements SensitiveValue {}
}
