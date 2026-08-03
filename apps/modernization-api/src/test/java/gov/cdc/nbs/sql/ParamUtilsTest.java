package gov.cdc.nbs.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ParamUtilsTest {

  @Test
  void replaceListParam_replaces_handles_null_collection() {
    String result = ParamUtils.replaceListParam("blah in (:param)", "param", null);

    assertThat(result).isEqualTo("blah in ()");
  }

  @Test
  void replaceListParam_replaces_handles_empty_collection() {
    String result = ParamUtils.replaceListParam("blah in (:param)", "param", List.of());

    assertThat(result).isEqualTo("blah in ()");
  }

  @Test
  void replaceListParam_replaces_handles_one_len_collection() {
    String result = ParamUtils.replaceListParam("blah in (:param)", "param", List.of(1L));

    assertThat(result).isEqualTo("blah in (1)");
  }

  @Test
  void replaceListParam_replaces_handles_multi_len_collection() {
    String result =
        ParamUtils.replaceListParam("blah in (:param)", "param", List.of(1L, 100000L, 999999999L));

    assertThat(result).isEqualTo("blah in (1, 100000, 999999999)");
  }
}
