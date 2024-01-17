package gov.cdc.nbs.patient.documentsrequiringreview;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.page.PageableJsonNodeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@Component
class PatientProfileDocumentsRequiringReviewRequester {
  private static final String QUERY = """
      query documentsRequiringReview($patient: Int!, $page: DocumentRequiringReviewSortablePage) {
        findDocumentsRequiringReviewForPatient(patient: $patient, page: $page) {
          content {
            id
            type
            dateReceived
            facilityProviders {
              orderingProvider {
                  name
              }
              reportingFacility {
                  name
              }
              sendingFacility {
                  name
              }
            }
            eventDate
            descriptions {
              title
              value
            }
            eventDate
          }
          total
        }
      }
              
      """;

  private final GraphQLRequest graphql;

  PatientProfileDocumentsRequiringReviewRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions documentsRequiringReview(final PatientIdentifier patient, final Pageable pageable) {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
            .put("patient", patient.id())
            .set("page", PageableJsonNodeMapper.asJsonNode(pageable))
    );
  }

  ResultActions documentsRequiringReview(final PatientIdentifier patient) {
    try {
      return graphql.query(
          QUERY,
          JsonNodeFactory.instance.objectNode()
              .put("patient", patient.id())
      ).andDo(print());
    } catch (Exception exception) {
      throw new IllegalStateException("Unable to request patient documents requiring review");
    }
  }
}
