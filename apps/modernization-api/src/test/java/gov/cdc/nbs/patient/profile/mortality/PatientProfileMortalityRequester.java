package gov.cdc.nbs.patient.profile.mortality;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileMortalityRequester {

  private static final String QUERY = """
      query profileMortalityInformation($patient: ID!) {
              findPatientProfile(patient: $patient) {
                id
                local
                version
                mortality {
                    asOf
                    deceasedOn
                    city
                    county {
                      id
                      description
                    }
                    deceased {
                      id
                      description
                    }
                    state {
                      id
                      description
                    }
                    country {
                      id
                      description
                    }
                }
              }
            }
      """;

  private final GraphQLRequest graphql;

  PatientProfileMortalityRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions mortality(final PatientIdentifier patient) {
    try {
      return graphql.query(
          QUERY,
          JsonNodeFactory.instance.objectNode()
              .put("patient", patient.id()))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request patient mortality demographics");
    }
  }
}
