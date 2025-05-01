package gov.cdc.nbs.patient.file.delete;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class GraphQLPatientDeleteRequester {

  private static final String MUTATION = """
      mutation deletePatient($patient: ID!) {
        deletePatient(patient: $patient) {
          __typename
          ... on PatientDeleteFailed {
            patient
            reason
          }
          ... on PatientDeleteSuccessful {
            patient
          }
        }
      }
      
      """;

  private final GraphQLRequest graphql;

  GraphQLPatientDeleteRequester(final GraphQLRequest graphql) {

    this.graphql = graphql;
  }

  ResultActions request(final long patient) {
    return graphql.query(
        MUTATION,
        JsonNodeFactory.instance.objectNode()
            .put("patient", patient)
    );
  }

}
