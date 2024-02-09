package gov.cdc.nbs.questionbank.valueset.read;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


import gov.cdc.nbs.questionbank.entity.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import gov.cdc.nbs.questionbank.valueset.ValueSetReader;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import org.springframework.data.domain.Sort;

class ValueSetReaderTest {

  @Mock
  ValueSetRepository valueSetRepository;

  @InjectMocks
  ValueSetReader valueSetReader;


  @Mock
  CodeValueGeneralRepository codeValueGeneralRepository;

  ValueSetReaderTest() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void findAllValueSetsTest() {
    int max = 5;
    Pageable pageable = PageRequest.of(0, max);
    Page<Codeset> codePage = getCodeSetPage(max, pageable);
    when(valueSetRepository.findAll(pageable)).thenReturn(codePage);
    Page<ValueSet> result = valueSetReader.findAllValueSets(pageable);
    assertNotNull(result);
    assertEquals(max, result.getTotalElements());
    ValueSet one = result.get().toList().get(0);
    assertNotNull(one.valueSetNm());
    assertNotNull(one.valueSetCode());
  }

  private Page<Codeset> getCodeSetPage(int max, Pageable pageable) {
    List<Codeset> set = new ArrayList<Codeset>();
    for (int i = 0; i < max; i++) {
      set.add(getCodeSetRequest(i));
    }
    return new PageImpl<>(set, pageable, set.size());

  }

  private Codeset getCodeSetRequest(int i) {
    Codeset code = new Codeset();
    CodesetId id = new CodesetId();
    CodeSetGroupMetadatum grp = new CodeSetGroupMetadatum();
    grp.setId(1l);
    id.setClassCd("C");
    id.setCodeSetNm("setnm" + i);
    code.setId(id);
    code.setValueSetNm("codeSetNm" + i);
    code.setCodeSetDescTxt("codeDescTxt" + i);
    code.setAssigningAuthorityCd("cssigningAuthorityCd");
    code.setAssigningAuthorityDescTxt("assigningDescTx");
    code.setCodeSetDescTxt("codeSetDescTxt");
    code.setEffectiveFromTime(Instant.now());
    code.setEffectiveToTime(Instant.now().plusSeconds(500));
    code.setIsModifiableInd(Character.valueOf('Y'));
    code.setNbsUid(10);
    code.setSourceVersionTxt("sourceVersionTxt");
    code.setSourceDomainNm("SourceDomainNm");
    code.setStatusCd("statusCd");
    code.setStatusToTime(Instant.now());
    code.setCodeSetGroup(grp);
    code.setAdminComments("adminComments");
    code.setValueSetNm("valueSetNm");
    code.setLdfPicklistIndCd(Character.valueOf('L'));
    code.setValueSetCode("valueSetCode");
    code.setValueSetTypeCd("valueSetTypeCd");
    code.setValueSetOid("valueSetOid");
    code.setValueSetStatusCd("valueSetStatusCd");
    code.setValueSetStatusTime(Instant.now());
    code.setParentIsCd(UUID.randomUUID().getLeastSignificantBits());
    code.setAddTime(Instant.now());
    code.setAddUserId(UUID.randomUUID().getLeastSignificantBits());

    return code;
  }

  @Test
  void should_map_codeValueGeneral_to_concept() {
    CodeValueGeneral cvg = getCodeValueGeneral();
    Concept concept = valueSetReader.toConcept(cvg);
    assertEquals(cvg.getId().getCode(), concept.localCode());
    assertEquals(cvg.getId().getCodeSetNm(), concept.codeSetName());
    assertEquals(cvg.getCodeShortDescTxt(), concept.display());
    assertEquals(cvg.getCodeDescTxt(), concept.longName());
    assertEquals(cvg.getConceptCode(), concept.conceptCode());
    assertEquals(cvg.getConceptPreferredNm(), concept.preferredConceptName());
    assertEquals(cvg.getCodeSystemDescTxt(), concept.codeSystem());
    assertEquals(cvg.getConceptStatusCd(), concept.status());
    assertEquals(cvg.getEffectiveFromTime(), concept.effectiveFromTime());
    assertEquals(cvg.getEffectiveToTime(), concept.effectiveToTime());
  }


  @Test
  void should_return_empty_list_for_null_concept() {
    List<Concept> conceptResults = valueSetReader.findConceptCodes(null);
    assertNotNull(conceptResults);
    assertTrue(conceptResults.isEmpty());
  }


  @Test
  void should_return_concept_list_for_valid_concept() {
    String codeSetName = "codeSetName";
    when(codeValueGeneralRepository.findByIdCodeSetNm(any(), any(Sort.class)))
        .thenReturn(getListOfCodeValueGeneral());
    List<Concept> ConceptResults = valueSetReader.findConceptCodes(codeSetName);
    assertNotNull(ConceptResults);
    assertFalse(ConceptResults.isEmpty());
  }


  private List<CodeValueGeneral> getListOfCodeValueGeneral() {
    return Arrays.asList(getCodeValueGeneral());
  }

  private CodeValueGeneral getCodeValueGeneral() {
    Instant now = Instant.now();
    Instant future = now.plusSeconds(500);
    CodeValueGeneral cvg = new CodeValueGeneral();
    cvg.setId(new CodeValueGeneralId("codeSetNm", "code"));
    cvg.setCodeShortDescTxt("codeShortDescTxt");
    cvg.setConceptCode("conceptCode");
    cvg.setConceptPreferredNm("conceptPreferredName");
    cvg.setCodeSystemDescTxt("codeSystemDescTxt");
    cvg.setConceptStatusCd("Active");
    cvg.setEffectiveFromTime(now);
    cvg.setEffectiveToTime(future);
    return cvg;
  }
}
