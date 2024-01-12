package gov.cdc.nbs.patient.profile.race.change;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.graphql.GraphQLRequest;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientProfileDeleteRaceRequester {

  private static final String MUTATION = """
      mutation delete($input: DeletePatientRace!) {
        deletePatientRace(input: $input) {
          patient
                        
        }
      }
      """;

  private final GraphQLRequest graphql;
  private final ObjectMapper mapper;

  PatientProfileDeleteRaceRequester(
      final GraphQLRequest graphql,
      final ObjectMapper mapper
  ) {
    this.graphql = graphql;
    this.mapper = mapper;
  }

  ResultActions delete(final DeletePatientRace input) {
    try {

      JsonNode variables = JsonNodeFactory.instance.objectNode()
          .set("input", mapper.<ObjectNode>valueToTree(input));

      return this.graphql.query(
          MUTATION,
          variables
      );

    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request race deletion");
    }
  }

}
