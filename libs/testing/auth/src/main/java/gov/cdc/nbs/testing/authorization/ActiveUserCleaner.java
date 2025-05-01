package gov.cdc.nbs.testing.authorization;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;


@Component
class ActiveUserCleaner {

  private static final String DELETE = """
      delete from auth_user
      where auth_user_id = :id
      """;


  private final JdbcClient client;

  ActiveUserCleaner(final JdbcClient client) {

    this.client = client;
  }

  void clean(final ActiveUser user) {
    this.client.sql(DELETE)
        .param("id", user.id())
        .update();
  }

}
