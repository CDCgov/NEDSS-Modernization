package gov.cdc.nbs.event.search.investigation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class InvestigationSearchRequester {
  private static final String QUERY = """
      query findInvestigationsByFilter($filter: InvestigationFilter!, $page: SortablePage){
          findInvestigationsByFilter(filter: $filter, page: $page){
              content{
                  relevance
                  id
                  cdDescTxt
                  jurisdictionCodeDescTxt
                  localId
                  addTime
                  investigationStatusCd
                  notificationRecordStatusCd
                  personParticipations{
                      birthTime
                      currSexCd
                      typeCd
                      firstName
                      lastName
                      personCd
                      personParentUid
                      shortId
                  }
              }
              total
          }
      }
      """;
  private final ObjectMapper mapper;

  private final GraphQLRequest graphql;

  public InvestigationSearchRequester(
      final ObjectMapper mapper,
      final GraphQLRequest graphql
  ) {
    this.mapper = mapper;
    this.graphql = graphql;
  }

  ResultActions search(
      final InvestigationFilter filter,
      final Pageable paging,
      final SortCriteria sorting
  ) {
    try {
      return graphql.query(
          QUERY,
          mapper.createObjectNode()
              .<ObjectNode>set(
                  "filter",
                  mapper.convertValue(filter, JsonNode.class)
              )
              .set(
                  "page",
                  mapper.createObjectNode()
                      .put("pageNumber", paging.getPageNumber())
                      .put("pageSize", paging.getPageSize())
                      .put("sortDirection", sorting.direction().name())
                      .put("sortField", sorting.field())
              )
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request a Patient Search");
    }
  }

}
