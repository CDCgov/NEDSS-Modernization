package gov.cdc.nbs.questionbank.template;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.template.response.Template;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class TemplateReaderTest {

  @Mock WaTemplateRepository templateRepository;

  @InjectMocks TemplateFinder templateReader;

  public TemplateReaderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllTemplates() {
    when(templateRepository.findAllByTemplateType(eq("TEMPLATE"), Mockito.any()))
        .thenReturn(List.of(getWaTemplate(1)));
    List<Template> result = templateReader.findAllTemplates("");
    assertNotNull(result);
  }

  @Test
  void findAllTemplatesInv() {
    when(templateRepository.findAllByTemplateTypeAndBusObjType(
            eq("TEMPLATE"), eq("INV"), Mockito.any()))
        .thenReturn(List.of(getWaTemplate(1)));
    List<Template> result = templateReader.findAllTemplates("INV");
    assertNotNull(result);
  }

  private WaTemplate getWaTemplate(int i) {
    WaTemplate aWaTemplate = new WaTemplate();

    aWaTemplate.setId(1l);
    aWaTemplate.setTemplateNm("TemplateNm" + i);
    aWaTemplate.setTemplateType("Draft");
    aWaTemplate.setXmlPayload("Payload");
    aWaTemplate.setPublishVersionNbr(1);
    aWaTemplate.setFormCd("formCd");
    aWaTemplate.setConditionCd("conditionCd" + i);
    aWaTemplate.setBusObjType("BusObjType");
    aWaTemplate.setDatamartNm("DatamartNm");
    aWaTemplate.setRecordStatusCd("RecordStatusCd" + i);
    aWaTemplate.setRecordStatusTime(Instant.now());
    aWaTemplate.setLastChgTime(Instant.now());
    aWaTemplate.setLastChgUserId(1l);
    aWaTemplate.setLocalId("LocalId");
    aWaTemplate.setDescTxt("DescTxt");
    aWaTemplate.setPublishIndCd('T');
    aWaTemplate.setAddTime(Instant.now());
    aWaTemplate.setAddUserId(1l);
    aWaTemplate.setNndEntityIdentifier("NndEntityIdentifier");
    aWaTemplate.setParentTemplateUid(1l);
    aWaTemplate.setSourceNm("SourceNm");
    aWaTemplate.setTemplateVersionNbr(1);
    aWaTemplate.setVersionNote("VersionNote");

    return aWaTemplate;
  }
}
