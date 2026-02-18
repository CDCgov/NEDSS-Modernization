package gov.cdc.nbs.questionbank.page.information.change;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

record PageInformationChangeRequest(
    String messageMappingGuide,
    String name,
    String datamart,
    String description,
    Collection<String> conditions) {

  PageInformationChangeRequest() {
    this(null, null, null, null, List.of());
  }

  PageInformationChangeRequest withMessageMappingGuide(final String messageMappingGuide) {
    return new PageInformationChangeRequest(
        messageMappingGuide, name(), datamart(), description(), conditions());
  }

  PageInformationChangeRequest withName(final String name) {
    return new PageInformationChangeRequest(
        messageMappingGuide(), name, datamart(), description(), conditions());
  }

  PageInformationChangeRequest withDatamart(final String datamart) {
    return new PageInformationChangeRequest(
        messageMappingGuide(), name(), datamart, description(), conditions());
  }

  PageInformationChangeRequest withDescription(final String description) {
    return new PageInformationChangeRequest(
        messageMappingGuide(), name(), datamart(), description, conditions());
  }

  PageInformationChangeRequest withCondition(final String condition) {
    List<String> appended = Stream.concat(conditions.stream(), Stream.of(condition)).toList();
    return new PageInformationChangeRequest(
        messageMappingGuide(), name(), datamart(), description(), appended);
  }
}
