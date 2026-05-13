package gov.cdc.nbs.search;

/**
 * A document that can be written to an Elasticsearch index.
 *
 * @param identifier the unique Elasticsearch document id
 * @param payload the source object to store in the index
 */
public record SimpleDocument(String identifier, Object payload) {}
