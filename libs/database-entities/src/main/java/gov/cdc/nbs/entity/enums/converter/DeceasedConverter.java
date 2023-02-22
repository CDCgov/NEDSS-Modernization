package gov.cdc.nbs.entity.enums.converter;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

import gov.cdc.nbs.message.enums.Deceased;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
                case "Y", "true":
                    return Deceased.Y;
                case "N", "FALSE", "false":
                    return Deceased.N;
                case "UNK":
                    return Deceased.UNK;
                default:
                    log.warn("Failed to convert from String to Deceased enum. String value: {}", value);
                    return null;
            }
        } else {
            return value;
        }
    }

}
