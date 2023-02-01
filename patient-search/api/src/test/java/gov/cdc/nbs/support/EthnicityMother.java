package gov.cdc.nbs.support;

import java.util.Arrays;
import java.util.List;

public class EthnicityMother {
    // values from
    // https://phinvads.cdc.gov/vads/ViewValueSet.action?id=35D34BBC-617F-DD11-B38D-00188B398520
    public static final String HISPANIC_OR_LATINO = "2135-2";
    public static final String NOT_HISPANIC_OR_LATINO = "2186-5";
    public static final String UNKNOWN = "U";
    public static final List<String> ETHNICITY_LIST = Arrays.asList(HISPANIC_OR_LATINO, NOT_HISPANIC_OR_LATINO,
            UNKNOWN);
}
