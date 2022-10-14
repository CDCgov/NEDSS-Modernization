package gov.cdc.nbs.entity.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import gov.cdc.nbs.entity.enums.Ethnicity;

@Converter
public class EthnicityConverter implements AttributeConverter<Ethnicity, String> {

    @Override
    public String convertToDatabaseColumn(Ethnicity ethnicity) {
        return getValueFromEthnicity(ethnicity);
    }

    @Override
    public Ethnicity convertToEntityAttribute(String dbData) {
        return getEthnicityFromValue(dbData);
    }

    public static String getValueFromEthnicity(Ethnicity ethnicity) {
        if (ethnicity == null) {
            return null;
        }
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

    public static Ethnicity getEthnicityFromValue(String value) {
        if (value == null) {
            return null;
        }
        // https://phinvads.cdc.gov/vads/ViewValueSet.action?oid=2.16.840.1.114222.4.11.837
        switch (value) {
            case "2135-2":
                return Ethnicity.HISPANIC_OR_LATINO;
            case "2186-5":
                return Ethnicity.NOT_HISPANIC_OR_LATINO;
            case "UNK":
                return Ethnicity.UNKNOWN;
            default:
                throw new RuntimeException("Invalid ethnicity value supplied: " + value);
        }
    }

}
