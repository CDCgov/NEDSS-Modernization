package gov.cdc.nbs.patientlistener.enums.converter;



import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import gov.cdc.nbs.entity.enums.Race;


@Converter
public class RaceConverter implements AttributeConverter<Race, String> {

    @Override
    public String convertToDatabaseColumn(Race race) {
        return getValueFromRace(race);
    }

    @Override
    public Race convertToEntityAttribute(String string) {
        return getRaceFromValue(string);
    }

    public static String getValueFromRace(Race race) {
        if (race == null) {
            return null;
        }
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
                throw new ConversionException("Invalid race supplied: " + race);

        }
    }

    public static Race getRaceFromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "2054-5":
                return Race.AFRICAN_AMERICAN;
            case "1002-5":
                return Race.AMERICAN_INDIAN_OR_ALASKAN_NATIVE;
            case "2028-9":
                return Race.ASIAN;
            case "2076-8":
                return Race.NATIVE_HAWAIIAN_OR_PACIFIC_ISLANDER;
            case "NASK":
                return Race.NOT_ASKED;
            case "2131-1":
                return Race.OTHER_RACE;
            case "PHC1175":
                return Race.REFUSED_TO_ANSWER;
            case "U":
                return Race.UNKNOWN;
            case "2106-3":
                return Race.WHITE;
            default:
                throw new ConversionException("Invalid race value supplied: " + value);
        }
    }

}
