package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class WaMetaDataTest {

    @Test
    void should_initialize_correct_values(){
        RuleCommand.AddTextRule command=  createCommand();
        RuleCommand.RuleData ruleData=  command.ruleData();
        assertEquals(ruleData.ruleDescription(),"TestDescription");
        assertEquals(ruleData.sourceValue(), "testSourceValue");
        assertEquals(ruleData.targetType(),"testtargetValue" );
        assertEquals(ruleData.comparator(),">" );

    }


    private WaQuestion emptyQuestion() {
        return new WaQuestion() {
            @Override
            public String getDataType() {
                return "test";
            }
        };
    }

    private  RuleCommand.AddTextRule createCommand(){
        List<String> targetValues= new ArrayList<>();
        targetValues.add("INV123");
        targetValues.add("CBZ234");
        return new RuleCommand.AddTextRule(
                new RuleCommand.RuleData(
                        "TestRuleFunction",
                        "TestDescription",
                        "testSource INV123",
                        "INV123",
                        "testSourceValue",
                        true,
                        ">",
                        "testtargetValue",
                        targetValues

                )
        );

    }}
