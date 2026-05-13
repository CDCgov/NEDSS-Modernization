package gov.cdc.nbs.search;

/**
 * Describes an Elasticsearch index managed by the indexing library.
 *
 * <p>The {@code location} points to the JSON descriptor used to create the index when it does not
 * already exist. The descriptor is expected to define the index settings and mappings.
 *
 * @param name the Elasticsearch index name
 * @param location the classpath or resource location of the index descriptor JSON
 */
public record SimpleIndex(String name, String location) {}
