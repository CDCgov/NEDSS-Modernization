package gov.cdc.nbs.patient.search;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.data.pagination.PaginatedRequestJSONMapper;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.data.domain.Pageable;
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
              type
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
                type
                use
                address
                address2
                city
                county
                state
                zipcode
            }
            detailedPhones {
              number
              type
              use
            }
          }
          total
        }
      }
      """;
  private final ObjectMapper mapper;

  private final GraphQLRequest graphql;
  private final PaginatedRequestJSONMapper paginatedMapper;

  public PatientSearchRequester(
      final ObjectMapper mapper,
      final GraphQLRequest graphql,
      final PaginatedRequestJSONMapper paginatedMapper) {
    this.mapper = mapper;
    this.graphql = graphql;
    this.paginatedMapper = paginatedMapper;
  }

  ResultActions search(
      final PatientFilter filter,
      final Pageable paging,
      final SortCriteria sorting) {
    try {

      JsonNode page = paginatedMapper.map(paging, sorting);


      return graphql.query(
          QUERY,
          mapper.createObjectNode()
              .<ObjectNode>set(
                  "filter",
                  mapper.convertValue(filter, JsonNode.class))
              .set("page", page))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request a Patient Search");
    }
  }

}
