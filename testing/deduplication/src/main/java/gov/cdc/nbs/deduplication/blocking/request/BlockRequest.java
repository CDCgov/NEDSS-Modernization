package gov.cdc.nbs.deduplication.blocking.request;

public record BlockRequest(BlockField field, BlockTransformer transformer, String value) {

  public enum BlockField {
    FIRST_NAME,
    LAST_NAME,
    BIRTHDATE,
    SEX,
    STREET_ADDRESS,
    CITY,
    STATE,
    ZIP,
    IDENTIFICATION
  }

  public enum BlockTransformer {
    FIRST_FOUR,
    LAST_FOUR,
    NONE
  }

}
