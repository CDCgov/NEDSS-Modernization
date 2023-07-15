package gov.cdc.nbs.questionbank.support.valueset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.entity.CodeSetGroupMetadatum;
import gov.cdc.nbs.questionbank.entity.Codeset;
import gov.cdc.nbs.questionbank.valueset.repository.CodesetGroupMetadatumRepository;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;

@Component
public class ValueSetMother {
	
	@Autowired
	private ValueSetRepository valueSetRepository;
	
	@Autowired
	private CodesetGroupMetadatumRepository codeSetGrpMetaRepository;
	
	private List<Codeset> allValueSets = new ArrayList<>();
	
	
	public void clean() {
		valueSetRepository.deleteAll();
		codeSetGrpMetaRepository.deleteAll();
		allValueSets.clear();
	}
	
	public Codeset  valueSet() {
		return allValueSets.stream()
				.filter(v-> v instanceof Codeset).findFirst()
				.orElseGet(this::createValueSet);
	}
	
	public Codeset one() {
		return allValueSets.stream().findFirst()
				.orElseThrow(() -> new IllegalStateException("No value sets are available"));
	}
	
	public Codeset createValueSet () {
		Codeset v = ValueSetEntityMother.valueSet();
		v = valueSetRepository.save(v);
		allValueSets.add(v);
		return v;
	}
	
	public Codeset createCodeSetGroupForValueSet() {
		Codeset v = createValueSet ();
		CodeSetGroupMetadatum codeGrp = new  CodeSetGroupMetadatum();
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
