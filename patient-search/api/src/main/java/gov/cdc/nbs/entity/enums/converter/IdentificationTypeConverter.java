package gov.cdc.nbs.entity.enums.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import gov.cdc.nbs.entity.enums.IdentificationType;

@Converter
public class IdentificationTypeConverter implements AttributeConverter<IdentificationType, String> {

    @Override
    public String convertToDatabaseColumn(IdentificationType attribute) {
        return getValueFromType(attribute);
    }

    @Override
    public IdentificationType convertToEntityAttribute(String dbData) {
        return getTypeFromValue(dbData);
    }

    public static IdentificationType getTypeFromValue(String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "AN", "ACN":
                return IdentificationType.ACCOUNT_NUMBER;
            case "APT":
                return IdentificationType.ALTERNATE_PERSON_NUMBER;
            case "CI":
                return IdentificationType.CHIP_IDENTIFICATION_NUMBER;
            case "DL":
                return IdentificationType.DRIVERS_LICENSE_NUMBER;
            case "IIS":
                return IdentificationType.IMMUNIZATION_REGISTRY_ID;
            case "MA":
                return IdentificationType.MEDICAID_NUMBER;
            case "MR":
                return IdentificationType.MEDICAL_RECORD_NUMBER;
            case "MC":
                return IdentificationType.MEDICARE_NUMBER;
            case "MO":
                return IdentificationType.MOTHERS_IDENTIFIER;
            case "NI":
                return IdentificationType.NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER;
            case "OTH":
                return IdentificationType.OTHER;
            case "PSID":
                return IdentificationType.PARTNER_SERVICES_PATIENT_NUMBER;
            case "PT":
                return IdentificationType.PATIENT_EXTERNAL_IDENTIFIER;
            case "PI":
                return IdentificationType.PATIENT_INTERNAL_IDENTIFIER;
            case "PN":
                return IdentificationType.PERSON_NUMBER;
            case "PIN":
                return IdentificationType.PRISON_IDENTIFICATION_NUMBER;
            case "QEC":
                return IdentificationType.QUICK_ENTRY_CODE;
            case "RW":
                return IdentificationType.RYAN_WHITE_IDENTIFIER;
            case "SS":
                return IdentificationType.SOCIAL_SECURITY;
            case "VS":
                return IdentificationType.VISA_PASSPORT;
            case "WC":
                return IdentificationType.WIC_IDENTIFIER;
            default:
                return null;
        }
    }

    public static String getValueFromType(IdentificationType type) {
        if (type == null) {
            return null;
        }

        switch (type) {
            case ACCOUNT_NUMBER:
                return "AN";
            case ALTERNATE_PERSON_NUMBER:
                return "APT";
            case CHIP_IDENTIFICATION_NUMBER:
                return "CI";
            case DRIVERS_LICENSE_NUMBER:
                return "DL";
            case IMMUNIZATION_REGISTRY_ID:
                return "IIS";
            case MEDICAID_NUMBER:
                return "MA";
            case MEDICAL_RECORD_NUMBER:
                return "MR";
            case MEDICARE_NUMBER:
                return "MC";
            case MOTHERS_IDENTIFIER:
                return "MO";
            case NATIONAL_UNIQUE_INDIVIDUAL_IDENTIFIER:
                return "NI";
            case OTHER:
                return "OTH";
            case PARTNER_SERVICES_PATIENT_NUMBER:
                return "PSID";
            case PATIENT_EXTERNAL_IDENTIFIER:
                return "PT";
            case PATIENT_INTERNAL_IDENTIFIER:
                return "PI";
            case PERSON_NUMBER:
                return "PN";
            case PRISON_IDENTIFICATION_NUMBER:
                return "PIN";
            case QUICK_ENTRY_CODE:
                return "QEC";
            case RYAN_WHITE_IDENTIFIER:
                return "RW";
            case SOCIAL_SECURITY:
                return "SS";
            case VISA_PASSPORT:
                return "VS";
            case WIC_IDENTIFIER:
                return "WC";
            default:
                return null;
        }
    }

}
