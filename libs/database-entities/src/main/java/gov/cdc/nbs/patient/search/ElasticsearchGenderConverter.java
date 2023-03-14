package gov.cdc.nbs.patient.search;

import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;
import gov.cdc.nbs.message.enums.Gender;
import gov.cdc.nbs.patient.GenderStringConverter;

public class ElasticsearchGenderConverter implements PropertyValueConverter {
    @Override
    public Object write(final Object value) {
        return (value instanceof Gender suffix)
                ? GenderStringConverter.toString(suffix)
                : null;
    }

    @Override
    public Object read(final Object value) {
        return (value instanceof String suffix)
                ? GenderStringConverter.fromString(suffix)
                : null;
    }

}
