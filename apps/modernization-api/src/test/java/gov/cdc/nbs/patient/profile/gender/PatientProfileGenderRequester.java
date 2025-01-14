package gov.cdc.nbs.patient.profile.gender;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileGenderRequester {

  private static final String QUERY = """
      query patientProfileGender($patient: ID!) {
       findPatientProfile(patient: $patient) {
        gender {
          asOf
          birth {
            id
            description
          }
          current {
            id
            description
          }
          unknownReason {
            id
            description
          }
          preferred {
            id
            description
          }
          additional
        }
       }
      }
      """;

  private final GraphQLRequest graphql;

  PatientProfileGenderRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions gender(final PatientIdentifier patient) {
    try {
      return graphql.query(
          QUERY,
          JsonNodeFactory.instance.objectNode()
              .put("patient", patient.id())
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request patient race demographics");
    }
  }
}
