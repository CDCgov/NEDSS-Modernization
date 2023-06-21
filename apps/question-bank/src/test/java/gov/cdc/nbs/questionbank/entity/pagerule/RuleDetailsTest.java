package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.model.RuleDetails;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


public class RuleDetailsTest {

    @Test
    void should_have_text_type() {
        RuleDetails entity = new RuleDetails();
        assertEquals("TEXT", entity.getDataType());
    }
}
