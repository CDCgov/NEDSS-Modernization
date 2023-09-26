package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.Authenticated;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
class TestPatientSearchRequest {

  private final ObjectMapper mapper;
  private final Authenticated authenticated;
  private final MockMvc mvc;

  public TestPatientSearchRequest(
      final ObjectMapper mapper,
      final Authenticated authenticated,
      final MockMvc mvc
  ) {
    this.mapper = mapper;
    this.authenticated = authenticated;
    this.mvc = mvc;
  }

  ResultActions search(final PatientFilter filter, final SortCriteria sorting) throws Exception {
    JsonNode content = payload(filter, sorting);

    return graphQLRequest(content);
  }

  private JsonNode payload(
      final PatientFilter filter,
      final SortCriteria sorting
  ) {

    return mapper.createObjectNode()
        .put(
            "query",
            """
                query search($filter: PersonFilter!, $page: SortablePage) {
                  findPatientsByFilter(filter: $filter, page: $page) {
                    content {
                      id
                      shortId
                      recordStatusCd
                      birthTime
                      names {
                        firstNm
                        middleNm
                        lastNm
                        nmSuffix
                        nmPrefix
                      }
                      identification {
                        type
                        value
                      }
                      nbsEntity {
                        entityLocatorParticipations {
                          locator {
                            phoneNbrTxt
                          }
                        }
                      }
                    }
                    total
                  }
                }
                """
        )
        .<ObjectNode>set(
            "variables",
            mapper.createObjectNode()
                .<ObjectNode>set(
                    "filter",
                    mapper.convertValue(filter, JsonNode.class)
                )
                .set(
                    "page",
                    mapper.createObjectNode()
                        .put("pageNumber", 0)
                        .put("pageSize", 15)
                        .put("sortDirection", sorting.direction().name())
                        .put("sortField", sorting.field())
                )
        );
  }

  private ResultActions graphQLRequest(final JsonNode payload) throws Exception {
    byte[] content = mapper.writeValueAsBytes(payload);

    MvcResult graphQLRequest = mvc.perform(
            this.authenticated.withUser(post("/graphql"))
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(request().asyncStarted())
        .andReturn();

    return mvc.perform(asyncDispatch(graphQLRequest))
        .andExpect(status().isOk());
  }
}
