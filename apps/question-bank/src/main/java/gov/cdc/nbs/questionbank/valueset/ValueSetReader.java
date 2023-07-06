package gov.cdc.nbs.questionbank.valueset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneral;
import gov.cdc.nbs.questionbank.entity.CodeValueGeneralRepository;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.response.Concept;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;

@Service
public class ValueSetReader {

    @Autowired
    private ValueSetRepository valueSetRepository;

    @Autowired
    private CodeValueGeneralRepository codeValueGeneralRepository;

    public Page<ValueSet> findAllValueSets(Pageable pageable) {
        Page<Codeset> rawResults = valueSetRepository.findAll(pageable);
        List<ValueSet> formatResults = toReadValueSet(rawResults);
        return new PageImpl<>(formatResults, pageable, rawResults.getTotalElements());

    }

    public Page<ValueSet> searchValueSearch(ValueSetSearchRequest search, Pageable pageable) {
        Page<Codeset> rawResults = valueSetRepository.findByCodeSetNmOrValueSetNmorValueSetCode(search.getCodeSetName(),
                search.getValueSetNm(), search.getValueSetCode(), pageable);
        List<ValueSet> formatResults = toReadValueSet(rawResults);
        return new PageImpl<>(formatResults, pageable, rawResults.getTotalElements());

    }

    public List<ValueSet> toReadValueSet(Page<Codeset> rawResults) {
        List<ValueSet> results = new ArrayList<>();

        for (Codeset codeSet : rawResults.getContent()) {

            results.add(new ValueSet(
                    codeSet.getId().getClassCd(),
                    codeSet.getId().getCodeSetNm(),
                    codeSet.getAssigningAuthorityCd(),
                    codeSet.getAssigningAuthorityDescTxt(),
                    codeSet.getCodeSetDescTxt(),
                    codeSet.getEffectiveFromTime(),
                    codeSet.getEffectiveToTime(),
                    codeSet.getIsModifiableInd(),
                    codeSet.getNbsUid(),
                    codeSet.getSourceVersionTxt(),
                    codeSet.getSourceDomainNm(),
                    codeSet.getStatusCd(),
                    codeSet.getStatusToTime(),
                    codeSet.getCodeSetGroup().getId(),
                    codeSet.getAdminComments(),
                    codeSet.getValueSetNm(),
                    codeSet.getLdfPicklistIndCd(),
                    codeSet.getValueSetCode(),
                    codeSet.getValueSetTypeCd(),
                    codeSet.getValueSetOid(),
                    codeSet.getValueSetStatusCd(),
                    codeSet.getValueSetStatusTime(),
                    codeSet.getParentIsCd(),
                    codeSet.getAddTime(),
                    codeSet.getAddUserId()));

        }
        return results;
    }

    public List<Concept> findConceptCodes(String codeSetNm) {
        if (codeSetNm == null) {
            return Collections.emptyList();
        }

        return codeValueGeneralRepository
                .findByIdCodeSetNm(codeSetNm, Sort.by(Sort.Direction.ASC, "codeShortDescTxt"))
                .stream()
                .map(this::toConcept)
                .toList();
    }

    Concept toConcept(CodeValueGeneral codeValueGeneral) {
        return new Concept(
                codeValueGeneral.getId().getCode(),
                codeValueGeneral.getId().getCodeSetNm(),
                codeValueGeneral.getCodeShortDescTxt(),
                codeValueGeneral.getCodeDescTxt(),
                codeValueGeneral.getConceptCode(),
                codeValueGeneral.getConceptPreferredNm(),
                codeValueGeneral.getCodeSystemDescTxt(),
                codeValueGeneral.getConceptStatusCd(),
                codeValueGeneral.getEffectiveFromTime(),
                codeValueGeneral.getEffectiveToTime());
    }

}
