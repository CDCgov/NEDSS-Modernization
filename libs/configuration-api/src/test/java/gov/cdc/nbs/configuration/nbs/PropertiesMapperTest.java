package gov.cdc.nbs.configuration.nbs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PropertiesMapperTest {

  @Test
  void shouldMapValues() {
    Map<String, String> map = new HashMap<>();
    map.put(NbsPropertiesFinder.STD_PROGRAM_AREAS, "STD,   SomethingElse ");
    map.put(NbsPropertiesFinder.HIV_PROGRAM_AREAS, "HIV");
    map.put(NbsPropertiesFinder.CODE_BASE, "nbs 6");
    map.put("dontInclude", "AnyValue");

    Properties props = PropertiesMapper.toProperties(map);

    assertThat(props.stdProgramAreas()).containsExactly("STD", "SOMETHINGELSE");
    assertThat(props.hivProgramAreas()).containsExactly("HIV");
    assertThat(props.entries()).containsEntry(NbsPropertiesFinder.CODE_BASE, "nbs 6");
    assertThat(props.entries()).doesNotContainKey("dontInclude");
  }

  @Test
  void shouldNotFailForNullTokenizeCall() {
    Map<String, String> map = new HashMap<>();
    Properties props = PropertiesMapper.toProperties(map);
    assertThat(props).isNotNull();
    assertThat(props.stdProgramAreas()).isEmpty();
    assertThat(props.hivProgramAreas()).isEmpty();
    assertThat(props.entries().entrySet()).isEmpty();
  }
}
