package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entities.Condition;

public class ConditionMother {

    public static Condition syphilisPrimary() {
        return condition("Syphilis, primary");
    }

    public static Condition asepticMeningitis() {
        return condition("septic meningitis");
    }

    public static Condition brucellosis() {
        return condition("Brucellosis");
    }

    public static Condition condition(String name) {
        Condition c = new Condition();
        c.setName(name);
        c.setDescription(name);
        return c;
    }


}
