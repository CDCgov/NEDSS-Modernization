package gov.cdc.nbs.patient.profile.general.change;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientGeneralChangeRequester {

  private static final String MUTATION = """
    mutation general($input: GeneralInfoInput!) {
       updatePatientGeneralInfo(input: $input) {
         patient
       }
     }
    """;

  private final ObjectMapper mapper;
  private final GraphQLRequest graphql;

  PatientGeneralChangeRequester(final ObjectMapper mapper, final GraphQLRequest graphql) {
    this.mapper = mapper;
    this.graphql = graphql;
  }

  ResultActions change(final UpdateGeneralInformation changes) {
    JsonNode input = mapper.convertValue(changes, JsonNode.class);
    try {
      return graphql.query(
          MUTATION,
          JsonNodeFactory.instance.objectNode()
              .set("input", input)
      ).andDo(print());
    } catch(Exception exception) {
      throw new IllegalStateException("Unable to request patient general demographics");
    }
  }
}
