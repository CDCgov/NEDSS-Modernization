package gov.cdc.nbs.questionbank.page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.entity.repository.PageCondMappingRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.exception.PageUpdateException;
import gov.cdc.nbs.questionbank.pagerules.repository.WaRuleMetaDataRepository;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PageStateChangerTest {

  @Mock private WaTemplateRepository templateRepository;
  @Mock private WaUiMetadataRepository waUiMetadataRepository;
  @Mock private PageCondMappingRepository pageConMappingRepository;
  @Mock private WaRuleMetaDataRepository waRuleMetaDataRepository;
  @InjectMocks private PageStateChanger changer;

  @Test
  void should_copy_rules() {
    when(waRuleMetaDataRepository.findByWaTemplateUid(1l)).thenReturn(rules());
    List<WaRuleMetadata> newRules = changer.copyRules(1l, 2l);
    assertThat(newRules).hasSize(2).allSatisfy(c -> assertThat(c.getWaTemplateUid()).isEqualTo(2));
  }

  @Test
  void should_throw_exception() {
    WaTemplate page = new WaTemplate();
    page.setTemplateType("Published With Draft");
    when(templateRepository.findById(1l)).thenReturn(Optional.of(page));
    assertThrows(PageUpdateException.class, () -> changer.savePageAsDraft(1l));
  }

  List<WaRuleMetadata> rules() {
    WaRuleMetadata rule1 = new WaRuleMetadata();
    rule1.setId(1l);
    WaRuleMetadata rule2 = new WaRuleMetadata();
    rule2.setId(2l);
    return Arrays.asList(rule1, rule2);
  }
}
