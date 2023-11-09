package gov.cdc.nbs.questionbank.page;

import com.querydsl.core.Tuple;
import gov.cdc.nbs.questionbank.entity.QCodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.QWaTemplate;
import gov.cdc.nbs.questionbank.entity.condition.QConditionCode;
import gov.cdc.nbs.questionbank.exception.QueryException;
import gov.cdc.nbs.questionbank.page.model.PageInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PageInfoMapperTest {

  private static final QWaTemplate waTemplate = QWaTemplate.waTemplate;
  private static final QConditionCode conditionCode = QConditionCode.conditionCode;
  private static final QCodeValueGeneral codeValueGeneral = QCodeValueGeneral.codeValueGeneral;
  private final PageInfoMapper mapper = new PageInfoMapper();

  @Test
  void mapPageInfo() {
    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(this.waTemplate.templateNm)).thenReturn("template name");
    when(tuple.get(this.waTemplate.descTxt)).thenReturn("template description");
    when(tuple.get(this.waTemplate.waTemplate.busObjType)).thenReturn("INV");
    when(tuple.get(this.waTemplate.datamartNm)).thenReturn("data mart");
    when(tuple.get(this.conditionCode.id)).thenReturn("1000");
    when(tuple.get(this.conditionCode.conditionShortNm)).thenReturn("condition");
    when(tuple.get(this.waTemplate.nndEntityIdentifier)).thenReturn("message mapping name");
    when(tuple.get(this.codeValueGeneral.codeShortDescTxt)).thenReturn("message mapping value");

    PageInfo actual = mapper.map(tuple);

    assertThat(actual.name()).isEqualTo("template name");
    assertThat(actual.description()).isEqualTo("template description");
    assertThat(actual.dataMartName()).isEqualTo("data mart");
    assertThat(actual.eventType().name()).isEqualTo("Investigation");
    assertThat(actual.conditions()).as("Check conditions size").hasSize(1);
    assertThat(actual.conditions().get(0).id()).isEqualTo("1000");
    assertThat(actual.conditions().get(0).name()).isEqualTo("condition");
    assertThat(actual.messageMappingGuide().name()).isEqualTo("message mapping value");
    assertThat(actual.messageMappingGuide().value()).isEqualTo("message mapping name");
  }

  @Test
  void mapPageInfoException() {
    Tuple tuple = Mockito.mock(Tuple.class);
    when(tuple.get(this.waTemplate.templateNm)).thenReturn("template name");
    when(tuple.get(this.waTemplate.descTxt)).thenReturn("template description");
    when(tuple.get(this.waTemplate.waTemplate.busObjType)).thenReturn("INV");
    when(tuple.get(this.waTemplate.datamartNm)).thenReturn("data mart");
    when(tuple.get(this.conditionCode.id)).thenReturn("1000");
    when(tuple.get(this.conditionCode.conditionShortNm)).thenReturn("condition");
    when(tuple.get(this.waTemplate.nndEntityIdentifier)).thenReturn("message mapping name");
    when(tuple.get(this.codeValueGeneral.codeShortDescTxt)).thenReturn("message mapping value");
    when(mapper.toPageInfo(tuple)).thenThrow(new ClassCastException("Error Converting Tuple to PageInfo"));
    var exception = assertThrows(ClassCastException.class, () -> mapper.map(tuple));
    assertTrue(exception.getMessage().contains("Error Converting Tuple to PageInfo"));

  }

  @Test
  void getEventType() {
    Map<String, String> eventTypeMap = Map.ofEntries(
            Map.entry("INV", "Investigation"),
            Map.entry("CON", "Contact"),
            Map.entry("VAC", "Vaccination"),
            Map.entry("IXS", "Interview"),
            Map.entry("SUS", "Lab Susceptibility"),
            Map.entry("LAB", "Lab Report"),
            Map.entry("ISO", "Lab Isolate Tracking")
    );
    for(String value :  eventTypeMap.keySet()){
      assertThat(mapper.getEventType(value).name()).isEqualTo(eventTypeMap.get(value));
    }
  }


}
