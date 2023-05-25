package gov.cdc.nbs.questionbank.support;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entities.ValueEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.entities.ValueSet.ValueSetType;
import gov.cdc.nbs.questionbank.valueset.repository.ValueSetRepository;

@Component
@Transactional
public class ValueSetMother {
    private final static String YES_NO_UNKNOWN = "yesNoUnknown";

    @Autowired
    private ValueSetRepository repository;

    public ValueSet yesNoUnknown() {
        return repository.findFirstByName(YES_NO_UNKNOWN).orElseGet(() -> createYesNoUnknownValueSet());
    }

    private ValueSet createYesNoUnknownValueSet() {
        ValueSet valueSet = new ValueSet();
        List<ValueEntity> values = new ArrayList<>();
        values.add(yes(valueSet));
        values.add(no(valueSet));
        values.add(unknown(valueSet));

        valueSet.setCode("yesNoUnknownCode");
        valueSet.setDescription("yesNoUnknown value set used for testing");
        valueSet.setName(YES_NO_UNKNOWN);
        valueSet.setType(ValueSetType.LOCAL);
        valueSet.setValues(values);

        valueSet = repository.save(valueSet);
        repository.flush();
        return valueSet;
    }

    private ValueEntity yes(ValueSet valueSet) {
        var yes = new ValueEntity();
        yes.setCode("yes");
        yes.setComment("yes value set for testing");
        yes.setDisplay("yes");
        yes.setDisplayOrder(0);
        yes.setValue("Y");
        yes.setValueSet(valueSet);
        return yes;
    }

    private ValueEntity no(ValueSet valueSet) {
        var no = new ValueEntity();
        no.setCode("no");
        no.setComment("no value set for testing");
        no.setDisplay("no");
        no.setDisplayOrder(1);
        no.setValue("N");
        no.setValueSet(valueSet);
        return no;
    }

    private ValueEntity unknown(ValueSet valueSet) {
        var unknown = new ValueEntity();
        unknown.setCode("unknown");
        unknown.setComment("unknown value set for testing");
        unknown.setDisplay("unknown");
        unknown.setDisplayOrder(0);
        unknown.setValue("U");
        unknown.setValueSet(valueSet);
        return unknown;
    }

}
