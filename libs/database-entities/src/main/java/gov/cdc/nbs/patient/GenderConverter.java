package gov.cdc.nbs.patient;

import jakarta.persistence.AttributeConverter;
import gov.cdc.nbs.message.enums.Gender;

public class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    public String convertToDatabaseColumn(Gender attribute) {
        return GenderStringConverter.toString(attribute);
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        return GenderStringConverter.fromString(dbData);
    }

}
