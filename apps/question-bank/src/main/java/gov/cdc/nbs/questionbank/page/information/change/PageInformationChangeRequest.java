package gov.cdc.nbs.questionbank.page.information.change;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

record PageInformationChangeRequest(
    String messageMappingGuide,
    String name,
    String datamart,
    String description,
    Collection<String> associated
) {

  PageInformationChangeRequest() {
    this(null, null, null, null, List.of());
  }

  PageInformationChangeRequest withMessageMappingGuide(final String messageMappingGuide) {
    return new PageInformationChangeRequest(
        messageMappingGuide,
        name(),
        datamart(),
        description(),
        associated()
    );
  }

  PageInformationChangeRequest withName(final String name) {
    return new PageInformationChangeRequest(
        messageMappingGuide(),
        name,
        datamart(),
        description(),
        associated()
    );
  }

  PageInformationChangeRequest withDatamart(final String datamart) {
    return new PageInformationChangeRequest(
        messageMappingGuide(),
        name(),
        datamart,
        description(),
        associated()
    );
  }

  PageInformationChangeRequest withDescription(final String description) {
    return new PageInformationChangeRequest(
        messageMappingGuide(),
        name(),
        datamart(),
        description,
        associated()
    );
  }

  PageInformationChangeRequest withCondition(final String condition) {
    List<String> appended = Stream.concat(associated.stream(), Stream.of(condition)).toList();
    return new PageInformationChangeRequest(
        messageMappingGuide(),
        name(),
        datamart(),
        description(),
        appended
    );
  }
}
