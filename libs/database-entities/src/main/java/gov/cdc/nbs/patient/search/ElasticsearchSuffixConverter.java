package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.message.enums.Suffix;
import gov.cdc.nbs.patient.SuffixStringConverter;
import org.springframework.data.elasticsearch.core.mapping.PropertyValueConverter;

public class ElasticsearchSuffixConverter implements PropertyValueConverter {
  @Override
  public Object write(final Object value) {
    return (value instanceof Suffix suffix)
        ? SuffixStringConverter.toString(suffix)
        : null;
  }

  @Override
  public Object read(final Object value) {
    return (value instanceof String suffix)
        ? SuffixStringConverter.fromString(suffix)
        : null;
  }
}
