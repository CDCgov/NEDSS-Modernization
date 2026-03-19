package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.util.Objects;
import java.util.OptionalLong;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
class InvestigationPersonParticipationShortIdentifierResolver {

  private final PatientShortIdentifierResolver resolver;

  InvestigationPersonParticipationShortIdentifierResolver(
      final PatientShortIdentifierResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(typeName = "InvestigationPersonParticipation", field = "shortId")
  OptionalLong resolve(final InvestigationSearchResult.PersonParticipation participation) {
    if (Objects.equals(participation.typeCd(), "SubjOfPHC")) {
      return this.resolver.resolve(participation.local());
    } else {
      return OptionalLong.empty();
    }
  }
}
