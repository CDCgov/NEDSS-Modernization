package gov.cdc.nbs.accumulation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.Test;

class CollectionMergeTest {

  @Test
  void should_merge_elements_from_each_collection() {

    Collection<String> actual =
        CollectionMerge.merged(List.of("Q", "W", "E", "R"), List.of("T", "Y"));

    assertThat(actual).contains("Q", "W", "E", "R", "T", "Y");
  }

  @Test
  void should_merge_elements_from_each_collection_without_duplicates() {

    Collection<String> actual =
        CollectionMerge.merged(List.of("Q", "W", "E", "R"), List.of("T", "Y", "Q"));

    assertThat(actual).contains("Q", "W", "E", "R", "T", "Y");
  }
}
