package gov.cdc.nbs.patient.profile.summary;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientProfileSummaryRequest {

  private static final String QUERY = """
      query profile($patient: ID!, $asOf: Date) {
        findPatientProfile(patient: $patient) {
          id
          local
          shortId
          summary(asOf: $asOf) {
            legalName {
              prefix
              first
              middle
              last
              suffix
            }
            birthday
            age
            gender
            ethnicity
            races
            home {
              use
              address
              address2
              city
              state
              zipcode
              country
            }
            address {
              use
              address
              address2
              city
              state
              zipcode
              country
            }
            phone {
              use
              number
            }
            email {
              use
              address
            }
            identification {
              type
              value
            }
          }
        }
      }
      """;

  private final GraphQLRequest graphql;

  PatientProfileSummaryRequest(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions summary(final PatientIdentifier patient) {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
            .put("patient", patient.id())
    );
  }

}
