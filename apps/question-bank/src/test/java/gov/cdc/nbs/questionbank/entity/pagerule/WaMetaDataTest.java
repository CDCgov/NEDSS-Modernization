package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import gov.cdc.nbs.questionbank.pagerules.command.RuleCommand;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class WaMetaDataTest {

    @Test
    public void should_initialize_correct_values(){
        RuleCommand.AddTextRule command=  createCommand();
        WaRuleMetadata ruleMetadata= new RuleDetails(command);
        RuleCommand.RuleData ruleData=  command.ruleData();
        assertEquals(ruleData.ruleDescription(), ruleMetadata.getRuleDescText());
        assertEquals(ruleData.comparator(), ruleMetadata.getLogic());
        assertEquals(ruleData.sourceValue(), ruleMetadata.getSourceValues());
        assertEquals(ruleData.targetType(), ruleMetadata.getTargetType());
        assertEquals(ruleData.targetValue(), ruleMetadata.getTargetQuestionIdentifier());

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
        return new RuleCommand.AddTextRule(
                "Mask",
                "25",
                "default value",
                new RuleCommand.RuleData(
                        "TestRuleFunction",
                        "TestDescription",
                        "testSource",
                        null,
                        "=>",
                        "testSourceValue",
                        "testTargetType",
                        "testtargetValue"
                )
        );

    }}
