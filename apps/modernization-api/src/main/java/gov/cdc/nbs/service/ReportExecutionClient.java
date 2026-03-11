package gov.cdc.nbs.service;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ReportExecutionClient {
  RestClient reportExecutionClient =
      RestClient.builder()
          .requestFactory(new HttpComponentsClientHttpRequestFactory())
          .baseUrl("http://localhost:8001/report/execute")
          .defaultHeader("Accept", "application/json")
          .defaultHeader("Content-Type", "application/json")
          .build();
}
