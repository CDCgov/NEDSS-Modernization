package gov.cdc.nbs.patient.profile.administrative;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.page.PageableJsonNodeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileAdministrativeRequester {

  private static final String QUERY = """
      query profile($patient: ID!, $page: Page) {
              findPatientProfile(patient: $patient) {
                id
                local
                version
                administrative(page: $page) {
                  total
                  number
                  size
                  content {
                    asOf
                    comment
                  }
                }
              }
            }
      """;

  private final GraphQLRequest graphql;

  PatientProfileAdministrativeRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions administrative(final PatientIdentifier patient, final Pageable pageable) {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
            .put("patient", patient.id())
            .set("page", PageableJsonNodeMapper.asJsonNode(pageable))
    );
  }

  ResultActions administrative(final PatientIdentifier patient) {
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
