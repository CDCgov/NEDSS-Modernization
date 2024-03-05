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
            observationUid
            lastChange
            classCd
            moodCd
            observationLastChgTime
            cdDescTxt
            recordStatusCd
            programAreaCd
            jurisdictionCd
            jurisdictionCodeDescTxt
            pregnantIndCd
            localId
            activityToTime
            effectiveFromTime
            rptToStateTime
            addTime
            electronicInd
            versionCtrlNbr
            addUserId
            lastChgUserId
            personParticipations{
                actUid
                localId
                typeCd
                entityId
                subjectClassCd
                participationRecordStatus
                typeDescTxt
                participationLastChangeTime
                firstName
                lastName
                birthTime
                currSexCd
                personCd
                personParentUid
                personRecordStatus
                personLastChangeTime
                shortId
            }
            organizationParticipations{
                actUid
                typeCd
                entityId
                subjectClassCd
                typeDescTxt
                participationRecordStatus
                participationLastChangeTime
                name
                organizationLastChangeTime
            }
            materialParticipations{
                actUid
                typeCd
                entityId
                subjectClassCd
                typeDescTxt
                participationRecordStatus
                participationLastChangeTime
                cd
                cdDescTxt
            }
            observations{
                cd
                cdDescTxt
                domainCd
                statusCd
                altCd
                altDescTxt
                altCdSystemCd
                displayName
                ovcCode
                ovcAltCode
                ovcAltDescTxt
                ovcAltCdSystemCd
            }
            actIds{
                id
                recordStatus
                actIdSeq
                rootExtensionTxt
                typeCd
                lastChangeTime
            }
            associatedInvestigations{
                publicHealthCaseUid
                cdDescTxt
                localId
                lastChgTime
                actRelationshipLastChgTime
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
