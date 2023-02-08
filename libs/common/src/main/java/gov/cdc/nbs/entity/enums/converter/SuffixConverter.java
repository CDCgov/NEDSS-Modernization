package gov.cdc.nbs.entity.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import gov.cdc.nbs.entity.enums.Suffix;

@Converter
public class SuffixConverter implements AttributeConverter<Suffix, String> {

    @Override
    public String convertToDatabaseColumn(Suffix attribute) {
        return getValueFromType(attribute);
    }

    @Override
    public Suffix convertToEntityAttribute(String dbData) {
        return getTypeFromValue(dbData);
    }

    private Suffix getTypeFromValue(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "ESQ":
                return Suffix.ESQ;
            case "II":
                return Suffix.II;
            case "III":
                return Suffix.III;
            case "IV":
                return Suffix.IV;
            case "JR":
                return Suffix.JR;
            case "SR":
                return Suffix.SR;
            case "V":
                return Suffix.V;
            default:
                return null;
        }
    }

    private String getValueFromType(Suffix attribute) {
        if (attribute == null) {
            return null;
        }
        switch (attribute) {
            case ESQ:
                return "ESQ";
            case II:
                return "II";
            case III:
                return "III";
            case IV:
                return "IV";
            case JR:
                return "JR";
            case SR:
                return "SR";
            case V:
                return "V";
            default:
                return null;
        }
    }

}
