package gov.cdc.nbs.event.search.labreport;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.util.Objects;
import java.util.OptionalLong;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
class LabReportSearchResultShortIdentifierResolver {

  private final PatientShortIdentifierResolver resolver;

  LabReportSearchResultShortIdentifierResolver(final PatientShortIdentifierResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(typeName = "LabReportPersonParticipation", field = "shortId")
  OptionalLong resolve(final LabReportSearchResult.PersonParticipation participation) {
    if (Objects.equals(participation.typeCd(), "PATSBJ")) {
      return this.resolver.resolve(participation.local());
    } else {
      return OptionalLong.empty();
    }
  }
}
