package gov.cdc.nbs.search;

import java.nio.file.Path;

/**
 * A simple representation of an Elasticsearch index that contains a name and the {@code Path} to the descriptor that
 * defines it.
 *
 * @param name       The name of the index
 * @param descriptor The {@link Path} to the JSON file containing the {@code settings} and {@code mappings} of the
 *                   index.
 */
public record SimpleIndex(String name, Path descriptor) {
}
