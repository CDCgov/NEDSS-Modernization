package gov.cdc.nbs.entity.enums.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.SuffixStringConverter;

@Converter
public class SuffixConverter implements AttributeConverter<Suffix, String> {

    @Override
    public String convertToDatabaseColumn(Suffix attribute) {
        return SuffixStringConverter.toString(attribute);
    }

    @Override
    public Suffix convertToEntityAttribute(String dbData) {
        return SuffixStringConverter.fromString(dbData);
    }

}
