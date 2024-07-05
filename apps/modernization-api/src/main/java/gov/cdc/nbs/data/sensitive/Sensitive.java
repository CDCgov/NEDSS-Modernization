package gov.cdc.nbs.data.sensitive;

public sealed interface Sensitive<V> {

  record Allowed<V>(V value) implements Sensitive<V> {
  }


  record Restricted<V>(String reason) implements Sensitive<V> {
  }


}
