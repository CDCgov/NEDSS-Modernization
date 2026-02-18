package gov.cdc.nbs.search;

/**
 * A simple representation of an Elasticsearch index that contains a name and the {@code Path} to
 * the descriptor that defines it.
 *
 * @param name The name of the index
 * @param location The location of the JSON file containing the {@code settings} and {@code
 *     mappings} of the index.
 */
public record SimpleIndex(String name, String location) {}
