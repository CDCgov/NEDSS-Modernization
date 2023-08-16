package gov.cdc.nbs.questionbank.support.valueset;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralId;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.entity.CodesetId;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;

@Component
@Transactional
public class ValueSetMother {
    private final String CODESET_NAME = "TestCodeset";

    @Autowired
    private ValueSetRepository valueSetRepository;

    @Autowired
    private CodeValueGeneralRepository conceptRepository;

    @Autowired
    private CodesetGroupMetadatumRepository codeSetGrpMetaRepository;

    private List<Codeset> allValueSets = new ArrayList<>();
    private HashMap<String, List<CodeValueGeneral>> concepts = new HashMap<>();


    public void clean() {
        valueSetRepository.deleteAll();
        codeSetGrpMetaRepository.deleteAll();
        conceptRepository.deleteAllByCodesetName(CODESET_NAME);
        concepts.clear();
        allValueSets.clear();
    }

    public Codeset valueSet() {
        return allValueSets.stream()
                .filter(v -> v instanceof Codeset)
                .findFirst()
                .orElseGet(this::valueSetWithConcepts);
    }

    public Codeset one() {
        return allValueSets.stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No value sets are available"));
    }

    public List<CodeValueGeneral> concept(String codeSetName) {
        return concepts.get(codeSetName);
    }

    public Codeset createValueSet() {
        Codeset v = ValueSetEntityMother.valueSet();
        v = valueSetRepository.save(v);
        allValueSets.add(v);
        return v;
    }

    private Codeset valueSetWithConcepts() {
        Instant now = Instant.now();
        Codeset codeset = new Codeset();
        codeset.setId(new CodesetId("code_value_general", CODESET_NAME));
        codeset.setStatusCd("A");
        codeset.setStatusToTime(now);
        codeset.setCodeSetDescTxt(CODESET_NAME);
        codeset.setAssigningAuthorityCd("L");
        codeset.setValueSetTypeCd("LOCAL");
        codeset.setAddTime(now);
        codeset.setAddUserId(99999999L);
        codeset.setEffectiveFromTime(now);
        codeset = valueSetRepository.save(codeset);

        CodeValueGeneral yes = new CodeValueGeneral();
        yes.setId(new CodeValueGeneralId(CODESET_NAME, "Yes"));
        yes.setCodeDescTxt("Yes");
        yes.setCodeShortDescTxt("Yes");
        yes.setCodeSystemCd("2.16.840.1.113883.12.136");
        yes.setCodeSystemDescTxt("Yes/No Indicator (HL7)");
        yes.setEffectiveFromTime(now);
        yes.setIsModifiableInd('Y');
        yes.setStatusCd('A');
        yes.setStatusTime(now);
        yes.setConceptTypeCd("PHIN");
        yes.setConceptPreferredNm("Yes");
        yes.setConceptStatusCd("Active");
        yes.setConceptStatusTime(now);
        yes.setAddTime(now);
        yes.setAddUserId(99999999L);
        yes = conceptRepository.save(yes);

        CodeValueGeneral no = new CodeValueGeneral();
        no.setId(new CodeValueGeneralId(CODESET_NAME, "No"));
        no.setCodeDescTxt("No");
        no.setCodeShortDescTxt("No");
        no.setCodeSystemCd("2.16.840.1.113883.12.136");
        no.setCodeSystemDescTxt("Yes/No Indicator (HL7)");
        no.setEffectiveFromTime(now);
        no.setIsModifiableInd('Y');
        no.setStatusCd('A');
        no.setStatusTime(now);
        no.setConceptTypeCd("PHIN");
        no.setConceptPreferredNm("No");
        no.setConceptStatusCd("Active");
        no.setConceptStatusTime(now);
        no.setAddTime(now);
        no.setAddUserId(99999999L);
        no = conceptRepository.save(no);

        List<CodeValueGeneral> conceptList = new ArrayList<>();
        conceptList.add(yes);
        conceptList.add(no);

        concepts.put(CODESET_NAME, conceptList);
        allValueSets.add(codeset);
        return codeset;
    }

    public Codeset createCodeSetGroupForValueSet() {
        Codeset v = createValueSet();
        CodeSetGroupMetadatum codeGrp = new CodeSetGroupMetadatum();
        codeGrp.setId(getCodeSetGroupID());
        codeGrp.setCodeSetDescTxt(v.getCodeSetDescTxt());
        codeGrp.setCodeSetNm(v.getValueSetNm());
        codeGrp.setLdfPicklistIndCd(v.getLdfPicklistIndCd());
        CodeSetGroupMetadatum result = codeSetGrpMetaRepository.save(codeGrp);
        v.setCodeSetGroup(result);
        Codeset codeSetResult = valueSetRepository.save(v);
        return codeSetResult;
    }

    private long getCodeSetGroupID() {
        long maxGroupID = valueSetRepository.getCodeSetGroupCeilID();
        if (maxGroupID > 0) {
            maxGroupID = codeSetGrpMetaRepository.getCodeSetGroupMaxID() + 10;
        } else {
            maxGroupID = 9910;
        }
        return maxGroupID;
    }

}
