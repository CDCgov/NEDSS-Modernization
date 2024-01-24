package gov.cdc.nbs.patient.profile.identification;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.page.PageableJsonNodeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileIdentificationRequester {

  private static final String QUERY = """
      query identifications($patient: ID!, $page: Page) {
        findPatientProfile(patient: $patient) {
          identification(page: $page) {
            total
            number
            size
            content {
              patient
              sequence
              asOf
              type {
                id
                description
              }
              authority {
                id
                description
              }
              value
            }
          }
        }
      }
      """;

  private final GraphQLRequest graphql;

  PatientProfileIdentificationRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions identifications(final PatientIdentifier patient, final Pageable pageable) {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
            .put("patient", patient.id())
            .set("page", PageableJsonNodeMapper.asJsonNode(pageable))
    );
  }

  ResultActions identifications(final PatientIdentifier patient) {
    try {
      return graphql.query(
          QUERY,
          JsonNodeFactory.instance.objectNode()
              .put("patient", patient.id())
      ).andDo(print());
    } catch(Exception exception) {
      throw new IllegalStateException("Unable to request patient race demographics");
    }
  }
}
