package gov.cdc.nbs.questionbank.valueset.repository;

import java.util.Optional;

import org.springframework.stereotype.Service;

import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.valueset.ValueSetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValueSetService {
	
	private ValueSetRepository valueSetRepository;
	
	public void createValueSet(long userId,ValueSetRequest.createValueSet request) {
		
	}
	
	public Optional<ValueSet> processValueSet(long userId,ValueSetRequest.createValueSet request) {
		Optional<ValueSet> result = Optional.empty();
		try {
			
		}catch(Exception e) {
			
		}
		return result;
	}

}
