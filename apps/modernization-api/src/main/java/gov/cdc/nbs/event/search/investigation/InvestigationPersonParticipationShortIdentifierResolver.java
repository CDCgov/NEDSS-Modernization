package gov.cdc.nbs.event.search.investigation;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPersonParticipation;
import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.OptionalLong;

@Controller
class InvestigationPersonParticipationShortIdentifierResolver {

  private final PatientShortIdentifierResolver resolver;

  InvestigationPersonParticipationShortIdentifierResolver(final PatientShortIdentifierResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(typeName = "InvestigationPersonParticipation", field = "shortId")
  OptionalLong resolve(final ElasticsearchPersonParticipation participation) {
    return this.resolver.resolve(participation.getLocalId());
  }
}
