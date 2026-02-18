package gov.cdc.nbs.event.search.labreport;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.data.pagination.PaginatedRequestJSONMapper;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class LabReportSearchRequester {
  private static final String QUERY =
      """
      query search($filter: LabReportFilter!, $page: SortablePage) {
        findLabReportsByFilter(filter: $filter, page: $page) {
          total
          content{
              relevance
              id
              jurisdictionCd
              localId
              addTime
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
              organizationParticipations{
                  typeCd
                  name
              }
              observations{
                  cdDescTxt
                  statusCd
                  altCd
                  displayName
              }
              associatedInvestigations{
                  cdDescTxt
                  localId
              }
          }
        }
      }
      """;
  private final ObjectMapper mapper;

  private final GraphQLRequest graphql;
  private final PaginatedRequestJSONMapper paginatedMapper;

  public LabReportSearchRequester(
      final ObjectMapper mapper,
      final GraphQLRequest graphql,
      final PaginatedRequestJSONMapper paginatedMapper) {
    this.mapper = mapper;
    this.graphql = graphql;
    this.paginatedMapper = paginatedMapper;
  }

  ResultActions search(
      final LabReportFilter filter, final Pageable paging, final SortCriteria sorting) {
    try {

      JsonNode page = paginatedMapper.map(paging, sorting);

      return graphql
          .query(
              QUERY,
              mapper
                  .createObjectNode()
                  .<ObjectNode>set("filter", mapper.convertValue(filter, JsonNode.class))
                  .set("page", page))
          .andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request a Patient Search");
    }
  }
}
