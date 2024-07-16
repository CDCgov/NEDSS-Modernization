package gov.cdc.nbs.patient.profile.general;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientGeneralRequester {

  private static final String QUERY = """
      query profileGeneralInformation($patient: ID!) {
        findPatientProfile(patient: $patient) {
          id
          local
          general {
            patient
            id
            version
            asOf
            maritalStatus {
              id
              description
            }
            maternalMaidenName
            adultsInHouse
            childrenInHouse
            occupation {
              id
              description
            }
            educationLevel {
              id
              description
            }
            primaryLanguage {
              id
              description
            }
            speaksEnglish {
              id
              description
            }
            stateHIVCase {
              __typename
              ... on Allowed {
                value
              }
              ... on Restricted {
                reason
              }
            }
          }
        }
      }
      """;

  private final GraphQLRequest graphql;

  public PatientGeneralRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions general(final PatientIdentifier patient) {
    try {
      return graphql.query(
          QUERY,
          JsonNodeFactory.instance.objectNode()
              .put("patient", patient.id())
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request patient general demographics");
    }
  }
}
