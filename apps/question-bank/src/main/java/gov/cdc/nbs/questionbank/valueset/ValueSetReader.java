package gov.cdc.nbs.questionbank.valueset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;
import gov.cdc.nbs.questionbank.valueset.request.ValueSetSearchRequest;
import gov.cdc.nbs.questionbank.valueset.response.ValueSet;

@Service
public class ValueSetReader {

	@Autowired
	private ValueSetRepository valueSetRepository;

	public Page<ValueSet.GetValueSet> findAllValueSets(Pageable pageable) {
		Page<Codeset> rawResults = valueSetRepository.findAll(pageable);
		List<ValueSet.GetValueSet> formatResults = toReadValueSet(rawResults);
		return new PageImpl<>(formatResults, pageable, rawResults.getTotalElements());

	}

	public Page<ValueSet.GetValueSet> searchValueSearch(ValueSetSearchRequest search, Pageable pageable) {
		Page<Codeset> rawResults = valueSetRepository.findByCodeSetNmOrValueSetNmorValueSetCode(search.getCodeSetName(),
				search.getValueSetNm(), search.getValueSetCode(),pageable);
		List<ValueSet.GetValueSet> formatResults = toReadValueSet(rawResults);
		return new PageImpl<>(formatResults, pageable, rawResults.getTotalElements());

	}

	public List<ValueSet.GetValueSet> toReadValueSet(Page<Codeset> rawResults) {
		List<ValueSet.GetValueSet> results = new ArrayList<>();

		for (Codeset codeSet : rawResults.getContent()) {

			results.add(new ValueSet.GetValueSet(codeSet.getId().getClassCd(), codeSet.getId().getCodeSetNm(),
					codeSet.getAssigningAuthorityCd(), codeSet.getAssigningAuthorityDescTxt(),
					codeSet.getCodeSetDescTxt(), codeSet.getEffectiveFromTime(), codeSet.getEffectiveToTime(),
					codeSet.getIsModifiableInd(), codeSet.getNbsUid(), codeSet.getSourceVersionTxt(),
					codeSet.getSourceDomainNm(), codeSet.getStatusCd(), codeSet.getStatusToTime(),
					codeSet.getCodeSetGroup().getId(), codeSet.getAdminComments(), codeSet.getValueSetNm(),
					codeSet.getLdfPicklistIndCd(), codeSet.getValueSetCode(), codeSet.getValueSetTypeCd(),
					codeSet.getValueSetOid(), codeSet.getValueSetStatusCd(), codeSet.getValueSetStatusTime(),
					codeSet.getParentIsCd(), codeSet.getAddTime(), codeSet.getAddUserId()

			));

		}
		return results;

	}

}
