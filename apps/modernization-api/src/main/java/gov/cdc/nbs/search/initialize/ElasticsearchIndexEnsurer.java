package gov.cdc.nbs.search.initialize;

import gov.cdc.nbs.search.SimpleIndex;
import java.io.IOException;
import java.util.Collection;

class ElasticsearchIndexEnsurer {

  private static final System.Logger LOGGER =
      System.getLogger(ElasticsearchIndexEnsurer.class.getName());

  private final ElasticsearchIndexExistenceVerifier verifier;
  private final ElasticsearchIndexCreator creator;

  ElasticsearchIndexEnsurer(
      final ElasticsearchIndexExistenceVerifier verifier, final ElasticsearchIndexCreator creator) {
    this.verifier = verifier;
    this.creator = creator;
  }

  /**
   * Ensures that an Elasticsearch index exists for each entry, creating the index if it does not
   * exist.
   *
   * @param indices The {@link SimpleIndex} instances to ensure.
   */
  void ensure(final Collection<SimpleIndex> indices) {
    this.verifier.notExists(indices).forEach(this::safelyCreate);
  }

  private void safelyCreate(final SimpleIndex index) {
    try {
      this.creator.create(index);
    } catch (IOException exception) {
      LOGGER.log(
          System.Logger.Level.ERROR,
          () ->
              "An unexpected error occurred when ensuring the existence of the %s index"
                  .formatted(index),
          exception);
    }
  }
}
