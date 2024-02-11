package gov.cdc.nbs.codes.user;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.cdc.nbs.graphql.GraphQLRequest;
import gov.cdc.nbs.testing.interaction.http.page.PageableJsonNodeMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class UserListItemRequester {

  private static final String QUERY = """
      query users($page: Page) {
        findAllUsers(page: $page) {
          content {
              nedssEntryId,
              userId,
              userFirstNm,
              userLastNm,
              recordStatusCd
          }
          total
          }
      }
      """;

  private final GraphQLRequest graphql;

  UserListItemRequester(final GraphQLRequest graphql) {
    this.graphql = graphql;
  }

  ResultActions users(final Pageable page) {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
            .set("page", PageableJsonNodeMapper.asJsonNode(page))
    );
  }

  ResultActions users() {
    return graphql.query(
        QUERY,
        JsonNodeFactory.instance.objectNode()
    );
  }
}
