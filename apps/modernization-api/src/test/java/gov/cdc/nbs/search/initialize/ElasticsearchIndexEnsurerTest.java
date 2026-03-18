package gov.cdc.nbs.search.initialize;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import gov.cdc.nbs.search.SimpleIndex;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

class ElasticsearchIndexEnsurerTest {

  @Test
  void should_create_index_for_any_index_that_does_not_exist() throws IOException {

    ElasticsearchIndexExistenceVerifier verifier = mock(ElasticsearchIndexExistenceVerifier.class);

    SimpleIndex existing = new SimpleIndex("existing", "location");

    List<SimpleIndex> indices = List.of(existing, new SimpleIndex("other", "location"));

    when(verifier.notExists(any())).thenReturn(List.of(existing));

    ElasticsearchIndexCreator creator = mock(ElasticsearchIndexCreator.class);

    new ElasticsearchIndexEnsurer(verifier, creator).ensure(indices);

    verify(verifier).notExists(indices);
    verify(creator).create(existing);
  }

  @Test
  void should_not_create_indexes_when_they_all_exist() {

    ElasticsearchIndexExistenceVerifier verifier = mock(ElasticsearchIndexExistenceVerifier.class);

    SimpleIndex existing = new SimpleIndex("existing", "location");

    List<SimpleIndex> indices = List.of(existing, new SimpleIndex("other", "location"));

    when(verifier.notExists(any())).thenReturn(Collections.emptyList());

    ElasticsearchIndexCreator creator = mock(ElasticsearchIndexCreator.class);

    new ElasticsearchIndexEnsurer(verifier, creator).ensure(indices);

    verify(verifier).notExists(indices);
    verifyNoInteractions(creator);
  }

  @Test
  void should_capture_any_error_from_the_creator() throws IOException {

    ElasticsearchIndexExistenceVerifier verifier = mock(ElasticsearchIndexExistenceVerifier.class);

    SimpleIndex existing = new SimpleIndex("existing", "location");

    List<SimpleIndex> indices = List.of(existing, new SimpleIndex("other", "location"));

    when(verifier.notExists(any())).thenReturn(List.of(existing));

    ElasticsearchIndexCreator creator = mock(ElasticsearchIndexCreator.class);

    doThrow(new IOException()).when(creator).create(any());

    ElasticsearchIndexEnsurer ensurer = new ElasticsearchIndexEnsurer(verifier, creator);

    assertDoesNotThrow(() -> ensurer.ensure(indices));

    verify(verifier).notExists(indices);
    verify(creator).create(existing);
  }
}
