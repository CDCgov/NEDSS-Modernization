package gov.cdc.nbs.entity.enums.converter;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

import gov.cdc.nbs.entity.enums.Deceased;

public class DeceasedConverter implements PropertyValueConverter {

    @Override
    public Object write(Object value) {
        if (value instanceof Deceased deceased) {
            return deceased.name();
        } else {
            return value;
        }
    }

    @Override
    public Object read(Object value) {
        if (value instanceof String s) {
            switch (s) {
                case "Y":
                    return Deceased.Y;
                case "N":
                    return Deceased.N;
                case "UNK":
                    return Deceased.UNK;
                case "FALSE", "false":
                    return Deceased.FALSE;
                default:
                    throw new IllegalArgumentException("Invalid Deceased string value: " + s);
            }
        } else {
            return value;
        }
    }

}
