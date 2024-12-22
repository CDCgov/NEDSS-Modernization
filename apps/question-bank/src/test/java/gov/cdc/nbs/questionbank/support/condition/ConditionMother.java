package gov.cdc.nbs.questionbank.support.condition;

import java.util.ArrayList;
import java.util.List;

import gov.cdc.nbs.questionbank.condition.repository.LdfPageSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;


@Component
public class ConditionMother {

    @Autowired
    private ConditionCodeRepository conditionCodeRepository;

    @Autowired
    private LdfPageSetRepository ldfPageSetRepository;

    private final List<ConditionCode> allConditions = new ArrayList<>();

    public void clean() {
        conditionCodeRepository.deleteAll();
        ldfPageSetRepository.deleteAll();
        allConditions.clear();
    }

    public ConditionCode one() {
        return allConditions.stream().findFirst()
                .orElseThrow(() -> new IllegalStateException("No conditions are available"));
    }

    public ConditionCode createCondition() {
        ConditionCode c = ConditionEntityMother.conditionCode();
        c = conditionCodeRepository.save(c);
        allConditions.add(c);
        return c;
    }
}
