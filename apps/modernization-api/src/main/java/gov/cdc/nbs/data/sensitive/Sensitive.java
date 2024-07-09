package gov.cdc.nbs.data.sensitive;

public sealed interface Sensitive {

  record Allowed<V>(V value) implements Sensitive {
  }


  record Restricted(String reason) implements Sensitive {
  }


}
