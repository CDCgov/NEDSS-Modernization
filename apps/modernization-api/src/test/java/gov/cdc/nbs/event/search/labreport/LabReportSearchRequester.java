package gov.cdc.nbs.event.search.labreport;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.search.support.SortCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class LabReportSearchRequester {
  private static final String QUERY = """
      query search($filter: LabReportFilter!, $page: SortablePage) {
        findLabReportsByFilter(filter: $filter, page: $page) {
          total
          content{
              id
              jurisdictionCd
              jurisdictionCodeDescTxt
              localId
              addTime
              personParticipations{
                  birthTime
                  currSexCd
                  typeCd
                  participationRecordStatus
                  typeDescTxt
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

  public LabReportSearchRequester(
      final ObjectMapper mapper,
      final GraphQLRequest graphql
  ) {
    this.mapper = mapper;
    this.graphql = graphql;
  }

  ResultActions search(
      final LabReportFilter filter,
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
