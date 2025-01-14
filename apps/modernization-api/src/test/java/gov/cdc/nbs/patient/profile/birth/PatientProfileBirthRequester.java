package gov.cdc.nbs.patient.profile.birth;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileBirthRequester {

  private static final String QUERY = """
       query patientProfileBirth($patient: ID!) {
        findPatientProfile(patient: $patient) {
          birth {
            patient
            id
            version
            asOf
            bornOn
            age
            multipleBirth {
              id
              description
            }
            birthOrder
            city
            state {
              id
              description
            }
            county {
              id
              description
            }
            country {
              id
              description
            }
          }
          gender {
           birth {
             id
             description
           }
          }
        }
      }
      """;

  private final GraphQLRequest graphql;

  PatientProfileBirthRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions birth(final PatientIdentifier patient) {
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
