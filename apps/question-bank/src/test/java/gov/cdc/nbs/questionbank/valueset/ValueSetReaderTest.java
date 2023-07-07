package gov.cdc.nbs.questionbank.valueset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;

class ValueSetReaderTest {

    @Mock
    ValueSetRepository valueSetRepository;

    @InjectMocks
    ValueSetReader valueSetReader;

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

    @Test
    void searchValueSetSearchTest() {
        ValueSetSearchRequest search = new ValueSetSearchRequest();
        search.setCodeSetName("setnm");
        search.setValueSetNm("setnm");
        search.setValueSetCode("setCode");
        int max = 5;
        Pageable pageable = PageRequest.of(0, max);
        Page<Codeset> codePage = getCodeSetPage(max, pageable);
        when(valueSetRepository.findByCodeSetNmOrValueSetNmorValueSetCode(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyString(), Mockito.any())).thenReturn(codePage);
        Page<ValueSet> result = valueSetReader.searchValueSearch(search, pageable);
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
        } ;
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
        code.setParentIsCd(Uuid.randomUuid().getLeastSignificantBits());
        code.setAddTime(Instant.now());
        code.setAddUserId(Uuid.randomUuid().getLeastSignificantBits());

        return code;
    }

    @Test
    void should_map_codeValueGeneral_to_concept() {
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

        Concept concept = valueSetReader.toConcept(cvg);

        assertEquals(cvg.getId().getCode(), concept.localCode());
        assertEquals(cvg.getId().getCodeSetNm(), concept.codesetName());
        assertEquals(cvg.getCodeShortDescTxt(), concept.display());
        assertEquals(cvg.getCodeDescTxt(), concept.value());
        assertEquals(cvg.getConceptCode(), concept.conceptCode());
        assertEquals(cvg.getConceptPreferredNm(), concept.messagingConceptName());
        assertEquals(cvg.getCodeSystemDescTxt(), concept.codeSystem());
        assertEquals(cvg.getConceptStatusCd(), concept.status());
        assertEquals(cvg.getEffectiveFromTime(), concept.effectiveFromTime());
        assertEquals(cvg.getEffectiveToTime(), concept.effectiveToTime());
    }

    @Test
    void should_return_empty_list_for_null_concept() {
        List<Concept> results = valueSetReader.findConceptCodes(null);
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
