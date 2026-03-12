package gov.cdc.nbs.service;

import jakarta.json.JsonObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ReportExecutionClient {
  private final RestClient client;

  public ReportExecutionClient() {
    this.client =
        RestClient.builder()
            .baseUrl("http://report-execution:8001/report/execute")
            .defaultHeader("Accept", "application/json")
            .defaultHeader("Content-Type", "application/json")
            .build();
    ;
  }

  public String executeReport(JsonObject reportSpec) {
    return client
        .post()
        .contentType(MediaType.APPLICATION_JSON)
        .body(reportSpec)
        .retrieve()
        .body(String.class);
  }
}
