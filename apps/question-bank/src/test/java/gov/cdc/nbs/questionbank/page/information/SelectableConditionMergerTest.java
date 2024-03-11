package gov.cdc.nbs.questionbank.page.information;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.page.SelectableCondition;

class SelectableConditionMergerTest {

  private final SelectableConditionMerger merger = new SelectableConditionMerger();

  @Test
  void merging_true() {
    SelectableCondition a = new SelectableCondition("value", "name", false);
    SelectableCondition b = new SelectableCondition("other value", "other name", true);
    SelectableCondition merged = merger.merge(a, b);

    assertThat(merged.value()).isEqualTo("value");
    assertThat(merged.name()).isEqualTo("name");
    assertThat(merged.published()).isTrue();
  }

  @Test
  void merging_false() {
    SelectableCondition a = new SelectableCondition("value", "name", false);
    SelectableCondition b = new SelectableCondition("value", "name", false);
    SelectableCondition merged = merger.merge(a, b);

    assertThat(merged.value()).isEqualTo("value");
    assertThat(merged.name()).isEqualTo("name");
    assertThat(merged.published()).isFalse();
  }
}
