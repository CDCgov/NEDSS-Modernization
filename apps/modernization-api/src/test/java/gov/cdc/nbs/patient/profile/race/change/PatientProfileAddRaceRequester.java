package gov.cdc.nbs.patient.profile.race.change;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.message.patient.input.RaceInput;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileAddRaceRequester {

  private static final String MUTATION = """
      mutation add($input: RaceInput!) {
        addPatientRace(input: $input) {
          __typename
          ... on PatientRaceChangeFailureExistingCategory {
            patient
          }
          ... on PatientRaceChangeSuccessful {
            patient
          }
        }
      }
      """;

  private final GraphQLRequest graphql;
  private final ObjectMapper mapper;

  PatientProfileAddRaceRequester(
      final GraphQLRequest graphql,
      final ObjectMapper mapper
  ) {
    this.graphql = graphql;
    this.mapper = mapper;
  }

  ResultActions add(final RaceInput input) {
    try {
      JsonNode variables = JsonNodeFactory.instance.objectNode()
          .set("input", mapper.<ObjectNode>valueToTree(input));

      return this.graphql.query(
          MUTATION,
          variables
      ).andDo(print());

    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request race addition");
    }
  }

}
