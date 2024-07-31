package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientSearchRequester {
  private static final String QUERY = """
      query search($filter: PersonFilter!, $page: SortablePage) {
        findPatientsByFilter(filter: $filter, page: $page) {
          content {
            patient
            shortId
            status
            birthday
            age
            gender
            legalName {
              first
              middle
              last
              suffix
            }
            names {
              first
              middle
              last
              suffix
            }
            identification {
              type
              value
            }
            emails
            phones
            addresses {
                use
                address
                address2
                city
                county
                state
                zipcode
            }
          }
          total
        }
      }
      """;
  private final ObjectMapper mapper;

  private final GraphQLRequest graphql;

  public PatientSearchRequester(
      final ObjectMapper mapper,
      final GraphQLRequest graphql) {
    this.mapper = mapper;
    this.graphql = graphql;
  }

  ResultActions search(final PatientFilter filter, final SortCriteria sorting) {
    try {
      return graphql.query(
          QUERY,
          mapper.createObjectNode()
              .<ObjectNode>set(
                  "filter",
                  mapper.convertValue(filter, JsonNode.class))
              .set(
                  "page",
                  mapper.createObjectNode()
                      .put("pageNumber", 0)
                      .put("pageSize", 15)
                      .put("sortDirection", sorting.direction().name())
                      .put("sortField", sorting.field())))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request a Patient Search");
    }
  }

}
