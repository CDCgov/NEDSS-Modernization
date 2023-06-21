package gov.cdc.nbs.questionbank.entity.pagerule;

import gov.cdc.nbs.questionbank.model.RuleDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class RuleDetailsTest {

    @Test
    void should_have_text_type() {
        RuleDetails entity = new RuleDetails();
        Assertions.assertEquals("TEXT", entity.getDataType());
    }
}
