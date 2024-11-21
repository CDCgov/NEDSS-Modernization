package gov.cdc.nbs.testing.classic;

import io.cucumber.spring.ScenarioScope;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.function.BiConsumer;

@Component
@ScenarioScope
public class NBS6Server {

  private final String classicUrl;
  private final MockRestServiceServer server;

  public NBS6Server(
      @Value("${nbs.wildfly.url}") final String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server
  ) {
    this.classicUrl = classicUrl;
    this.server = server;
  }

  @PostConstruct
  void shutdown() {
    this.server.reset();
  }

  public void using(final BiConsumer<MockRestServiceServer, String> consumer) {
    consumer.accept(server, classicUrl);
  }

  public void verify() {
    this.server.verify();
  }
}
