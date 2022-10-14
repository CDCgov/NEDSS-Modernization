package gov.cdc.nbs.service.util;

import gov.cdc.nbs.entity.enums.Ethnicity;
import gov.cdc.nbs.entity.enums.Race;

public class RaceUtil {

    public static String getRaceValue(Race race) {
        // Values from NBS - NEDSSConstants
        // https://phinvads.cdc.gov/vads/ViewCodeSystem.action?id=2.16.840.1.113883.6.238
        switch (race) {
            case AFRICAN_AMERICAN:
                return "2054-5";
            case AMERICAN_INDIAN_OR_ALASKAN_NATIVE:
                return "1002-5";
            case ASIAN:
                return "2028-9";
            case NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER:
                return "2076-8";
            case NOT_ASKED:
                return "NASK";
            case OTHER_RACE:
                return "2131-1";
            case REFUSED_TO_ANSWER:
                return "PHC1175";
            case UNKNOWN:
                return "U";
            case WHITE:
                return "2106-3";
            default:
                throw new RuntimeException("Invalid race supplied: " + race);

        }
    }

    public static String getEthnicityValue(Ethnicity ethnicity) {
        // https://phinvads.cdc.gov/vads/ViewValueSet.action?oid=2.16.840.1.114222.4.11.837
        switch (ethnicity) {
            case HISPANIC_OR_LATINO:
                return "2135-2";
            case NOT_HISPANIC_OR_LATINO:
                return "2186-5";
            case UNKNOWN:
                return "UNK";
            default:
                throw new RuntimeException("Invalid ethnicity supplied: " + ethnicity);
        }
    }

}
